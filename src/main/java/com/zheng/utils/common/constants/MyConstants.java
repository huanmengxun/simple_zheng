package com.zheng.utils.common.constants;

import java.io.File;
import java.text.ParseException;
import java.util.Date;
import java.util.regex.Pattern;

import org.apache.commons.lang3.time.DateFormatUtils;
import org.apache.commons.lang3.time.DateUtils;

/**
 * 功能描述：常量类
 * 
 * @Package: com.zheng.utils.common
 * @author: zheng
 * @date: 2019年5月18日 上午12:41:40
 */
public class MyConstants {
	private MyConstants() {

	}

	/**
	 * 指定配置文件路径
	 */
	private static String APPOINT_FILEPATH = "src/main/";
	/**
	 * 本地配置
	 */
	private static String localConfig = null;
	/**
	 * 本地静态资源
	 */
	private static String localStaticResources = null;

	public synchronized static String getConfigPath() {
		if (localConfig == null) {
			localConfig = new File(APPOINT_FILEPATH + "config").getAbsolutePath() + File.separator;
		}
		return localConfig;
	}

	public synchronized static String getLocalStaticResourcesPath() {
		if (localStaticResources == null) {
			localStaticResources = new File(APPOINT_FILEPATH + "/resources/static").getAbsolutePath() + File.separator;
		}
		return localStaticResources;
	}

	public static void main(String[] args) {
		String value="18446081979";
		boolean isture=Pattern.matches(RegularConstants.MOBILE, 
				value);
//		System.out.println(Pattern.matches("\\w","a"));
		System.out.println(isture);
	}

	/**
	 * 功能描述：常见正则
	 * 
	 * @Package: com.zheng.localProperties
	 * @author: zheng
	 */
	public class RegularConstants {
		/**
		 * 移动电话正则
		 */
		public static final String MOBILE = "^(13[0-9]|14[5|7]|15[0|1|2|3|5|6|7|8|9]|18[0|1|2|3|5|6|7|8|9])\\d{8}$";

		/**
		 * 常见url正则
		 */
		public static final String URL = "^((http|https)://)?([\\w-]+.)+[\\w-]+(/[\\w-.?%&=]*)?$";
		
		
		/**
		 * IP正则
		 */
		public static final String IP = "((?:(?:25[0-5]|2[0-4]\\d|[01]?\\d?\\d)\\.){3}(?:25[0-5]|2[0-4]\\d|[01]?\\d?\\d))";

