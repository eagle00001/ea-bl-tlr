package com.ea.tlr.http.impl;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;

import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.math.NumberUtils;
import org.apache.http.Consts;
import org.apache.http.NameValuePair;
import org.apache.http.client.HttpClient;
import org.apache.http.client.ResponseHandler;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.BasicResponseHandler;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.impl.conn.PoolingClientConnectionManager;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.params.HttpParams;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.DisposableBean;
import org.springframework.beans.factory.InitializingBean;

import com.ea.bl.common.utils.FileLoadUtils;
import com.ea.tlr.http.HttpClientService;

/**
 * @author eagle.daiq
 * 2013-11-14
 *
 */
public class HttpClientServiceImpl implements HttpClientService,InitializingBean,DisposableBean {
	private PoolingClientConnectionManager poolClientConnectionMgr;
	private HttpClient httpclient;
	private Logger logger = LoggerFactory.getLogger(HttpClientServiceImpl.class);

	/**
	 * @return the httpclient
	 */
	public HttpClient getHttpclient() {
		return httpclient;
	}

	/**
	 * @param httpclient the httpclient to set
	 */
	public void setHttpclient(HttpClient httpclient) {
		this.httpclient = httpclient;
	}

	/**
	 * @return the poolClientConnectionMgr
	 */
	public PoolingClientConnectionManager getPoolClientConnectionMgr() {
		return poolClientConnectionMgr;
	}

	/**
	 * @param poolClientConnectionMgr the poolClientConnectionMgr to set
	 */
	public void setPoolClientConnectionMgr(
			PoolingClientConnectionManager poolClientConnectionMgr) {
		this.poolClientConnectionMgr = poolClientConnectionMgr;
	}

	public String httpClientGet(String httpUrl) {
		String responseBody = null;
        try {
            HttpGet httpget = new HttpGet(httpUrl);

            // Create a response handler
            ResponseHandler<String> responseHandler = new BasicResponseHandler();
            responseBody = httpclient.execute(httpget, responseHandler);

        } catch(Exception e){
        	logger.error("httpClient [url="+httpUrl+"]调用失败:",e);
        }
        return responseBody;
	}
	

	/* (non-Javadoc)
	 * @see com.ea.tlr.http.HttpClientService#httpClientPost(java.lang.String)
	 */
	@Override
	public String httpClientPost(String httpUrl,Map<String,String> paramsValPair,Map<String,String> headerMap) {
		String responseBody = null;
        try {
            HttpPost httpPost = new HttpPost(httpUrl);
            //set parametre value pairs
            List<NameValuePair> nvpLst = new ArrayList<NameValuePair>();
            for(Entry<String, String> entry:paramsValPair.entrySet()){
            	nvpLst.add(new BasicNameValuePair(entry.getKey(), entry.getValue()));
            }
            httpPost.setEntity(new UrlEncodedFormEntity(nvpLst, Consts.UTF_8));
            
            //set header info.
            if(headerMap!=null){
            	for(Entry<String,String> headerEntry:headerMap.entrySet()){
            		httpPost.setHeader(headerEntry.getKey(), headerEntry.getValue());
            	}
            }
            // Create a response handler
            ResponseHandler<String> responseHandler = new BasicResponseHandler();
            responseBody = httpclient.execute(httpPost, responseHandler);

        } catch(Exception e){
        	logger.error("httpClient [url="+httpUrl+"]调用失败:",e);
        }
        return responseBody;
	}

	/* (non-Javadoc)
	 * @see com.ea.tlr.http.HttpClientService#httpClientGetToDoc(java.lang.String)
	 */
	@Override
	public Document httpClientGetToDoc(String httpUrl) {
		String html = this.httpClientGet(httpUrl);
		System.out.println(html);
		if(StringUtils.isBlank(html))
			return null;
		return Jsoup.parse(html);
	}

	/* (non-Javadoc)
	 * @see com.ea.tlr.http.HttpClientService#selectFromHttpDocument(java.lang.String, java.lang.String)
	 */
	@Override
	public Elements selectFromHttpDocument(String httpUrl, String selectCss) {
		Document doc = this.httpClientGetToDoc(httpUrl);
		if(doc==null)
			return null;
		return doc.select(selectCss);
	}

	public void destroy() throws Exception {
		if(httpclient!=null){
			httpclient.getConnectionManager().shutdown();
		}
	}

	public void afterPropertiesSet() throws Exception {
		initHttpConfig();
	}
	
	private HttpClient initHttpConfig(){
		httpclient = new DefaultHttpClient(poolClientConnectionMgr);
		
		Map<Object, Object> params = FileLoadUtils.loadFileWithMap("httpClient.properties");
		
		if(params!=null){
			 HttpParams httpParams = httpclient.getParams();
			 Set<Map.Entry<Object, Object>> entrySet = params.entrySet();
			 logger.info("---------start init httpClient params:---------");
			 for(Map.Entry<Object, Object> entry:entrySet){
				 String value =(String) entry.getValue();
				 Object setVal = value;
				 if(NumberUtils.isNumber(value)){
					 setVal = Integer.valueOf(value);
				 }
				 httpParams.setParameter((String)entry.getKey(), setVal);
				 logger.info("key:"+entry.getKey()+",value:"+setVal);
			 }
			 logger.info("---------end init httpClient params---------");
		}
		
		return httpclient;
	}
	
	

}

