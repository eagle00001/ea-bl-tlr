package com.ea.bl.core.common;

import javax.crypto.SecretKey;

import com.ea.bl.common.utils.EncryptionUtils;
import com.ea.bl.core.datasource.DataSourceProxy;

public class CommonUtilTest {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		String pwd = "xewers#df";
		SecretKey key = EncryptionUtils.createDESSecretKey(pwd);
//		String pwd = "hello";
		String encryptPwd = EncryptionUtils.encryptToDES(key, pwd);
		System.out.println(encryptPwd);
		System.out.println(EncryptionUtils.decryptByDES(key, encryptPwd));
		
		System.out.println("--------------");
		SecretKey dataSouceKey = EncryptionUtils.createDESSecretKey(DataSourceProxy.DEFAULT_DES_DATASOURCE_KEY);
		String dsPwd = EncryptionUtils.encryptToDES(dataSouceKey,pwd);
		System.out.println(dsPwd);
		System.out.println(EncryptionUtils.decryptByDES(dataSouceKey,dsPwd));
	}

}
