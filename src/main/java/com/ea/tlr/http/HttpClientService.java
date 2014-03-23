package com.ea.tlr.http;

import java.util.Map;

import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;

/**
 * @author eagle.daiq
 * 2013-11-14
 *
 */
public interface HttpClientService {
	/**
	 * 
	 * @param httpUrl
	 * @return
	 */
	public String httpClientPost(String httpUrl,Map<String,String> paramsValPair,Map<String,String> headerMap);
	/**
	 * 通过http get请求地址
	 * @param httpUrl
	 * @return
	 */
	public String httpClientGet(String httpUrl);
	
	/**
	 * 将返回http结果转化成jsoup文档对象
	 * @param httpUrl
	 * @return
	 */
	public Document httpClientGetToDoc(String httpUrl);
	
	/**
	 * 将返回http html转化成按指定css查询条件返回的结果
	 * @param httpUrl
	 * @param selectCssQuery
	 * @return
	 */
	public Elements selectFromHttpDocument(String httpUrl,String selectCssQuery);
}

