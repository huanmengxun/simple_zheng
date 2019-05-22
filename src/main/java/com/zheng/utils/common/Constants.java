package com.zheng.utils.common;

import java.text.SimpleDateFormat;

/**
 * 功能描述：常量类
 * 
 * @Package: com.zheng.utils.common
 * @author: zheng
 * @date: 2019年5月18日 上午12:41:40
 */
public class Constants {
	/**
	 * 功能描述：数据库常量 数据库连接=各自的数据库前缀+ip号+端口号+数据库连接符号+数据库名称？
	 * 
	 * @Package: com.zheng.utils.common
	 * @author: zheng
	 * @date: 2019年5月18日 上午12:41:53
	 */
	public static enum DataBaseConstants {
		/**
		 * mysql基础信息
		 */
		ORACLE("jdbc:oracle:thin:@", ":XE", "oracle.jdbc.driver.OracleDriver"),
		/**
		 * mysql基础信息
		 */
		MYSQL("jdbc:mysql://", "/", "com.mysql.jdbc.Driver"),
		/**
		 * h2数据库基本信息
		 */
		H2("jdbc:h2:tcp://", "/~/test", "org.h2.Driver");
		private DataBaseConstants(String prefixUrl, String urlSplit, String driverName) {
			this.prefixUrl = prefixUrl;
			this.urlSplit = urlSplit;
			this.driverName = driverName;
		}

		/**
		 * 数据库链接前缀
		 */
		private final String prefixUrl;
		/**
		 * 数据库链接与具体数据库之间的分隔符
		 */
		private final String urlSplit;

		/**
		 * 数据库驱动
		 */
		private final String driverName;

		public String getPrefixUrl() {
			return prefixUrl;
		}

		public String getUrlSplit() {
			return urlSplit;
		}

		public String getDriverName() {
			return driverName;
		}

	}

	/**
	 * 功能描述：时间常量接口
	 * 
	 * @Package: com.zheng.utils.common
	 * @author: zheng
	 * @date: 2019年5月18日 上午12:56:24
	 */
	public interface TimeConstant {
		/**
		 * 年
		 */
		public static int YEAR= 1;
		/**
		 * 月
		 */
		public static int MONTH=2;
		/**
		 * 日
		 */
		public static int DAY=3;
		/**
		 * 时
		 */
		public static int HOUR=4;
		/**
		 * 分
		 */
		public static int MINUTE=5;
		/**
		 * 秒
		 */
		public static int SECOND=6;

		/**
		 * 日期格式化
		 */
		public static SimpleDateFormat DATESDF = new SimpleDateFormat("yyyy-MM-dd");
		/**
		 * 时间格式化
		 */
		public static SimpleDateFormat TIMESDF = new SimpleDateFormat("HH:mm:ss");
		/**
		 * 时间日期格式化
		 */
		public static SimpleDateFormat SDF = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		/**
		 * 正午时间
		 */
		public static SimpleDateFormat NOONSDF = new SimpleDateFormat("yyyy-MM-dd 12:00:00");

	}
}
