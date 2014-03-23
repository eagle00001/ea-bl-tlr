package com.ea.tlr.mail;

import java.util.Properties;

import javax.mail.internet.MimeMessage;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.mail.javamail.JavaMailSenderImpl;
import org.springframework.mail.javamail.MimeMessageHelper;

/**
 * @author eagle.daiq
 * 2014-1-6
 *
 */
public class MailBoxBuilder {
	private MailBoxConfig mailBoxCfg;
	private JavaMailSenderImpl mailSenderHelper;
	private static final int DEFAULT_MAILBOX_TIMEOUT = 25000;
	private final Logger logger = LoggerFactory.getLogger(MailBoxBuilder.class);
	
	public MailBoxBuilder(MailBoxConfig mailBoxCfg){
		if(mailBoxCfg==null){
			throw new IllegalArgumentException("[mailBoxCfg] parameter must not be null.");
		}
		this.mailBoxCfg = mailBoxCfg;
		mailSenderHelper = new JavaMailSenderImpl();
		initMailBoxConfigSet();
	}

	private void initMailBoxConfigSet() {
		mailSenderHelper.setHost(this.mailBoxCfg.getHost());
		mailSenderHelper.setUsername(this.mailBoxCfg.getUsername());
		mailSenderHelper.setPassword(this.mailBoxCfg.getPassword());
		Properties javaMailProperties = this.mailBoxCfg.getJavaMailProperties();
		if(javaMailProperties==null){
			javaMailProperties = new Properties();
		}
		if(javaMailProperties.get(MailBoxConstants.SMTP_AUTH)==null){
			javaMailProperties.put(MailBoxConstants.SMTP_AUTH, true);
		}
		if(javaMailProperties.get(MailBoxConstants.SMTP_TIMEOUT)==null){
			javaMailProperties.put(MailBoxConstants.SMTP_TIMEOUT, DEFAULT_MAILBOX_TIMEOUT);
		}
		mailSenderHelper.setJavaMailProperties(javaMailProperties);
	}
	
	public static MailBoxBuilder createMailBox(MailBoxConfig mailBoxCfg){
		MailBoxBuilder builder = new MailBoxBuilder(mailBoxCfg);
		return builder;
	}
	
	/**
	 * 发送邮件
	 * @param mailMsg
	 * @throws MailBoxException
	 */
	public void sendMail(MailBoxMsg mailMsg) throws MailBoxException{
		if(mailMsg==null){
			throw new MailBoxException("[MailBoxMsg] parameter must not be null.");
		} 
		try {
			MimeMessage mime = mailSenderHelper.createMimeMessage();
			MimeMessageHelper messageHelper = null;
			messageHelper = new MimeMessageHelper(mime, true,
					"utf-8");
			messageHelper.setTo(mailMsg.getToMailAddressArr());// 接受者
			messageHelper.setFrom(mailMsg.getFromMailAddress());// 发送者
			messageHelper.setSubject(mailMsg.getSubject());// 主题
			// 邮件内容，注意加参数true
			messageHelper.setText(mailMsg.getBody(), true);
			
			logger.debug("from :"+mailMsg.getFromMailAddress());
			logger.debug("to :"+mailMsg.getToMailAddressArr());
			logger.debug(mailMsg.getBody());
			
			mailSenderHelper.send(mime);
			
			logger.debug("The mail of subject["+mailMsg.getSubject()+"] had been sent successfully.");
		} catch (Exception e) {
			logger.error("login message: [user="+mailBoxCfg.getUsername()+",password="+mailBoxCfg.getPassword()+",host="+mailBoxCfg.getHost()+"]");
			logger.error("[sender mail address:"+mailMsg.getFromMailAddress()+"],[reciever mail address:"+mailMsg.getToMailAddressArr()+"],[the subject:"+mailMsg.getSubject()+"].");
			throw new MailBoxException("send mailbox error:",e);
		}  
		
	}
	
	public static void main(String[] args){
		MailBoxConfig mailBoxCfg = new MailBoxConfig();
		mailBoxCfg.setHost("smtp.qq.com");
		mailBoxCfg.setUsername("2222@qq.com");
		mailBoxCfg.setPassword("22222");
		
		MailBoxBuilder builder = MailBoxBuilder.createMailBox(mailBoxCfg);
		
		MailBoxMsg mailMsg = new MailBoxMsg();
		mailMsg.setFromMailAddress("284956732@qq.com");
		mailMsg.setSubject("This is a test mail.");
		mailMsg.setBody("Tesfdfdfdfdt mail");
		String[] toMailAddressArr = {"fdfdfd@qq.com"};
		mailMsg.setToMailAddressArr(toMailAddressArr);
		
		builder.sendMail(mailMsg);
		
	}
	
	
}

