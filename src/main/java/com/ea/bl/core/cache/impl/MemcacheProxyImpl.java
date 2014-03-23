package com.ea.bl.core.cache.impl;

import java.util.Properties;

import net.rubyeye.xmemcached.MemcachedClient;
import net.rubyeye.xmemcached.MemcachedClientBuilder;
import net.rubyeye.xmemcached.XMemcachedClientBuilder;
import net.rubyeye.xmemcached.command.BinaryCommandFactory;
import net.rubyeye.xmemcached.impl.ElectionMemcachedSessionLocator;
import net.rubyeye.xmemcached.impl.KetamaMemcachedSessionLocator;
import net.rubyeye.xmemcached.impl.RoundRobinMemcachedSessionLocator;
import net.rubyeye.xmemcached.utils.AddrUtil;

import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.ea.bl.common.utils.FileLoadUtils;
import com.ea.bl.core.cache.CacheListener;
import com.google.code.yanf4j.core.impl.StandardSocketOption;

/**
 * @comments
 * @author eagle.daiq
 * @date 2013-4-5
 */
public class MemcacheProxyImpl extends DefaultCacheProxyAdapter {
	private static final Logger log = LoggerFactory.getLogger(MemcacheProxyImpl.class);
	private MemcachedClient client;
	private CacheListener listener;
	
	public static final String DEFAULT_MEMCACHE_PROPERTIES = "memcache.properties";
	
	public static final String SERVERADDR_KEY = "serverAddrUrl";
	public static final String BINARYPROTOCOL_KEY = "binaryProtocol";
	public static final String POOLSIZE_KEY = "poolSize";
	public static final String TIMEOUT_KEY = "timeout";
	public static final String OPTIMIZEGET_KEY = "optimizeGet";
	
	
	public MemcacheProxyImpl(String filePath,CacheListener listener){
		Properties p = FileLoadUtils.loadFileWithProperties(filePath);
		if(p==null){
			throw new RuntimeException("Init MemcacheProxy Client error: The Memcache client file can not find.[filePath="+filePath+"].");
		}
		String serverAddr = p.getProperty(SERVERADDR_KEY);
		String binaryProtocol = p.getProperty(BINARYPROTOCOL_KEY);
		String poolsize = p.getProperty(POOLSIZE_KEY);
		String timeout = p.getProperty(TIMEOUT_KEY);
		String optimizeGet = p.getProperty(OPTIMIZEGET_KEY);
		
		String socketRevBuf = p.getProperty("socketRevBuf");
		String socketSndBuf = p.getProperty("socketSndBuf");
		String sessionIdleTimeout = p.getProperty("sessionIdleTimeout");
		String hashAlgorithm = p.getProperty("hashAlgorithm");
		
		MemcachedClientBuilder builder = new XMemcachedClientBuilder(AddrUtil.getAddresses(serverAddr));
		
		if("true".equalsIgnoreCase(binaryProtocol))
			builder.setCommandFactory(new BinaryCommandFactory());
		if(StringUtils.isNotBlank(poolsize))
			builder.setConnectionPoolSize(Integer.valueOf(poolsize));
		
		if(StringUtils.isNotBlank(socketRevBuf))
			builder.setSocketOption(StandardSocketOption.SO_RCVBUF, Integer.valueOf(socketRevBuf)); // 设置接收缓存区为32K，默认16K
		if(StringUtils.isNotBlank(socketSndBuf))
			builder.setSocketOption(StandardSocketOption.SO_SNDBUF, Integer.valueOf(socketSndBuf)); // 设置发送缓冲区为16K，默认为8K
		if(StringUtils.isNotBlank(sessionIdleTimeout))
			builder.getConfiguration().setSessionIdleTimeout(Long.valueOf(sessionIdleTimeout));
		//hash算法
		if(StringUtils.isNotBlank(hashAlgorithm)){
			if("consistent".equalsIgnoreCase(hashAlgorithm)){
				builder.setSessionLocator(new KetamaMemcachedSessionLocator());
			}else if("election".equalsIgnoreCase(hashAlgorithm)){
				builder.setSessionLocator(new ElectionMemcachedSessionLocator());
			}else if("mod".equalsIgnoreCase(hashAlgorithm)){
				builder.setSessionLocator(new RoundRobinMemcachedSessionLocator());
			}
		}
		
		try {
			this.client=builder.build();
			
			if(StringUtils.isNotBlank(timeout))
				this.client.setOpTimeout(Long.valueOf(timeout));
			if("true".equalsIgnoreCase(optimizeGet))
				this.client.setOptimizeGet(true);
			
		} catch (Exception e) {
			throw new RuntimeException("create MemcacheClient failed.",e);
		}
		
		log.info("-----------MemcacheClient connection has been established-----------");
		
		this.listener=listener;
	}
	
	public MemcacheProxyImpl(String filePath){
		this(filePath,null);
	}

	/* (non-Javadoc)
	 * @see com.ea.bl.core.cache.impl.DefaultCacheProxyAdapter#putInCache(java.lang.String, java.lang.Object, int)
	 */
	@Override
	public void putInCache(String key, Object content, int expairTime) {
		paramNullCheck(key,"The localCache key is not allowed null.");
		paramNullCheck(content,"The localCache value is not allowed null.");
		
		try {
			if(this.listener!=null){
				if(this.listener.beforePutInCache(key, content, expairTime)){
					this.client.set(key, expairTime*60, content);
				}
			}else{
				this.client.set(key, expairTime*60, content);
			}
		} catch (Exception e) {
			log.error("put object to Memcache failed.[key="+key+"]",e);
		} 
	}

	/* (non-Javadoc)
	 * @see com.ea.bl.core.cache.impl.DefaultCacheProxyAdapter#getFromCache(java.lang.String)
	 */
	@Override
	public Object getFromCache(String key) {
		Object val = null;
		try {
			val = this.client.get(key);
			
			if(this.listener!=null)
				val = this.listener.afterGetFromCache(key, val);
		} catch (Exception e) {
			log.error("Get Val from Memcache failed.[key="+key+"]",e);
		} 
		return val;
	}

	/* (non-Javadoc)
	 * @see com.ea.bl.core.cache.impl.DefaultCacheProxyAdapter#removeEntry(java.lang.String)
	 */
	@Override
	public void removeEntry(String key) {
		try {
			this.client.delete(key);
			if(this.listener!=null){
				this.listener.afterRemoveEntry(key);
			}
		} catch (Exception e) {
			log.error("delete val from Memcache failed.[key="+key+"].",e);
		} 
	}

	/* (non-Javadoc)
	 * @see com.ea.bl.core.cache.impl.DefaultCacheProxyAdapter#flushAll()
	 */
	@Override
	public void flushAll() {
		try {
			this.client.flushAll();
			if(this.listener!=null)
				this.listener.afterFlushAll();
		} catch (Exception e) {
			log.error("flush all from Memcache failed.",e);
		}
	}

	/* (non-Javadoc)
	 * @see com.ea.bl.core.cache.impl.DefaultCacheProxyAdapter#flushEntry(java.lang.String)
	 */
	@Override
	public void flushEntry(String key) {
		this.removeEntry(key);
	}
	
	
}
