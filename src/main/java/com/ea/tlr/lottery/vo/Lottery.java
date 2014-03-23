package com.ea.tlr.lottery.vo;

import java.io.Serializable;
import java.util.Date;

/**
 * @comments
 * @author eagle.daiq
 * @date 2013-12-1
 */
public class Lottery implements Serializable {

	private static final long serialVersionUID = 8169467855605503947L;
	public static final Integer TYPE_SSC = 1;
	
	private long id;
	private Date date;
	private Integer type;
	private String serialNumber;
	private String lotteryNumber;
	private Date createTime;
	/**
	 * @return the id
	 */
	public long getId() {
		return id;
	}
	/**
	 * @param id the id to set
	 */
	public void setId(long id) {
		this.id = id;
	}
	/**
	 * @return the date
	 */
	public Date getDate() {
		return date;
	}
	/**
	 * @param date the date to set
	 */
	public void setDate(Date date) {
		this.date = date;
	}
	/**
	 * @return the type
	 */
	public Integer getType() {
		return type;
	}
	/**
	 * @param type the type to set
	 */
	public void setType(Integer type) {
		this.type = type;
	}
	
	/**
	 * @return the serialNumber
	 */
	public String getSerialNumber() {
		return serialNumber;
	}
	/**
	 * @param serialNumber the serialNumber to set
	 */
	public void setSerialNumber(String serialNumber) {
		this.serialNumber = serialNumber;
	}
	/**
	 * @return the lotteryNumber
	 */
	public String getLotteryNumber() {
		return lotteryNumber;
	}
	/**
	 * @param lotteryNumber the lotteryNumber to set
	 */
	public void setLotteryNumber(String lotteryNumber) {
		this.lotteryNumber = lotteryNumber;
	}
	/**
	 * @return the createTime
	 */
	public Date getCreateTime() {
		return createTime;
	}
	/**
	 * @param createTime the createTime to set
	 */
	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}
	
	

}
