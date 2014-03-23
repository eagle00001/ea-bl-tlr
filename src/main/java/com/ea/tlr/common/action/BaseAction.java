package com.ea.tlr.common.action;

import com.ea.tlr.common.vo.JsonResult;
import com.opensymphony.xwork2.Action;

/**
 * @comments
 * @author eagle.daiq
 * @date 2013-12-1
 */
public class BaseAction implements Action {

	/* (non-Javadoc)
	 * @see com.opensymphony.xwork2.Action#execute()
	 */
	@Override
	public String execute() throws Exception {
		return null;
	}
	
	protected JsonResult jsonResult;
	
	protected void generateJsonResult(int code, String message, Object data) {
		jsonResult = new JsonResult();
		jsonResult.setCode(code);
		jsonResult.setMessage(message);
		jsonResult.setData(data);
	}

	/**
	 * @return the jsonResult
	 */
	public JsonResult getJsonResult() {
		return jsonResult;
	}

	/**
	 * @param jsonResult the jsonResult to set
	 */
	public void setJsonResult(JsonResult jsonResult) {
		this.jsonResult = jsonResult;
	}
	
	

}
