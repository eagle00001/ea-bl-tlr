package com.ea.bl.core.cache.impl;

import java.lang.ref.SoftReference;

import com.ea.bl.core.cache.CacheListener;
import com.ea.bl.core.cache.CacheProxy;
import com.opensymphony.oscache.base.NeedsRefreshException;
import com.opensymphony.oscache.general.GeneralCacheAdministrator;
import com.opensymphony.oscache.web.filter.ExpiresRefreshPolicy;
/**
 * 
 * @author daiqiang
 *
 */
public class LocalCacheProxyImpl extends DefaultCacheProxyAdapter implements CacheProxy {
	
	private GeneralCacheAdministrator instance;
	private CacheListener cacheListener;
	
	public static final String DEFAULT_OSCACHE_PROPERTIES = "oscache.properties";
	
	public LocalCacheProxyImpl(GeneralCacheAdministrator instance,CacheListener listener){
		this.instance = instance;
		this.cacheListener = listener;
	}
	
	public LocalCacheProxyImpl(GeneralCacheAdministrator instance){
		this(instance,null);
	}
	
	@Override
	public void putInCache(String key, Object content,int expairTime) {
		paramNullCheck(key,"The localCache key is not allowed null.");
		paramNullCheck(content,"The localCache value is not allowed null.");
		
		if(this.cacheListener!=null){
			if(this.cacheListener.beforePutInCache(key, content, expairTime))
				instance.putInCache(key, new SoftReference<Object>(content), new ExpiresRefreshPolicy(expairTime*60));
		}else{
			instance.putInCache(key, new SoftReference<Object>(content), new ExpiresRefreshPolicy(expairTime*60));
		}
		
	}

	@Override
	public Object getFromCache(String key) {
		try {
			Object obj = this.instance.getFromCache(key);
			
			if(this.cacheListener!=null){
				obj = this.cacheListener.afterGetFromCache(key, obj);
			}
			
			if(obj instanceof SoftReference){
				return ((SoftReference<?>)obj).get();
			}
			return obj;
		} catch (NeedsRefreshException e) {
			this.instance.cancelUpdate(key);
//			log.error("The key["+key+"] from cache is null.",e);
			return null;
		}
	}


	@Override
	public void removeEntry(String key) {
		this.instance.removeEntry(key);
		if(this.cacheListener!=null){
			this.cacheListener.afterRemoveEntry(key);
		}
	}

	@Override
	public void flushAll() {
		this.instance.flushAll();
		if(this.cacheListener!=null){
			this.cacheListener.afterFlushAll();
		}
	}

	@Override
	public void flushEntry(String key) {
		this.instance.flushEntry(key);
		if(this.cacheListener!=null){
			this.cacheListener.afterRemoveEntry(key);
		}
	}

}
