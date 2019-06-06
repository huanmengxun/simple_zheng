package com.zheng.localProperties;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;

import org.yaml.snakeyaml.DumperOptions;
import org.yaml.snakeyaml.Yaml;

import com.zheng.localProperties.commons.MyConstants;

/**
 * 功能描述：加载本地配置文件--指定只能加载一个配置文件
 * 
 * @Package: com.zheng.localProperties
 * @author: zheng
 */
public class LoadMyYmal {

	private final static DumperOptions OPTIONS = new DumperOptions();
	private static String Config_FILE_PATH = MyConstants.getConfigPath() + "zhengApplication.yml";

	private static Map<String, Object> localConfig = new HashMap<>();

	static {
		// 将默认读取的方式设置为块状读取
		OPTIONS.setDefaultFlowStyle(DumperOptions.FlowStyle.BLOCK);
	}

	/**
	 * 功能描述：获取配置文件信息
	 *
	 * @author: zheng
	 * @return
	 */
	public static Map<String, Object> getConfigMsg() {
		return getConfigMsg(Config_FILE_PATH);
	}
	
	/**
	 * 功能描述：根据指定路径获取配置文件信息
	 *
	 * @author: zheng
	 * @param configPath
	 * @return
	 */
	public static Map<String, Object> getConfigMsg(String configPath) {
		if (configPath.equals(Config_FILE_PATH)) {
			return localConfig;
		} else {
			return reGetConfig(configPath);
		}
	}
	
	/**
	 * 功能描述：根据键获取对应值
	 *
	 * @author: zheng  
	 * @param key
	 * @return
	 */
	public static Object getConfigValueByKey(String key) {
		return localConfig.get(key);
	}

	/**
	 * 功能描述：根据默认配置问文件路径移除键值
	 *
	 * @author: zheng
	 * @param key
	 * @throws IOException
	 */
	public static void removeConfigByKey(String key) throws IOException {
		removeConfigByKey(Config_FILE_PATH, key);
	}

	/**
	 * 功能描述：根据指定配置问文件路径移除键值
	 *
	 * @author: zheng
	 * @param configPath 配置文件路径
	 * @param key         移除键
	 * @throws IOException
	 */
	public static void removeConfigByKey(String configPath, String key) throws IOException {
		Map<String, Object> dataMap = new HashMap<>();
		dataMap = removeMap(localConfig, key);
		reWriteConfig(configPath, dataMap);
		reGetConfig(configPath);
	}

	/**
	 * 功能描述：根据默认配置路径添加键值 *
	 * 
	 * @author: zheng
	 * @param key
	 * @param value
	 * @throws IOException
	 */
	public static void addKeyIntoConfig(String key, Object value) throws IOException {
		addKeyIntoConfig(Config_FILE_PATH, key, value);
	}

	/**
	 * 功能描述：根据配置路径添加键值
	 *
	 * @author: zheng
	 * @param configPath 配置文件路径
	 * @param key         键
	 * @param value       值
	 * @throws IOException
	 */
	public static void addKeyIntoConfig(String configPath, String key, Object value) throws IOException {
		Map<String, Object> dataMap = localConfig;
		int firstPoint = key.indexOf('.');
		if (firstPoint != -1 && firstPoint < key.length()) {
			dataMap = (LinkedHashMap<String, Object>) addMap(dataMap, key, value);
		} else {
			dataMap.put(key, value);
		}
		reWriteConfig(configPath, dataMap);
		reGetConfig(configPath);
	}

//	----------------------私有对象，指定在此文件之中使用---------------------------------

	/**
	 * 功能描述：重新状态移除指定键之后的配置信息
	 *
	 * @author: zheng
	 * @date: 2019年6月6日 下午2:02:09
	 * @param dataMap
	 * @param key
	 * @return
	 */
	private static Map<String, Object> removeMap(Map<String, Object> dataYmalMap, String key) {
		int firstPoint = key.indexOf('.');
		if (firstPoint != -1 && firstPoint < key.length()) {
			String firstKey = key.substring(0, firstPoint);
			if (dataYmalMap.containsKey(firstKey)) {
				if (dataYmalMap.get(firstKey) == null) {
					dataYmalMap.remove(firstKey);
					return dataYmalMap;
				} else {
					String subKey = key.substring(firstPoint + 1);
					return removeMap(dataYmalMap, subKey);
				}
			}
		} else {
			if (dataYmalMap.get(key) != null) {
				dataYmalMap.remove(key);
			}
		}
		return dataYmalMap;
	}

	/**
	 * 功能描述：重新装填添加指定键值之后的配置信息
	 *
	 * @author: zheng
	 * @date: 2019年6月6日 下午2:02:58
	 * @param dataMap
	 * @param key
	 * @param value
	 * @return
	 */
	private static Map<String, Object> addMap(Map<String, Object> dataYmalMap, String key, Object value) {
		int firstPoint = key.indexOf('.');
		Map<String, Object> param = new HashMap<>();
		if (firstPoint != -1 && firstPoint < key.length()) {
			String firstKey = key.substring(0, firstPoint);
			String subKey = key.substring(firstPoint + 1);
			if (dataYmalMap.get(firstKey) != null) {
				Map<String, Object> subData = (Map<String, Object>) dataYmalMap.get(firstKey);
				Map<String, Object> subParam = addMap(subData, subKey, value);
				param = dataYmalMap;
				param.put(firstKey, subParam);
			} else {
				param.put(firstKey, addMap(new HashMap<String, Object>(), firstKey, value));
			}
		} else {
			param = dataYmalMap;
			param.put(key, value);
		}
		return param;
	}

	/**
	 * 功能描述：获取本地配置信息
	 *
	 * @author: zheng
	 * @date: 2019年6月6日 下午3:05:21
	 * @param configPath
	 * @return
	 */
	private static Map<String, Object> reGetConfig(String configPath) {
		try {
			Yaml yaml = new Yaml(OPTIONS);
			localConfig.clear();
			localConfig = yaml.load(new FileInputStream(new File(configPath)));
			return localConfig;
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
		return localConfig;
	}

	/**
	 * 功能描述： 重写配置文件
	 * 
	 * @author: zheng
	 * @date: 2019年6月6日 下午3:21:43
	 * @param configPath 路径
	 * @param dataMap     配置信息
	 */
	private static void reWriteConfig(String configPath, Map<String, Object> dataMap) {
		File dest = new File(configPath);
		Yaml yaml = new Yaml(OPTIONS);
		try {
			yaml.dump(dataMap, new FileWriter(dest));
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

}
