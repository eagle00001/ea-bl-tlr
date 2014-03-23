package com.ea.tlr.lottery.service;

import org.apache.commons.lang3.math.NumberUtils;

import com.ea.tlr.lottery.vo.Adding2Or3ResultVo;
import com.ea.tlr.lottery.vo.Lottery;
import com.ea.tlr.lottery.vo.LotteryStatisticsContext;
import com.ea.tlr.lottery.vo.LotteryStatisticsResult;

/**
 * @comments
 * @author eagle.daiq
 * @date 2013-12-9
 */
public class Adding2Or3LotteryRule implements LotteryRule {

	/* (non-Javadoc)
	 * @see com.ea.tlr.lottery.service.LotteryRule#executeRule(com.ea.tlr.lottery.vo.LotteryStatisticsContext)
	 */
	@Override
	public LotteryStatisticsResult executeRule(LotteryStatisticsContext context) {
		Lottery cur = context.getCurLottery();
		String number = cur.getLotteryNumber();
		int threeAdding = 0;
		int towAdding = this.getNumberByIndex(0, number)+this.getNumberByIndex(1, number);
		threeAdding = this.getNumberByIndex(2, number)+towAdding;
		
		Adding2Or3ResultVo vo2 = (Adding2Or3ResultVo)context.getCtxVal("2_"+towAdding);
		Adding2Or3ResultVo vo3 = (Adding2Or3ResultVo)context.getCtxVal("3_"+threeAdding);
		
		if(vo2!=null){
			vo2.setTotalCount(context.getTotalStatisticsCount());
			vo2.setRepeatCount(vo2.getRepeatCount()+1);
		}else{
			Adding2Or3ResultVo tmpVo2 = new Adding2Or3ResultVo();
			tmpVo2.setTotalCount(context.getTotalStatisticsCount());
			tmpVo2.setRepeatCount(1);
			tmpVo2.setAdingVal(towAdding);
			context.setCtxKeyVal("2_"+towAdding, tmpVo2);
		}
		
		if(vo3!=null){
			vo3.setTotalCount(context.getTotalStatisticsCount());
			vo3.setRepeatCount(vo3.getRepeatCount()+1);
		}else{
			Adding2Or3ResultVo tmpVo3 = new Adding2Or3ResultVo();
			tmpVo3.setTotalCount(context.getTotalStatisticsCount());
			tmpVo3.setRepeatCount(1);
			tmpVo3.setAdingVal(threeAdding);
			context.setCtxKeyVal("3_"+threeAdding, tmpVo3);
		}
		
		LotteryStatisticsResult result = new LotteryStatisticsResult();
		result.setCtx(context);
		
		return result;
	}
	
	private int getNumberByIndex(int index,String number){
		int len = number.length();
		String tmpStr = number.substring(len-index-1, len-index);
		return NumberUtils.toInt(tmpStr);
	}
	
	public static void main(String[] args){
		Adding2Or3LotteryRule rule = new Adding2Or3LotteryRule();
		System.out.println(rule.getNumberByIndex(0, "2324309"));
		System.out.println(rule.getNumberByIndex(1, "2324309"));
		System.out.println(rule.getNumberByIndex(2, "2324309"));
	}

}
