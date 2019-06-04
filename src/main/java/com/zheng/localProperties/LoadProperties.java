package com.zheng.localProperties;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.Map;
import java.util.Properties;

import com.alibaba.fastjson.JSON;

import lombok.extern.log4j.Log4j2;

/**
 * 功能描述：读取本地数据库配置信息
 * 
 * @Package: com.zheng.localProperties
 * @author: zheng
 */
@Log4j2
public class LoadProperties {
	private static String PROP_PATH="src/main/resources/JDBCProperties.properties";
//	private static String PROP_PATH = "JDBCProperties.properties";
	public static void main(String[] args) {
		System.out.println(JSON.toJSONString(getPropMap()));
	}

	public static void setPropPath(String propPath) {
		PROP_PATH = propPath;
	}
	
	public static Properties getPropMap() {
		return getPropMap(PROP_PATH);
	}

	public static Properties getPropMap(String propPath) {
		Properties props = new Properties();
		try {
			props.load(new FileInputStream(propPath));
			return props;
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
	}

	public static void updateProp(Properties prop) {
		updateProp(prop, PROP_PATH);
	}

	public static void updateProp(Properties prop, String propPath) {
		try {
			Properties props = new Properties();
			props.load(new FileInputStream(propPath));
			OutputStream fos = new FileOutputStream(propPath);
			props = prop;
			props.store(fos, "Update value");
			fos.close();
		} catch (IOException e) {
			e.printStackTrace();
			System.err.println("属性文件更新错误");
		}
	}

	/**
	 * 功能描述：
	 *
	 * @author: zheng
	 * @param propertiesPath 配置文件路径
	 * @return
	 */
	public static Map<Object, Object> GetDBDefaultSet(String propertiesPath) {
		try {
			if (propertiesPath == null) {
				propertiesPath = PROP_PATH;
			}
			File f = new File(propertiesPath);
			if (!f.exists()) {
				log.error("文件" + propertiesPath + "不存在");
				return null;
			}
			InputStream in = new BufferedInputStream(new FileInputStream(f));
			Properties properties = new Properties();
			properties.load(in);
			return properties;
		} catch (IOException e) {
			e.printStackTrace();
		}
		return null;
	}

}
