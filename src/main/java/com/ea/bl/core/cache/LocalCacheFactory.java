/**
 * 
 */
package com.ea.bl.core.cache;

import java.util.Properties;

import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.ea.bl.common.utils.FileLoadUtils;
import com.ea.bl.core.cache.impl.LocalCacheProxyImpl;
import com.opensymphony.oscache.general.GeneralCacheAdministrator;


/**
 * @author daiqiang
 * 本地缓存工厂类
 */
public class LocalCacheFactory {
	private static final Logger log = LoggerFactory.getLogger(LocalCacheFactory.class);
	private static CacheProxy cache = null;
	
	private LocalCacheFactory(){
	}
	
	public synchronized static CacheProxy initLocalCache(String filePath,CacheListener listener){
		log.info("Begin to initialize the localCache...");
		
		Properties p = null;
		
		if(StringUtils.isNotBlank(filePath)){
			p = FileLoadUtils.loadFileWithProperties(filePath);
		}
		
		GeneralCacheAdministrator osCacheAdmin = new GeneralCacheAdministrator(p);
		cache = new LocalCacheProxyImpl(osCacheAdmin,listener);
		
		log.info("The localCache initialized successful.");
		return cache;
	}
	
	public static CacheProxy getLocalCache(){
		if(cache==null){
			initLocalCache(LocalCacheProxyImpl.DEFAULT_OSCACHE_PROPERTIES,null);
		}
		return cache;
	}
}
