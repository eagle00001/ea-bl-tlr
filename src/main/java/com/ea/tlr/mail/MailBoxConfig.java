package com.ea.tlr.mail;

import java.util.Properties;

/**
 * @author eagle.daiq
 * 2014-1-6
 *
 */
public class MailBoxConfig {
	private String username;

	private String password;
	
	private String host;
	
	private Properties javaMailProperties = new Properties();
	
	public String getHost() {
		return host;
	}

	public void setHost(String host) {
		this.host = host;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public Properties getJavaMailProperties() {
		return javaMailProperties;
	}

	public void setJavaMailProperties(Properties javaMailProperties) {
		this.javaMailProperties = javaMailProperties;
	}
	
	
}

