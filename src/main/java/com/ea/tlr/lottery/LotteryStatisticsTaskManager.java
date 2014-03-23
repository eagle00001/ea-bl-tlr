package com.ea.tlr.lottery;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.List;
import java.util.Map.Entry;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.util.CollectionUtils;

import com.ea.bl.common.utils.DateUtils;
import com.ea.tlr.lottery.dao.LotteryDao;
import com.ea.tlr.lottery.service.LotteryRule;
import com.ea.tlr.lottery.vo.Adding2Or3ResultVo;
import com.ea.tlr.lottery.vo.Lottery;
import com.ea.tlr.lottery.vo.LotteryStatisticsContext;
import com.ea.tlr.lottery.vo.LotteryStatisticsResult;

/**
 * @comments
 * @author eagle.daiq
 * @date 2013-12-9
 */
public class LotteryStatisticsTaskManager {
	private LotteryDao lotteryDao;
	private Logger logger = LoggerFactory.getLogger(LotteryStatisticsTaskManager.class);
	private List<LotteryRule> rules=new ArrayList<LotteryRule>();
	
	public LotteryStatisticsTaskManager addRule(LotteryRule rule){
		this.rules.add(rule);
		return this;
	}
	
	public LotteryStatisticsTaskManager batchAddRules(List<LotteryRule> list){
		this.rules.addAll(list);
		return this;
	}
	
	
	public void batchCalulate2Or3StarAddingVal(String startDateStr,String endDateStr){
		Date startDate = DateUtils.toDefaultDate(startDateStr);
		Date endDate = DateUtils.toDefaultDate(endDateStr);
		LotteryStatisticsResult result=null;
		Lottery preLottery = null;
		LotteryStatisticsContext context = new LotteryStatisticsContext();
		
		while(DateUtils.subDays(endDate, startDate)>0){
			Date nextDay = org.apache.commons.lang3.time.DateUtils.addDays(startDate, 5);
			List<Lottery> lotteryLst = this.lotteryDao.getSscLotteryByDate(startDate, nextDay);
			System.out.println("Get data from db ["+DateUtils.formatDateToDefaultString(startDate)+"-"+DateUtils.formatDateToDefaultString(nextDay)+"].");
			startDate = nextDay;
			if(CollectionUtils.isEmpty(lotteryLst)){
				logger.info("the lottery datas are not found by ["+startDateStr+"-"+endDateStr+"].");
				continue;
			}
			
			
			for(Lottery lty:lotteryLst){
				context.setCurLottery(lty);
				context.setPreLottery(preLottery);
				context.setTotalStatisticsCount(context.getTotalStatisticsCount()+1);
				
				for(LotteryRule rule:this.rules){
					result = rule.executeRule(context);
				}
				preLottery = lty;
			}
		}
		
		
		
		if(result!=null){
			LotteryStatisticsContext cxt = result.getCtx();
			List<Adding2Or3ResultVo> twoRsVo = new ArrayList<Adding2Or3ResultVo>();
			List<Adding2Or3ResultVo> threeRsVo = new ArrayList<Adding2Or3ResultVo>();
			for(Entry<String, Object> entry:cxt.getCtxKeyVal().entrySet()){
				String key = entry.getKey();
				Object obj = entry.getValue();
				if(key.startsWith("2_")){
					twoRsVo.add((Adding2Or3ResultVo)obj);
				}else if(key.startsWith("3_")){
					threeRsVo.add((Adding2Or3ResultVo)obj);
				}
			}
			Collections.sort(twoRsVo, new Comparator<Adding2Or3ResultVo>() {
				@Override
				public int compare(Adding2Or3ResultVo o1, Adding2Or3ResultVo o2) {
					return (int)(o1.getCorrectRate()-o2.getCorrectRate());
				}
				
			});
			Collections.sort(threeRsVo, new Comparator<Adding2Or3ResultVo>() {
				@Override
				public int compare(Adding2Or3ResultVo o1, Adding2Or3ResultVo o2) {
					return (int)(o1.getCorrectRate()-o2.getCorrectRate());
				}
				
			});
			System.out.println("Start tow adding statistics result:------------");
			for(Adding2Or3ResultVo rsVo:twoRsVo){
				rsVo.setTotalCount(cxt.getTotalStatisticsCount());
				System.out.println(rsVo.getAdingVal()+", "+ rsVo.getRepeatCount()+", "+rsVo.getTotalCount()+", "+rsVo.getCorrectRate());
			}
			System.out.println("End tow adding statistics result:------------");
			System.out.println("Start three adding statistics result:------------");
			for(Adding2Or3ResultVo rsVo:threeRsVo){
				rsVo.setTotalCount(cxt.getTotalStatisticsCount());
				System.out.println(rsVo.getAdingVal()+", "+ rsVo.getRepeatCount()+", "+rsVo.getTotalCount()+", "+rsVo.getCorrectRate());
			}
			System.out.println("End three adding statistics result:------------");
		}
		
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
	
	
}
