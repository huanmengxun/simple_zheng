package com.zheng.utils.file;

import java.io.File;
import java.util.Date;

import com.zheng.localProperties.commons.MyConstants;

/**
 * 功能描述：和文件名有关的一些方法
 * 
 * @Package: com.zheng.utils.file
 * @author: zheng
 */
//https://www.cnblogs.com/ryelqy/p/10104171.html
public class MyFileNameUtils {
	/**
	 * 功能描述： 获取文件对象，并且创建起上级目录
	 * 
	 * @author: zheng
	 * @param filePath
	 * @param fileName
	 * @return
	 */
	public static File getExistFile(String filePath, String fileName, String suffix) {
		File file = new File(filePath + File.separator + fileName + suffix);
		file.getParentFile().mkdirs();
		return file;
	}

	/**
	 * 文件类型划分||作为后期的文件名定义则用
	 * 
	 * @param type
	 * @param orgFilePath
	 * @return
	 */
	public static String getFileLogType(String type, String orgFilePath) {
		return MyConstants.TimeConstantFormatter.format(new Date(), MyConstants.TimeConstantFormatter.DATE_PATTERN)
				+ ":" + orgFilePath + File.separator + "copyFile.log";
	}

	/**
	 * 功能描述：获取文件名称||未完善||文件名后缀获取有问题
	 *
	 * @author: zheng
	 * @param file
	 * @return
	 */
	public static String getTimeName(File file) {
		StringBuffer sb = new StringBuffer();
		sb.append(file.getPath());
		sb.append(File.separator);
		sb.append(System.currentTimeMillis());
		sb.append('.');
		sb.append(MyFileTypeUtil.getFileType(file).toLowerCase());
		return sb.toString();
	}
//	public static String getTimeName(String oldFilePath, String newPath) {
//		String fileName = "";
//		int lastPoint = oldFilePath.lastIndexOf(".");
//		if (oldFilePath.lastIndexOf("/") > lastPoint) {
//			fileName = newPath + File.separator + System.currentTimeMillis();
//		} else {
//			fileName = newPath + File.separator + System.currentTimeMillis() + oldFilePath.substring(lastPoint);
//		}
//		return fileName;
//	}

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
	 * 
	 * @param path
	 *
	 * @author: zheng
	 * @return
	 */
	public static void ensureFileExists(String path) {
		File f = new File(path);
		if (!f.exists()) {
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
