package com.ea.bl.core.cache;

import com.ea.bl.core.cache.impl.MemcacheProxyImpl;

/**
 * @comments
 * @author eagle.daiq
 * @date 2013-4-5
 */
public class MemcacheProxyFactory {
	private static CacheProxy cache;
	
	private MemcacheProxyFactory(){
		
	}
	
	public synchronized static CacheProxy initMemcacheProxy(String filePath,CacheListener listener){
		cache = new MemcacheProxyImpl(filePath,listener);
		return cache;
	}
	
	public static CacheProxy getMemcache(){
		if(cache==null)
			initMemcacheProxy(MemcacheProxyImpl.DEFAULT_MEMCACHE_PROPERTIES,null);
		return cache;
	}
}
