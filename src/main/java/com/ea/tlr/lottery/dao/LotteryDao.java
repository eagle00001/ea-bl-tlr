package com.ea.tlr.lottery.dao;

import java.util.Date;
import java.util.List;

import com.ea.tlr.lottery.vo.Lottery;

/**
 * @comments
 * @author eagle.daiq
 * @date 2013-12-1
 */
public interface LotteryDao {
	/**
	 * 
	 * @param startDate
	 * @param endDate
	 * @return
	 */
	public List<Lottery> getSscLotteryByDate(Date startDate,Date endDate);
	/**
	 * insert lottery for ssc
	 * @param sscLotteryList
	 * @return
	 */
	public boolean insertSscLottery(List<Lottery> sscLotteryList);
}
