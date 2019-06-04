package com.zheng.utils.dataUtil;

import java.io.File;
import java.util.Date;

import org.apache.commons.lang3.time.DateUtils;

import com.zheng.localProperties.Constants;
import com.zheng.utils.mylog.MyLoggerInfo;

/**
 * 功能描述：和文件名有关的一些方法
 * 
 * @Package: com.zheng.utils.file 
 * @author: zheng  
 */
//https://www.cnblogs.com/ryelqy/p/10104171.html
public class FileNameUtils {
	public static void main(String[] args) {
		System.out.println(getFileSuffix("1.txt"));
	}
	/**
	 * 功能描述：获取文件后缀
	 *
	 * @author: zheng  
	 * @param fileName 文件名称
	 * @return
	 */
	public static String getFileSuffix(String fileName) {
		int lastPoint=fileName.lastIndexOf(".");
		if(lastPoint==-1) {
			return "";
		}else {
			return fileName.substring(lastPoint);
		}
	}

	/**
	 * 文件类型划分||作为后期的文件名定义则用
	 * 
	 * @param type
	 * @param orgFilePath
	 * @return
	 */
	public static String getFileLogType(String type, String orgFilePath) {
		return Constants.TimeConstantFormatter.format(new Date(),Constants.TimeConstantFormatter.DATE_PATTERN) + ":" + orgFilePath + File.separator + "copyFile.log";
	}

	public  static String getTimeName(String oldFilePath,String newPath) {
		String fileName="";
		int lastPoint=oldFilePath.lastIndexOf(".");
		if(oldFilePath.lastIndexOf("/")>lastPoint) {
			fileName = newPath + File.separator + System.currentTimeMillis()  ;
		}else {
			fileName = newPath + File.separator + System.currentTimeMillis()  +oldFilePath.substring(lastPoint);
		}
		return fileName;
	}
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
