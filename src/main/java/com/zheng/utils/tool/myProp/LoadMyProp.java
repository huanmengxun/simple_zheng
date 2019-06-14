package com.zheng.utils.tool.myProp;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.util.Map;
import java.util.Properties;

import com.zheng.utils.common.constants.MyConstants;
import com.zheng.utils.tool.myProp.impl.MyConfig;

/**
 * 功能描述：读取本地数据库配置信息
 * 
 * @Package: com.zheng.localProperties
 * @author: zheng
 */
public class LoadMyProp implements MyConfig{
	private static String Config_FILE_PATH = MyConstants.getConfigPath() + "zhengProperties.properties";
	private static Properties localConfig = new Properties();

	private static volatile LoadMyProp INSTANCE ;

	private LoadMyProp() {

	}

	public static LoadMyProp getInstance() {
		if (INSTANCE == null) {
			synchronized (LoadMyProp.class) {
				if (INSTANCE == null) {
					INSTANCE = new LoadMyProp();
				}
			}
		}
		return INSTANCE;
	}

	/**
	 * 功能描述：获取配置文件信息
	 *
	 * @author: zheng
	 * @return
	 */
	public Map<Object, Object> getConfigMsg() {
		return getConfigMsg(Config_FILE_PATH);
	}

	/**
	 * 功能描述：根据指定路径获取配置文件信息
	 *
	 * @author: zheng
	 * @param configPath
	 * @return
	 */
	public Map<Object, Object> getConfigMsg(String configPath) {
		if (configPath.equals(Config_FILE_PATH) && !localConfig.isEmpty()) {
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
	public  Object getConfigValueByKey(String key) {
		return localConfig.get(key);
	}

	/**
	 * 功能描述：根据默认配置问文件路径移除键值
	 *
	 * @author: zheng
	 * @param key
	 * @throws IOException
	 */
	public void removeConfigByKey(String key) {
		removeConfigByKey(Config_FILE_PATH, key);
	}

	/**
	 * 功能描述：根据指定配置问文件路径移除键值
	 *
	 * @author: zheng
	 * @param configPath 配置文件路径
	 * @param key        移除键
	 * @throws IOException
	 */
	public void removeConfigByKey(String configPath, String key) {
		if (localConfig.isEmpty()) {
			reGetConfig(configPath);
		}
		if (localConfig.containsKey(key)) {
			localConfig.remove(key);
		}
		reWriteConfig(configPath, localConfig);
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
	public void addKeyIntoConfig(String key, Object value) {
		addKeyIntoConfig(Config_FILE_PATH, key, value);
	}

	/**
	 * 功能描述：根据配置路径添加键值
	 *
	 * @author: zheng
	 * @param configPath 配置文件路径
	 * @param key        键
	 * @param value      值
	 * @throws IOException
	 */
	public  void addKeyIntoConfig(String configPath, String key, Object value) {
		localConfig.put(key, value);
		reWriteConfig(configPath, localConfig);
	}

//---------------------私有方法---------------------------
	/**
	 * 功能描述：获取本地配置信息
	 *
	 * @author: zheng
	 * @date: 2019年6月6日 下午3:05:21
	 * @param configPath
	 * @return
	 */
	private static Properties reGetConfig(String configPath) {
		Properties props = new Properties();
		try {
			props.load(new FileInputStream(configPath));
			localConfig.clear();
			localConfig = props;
			return localConfig;
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return localConfig;
	}

	/**
	 * 功能描述： 重写配置文件
	 * 
	 * @author: zheng
	 * @param configPath 路径
	 * @param dataMap    配置信息
	 */
	private static void reWriteConfig(String configPath, Properties prop) {
		try {
			Properties props = new Properties();
			props.load(new FileInputStream(configPath));
			OutputStream fos = new FileOutputStream(configPath);
			props = (Properties) prop;
			props.store(fos, "Update value");
			fos.close();
		} catch (IOException e) {
			e.printStackTrace();
			System.err.println("属性文件更新错误");
		}
	}
}