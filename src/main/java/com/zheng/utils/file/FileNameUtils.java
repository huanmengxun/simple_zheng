package com.zheng.utils.file;

import java.io.File;

/**
 * 功能描述：和文件名有关的一些方法
 * 
 * @Package: com.zheng.utils.file 
 * @author: zheng  
 */
public class FileNameUtils {
	/**
	 * 功能描述：验证url开头是否带着http，若没有则加上
	 *
	 * @author: zheng
	 * @param url
	 * @return
	 */
	public static String validateStartUrl(String url) {
		url = url.startsWith("http") ? url : url + "https://";
		return url;
	}

	/**
	 * 功能描述：确认文件路径
	 * @param path
	 *
	 * @author: zheng  
	 * @return
	 */
	public static void ensureFileExists(String path) {
		File f=new File(path);
		if(!f.exists()) {
			f.mkdirs();
		}
	}
	/**
	 * 功能描述：截取url结尾的文件名称
	 *
	 * @author: zheng
	 * @param url
	 * @return
	 */
	public static String getFileNameByUrl(String url) {
		if (url.length() == url.lastIndexOf("/")) {
			return url;
		} else {
			return url.substring(url.lastIndexOf("/"), url.length());
		}
	}
}
