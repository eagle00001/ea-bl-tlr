package com.ea.bl.core.cache;

import com.ea.bl.core.cache.impl.MemcacheProxyImpl;

/**
 * @comments
 * @author Administrator
 * @date 2013-4-5
 */
public class CacheTest {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
//		oscacheTest();
		memcachTest();
	}
	
	private static void memcachTest(){
//		CacheProxy proxy = MemcacheProxyFactory.initMemcacheProxy(MemcacheProxyImpl.DEFAULT_MEMCACHE_PROPERTIES, new MemcacheListenerTest());
		CacheProxy proxy = MemcacheProxyFactory.initMemcacheProxy(MemcacheProxyImpl.DEFAULT_MEMCACHE_PROPERTIES, null);
		String cacheKey = "memcachKey";
		String cacheVal = "this is a memcache Val test.";
		int expairTime = 1;
		
		proxy.putInCache(cacheKey, cacheVal, expairTime);
		System.out.println("测试结果------------memcach val is :"+proxy.getFromCache(cacheKey));
		proxy.removeEntry(cacheKey);
		System.out.println("删除key后:"+proxy.getFromCache(cacheKey));
	}
	
	private static void oscacheTest(){
//		LocalCacheFactory.initLocalCache("oscache.properties");
		LocalCacheFactory.initLocalCache("E:\\working\\projects\\ea-bl-core\\target\\classes\\oscache.properties",null);
		CacheProxy cache = LocalCacheFactory.getLocalCache();
		String localCacheKey = "LocalCacheKey";
		String localCacheVal = "this is a localCache Val test.";
		int expairTime = 1;
		cache.putInCache(localCacheKey, localCacheVal, expairTime);
		
		System.out.println("Get Cache val:"+cache.getFromCache(localCacheKey));
		cache.removeEntry(localCacheKey);
		System.out.println("删除key后:"+cache.getFromCache(localCacheKey));
	}

}
