package com.ea.tlr.lottery.vo;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;

/**
 * @comments
 * @author eagle.daiq
 * @date 2013-12-9
 */
public class LotteryStatisticsContext implements Serializable {

	private static final long serialVersionUID = -2386506939211255254L;
	
	private Lottery curLottery;
	private Lottery preLottery;
	private Map<String,Object> ctxKeyVal = new HashMap<String, Object>();
	private long totalStatisticsCount;
	
	/**
	 * @return the totalStatisticsCount
	 */
	public long getTotalStatisticsCount() {
		return totalStatisticsCount;
	}
	/**
	 * @param totalStatisticsCount the totalStatisticsCount to set
	 */
	public void setTotalStatisticsCount(long totalStatisticsCount) {
		this.totalStatisticsCount = totalStatisticsCount;
	}
	/**
	 * @return the curLottery
	 */
	public Lottery getCurLottery() {
		return curLottery;
	}
	/**
	 * @param curLottery the curLottery to set
	 */
	public void setCurLottery(Lottery curLottery) {
		this.curLottery = curLottery;
	}
	/**
	 * @return the preLottery
	 */
	public Lottery getPreLottery() {
		return preLottery;
	}
	/**
	 * @param preLottery the preLottery to set
	 */
	public void setPreLottery(Lottery preLottery) {
		this.preLottery = preLottery;
	}
	/**
	 * @return the ctxKeyVal
	 */
	public Map<String, Object> getCtxKeyVal() {
		return ctxKeyVal;
	}
	
	public void setCtxKeyVal(String key,Object val){
		this.ctxKeyVal.put(key, val);
	}
	
	public Object getCtxVal(String key){
		return this.ctxKeyVal.get(key);
	}
	

}
