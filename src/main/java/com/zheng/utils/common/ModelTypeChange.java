package com.zheng.utils.common;

import java.lang.reflect.Field;
import java.util.HashMap;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;

import com.alibaba.fastjson.JSON;
import com.zheng.utils.dataUtil.model.DataBaseModel;


public class ModelTypeChange {
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
	public static Object mapToObject(Object obj,Map<String,Object> map){
		Class<?> clazz = obj.getClass();
		for (Field field : clazz.getDeclaredFields()) {
			field.setAccessible(true);
			String fieldName = field.getName();
			Object o =map.get(fieldName);
			if(o!=null&&o.getClass().equals(field.getType().getName().getClass())) {
				try {
					field.set(fieldName, o);
				} catch (IllegalArgumentException e) {
					System.out.println(")))))))))");
					e.printStackTrace();
				} catch (IllegalAccessException e) {
					e.printStackTrace();
				}
			}
		}
		return obj;
	}

	public static void main(String[] args) throws IllegalAccessException {
		Map<String,Object>m=new HashMap<>();
		m.put("port", "1231");
		DataBaseModel model=(DataBaseModel) mapToObject(new DataBaseModel(),m);
		System.out.println(JSON.toJSONString(model));
	}
}
