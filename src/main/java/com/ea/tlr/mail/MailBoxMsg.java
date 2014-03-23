package com.ea.tlr.mail;

import java.io.Serializable;

/**
 * @author eagle.daiq
 * 2014-1-6
 *
 */
public class MailBoxMsg implements Serializable {

	private static final long serialVersionUID = 1L;
	
	private String subject;			//邮件主题
	private String body;			//邮件内容
	private String fromMailAddress;	//发送邮件地址
	private String[] toMailAddressArr; //接收多个邮件地址
	public String getSubject() {
		return subject;
	}
	public void setSubject(String subject) {
		this.subject = subject;
	}
	public String getBody() {
		return body;
	}
	public void setBody(String body) {
		this.body = body;
	}
	public String getFromMailAddress() {
		return fromMailAddress;
	}
	public void setFromMailAddress(String fromMailAddress) {
		this.fromMailAddress = fromMailAddress;
	}
	public String[] getToMailAddressArr() {
		return toMailAddressArr;
	}
	public void setToMailAddressArr(String[] toMailAddressArr) {
		this.toMailAddressArr = toMailAddressArr;
	}
	
	
}

