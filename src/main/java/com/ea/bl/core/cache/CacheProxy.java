package com.ea.bl.core.cache;


/**
 * 
 * @author daiqiang
 *
 */
public interface CacheProxy {
	/**
	 * 加入缓存
	 * @param key
	 * @param content
	 * @param expairTime
	 */
	public void putInCache(String key, Object content,int expairTime);
	
	/**
	 * 取缓存对象
	 * @param key
	 * @return
	 */
	public Object getFromCache(String key);
	
	/**
	 * 删除缓存
	 * @param key
	 */
	public void removeEntry(String key);
	
	/**
	 * 清空所有缓存
	 */
	public void flushAll();
	
	/**
	 * 清除指定缓存
	 * @param key
	 */
	public void flushEntry(String key);
}
