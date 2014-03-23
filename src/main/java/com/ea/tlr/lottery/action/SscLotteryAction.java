package com.ea.tlr.lottery.action;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang.StringUtils;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.alibaba.fastjson.JSON;
import com.ea.bl.common.utils.DateUtils;
import com.ea.tlr.common.action.BaseAction;
import com.ea.tlr.http.HttpClientService;
import com.ea.tlr.lottery.LotteryStatisticsTaskManager;
import com.ea.tlr.lottery.ReportChartHelper;
import com.ea.tlr.lottery.dao.LotteryDao;
import com.ea.tlr.lottery.service.Adding2Or3LotteryRule;
import com.ea.tlr.lottery.service.LotteryRule;
import com.ea.tlr.lottery.vo.Lottery;
import com.opensymphony.xwork2.Action;

/**
 * @comments重庆时时彩action
 * @author eagle.daiq
 * @date 2013-12-1
 */
public class SscLotteryAction extends BaseAction {

	private static final long serialVersionUID = -1767347458292328573L;
	private HttpClientService httpClientRequestService;
	private LotteryDao lotteryDao;
	private String startDate;
	private String endDate;
	private Logger logger = LoggerFactory.getLogger(SscLotteryAction.class);
	
	/**
	 * @return the startDate
	 */
	public String getStartDate() {
		return startDate;
	}

	/**
	 * @param startDate the startDate to set
	 */
	public void setStartDate(String startDate) {
		this.startDate = startDate;
	}

	/**
	 * @return the endDate
	 */
	public String getEndDate() {
		return endDate;
	}

	/**
	 * @param endDate the endDate to set
	 */
	public void setEndDate(String endDate) {
		this.endDate = endDate;
	}

	/**
	 * @return the httpClientRequestService
	 */
	public HttpClientService getHttpClientRequestService() {
		return httpClientRequestService;
	}

	/**
	 * @param httpClientRequestService the httpClientRequestService to set
	 */
	public void setHttpClientRequestService(
			HttpClientService httpClientRequestService) {
		this.httpClientRequestService = httpClientRequestService;
	}

	/**
	 * @return the lotteryDao
	 */
	public LotteryDao getLotteryDao() {
		return lotteryDao;
	}

	/**
	 * @param lotteryDao the lotteryDao to set
	 */
	public void setLotteryDao(LotteryDao lotteryDao) {
		this.lotteryDao = lotteryDao;
	}

	/* (non-Javadoc)
	 * @see com.opensymphony.xwork2.ActionSupport#execute()
	 */
	@Override
	public String execute() throws Exception {
		// TODO Auto-generated method stub
		return super.execute();
	}
	
	/**
	 * 分析历史数据统计信息
	 * @return
	 */
	public String analyseHistoryDatas(){
		try {
			Adding2Or3LotteryRule rule = new Adding2Or3LotteryRule();
			LotteryStatisticsTaskManager taskMgr = new LotteryStatisticsTaskManager();
			taskMgr.addRule(rule);
			taskMgr.setLotteryDao(this.lotteryDao);
			
			taskMgr.batchCalulate2Or3StarAddingVal("2013-01-01", "2013-11-30");
			this.generateJsonResult(1, "analyse datas finished", null);
		} catch (Exception e) {
			System.out.println("error happened:"+e.getMessage());
		}
		
		return Action.SUCCESS;
	}
	
	private int postion = 1;
	
	/**
	 * @return the postion
	 */
	public int getPostion() {
		return postion;
	}

	/**
	 * @param postion the postion to set
	 */
	public void setPostion(int postion) {
		this.postion = postion;
	}

	/**
	 * 创建曲线图报表
	 * @return
	 */
	public String createLineChartReport(){
		if(StringUtils.isBlank(this.startDate) || StringUtils.isBlank(this.endDate)){
			this.generateJsonResult(0, "开始结束时间不能为空", null);
			return Action.ERROR;
		}
		ReportChartHelper chartHelper =  ReportChartHelper.getInstance();
		chartHelper.setLotteryDao(this.lotteryDao);
		String chartFile = chartHelper.generateLineChartByPosition(this.startDate, this.endDate,this.postion);
		this.generateJsonResult(1, chartFile, null);
		return Action.SUCCESS;
	}
	
