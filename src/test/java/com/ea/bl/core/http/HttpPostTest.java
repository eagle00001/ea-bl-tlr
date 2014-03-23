package com.ea.bl.core.http;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import com.alibaba.fastjson.JSON;
import com.ea.tlr.http.HttpClientService;

/**
 * @comments
 * @author eagle.daiq
 * @date 2013-12-1
 */
public class HttpPostTest {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		ClassPathXmlApplicationContext cxt = new ClassPathXmlApplicationContext("applicationContext-test.xml");
		HttpClientService httpClient = (HttpClientService)cxt.getBean("httpClientRequestService");
		String httpUrl = "http://data.shishicai.cn/handler/kuaikai/data.ashx";
		Map<String,String> params = new HashMap<String,String>();
		params.put("lottery", "4");
		params.put("date", "2013-11-01");
		
		Map<String,String> headerMap = new HashMap<String,String>();
		headerMap.put("Referer", "http://data.shishicai.cn/cqssc/haoma/2013-11-23/");
		headerMap.put("Cookie", "Hm_lvt_cad2e9c6544a1e8f06862d019ce44f71=1385257648,1385806261,1385886564,1385903661; Html_v_54=1641; ssc_user_LandingPage=http%3a%2f%2fdata.shishicai.cn%2fcqssc%2fhaoma%2f2013-11-22%2f; ssc_user_RegEnterPage=http%3a%2f%2fdata.shishicai.cn%2fcqssc%2fhaoma%2f2013-11-23%2f; Hm_lpvt_cad2e9c6544a1e8f06862d019ce44f71=1385903661");
		
		String httpResp = httpClient.httpClientPost(httpUrl, params,headerMap);
		if(StringUtils.isBlank(httpResp)){
			System.out.println("no response...");
		}else{
			List<String> contentLst = JSON.parseArray(httpResp,String.class);
			for(String content:contentLst){
				System.out.println(content);
			}
//			System.out.println(httpResp);
		}
	}

}
