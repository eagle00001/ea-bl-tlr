package com.ea.tlr.lottery.service;

import com.ea.tlr.lottery.vo.LotteryStatisticsContext;
import com.ea.tlr.lottery.vo.LotteryStatisticsResult;

/**
 * @comments
 * @author eagle.daiq
 * @date 2013-12-9
 */
public interface LotteryRule {
	public LotteryStatisticsResult executeRule(LotteryStatisticsContext context);
}
