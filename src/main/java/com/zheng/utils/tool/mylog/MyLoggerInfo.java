package com.zheng.utils.tool.mylog;

import com.zheng.utils.common.MyComUtils;
import com.zheng.utils.tool.myProp.LoadMyYmal;

import lombok.Data;

/**
 * 功能描述： 自己写的日志类 //https://www.cnblogs.com/zhaoyan001/p/6365064.html //单例
 * 仅仅是学习深入时使用
 * 
 * @Package: com.zheng.utils.mylog
 */
public class MyLoggerInfo {
	private static volatile MyLoggerInfo SINGLETON;
	/**
	 * 使用了info的当前方法
	 */
	private static Integer THIS_MOTHOD = 3;

	private MyLoggerInfo() {
	}

	public static MyLoggerInfo getInstance() {
		if (SINGLETON == null) {
			synchronized (MyLoggerInfo.class) {
				if (SINGLETON == null) {
					SINGLETON = new MyLoggerInfo();
				}
			}
		}
		return SINGLETON;
	}
//	private static class MyLoggerInfoInstance {
//		private final static MyLoggerInfo INSTANCE=new MyLoggerInfo();
//	}
//	public static MyLoggerInfo getInstance() {
//		return MyLoggerInfoInstance.INSTANCE;
//	}

	/**
	 * 功能描述：加载配置文件，指定配置信息未默认配置文件里面的mylogger.console下面
	 *
	 * @author: zheng
	 * @date: 2019年6月13日 下午3:14:46
	 */
	public void getConsoleConfig() {
		LoadMyYmal.getInstance().getConfigValueByKey("mylogger.console");
	}

	public void debug(Object msg) {
		console("DEBUG", msg);
	}

	public void debug(Object msg, Object... object) {
		console("DEBUG", msg, object);
	}

	public void info(Object msg) {
		console("INFO", msg);
	}

	public void info(Object msg, Object... object) {
		console("INFO", msg, object);
	}

	public void warn(Object msg) {
		console("WARN", msg);
	}

	public void warn(Object msg, Object... object) {
		console("WARN", msg, object);
	}

	public void error(Object msg) {
		console("ERROR", msg);
	}

	public void error(Object msg, Object... object) {
		console("ERROR", msg, object);
	}

	public void fatal(Object msg) {
		console("FATAL", msg);
	}

	public void fatal(Object msg, Object... object) {
		console("FATAL", msg, object);
	}

//	--------------内部私有方法---------------------

	/**
	 * 功能描述：输出的处理
	 *
	 * @author: zheng
	 * @param level 级别
	 * @param msg   信息
	 * @param o     后面加的参数，现在只是简单的后面添加
	 */
	private void console(String level, Object msg, Object... objects) {
		console(level, msg, THIS_MOTHOD, objects);
	}

	/**
	 * 功能描述：输出的处理
	 *
	 * @author: zheng
	 * @param level  级别
	 * @param msg    信息
	 * @param infoIn 所输出的控制台所指向的类
	 * @param o      后面加的参数，现在只是简单的后面添加
	 */
	private void console(String level, Object msg, Integer infoIn, Object... objects) {
		if (isLevelOut(level) && !MyComUtils.isEmpty(msg)) {
			String preStr = MyComUtils.printConsoleAndIndex(++infoIn);
			StringBuilder out = new StringBuilder();
			out.append("[").append(level).append("]").append("- ").append(preStr).append(":\r\t")
					.append(handleString(msg, objects)).append("\n");

			if (isWarnOut(level)) {
				System.err.print(out.toString());
			} else {
				System.out.print(out.toString());
			}
		} else {
			return;
		}
	}

	/**
	 * 功能描述：处理msg数据
	 *
	 * @param msg
	 * @param objects
	 * @return
	 */
	private String handleString(Object msg, Object... objects) {
		StringBuilder sb = new StringBuilder();
		if (msg instanceof String) {
			sb = hadleOutString(sb, msg.toString(), 0, objects);
		} else {
			sb.append(msg);
			for (Object object : objects) {
				sb.append(object);
			}
		}
		return sb.toString();
	}

	/**
	 * 功能描述：使用后面的值代替前面的{}
	 *
	 * @param builder 拼接的字符串
	 * @param msg     剩余的字符串
	 * @param index   现在所指向的{}的index
	 * @param objects 参数列表
	 * @return
	 */
	private StringBuilder hadleOutString(StringBuilder builder, String msg, int index, Object... objects) {
		if (index < objects.length) {
			int firstLeft = msg.indexOf('{');
			if (firstLeft < 0) {
				builder.append(msg);
				for (; index < objects.length; index++) {
					builder.append(objects[index]);
				}
			} else if (firstLeft == msg.length()) {
				for (; index < objects.length; index++) {
					builder.append(objects[index]);
				}
			} else {
				if (msg.charAt(firstLeft + 1) == '}') {
					builder.append(msg.substring(0, firstLeft)).append(objects[index]);
					return hadleOutString(builder, msg.substring(firstLeft + 2), ++index, objects);
				} else {
					builder.append(msg.substring(0, firstLeft));
					return hadleOutString(builder, msg.substring(firstLeft), index, objects);
				}
			}
			return builder;
		} else {
			builder.append(msg);
			return builder;
		}
	}

	/**
	 * 功能描述： 判断日志级别||未去实现
	 * 
	 * @param level
	 * @return
	 */
	private Boolean isLevelOut(String level) {
		return true;
	}

	/**
	 * 功能描述： 是否是错误输出
	 * 
	 * @param level
	 * @return
	 */
	private Boolean isWarnOut(String level) {
		char startCharset = level.charAt(0);
		if (startCharset == 'W' || startCharset == 'F' || startCharset == 'E') {
			return true;
		}
		return false;
	}

	/**
	 * 功能描述：未去实现，日志的控制
	 * 
	 * @Package: com.zheng.utils.tool.mylog
	 */
	@Data
	private class LoggerModel {
		private String level;
	}
}
