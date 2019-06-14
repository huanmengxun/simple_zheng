package com.zheng.utils.common.Encry;

import com.zheng.utils.tool.mylog.MyLoggerInfo;

public class MyEncryCal {
	static MyLoggerInfo log = MyLoggerInfo.getInstance();
	public static void main(String[] args) {
	}
	/**
	 * 功能描述： 使用使用aes加密
	 *
	 * @author: zheng
	 * @param useKey 加密秘钥
	 * @param value  加密值
	 * @return
	 */
	public static String aesEncry(String useKey, String value) {
		return AESEncry.aesEncry(useKey, value);
	}

//	/**
//	 * 功能描述： 使用使用aes加密,秘钥随机。该加密就是真正的加密，需要保存秘钥，不然无法解密，并且由于秘钥不同每次加密的结果不同
//	 *
//	 * @author: zheng
//	 * @param value  加密值
//	 * @return
//	 */
//	public static String aesEncryRandom(String value) {
//		return AESEncry.aesEncryRandom(value);
//	}

	/**
	 * 功能描述： 使用ase解密
	 * 
	 * @author: zheng
	 * @param useKey     解密秘钥
	 * @param encryValue 解密值
	 * @return
	 */
	public static String aesDecrypt(String useKey, String encryValue) {
		return AESEncry.aesDecrypt(useKey, encryValue);
	}
}
