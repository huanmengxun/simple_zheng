package com.zheng.localProperties;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;

import org.yaml.snakeyaml.DumperOptions;
import org.yaml.snakeyaml.Yaml;

import com.alibaba.fastjson.JSON;

//1.JYAML
//
//2.SnakeYAML
//
//3.YamlBeans
public class LoadYmal {

	private final static DumperOptions OPTIONS = new DumperOptions();
	private static String APP_FILE_PATH = "src/main/resources/zhengApplication.yml";
	static {
		// 将默认读取的方式设置为块状读取
		OPTIONS.setDefaultFlowStyle(DumperOptions.FlowStyle.BLOCK);
	}

	public static void setYmlAppFilePath(String appFilePath) {
		APP_FILE_PATH = appFilePath;
	}
	public static Map<String, Object> getYmlMap() {
		return getYmlMap(APP_FILE_PATH);
	}
	public static Map<String, Object> getYmlMap(String appFilePath) {
		Yaml yaml = new Yaml();
		try {
			Map<String, Object> result= yaml.load(new FileInputStream(new File(appFilePath)));
			return result;
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
		return null;
	}
	

	public static void main(String[] args) {
		try {
			Map<String, Object> param = new HashMap<>();
			param.put("dataType1", "mysql2");
			String appFilePath = "src/main/resources/zhengApplication.yml";
			addIntoYml(appFilePath, "dataSource.dbName", "param");
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public static Map<String, Object> removeMap(Map<String, Object> dataMap, String key) {
		int firstPoint = key.indexOf('.');
		if (firstPoint != -1 && firstPoint < key.length()) {
			String firstKey = key.substring(0, firstPoint);
			if (dataMap.containsKey(firstKey)) {
				if (dataMap.get(firstKey) == null) {
					dataMap.remove(firstKey);
					return dataMap;
				} else {
					String subKey = key.substring(firstPoint + 1);
					return removeMap(dataMap, subKey);
				}
			}
		} else {
			if (dataMap.get(key) != null) {
				dataMap.remove(key);
			}
		}
		return dataMap;
	}

	public static Map<String, Object> addMap(Map<String, Object> dataMap, String key, Object value) {
		int firstPoint = key.indexOf('.');
		Map<String, Object> param = new HashMap<>();
		if (firstPoint != -1 && firstPoint < key.length()) {
			String firstKey = key.substring(0, firstPoint);
			String subKey = key.substring(firstPoint + 1);
			if (dataMap.get(firstKey) != null) {
				Map<String, Object> subData = (Map<String, Object>) dataMap.get(firstKey);
				Map<String, Object> subParam = addMap(subData, subKey, value);
				param = dataMap;
				param.put(firstKey, subParam);
			} else {
				param.put(firstKey, addMap(new HashMap<String, Object>(), firstKey, value));
			}
		} else {
			param = dataMap;
			param.put(key, value);
		}
		return param;
	}

	public static void removeYml(String key) throws IOException {
		removeYml(APP_FILE_PATH, key);
	}

	public static void removeYml(String appFilePath, String key) throws IOException {
		File dest = new File(appFilePath);
		Yaml yaml = new Yaml(OPTIONS);
		// 载入当前yml文件
		LinkedHashMap<String, Object> dataMap = yaml.load(new FileReader(dest));
		// 如果yml内容为空,则会引发空指针异常,此处进行判断
		if (null == dataMap) {
			dataMap = new LinkedHashMap<>();
		}
		dataMap = (LinkedHashMap<String, Object>) removeMap(dataMap, key);
		yaml.dump(dataMap, new FileWriter(dest));
	}

	public static void addIntoYml(String key, Object value) throws IOException {
		addIntoYml(APP_FILE_PATH, key, value);
	}

	public static void addIntoYml(String appFilePath, String key, Object value) throws IOException {
		File dest = new File(appFilePath);
		Yaml yaml = new Yaml(OPTIONS);
		// 载入当前yml文件
		LinkedHashMap<String, Object> dataMap = yaml.load(new FileReader(dest));
		// 如果yml内容为空,则会引发空指针异常,此处进行判断
		if (null == dataMap) {
			dataMap = new LinkedHashMap<>();
		}
		int firstPoint = key.indexOf('.');
		if (firstPoint != -1 && firstPoint < key.length()) {
			dataMap = (LinkedHashMap<String, Object>) addMap(dataMap, key, value);
		} else {
			dataMap.put(key, value);
		}
		yaml.dump(dataMap, new FileWriter(dest));
	}

}
