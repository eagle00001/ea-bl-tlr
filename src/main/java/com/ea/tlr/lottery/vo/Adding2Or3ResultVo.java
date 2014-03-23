package com.ea.tlr.lottery.vo;

import java.io.Serializable;

/**
 * @comments
 * @author eagle.daiq
 * @date 2013-12-9
 */
public class Adding2Or3ResultVo implements Serializable {
	private static final long serialVersionUID = -4588855420622274173L;
	private long adingVal;
	private long repeatCount;
	private long totalCount;
	/**
	 * @return the adingVal
	 */
	public long getAdingVal() {
		return adingVal;
	}
	/**
	 * @param adingVal the adingVal to set
	 */
	public void setAdingVal(long adingVal) {
		this.adingVal = adingVal;
	}
	/**
	 * @return the repeatCount
	 */
	public long getRepeatCount() {
		return repeatCount;
	}
	/**
	 * @param repeatCount the repeatCount to set
	 */
	public void setRepeatCount(long repeatCount) {
		this.repeatCount = repeatCount;
	}
	/**
	 * @return the totalCount
	 */
	public long getTotalCount() {
		return totalCount;
	}
	/**
	 * @param totalCount the totalCount to set
	 */
	public void setTotalCount(long totalCount) {
		this.totalCount = totalCount;
	}

	public long getCorrectRate(){
		double d = Double.valueOf(this.repeatCount*100)/Double.valueOf(this.totalCount);
		return (long)d;
	}
}
