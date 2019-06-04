package com.zheng.utils.mylog;

import java.util.Map;
import java.util.stream.IntStream;
import java.util.stream.Stream;
/**
 * 功能描述：
 * 自己写的日志类
 * //https://www.cnblogs.com/zhaoyan001/p/6365064.html //单例
 * @Package: com.zheng.utils.mylog 
 * @author: zheng  
 * @date: 2019年5月31日 上午9:03:33
 */

public class MyLoggerInfo {
	private static volatile MyLoggerInfo singleton;
	private  MyLoggerInfo() {}
	public static MyLoggerInfo getInstance() {
		if(singleton==null) {
			synchronized (MyLoggerInfo.class) {
				if(singleton==null) {
					singleton=new MyLoggerInfo();
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
	public Map<Object,Object> readProperties() {
		return null;
	}
	/**
	 * 功能描述：输出的处理
	 *
	 * @author: zheng  
	 * @param level 级别
	 * @param msg 信息
	 * @param o 后面加的参数，现在只是简单的后面添加
	 */
	public void console(String level,String msg, Object... o) {
		
		StackTraceElement clazz = new Exception().getStackTrace()[2];
		String classname = clazz.getClassName();
		String preStr = classname + "." + clazz.getMethodName() + "(" + clazz.getFileName() + ":"
				+ clazz.getLineNumber() + ")";
		for(Object obj:o) {
			msg+=obj;
		}
		System.out.println(level+"- "+preStr+ "-" + msg);
	}

	public void debug(String msg) {
		console("[DEBUG]",msg);
	}
	public void debug(String msg, Object... o) {
		console("[DEBUG]",msg, o);
	}

	public void info(String msg) {
		console("[INFO]",msg);
	}
	public void info(String msg, Object... o) {
		console("[INFO]",msg, o);
	}

	public void warn(String msg) {
		console("[WARN]",msg);
	}
	public void warn(String msg, Object... o) {
		console("[WARN]",msg, o);
	}

	public void error(String msg ) {
		console("[ERROR]",msg );
	}

	public void error(String msg, Object... o) {
		console("[ERROR]",msg, o);
	}

	public void fatal(String msg) {
		console("[FATAL]", msg);
	}

	public void fatal(String msg, Object... o) {
		console("[FATAL]",msg, o);
	}

}
