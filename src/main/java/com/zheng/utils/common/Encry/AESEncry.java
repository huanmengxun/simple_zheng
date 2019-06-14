package com.zheng.utils.common.Encry;

import java.security.InvalidKeyException;
import java.security.Key;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;

import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.KeyGenerator;
import javax.crypto.NoSuchPaddingException;
import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;

import org.bouncycastle.util.encoders.Hex;

import com.zheng.utils.common.MyComUtils;
import com.zheng.utils.tool.mylog.MyLoggerInfo;

/**
 * 功能描述： 对称算法 aes加密解密
 * 
 * @Package: com.zheng.utils.common.Encry
 */
public class AESEncry {
	static MyLoggerInfo log = MyLoggerInfo.getInstance();

	/**
	 * 功能描述： 使用aes加密，解密方式不知
	 * 
	 * @author: zheng
	 * @param value
	 * @return
	 */
	protected static String aesEncryRandom(String value) {
		String result = null;
		try {
			// 生成Key
			KeyGenerator keyGenerator = KeyGenerator.getInstance("AES");
			keyGenerator.init(128);
			SecretKey secretKey = keyGenerator.generateKey();
			byte[] keyBytes = secretKey.getEncoded();
			// Key转换
			Key key = new SecretKeySpec(keyBytes, "AES");
			// 加密
			Cipher cipher = Cipher.getInstance("AES/ECB/PKCS5Padding");
			cipher.init(Cipher.ENCRYPT_MODE, key);
			byte[] encodeResult = cipher.doFinal(value.getBytes());
			result = Hex.toHexString(encodeResult);
			log.info("Encode : ", result);
		} catch (NoSuchAlgorithmException e) {
			e.printStackTrace();
		} catch (NoSuchPaddingException e) {
			e.printStackTrace();
		} catch (InvalidKeyException e) {
			e.printStackTrace();
		} catch (IllegalBlockSizeException e) {
			e.printStackTrace();
		} catch (BadPaddingException e) {
			e.printStackTrace();
		}
		return result;
	}

	/**
	 * 功能描述： 使用使用aes加密
	 *
	 * @author: zheng
	 * @param useKey 加密秘钥
	 * @param value  加密值
	 * @return
	 */
	protected static String aesEncry(String useKey, String value) {
		String result = null;
		useKey=handleKey(useKey);
		try {
			// 生成Key
			KeyGenerator keyGenerator = KeyGenerator.getInstance("AES");
			keyGenerator.init(128, new SecureRandom(useKey.getBytes()));
			SecretKey secretKey = keyGenerator.generateKey();
			byte[] keyBytes = secretKey.getEncoded();
			Key key = new SecretKeySpec(keyBytes, "AES");
			Cipher cipher = Cipher.getInstance("AES/ECB/PKCS5Padding");
			cipher.init(Cipher.ENCRYPT_MODE, key);
			byte[] encodeResult = cipher.doFinal(value.getBytes());
			result = Hex.toHexString(encodeResult);
			log.info("Encode : ", result);
		} catch (NoSuchAlgorithmException e) {
			e.printStackTrace();
		} catch (NoSuchPaddingException e) {
			e.printStackTrace();
		} catch (InvalidKeyException e) {
			e.printStackTrace();
		} catch (IllegalBlockSizeException e) {
			e.printStackTrace();
		} catch (BadPaddingException e) {
			e.printStackTrace();
		}
		return result;
	}

	/**
	 * 功能描述： 使用ase解密
	 * 
	 * @author: zheng
	 * @param useKey     解密秘钥
	 * @param encryValue 解密值
	 * @return
	 */
	protected static String aesDecrypt(String useKey, String encryValue) {
		KeyGenerator keyGenerator;
		useKey=handleKey(useKey);
		String result = null;
		try {
			keyGenerator = KeyGenerator.getInstance("AES");
			keyGenerator.init(128, new SecureRandom(useKey.getBytes()));
			SecretKey secretKey = keyGenerator.generateKey();
			byte[] keyBytes = secretKey.getEncoded();
			Key key = new SecretKeySpec(keyBytes, "AES");
			Cipher cipher;
			cipher = Cipher.getInstance("AES/ECB/PKCS5Padding");
			cipher.init(Cipher.DECRYPT_MODE, key);
			byte[] decodeResult = cipher.doFinal(Hex.decode(encryValue));
			result = new String(decodeResult);
		} catch (NoSuchAlgorithmException e) {
			e.printStackTrace();
		} catch (NoSuchPaddingException e) {
			e.printStackTrace();
		} catch (InvalidKeyException e) {
			e.printStackTrace();
		} catch (IllegalBlockSizeException e) {
			e.printStackTrace();
		} catch (BadPaddingException e) {
			e.printStackTrace();
		}
		return result;
	}


	/**
	 * 功能描述： 随便处理一下key，使得其不容易被认出
	 * 
	 * @param useKey
	 * @return
	 */
	private static String handleKey(String useKey) {
		if (MyComUtils.isEmpty(useKey)) {
			return "132231";
		}
		int i = useKey.length();
		char first= useKey.charAt(0);
		char second= useKey.charAt(i / 2);
		char third= useKey.charAt(i - 1);
		String result;
		if(first>third) {
			result=""+first+second+third+third+second+first;
		}else if(first<third) {
			result=""+first+second+third+first+third+second;
		}else if(second%2==0){
			result=""+first+first+second+second+third+third;
		}else {
			result=""+first+third+second+second+first+third;
		}
		return result;
	}
}
