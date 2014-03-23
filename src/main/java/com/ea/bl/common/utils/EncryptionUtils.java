package com.ea.bl.common.utils;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;

import javax.crypto.Cipher;
import javax.crypto.KeyGenerator;
import javax.crypto.SecretKey;

import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import sun.misc.BASE64Decoder;
import sun.misc.BASE64Encoder;

/**
 * 
 * @author eagle.daiq
 *
 */
public class EncryptionUtils {
	private static final Logger log = LoggerFactory.getLogger(EncryptionUtils.class);
	
	/*加密算法*/
	public static final String ENCRYPT_ALGORTHM_DES="DES";
	public static final String ENCRYPT_ALGORTHM_MD5="MD5";

	public static void main(String[] args){
		String md5Src = "md5testString";
		System.out.println(MD5(md5Src));
	}
	/**
	 * MD5加密
	 * @param str
	 * @return
	 */
	public static String MD5(String str){
		if(StringUtils.isBlank(str))
			return str;
		String value = null;		
		try {
			MessageDigest md5 = null;
			md5 = MessageDigest.getInstance(ENCRYPT_ALGORTHM_MD5);
			sun.misc.BASE64Encoder baseEncoder = new sun.misc.BASE64Encoder();
			value = baseEncoder.encode(md5.digest(str.getBytes("utf-8")));
		}catch (Exception e) {
			log.error("MD5 Encode error for ["+str+"].",e);				
		}		
		return value;	
	}
	
	/**
	* 创建密匙
	* 
	* @param algorithm
	*            加密算法,可用 DES,DESede,Blowfish
	* @return SecretKey 秘密（对称）密钥
	*/
	public static SecretKey createSecretKey(String algorithm,String strSrc) {
		// 声明KeyGenerator对象
		KeyGenerator keygen;
		// 声明 密钥对象
		SecretKey deskey = null;
		try {
			// 返回生成指定算法的秘密密钥的 KeyGenerator 对象
			keygen = KeyGenerator.getInstance(algorithm);
			if(StringUtils.isNotBlank(strSrc)){
				keygen.init(new SecureRandom(strSrc.getBytes()));
			}
			// 生成一个密钥
			deskey = keygen.generateKey();
		} catch (NoSuchAlgorithmException e) {
			log.error("It is failed to create secretKey.",e);
		}
		// 返回密匙
		return deskey;
	}
	/**
	 * 创建DES加密key 
	 * @return
	 */
	public static SecretKey createDESSecretKey(String strSrc){
		return createSecretKey(ENCRYPT_ALGORTHM_DES,strSrc);
	}
	/**
	 * 创建DES加密key 
	 * 随机生成key.每次生成key会不同
	 * @see EncryptionUtils#createDESSecretKey(String)
	 * @return
	 */
	public static SecretKey createDESSecretKeyRandom(){
		return createSecretKey(ENCRYPT_ALGORTHM_DES,null);
	}
	/**
	 * 根据密匙进行DES加密
	 * 
	 * @param key
	 *            密匙
	 * @param info
	 *            要加密的信息
	 * @return String 加密后的信息
	 */
	public static String encryptToDES(SecretKey key, String info) {
		byte[] byteMi = null;
		byte[] byteMing = null;
		String strMi = "";
		BASE64Encoder base64en = new BASE64Encoder();
		try {
			byteMing = info.getBytes("UTF8");
			byteMi = encryptByte(byteMing,key);
			strMi = base64en.encode(byteMi);
		} catch (Exception e) {
			throw new RuntimeException(
					"Error encryptToDES. Cause: " + e);
		} finally {
			base64en = null;
			byteMing = null;
			byteMi = null;
		}
		return strMi;
	}
	
	/**
	 * 根据密匙进行DES解密
	 * 
	 * @param key
	 *            密匙
	 * @param sInfo
	 *            要解密的密文
	 * @return String 返回解密后信息
	 */
	public static String decryptByDES(SecretKey key, String sInfo) {
		
		BASE64Decoder base64De = new BASE64Decoder();
		byte[] byteMing = null;
		byte[] byteMi = null;
		String strMing = "";
		try {
			byteMi = base64De.decodeBuffer(sInfo);
			byteMing = decryptByte(byteMi,key);
			strMing = new String(byteMing, "UTF8");
		} catch (Exception e) {
			throw new RuntimeException(
					"Error decryptByDES. Cause: " + e);
		} finally {
			base64De = null;
			byteMing = null;
			byteMi = null;
		}
		return strMing;
	}
	
	/** 
     * 加密以 byte[] 明文输入 ,byte[] 密文输出 
     * 
     * @param byteS 
     * @return 
     */  
	private static byte[] encryptByte(byte[] byteS, SecretKey key) {
		byte[] byteFina = null;
		Cipher cipher;
		try {
			cipher = Cipher.getInstance(ENCRYPT_ALGORTHM_DES);
			cipher.init(Cipher.ENCRYPT_MODE, key);
			byteFina = cipher.doFinal(byteS);
		} catch (Exception e) {
			throw new RuntimeException("Error encypted with ["
					+ ENCRYPT_ALGORTHM_DES + "]. Cause: " + e);
		} finally {
			cipher = null;
		}
		return byteFina;
	}
	
	/**
	 * 解密以 byte[] 密文输入 , 以 byte[] 明文输出
	 * 
	 * @param byteD
	 * @return
	 */
	private static byte[] decryptByte(byte[] byteD,SecretKey key) {
		Cipher cipher;
		byte[] byteFina = null;
		try {
			cipher = Cipher.getInstance(ENCRYPT_ALGORTHM_DES);
			cipher.init(Cipher.DECRYPT_MODE, key);
			byteFina = cipher.doFinal(byteD);
		} catch (Exception e) {
			throw new RuntimeException(
					"Error decryptByte. Cause: " + e);
		} finally {
			cipher = null;
		}
		return byteFina;
	}
}
