package com.ea.tlr.common.vo;

import java.io.Serializable;

/**
 * @comments
 * @author eagle.daiq
 * @date 2013-12-1
 */
public class JsonResult implements Serializable {

	private static final long serialVersionUID = 5568474268546384814L;
	private Integer code;
    private String message;
    private Object data;
    public JsonResult() {
    }
    public JsonResult(Integer code, String message) {
        this.code = code;
        this.message = message;
    }
	/**
	 * @return the code
	 */
	public Integer getCode() {
		return code;
	}
	/**
	 * @param code the code to set
	 */
	public void setCode(Integer code) {
		this.code = code;
	}
	/**
	 * @return the message
	 */
	public String getMessage() {
		return message;
	}
	/**
	 * @param message the message to set
	 */
	public void setMessage(String message) {
		this.message = message;
	}
	/**
	 * @return the data
	 */
	public Object getData() {
		return data;
	}
	/**
	 * @param data the data to set
	 */
	public void setData(Object data) {
		this.data = data;
	}
    
    

}
