package com.zheng.utils.common;

import java.lang.reflect.Field;
import java.util.HashMap;
import java.util.Map;

public class MyCommonUtils {
	/**
	 * 功能描述： 判断对象是否为空
	 * 
	 * @author: zheng
	 * @param o
	 * @return
	 */
	public static Boolean isEmpty(Object o) {
		if (o == null || o.toString().equals("")) {
			return true;
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
	 * @param obj 装填map对象值得对象
	 * @param map
	 * @return
	 */
	public static Object mapToObject(Object obj, Map<String, Object> map) {
		Class<?> clazz = obj.getClass();
		for (Field field : clazz.getDeclaredFields()) {
			field.setAccessible(true);
			String fieldName = field.getName();
			Object o = map.get(fieldName);
			if (o != null && o.getClass().getName().equals(field.getType().getName())) {
				try {
					field.set(obj, o);
				} catch (IllegalArgumentException e) {
					e.printStackTrace();
				} catch (IllegalAccessException e) {
					e.printStackTrace();
				}
			}
		}
		return obj;
	}
}
