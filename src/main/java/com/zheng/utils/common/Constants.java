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
	 * 功能描述：数据库常量
	 * 
	 * @Package: com.zheng.utils.common
	 * @author: zheng
	 * @date: 2019年5月18日 上午12:41:53
	 */
	public static enum DataBaseConstants {
		/**
		 * mysql基础信息
		 */
		MYSQL("测试1", "测试2", null, null),
		/**
		 * h2数据库基本信息
		 */
		H2("root", "huan", "jdbc:h2:tcp://localhost/~/test", "org.h2.Driver");
		private DataBaseConstants(String username, String password, String url, String className) {
			this.username = username;
			this.password = password;
			this.url = url;
			this.className = className;
		}

		/**
		 * 数据库用户名
		 */
		private final String username;
		/**
		 * 数据库密码
		 */
		private final String password;
		/**
		 * 数据库连接
		 */
		private final String url;
		/**
		 * 数据库反射类
		 */
		private final String className;

		public String getUsername() {
			return username;
		}

		public String getPassword() {
			return password;
		}

		public String getUrl() {
			return url;
		}

		public String getClassName() {
			return className;
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
