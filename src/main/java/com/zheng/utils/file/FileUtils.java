package com.zheng.utils.file;

import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Date;
import java.util.List;

import org.apache.http.client.utils.DateUtils;

import com.zheng.localProperties.commons.MyConstants;

import lombok.extern.log4j.Log4j2;

/**
 * 
 * 功能描述： 文件相关方法类
 * 
 * @Package: com.zheng.utils.file
 * @author: zheng
 */
public class FileUtils {


	


	/**
	 * 功能描述： 文件名的方式
	 * 
	 * @Package: com.zheng.utils.file
	 * @author: zheng
	 */
	public class FileNameGetConstants {
		/**
		 * 从开头开始查询
		 */
		public static final int FIND_FROM_START = 1;
		/**
		 * 从开头开始查询
		 */
		public static final int FIND_FROM_END = 2;
		/**
		 * 查询此文件名
		 */
		public static final int FIND_AND_ONLY = 3;
		/**
		  * 查询含有的
		 */
		public static final int FIND_AND_CONTAINS = 4;
		/**
		  * 全部文件
		 */
		public static final int JUST_ALL = 5;

	}

	/**
	 * 功能描述：使用文件类型判断
	 * 
	 * @Package: com.zheng.utils.file
	 * @author: zheng
	 */
	public class FileFolderTypeConstants {
		/**
		 * 指定为当前目录下面的所有文件夹
		 */
		public static final int IS_FOLDER = 1;
		/**
		 * 指定为当前目录下面的所有文件
		 */
		public static final int IS_FILE = 2;
		/**
		 * 指定为当前目录下面的所有文件夹，包括其中的文件夹之内的
		 */
		public static final int IS_FOLDER_ALL = 3;
		/**
		 * 指定为当前目录下面的所有文件，包括其中的文件夹之内的
		 */
		public static final int IS_FILE_ALL = 4;

	}

}