		/**
		 * 邮箱正则
		 */
		public static final String EMAIL = "^\\w+([-+.]\\w+)*@\\w+([-.]\\w+)*\\.\\w+([-.]\\w+)*$";
//		/**
//		 * 手机正则
//		 */
//		public static final String PHONE = "";
//		/**
//		 * 常见用户名正则
//		 */
//		public static final String USERNAME = "";
//		/**
//		 * 常见密码正则
//		 */
//		public static final String PASSWORD = "";
		
//		IP地址：((?:(?:25[0-5]|2[0-4]\\d|[01]?\\d?\\d)\\.){3}(?:25[0-5]|2[0-4]\\d|[01]?\\d?\\d))
		
//		()表示捕获分组，()会把每个分组里的匹配的值保存起来，使用$n(n是一个数字，表示第n个捕获组的内容)
//		(?:)表示非捕获分组，和捕获分组唯一的区别在于，非捕获分组匹配的值不会保存起来
		
//		一、校验数字的表达式
//		数字：^[0-9]*$
//		n位的数字：^\d{n}$
//		至少n位的数字：^\d{n,}$
//		m-n位的数字：^\d{m,n}$
//		零和非零开头的数字：^(0|[1-9][0-9]*)$
//		非零开头的最多带两位小数的数字：^([1-9][0-9]*)+(\.[0-9]{1,2})?$
//		带1-2位小数的正数或负数：^(\-)?\d+(\.\d{1,2})$
//		正数、负数、和小数：^(\-|\+)?\d+(\.\d+)?$
//		有两位小数的正实数：^[0-9]+(\.[0-9]{2})?$
//		有1~3位小数的正实数：^[0-9]+(\.[0-9]{1,3})?$
//		非零的正整数：^[1-9]\d*$ 或 ^([1-9][0-9]*){1,3}$ 或 ^\+?[1-9][0-9]*$
//		非零的负整数：^\-[1-9][]0-9"*$ 或 ^-[1-9]\d*$
//		非负整数：^\d+$ 或 ^[1-9]\d*|0$
//		非正整数：^-[1-9]\d*|0$ 或 ^((-\d+)|(0+))$
//		非负浮点数：^\d+(\.\d+)?$ 或 ^[1-9]\d*\.\d*|0\.\d*[1-9]\d*|0?\.0+|0$
//		非正浮点数：^((-\d+(\.\d+)?)|(0+(\.0+)?))$ 或 ^(-([1-9]\d*\.\d*|0\.\d*[1-9]\d*))|0?\.0+|0$
//		正浮点数：^[1-9]\d*\.\d*|0\.\d*[1-9]\d*$ 或 ^(([0-9]+\.[0-9]*[1-9][0-9]*)|([0-9]*[1-9][0-9]*\.[0-9]+)|([0-9]*[1-9][0-9]*))$
//		负浮点数：^-([1-9]\d*\.\d*|0\.\d*[1-9]\d*)$ 或 ^(-(([0-9]+\.[0-9]*[1-9][0-9]*)|([0-9]*[1-9][0-9]*\.[0-9]+)|([0-9]*[1-9][0-9]*)))$
//		浮点数：^(-?\d+)(\.\d+)?$ 或 ^-?([1-9]\d*\.\d*|0\.\d*[1-9]\d*|0?\.0+|0)$
//		校验字符的表达式
//		汉字：^[\u4e00-\u9fa5]{0,}$
//		英文和数字：^[A-Za-z0-9]+$ 或 ^[A-Za-z0-9]{4,40}$
//		长度为3-20的所有字符：^.{3,20}$
//		由26个英文字母组成的字符串：^[A-Za-z]+$
//		由26个大写英文字母组成的字符串：^[A-Z]+$
//		由26个小写英文字母组成的字符串：^[a-z]+$
//		由数字和26个英文字母组成的字符串：^[A-Za-z0-9]+$
//		由数字、26个英文字母或者下划线组成的字符串：^\w+$ 或 ^\w{3,20}$
//		中文、英文、数字包括下划线：^[\u4E00-\u9FA5A-Za-z0-9_]+$
//		中文、英文、数字但不包括下划线等符号：^[\u4E00-\u9FA5A-Za-z0-9]+$ 或 ^[\u4E00-\u9FA5A-Za-z0-9]{2,20}$
//		可以输入含有^%&',;=?$\"等字符：[^%&',;=?$\x22]+
//		禁止输入含有~的字符：[^~\x22]+
//		三、特殊需求表达式
//		Email地址：^\w+([-+.]\w+)*@\w+([-.]\w+)*\.\w+([-.]\w+)*$
//		域名：[a-zA-Z0-9][-a-zA-Z0-9]{0,62}(/.[a-zA-Z0-9][-a-zA-Z0-9]{0,62})+/.?
//		InternetURL：[a-zA-z]+://[^\s]* 或 ^http://([\w-]+\.)+[\w-]+(/[\w-./?%&=]*)?$
//		手机号码：^(13[0-9]|14[5|7]|15[0|1|2|3|5|6|7|8|9]|18[0|1|2|3|5|6|7|8|9])\d{8}$
//		电话号码("XXX-XXXXXXX"、"XXXX-XXXXXXXX"、"XXX-XXXXXXX"、"XXX-XXXXXXXX"、"XXXXXXX"和"XXXXXXXX)：^(\(\d{3,4}-)|\d{3.4}-)?\d{7,8}$
//		国内电话号码(0511-4405222、021-87888822)：\d{3}-\d{8}|\d{4}-\d{7}
//		电话号码正则表达式（支持手机号码，3-4位区号，7-8位直播号码，1－4位分机号）: ((\d{11})|^((\d{7,8})|(\d{4}|\d{3})-(\d{7,8})|(\d{4}|\d{3})-(\d{7,8})-(\d{4}|\d{3}|\d{2}|\d{1})|(\d{7,8})-(\d{4}|\d{3}|\d{2}|\d{1}))$)
//		身份证号(15位、18位数字)，最后一位是校验位，可能为数字或字符X：(^\d{15}$)|(^\d{18}$)|(^\d{17}(\d|X|x)$)
//		帐号是否合法(字母开头，允许5-16字节，允许字母数字下划线)：^[a-zA-Z][a-zA-Z0-9_]{4,15}$
//		密码(以字母开头，长度在6~18之间，只能包含字母、数字和下划线)：^[a-zA-Z]\w{5,17}$
//		强密码(必须包含大小写字母和数字的组合，不能使用特殊字符，长度在 8-10 之间)：^(?=.*\d)(?=.*[a-z])(?=.*[A-Z])[a-zA-Z0-9]{8,10}$
//		强密码(必须包含大小写字母和数字的组合，可以使用特殊字符，长度在8-10之间)：^(?=.*\d)(?=.*[a-z])(?=.*[A-Z]).{8,10}$
//		日期格式：^\d{4}-\d{1,2}-\d{1,2}
//		一年的12个月(01～09和1～12)：^(0?[1-9]|1[0-2])$
//		一个月的31天(01～09和1～31)：^((0?[1-9])|((1|2)[0-9])|30|31)$
//		钱的输入格式：
//		有四种钱的表示形式我们可以接受:"10000.00" 和 "10,000.00", 和没有 "分" 的 "10000" 和 "10,000"：^[1-9][0-9]*$
//		这表示任意一个不以0开头的数字,但是,这也意味着一个字符"0"不通过,所以我们采用下面的形式：^(0|[1-9][0-9]*)$
//		一个0或者一个不以0开头的数字.我们还可以允许开头有一个负号：^(0|-?[1-9][0-9]*)$
//		这表示一个0或者一个可能为负的开头不为0的数字.让用户以0开头好了.把负号的也去掉,因为钱总不能是负的吧。下面我们要加的是说明可能的小数部分：^[0-9]+(.[0-9]+)?$
//		必须说明的是,小数点后面至少应该有1位数,所以"10."是不通过的,但是 "10" 和 "10.2" 是通过的：^[0-9]+(.[0-9]{2})?$
//		这样我们规定小数点后面必须有两位,如果你认为太苛刻了,可以这样：^[0-9]+(.[0-9]{1,2})?$
//		这样就允许用户只写一位小数.下面我们该考虑数字中的逗号了,我们可以这样：^[0-9]{1,3}(,[0-9]{3})*(.[0-9]{1,2})?$
//		1到3个数字,后面跟着任意个 逗号+3个数字,逗号成为可选,而不是必须：^([0-9]+|[0-9]{1,3}(,[0-9]{3})*)(.[0-9]{1,2})?$
//		备注：这就是最终结果了,别忘了"+"可以用"*"替代如果你觉得空字符串也可以接受的话(奇怪,为什么?)最后,别忘了在用函数时去掉去掉那个反斜杠,一般的错误都在这里
//		xml文件：^([a-zA-Z]+-?)+[a-zA-Z0-9]+\\.[x|X][m|M][l|L]$
//		中文字符的正则表达式：[\u4e00-\u9fa5]
//		双字节字符：[^\x00-\xff] (包括汉字在内，可以用来计算字符串的长度(一个双字节字符长度计2，ASCII字符计1))
//		空白行的正则表达式：\n\s*\r (可以用来删除空白行)
//		HTML标记的正则表达式：<(\S*?)[^>]*>.*?|<.*? /> ( 首尾空白字符的正则表达式：^\s*|\s*$或(^\s*)|(\s*$) (可以用来删除行首行尾的空白字符(包括空格、制表符、换页符等等)，非常有用的表达式)
//		腾讯QQ号：[1-9][0-9]{4,} (腾讯QQ号从10000开始)
//		中国邮政编码：[1-9]\d{5}(?!\d) (中国邮政编码为6位数字)
//		IP地址：((?:(?:25[0-5]|2[0-4]\\d|[01]?\\d?\\d)\\.){3}(?:25[0-5]|2[0-4]\\d|[01]?\\d?\\d))
	}

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
		ORACLE("jdbc:oracle:thin:@", ":", "oracle.jdbc.driver.OracleDriver"),
		/**
		 * mysql基础信息
		 */
//		MYSQL("jdbc:mysql://", "/", "com.mysql.jdbc.Driver"),
		MYSQL("jdbc:mysql://", "/", "com.mysql.cj.jdbc.Driver"),

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
		public static int YEAR = 1;
		/**
		 * 月
		 */
		public static int MONTH = 2;
		/**
		 * 日
		 */
		public static int DAY = 3;
		/**
		 * 时
		 */
		public static int HOUR = 4;
		/**
		 * 分
		 */
		public static int MINUTE = 5;
		/**
		 * 秒
		 */
		public static int SECOND = 6;
	}

	public static class TimeConstantFormatter {
		public static final String DATE_PATTERN = "yyyy-MM-dd";
		public static final String TIME_PATTERN = "yyyy-MM-dd HH:mm:ss";
		public static final String HOUR_PATTERN = "HH:mm:ss";

		public static String getDayFormat() {
			return DateFormatUtils.format(new Date(), DATE_PATTERN);
		}

		public static String getTimeFormat() {
			return DateFormatUtils.format(new Date(), TIME_PATTERN);
		}

		public static String format(Date date, String pattern) {
			return DateFormatUtils.format(date, DATE_PATTERN);
		}

		public static Date parse(String date, String pattern) {
			try {
				return DateUtils.parseDate(date, DATE_PATTERN);
			} catch (ParseException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			return null;
		}
	}
}
