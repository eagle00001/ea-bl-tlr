package com.ea.tlr.lottery.vo;

import java.io.Serializable;

/**
 * @comments
 * @author eagle.daiq
 * @date 2013-12-9
 */
public class LotteryStatisticsResult implements Serializable {

	private static final long serialVersionUID = -4784306404261727785L;
	
	private LotteryStatisticsContext ctx;
	private long correctRate;
	

	/**
	 * @return the correctRate
	 */
	public long getCorrectRate() {
		return correctRate;
	}

	/**
	 * @param correctRate the correctRate to set
	 */
	public void setCorrectRate(long correctRate) {
		this.correctRate = correctRate;
	}

	/**
	 * @return the ctx
	 */
	public LotteryStatisticsContext getCtx() {
		return ctx;
	}

	/**
	 * @param ctx the ctx to set
	 */
	public void setCtx(LotteryStatisticsContext ctx) {
		this.ctx = ctx;
	}
	
	

}
