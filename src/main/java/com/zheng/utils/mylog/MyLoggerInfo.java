package com.zheng.utils.mylog;

import java.util.Map;

/**
 * 功能描述： 自己写的日志类 //https://www.cnblogs.com/zhaoyan001/p/6365064.html //单例
 * 
 * @Package: com.zheng.utils.mylog
 * @author: zheng
 * @date: 2019年5月31日 上午9:03:33
 */

public class MyLoggerInfo {
	private static volatile MyLoggerInfo singleton;
	
	private MyLoggerInfo() {
		
	}

	public static MyLoggerInfo getInstance() {
		if (singleton == null) {
			synchronized (MyLoggerInfo.class) {
				if (singleton == null) {
					singleton = new MyLoggerInfo();
				}
			}
		}
		return singleton;
	}
//	private static class MyLoggerInfoInstance {
//		private final static MyLoggerInfo INSTANCE=new MyLoggerInfo();
//	}
//	public static MyLoggerInfo getInstance() {
//		return MyLoggerInfoInstance.INSTANCE;
//	}

	public void loadProperties() {

	}

	public void loadProperties(String path) {
	}

	public Map<Object, Object> readProperties() {
		return null;
	}

	/**
	 * 功能描述：输出的处理
	 *
	 * @author: zheng
	 * @param level 级别
	 * @param msg   信息
	 * @param o     后面加的参数，现在只是简单的后面添加
	 */
	public void console(String level, Object... o) {
		if (o.length > 0) {
			String msg=o[0]==null?"":o[0].toString();
			StackTraceElement clazz = new Exception().getStackTrace()[2];
			String classname = clazz.getClassName();
			String preStr = classname + "." + clazz.getMethodName() + "(" + clazz.getFileName() + ":"
					+ clazz.getLineNumber() + ")";
			for(int i=1;i<o.length;i++) {
				msg+=o[i];
			}
			System.out.println(level + "- " + preStr + "-" + msg);
		} else {
			return;
		}
	}

	public void debug(Object... o) {
		console("[DEBUG]", o);
	}

	public void info(Object... o) {
		console("[INFO]", o);
	}

	public void warn(Object... o) {
		console("[WARN]", o);
	}

	public void error(Object... o) {
		console("[ERROR]", o);
	}

	public void fatal(Object... o) {
		console("[FATAL]", o);
	}

}
