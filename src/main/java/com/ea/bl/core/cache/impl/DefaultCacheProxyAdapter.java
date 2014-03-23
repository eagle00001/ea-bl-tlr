package com.ea.bl.core.cache.impl;

import com.ea.bl.core.cache.CacheProxy;

/**
 * @comments
 * @author eagle.daiq
 * @date 2013-4-5
 */
public class DefaultCacheProxyAdapter implements CacheProxy {

	protected void paramNullCheck(Object obj,String tips){
		if(obj == null)
			throw new IllegalArgumentException(tips);
	}
	
	/* (non-Javadoc)
	 * @see com.ea.bl.core.cache.CacheProxy#putInCache(java.lang.String, java.lang.Object, int)
	 */
	@Override
	public void putInCache(String key, Object content, int expairTime) {

	}

	/* (non-Javadoc)
	 * @see com.ea.bl.core.cache.CacheProxy#getFromCache(java.lang.String)
	 */
	@Override
	public Object getFromCache(String key) {
		return null;
	}

	/* (non-Javadoc)
	 * @see com.ea.bl.core.cache.CacheProxy#removeEntry(java.lang.String)
	 */
	@Override
	public void removeEntry(String key) {

	}

	/* (non-Javadoc)
	 * @see com.ea.bl.core.cache.CacheProxy#flushAll()
	 */
	@Override
	public void flushAll() {

	}

	/* (non-Javadoc)
	 * @see com.ea.bl.core.cache.CacheProxy#flushEntry(java.lang.String)
	 */
	@Override
	public void flushEntry(String key) {

	}

}
