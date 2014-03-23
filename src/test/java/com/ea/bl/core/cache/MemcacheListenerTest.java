package com.ea.bl.core.cache;

/**
 * @comments
 * @author eagle.daiq
 * @date 2013-4-5
 */
public class MemcacheListenerTest implements CacheListener {

	/* (non-Javadoc)
	 * @see com.ea.bl.core.cache.CacheListener#beforePutInCache(java.lang.String, java.lang.Object, int)
	 */
	@Override
	public boolean beforePutInCache(String key, Object val, int expairTime) {
		if(val instanceof String)
			if(((String)val).length()>5){
				System.out.println("保存对象长度过大不放入缓存");
				return false;
			}
		return true;
	}

	/* (non-Javadoc)
	 * @see com.ea.bl.core.cache.CacheListener#afterGetFromCache(java.lang.String, java.lang.Object)
	 */
	@Override
	public Object afterGetFromCache(String key, Object val) {
		// TODO Auto-generated method stub
		return val;
	}

	/* (non-Javadoc)
	 * @see com.ea.bl.core.cache.CacheListener#afterRemoveEntry(java.lang.String)
	 */
	@Override
	public void afterRemoveEntry(String key) {
		// TODO Auto-generated method stub
		System.out.println("onfire remove entry. key="+key);
	}

	/* (non-Javadoc)
	 * @see com.ea.bl.core.cache.CacheListener#afterFlushAll()
	 */
	@Override
	public void afterFlushAll() {
		// TODO Auto-generated method stub

	}

}
