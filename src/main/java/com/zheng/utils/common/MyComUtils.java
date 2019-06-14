package com.zheng.utils.common;

import java.lang.reflect.Field;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;
import java.util.regex.Pattern;

import com.zheng.utils.common.constants.MyConstants;
import com.zheng.utils.tool.mylog.MyLoggerInfo;

/**
 * 功能描述：公用方法类
 * 
 * @Package: com.zheng.utils.common
 */
public class MyComUtils {
	static MyLoggerInfo log = MyLoggerInfo.getInstance();

	public static void main(String[] args) {
		System.out.println(printConsoleAndIndex());
	}
	
	
	/**
	 * 功能描述：获取可打印在所指向行的输出
	 *
	 * @param infoIn
	 * @return
	 */
	public static String printConsoleAndIndex() {
		return printConsoleAndIndex(2);
	}
	/**
	 * 功能描述：获取可打印在所指向行的输出
	 *
	 * @param infoIn
	 * @return
	 */
	public static String printConsoleAndIndex(Integer infoIn) {
		StackTraceElement[] stacks=new Exception().getStackTrace();
		if(stacks.length<infoIn) {
			try {
				throw new Exception("当前方法所在的级别过低");
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		StackTraceElement clazz = stacks[2];
		String classname = clazz.getClassName();
		StringBuilder builder=new StringBuilder(classname)
						.append(".")
						.append( clazz.getMethodName())
						.append("(")
						.append(clazz.getFileName())
						.append(":") 
						.append(clazz.getLineNumber())
						.append(")");
		return builder.toString();
	}

	/**
	 * 功能描述：首字母大写
	 * @param input
	 * @return
	 */
	public static String toUpperHeader(String input) {
		if (isEmpty(input)) {
			return input;
		} else {
			return (char) (input.charAt(0) - 32) + input.substring(1);
		}
	}
	/**
	 * 功能描述： 是否是邮箱地址
	 * @param input
	 * @return
	 */
	public boolean isEmail(String input) {
		if(!isEmpty(input)) {
			return Pattern.matches(MyConstants.RegularConstants.EMAIL, input);
		}else {
			return false;
		}
	}
	
	/**
	 * 功能描述： 判断对象是否为空
	 * 
	 * @param obj 传入对象
	 * @return
	 */
	public static boolean isEmpty(Object obj) {
		if (obj == null)
			return true;
		if (obj instanceof String) {
			if (obj.equals("")) {
				return true;
			}
		} else if (obj instanceof Collection) {
			if (((Collection) obj).isEmpty()) {
				return true;
			}
		} else if (obj.getClass().isArray()) {
			if (((Object[]) obj).length == 0) {
				return true;
			}
		} else if (obj instanceof Map) {
			if (((Map) obj).isEmpty()) {
				return true;
			}
		} else if (obj instanceof Long) {
			if (Long.valueOf(obj.toString()) < 0) {
				return true;
			}
		}
		return false;

	}

	/**
	 * 功能描述： 将对象转为map
	 * 
	 * @author: zheng
	 * @param obj 将被转为map的对象
	 * @return
	 * @throws IllegalAccessException
	 */
	public static Map<String, Object> objectToMap(Object obj) throws IllegalAccessException {
		Map<String, Object> map = new HashMap<String, Object>();
		Class<?> clazz = obj.getClass();
		for (Field field : clazz.getDeclaredFields()) {
			field.setAccessible(true);
			String fieldName = field.getName();
			map.put(fieldName, field.get(obj));
		}
		return map;
	}

	/**
	 * 功能描述：将map对象的值赋给另外一个obj对象
	 *
	 * @author: zheng
	 * @param bean 装填map对象值得对象
	 * @param map
	 * @return
	 */
	public static Object mapToObject(Object bean, Map<String, Object> map) {
		Class<?> clazz = bean.getClass();
		for (Field field : clazz.getDeclaredFields()) {
			field.setAccessible(true);
			String fieldName = field.getName();
			Object obj = map.get(fieldName);
			if (obj != null && obj.getClass().getName().equals(field.getType().getName())) {
				try {
					field.set(bean, obj);
				} catch (IllegalArgumentException e) {
					e.printStackTrace();
				} catch (IllegalAccessException e) {
					e.printStackTrace();
				}
			}
		}
		return bean;
	}
}
