package com.ea.bl.core.cache;

/**
 * 缓存监听器
 * @author eagle.daiq
 * @date 2013-4-5
 */
public interface CacheListener {
	/**
	 * 在对象加入缓存之前事件处理。返回true表示将对象放入缓存，false改对象不加入到缓存
	 * @param key
	 * @param val
	 * @param expairTime
	 * @return
	 */
	public boolean beforePutInCache(String key,Object val, int expairTime);
	
	/**
	 * 对返回缓存对象事件处理
	 * @param key
	 * @param val	缓存中获取的对象
	 * @return
	 */
	public Object afterGetFromCache(String key,Object val);
	
	public void afterRemoveEntry(String key);
	
	public void afterFlushAll();
}
