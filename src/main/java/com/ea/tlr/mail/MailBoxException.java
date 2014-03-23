package com.ea.tlr.mail;
/**
 * @author eagle.daiq
 * 2014-1-6
 *
 */
public class MailBoxException extends RuntimeException {

	private static final long serialVersionUID = 1L;
	
	public MailBoxException(String msg){
		super(msg);
	}

	public MailBoxException() {
		super();
	}

	public MailBoxException(String message, Throwable cause) {
		super(message, cause);
	}

	public MailBoxException(Throwable cause) {
		super(cause);
	}
	
	
}