	/**
	 * 导入历史数据
	 * @return
	 */
	public String importHistoryDatas(){
		if(StringUtils.isBlank(this.startDate) || StringUtils.isBlank(this.endDate)){
			this.generateJsonResult(0, "导入数据的开始结束时间不能为空", null);
			return Action.ERROR;
		}
			
		
		String httpUrlPrefix = "http://data.shishicai.cn/handler/kuaikai/data.ashx";
		String curDateStr = this.startDate;
		Date curDate = DateUtils.toDefaultDate(curDateStr);
		String endDateStr = this.endDate;
		Date endDate = DateUtils.toDefaultDate(endDateStr);
		while(DateUtils.subDays(endDate, curDate)>0){
			Map<String,String> params = new HashMap<String,String>();
			params.put("lottery", "4");
			params.put("date", curDateStr);
			
			Map<String,String> headerMap = new HashMap<String,String>();
			headerMap.put("Referer", "http://data.shishicai.cn/cqssc/haoma/2013-11-23/");
			headerMap.put("Cookie", "Hm_lvt_cad2e9c6544a1e8f06862d019ce44f71=1385257648,1385806261,1385886564,1385903661; Html_v_54=1641; ssc_user_LandingPage=http%3a%2f%2fdata.shishicai.cn%2fcqssc%2fhaoma%2f2013-11-22%2f; ssc_user_RegEnterPage=http%3a%2f%2fdata.shishicai.cn%2fcqssc%2fhaoma%2f2013-11-23%2f; Hm_lpvt_cad2e9c6544a1e8f06862d019ce44f71=1385903661");
			
			//get html datas
			String htmlConent = this.httpClientRequestService.httpClientPost(httpUrlPrefix, params, headerMap);
			if(StringUtils.isBlank(htmlConent)){
				logger.error("------------can't get datas from "+curDateStr);
				break;
			}
//			System.out.println(htmlConent);
			List<String> htmlLst=null;
			try {
				htmlLst = JSON.parseArray(htmlConent,String.class);
			} catch (Exception e) {
				logger.error("json parse error:",e);
				e.printStackTrace();
			}
			List<Lottery> entries = new ArrayList<Lottery>();
			for(String content:htmlLst){
				
				String[] arrayStr = content.split(";");
				String serialNumber = arrayStr[0].split("-")[1];
				String lotteryNumber = arrayStr[1];
				Lottery entry = new Lottery();
				entry.setDate(curDate);
				entry.setSerialNumber(serialNumber);
				entry.setLotteryNumber(lotteryNumber);
				entry.setType(Lottery.TYPE_SSC);
				
				entries.add(entry);
				
			}
			//save datas to DB
			this.lotteryDao.insertSscLottery(entries);
			logger.info("---import data successed by "+curDateStr);
			System.out.println("---import data successed by "+curDateStr);
			
			//set nextday to curDay in loop.
			curDate = org.apache.commons.lang3.time.DateUtils.addDays(curDate, 1);
			curDateStr = DateUtils.formatDateToDefaultString(curDate);
		}
		
		this.generateJsonResult(1, "it is successful to import datas by ["+this.startDate+"-"+this.endDate+"].", null);
		
		return Action.SUCCESS;
	}
	
	private List<Lottery> processHtmlElementsToEntries(Elements elements){
		if(elements==null)
			return null;
		List<Lottery> lotteryLst = new ArrayList<Lottery>(120);
		Elements tableEles = elements.select("table .data_tab");
		for(int i=0;i<tableEles.size();i++){
			Element element = tableEles.get(i);
			Elements trEles = element.select("tbody tr");
			for(int j=0;j<trEles.size();j++){
				Element trEle = trEles.get(j);
				lotteryLst.add(convertTrElementToLottery(trEle));
			}
		}
		return lotteryLst;
	}
	
	private Lottery convertTrElementToLottery(Element trEle){
		Elements eles = trEle.select("td");
		String serialNumber = eles.get(0).html();
		String dateStr = eles.get(0).attr("key").split("-")[0];
		Element lotteryNumberEle = eles.get(1);
		List<Element> eleLst = lotteryNumberEle.children();
		StringBuilder lotteryNumberSb = new StringBuilder();
		for(Element ele:eleLst){
			lotteryNumberSb.append(ele.html());
		}
		
		Lottery lottery = new Lottery();
		lottery.setSerialNumber(serialNumber);
		lottery.setLotteryNumber(lotteryNumberSb.toString());
		lottery.setType(Lottery.TYPE_SSC);
		lottery.setDate(DateUtils.toDefaultDate(dateStr));
		return lottery;
	}
	
}
