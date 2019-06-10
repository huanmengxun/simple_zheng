package com.zheng.utils.mylog;

import com.zheng.localProperties.LoadMyYmal;
import com.zheng.utils.common.MyCommonUtils;

import lombok.Data;

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

	public void getConsoleConfig() {
		LoadMyYmal.getConfigValueByKey("mylogger.console");
	}


	public void debug(Object msg) {
		console("DEBUG", msg);
	}
	public void debug(Object msg, Object... o) {
		console("DEBUG", msg, o);
	}

	public void info(Object msg) {
		console("INFO", msg);
	}
	public void info(Object msg, Object... o) {
		console("INFO", msg, o);
	}


	public void warn(Object msg) {
		console("WARN", msg);
	}
	public void warn(Object msg, Object... o) {
		console("WARN", msg, o);
	}

	public void error(Object msg) {
		console("ERROR", msg);
	}
	public void error(Object msg, Object... o) {
		console("ERROR", msg, o);
	}
	public void fatal(Object msg) {
		console("FATAL", msg);
	}
	public void fatal(Object msg, Object... o) {
		console("FATAL", msg, o);
	}


	@Data
	private class LoggerModel {
		private String level;
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
		if (isLevelOut(level) && !MyCommonUtils.isEmpty(msg)) {
			StackTraceElement clazz = new Exception().getStackTrace()[2];
			String classname = clazz.getClassName();
			String preStr = classname + "." + clazz.getMethodName() + "(" + clazz.getFileName() + ":"
					+ clazz.getLineNumber() + ")";

			StringBuilder out = new StringBuilder();
			out.append("[");
			out.append(level);
			out.append("]- ");
			out.append(preStr);
			out.append(":\r\t");
			out.append(handleString(msg,objects));
			System.out.println(out.toString());
		} else {
			return;
		}
	}

	private String handleString(Object msg, Object... objects) {
		StringBuilder sb=new StringBuilder();
		if(msg instanceof String) {
			sb=hadleOutString(sb, msg.toString(), 0, objects);
		}else {
			sb.append(msg);
			for(Object o:objects) {
				sb.append(o);
			}
		}
		return sb.toString();
	}
	

	private StringBuilder hadleOutString(StringBuilder sb,String msg,int index,Object... objects) {
		if(index<objects.length) {
			int firstLeft=msg.indexOf("{");
			if(firstLeft<0) {
				sb.append(msg);
				for(;index<objects.length;index++) {
					sb.append(objects[index]);
				}
			}else if(firstLeft==msg.length()){
				for(;index<objects.length;index++) {
					sb.append(objects[index]);
				}
			}else {
				if(msg.charAt(firstLeft+1)=='}') {
					sb.append(msg.substring(0, firstLeft));
					sb.append(objects[index]);
					return hadleOutString(sb, msg.substring(firstLeft+2), ++index, objects);
				}else {
					sb.append(msg.substring(0, firstLeft));
					return hadleOutString(sb, msg.substring(firstLeft), index, objects);
				}
			}
			return sb;
		}else {
			sb.append(msg);
			return sb;
		}
	}
	

	private Boolean isLevelOut(String level) {
		return true;
	}
}
