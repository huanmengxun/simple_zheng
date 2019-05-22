package com.zheng.localProperties;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;

import com.zheng.utils.common.ResultData;

/**
 * 功能描述：读取本地数据库配置信息
 * 
 * @Package: com.zheng.localProperties
 * @author: zheng
 */
public class DataBaseProperties {
	/**
	 * 功能描述：选择自由路径下面的properties
	 *
	 * @author: zheng
	 * @date: 2019年5月22日 下午1:03:47
	 * @param propertiesPath
	 * @param 是否选择resouces里面的文件
	 * @return
	 */
	public ResultData GetDBByFreeProperties(String propertiesPath) {
		return GetDBDefaultSet(false, propertiesPath);
	}

	/**
	 * 功能描述：选择resouces下面的指定properties
	 *
	 * @author: zheng
	 * @date: 2019年5月22日 下午1:03:59
	 * @param propertiesPath
	 * @param 是否选择resouces里面的文件
	 * @return
	 */
	public ResultData GetDBByResoucesProperties(String propertiesPath) {
		return GetDBDefaultSet(true, propertiesPath);
	}

	public ResultData GetDBDefaultSet() {
		return this.GetDBDefaultSet(false, "");
	}

	/**
	 * 功能描述：
	 *
	 * @author: zheng
	 * @param isResources    是否是根据默认路径查找
	 * @param propertiesPath 配置文件路径
	 * @return
	 */
	public ResultData GetDBDefaultSet(Boolean isResources, String propertiesPath) {
		try {
			if (propertiesPath.equals("")) {
				propertiesPath = "src/main/resources/DBProperties.properties";
			} else if (isResources) {
				propertiesPath = "src/main/resources/" + propertiesPath;
			}
			Map<String, Object> param = new HashMap<>();
			// 获取当前类下面的文件
			// InputStream in =
			// DataBaseProperties.class.getClass().getResourceAsStream("/DBProperties.properties");
			File f = new File(propertiesPath);
			if (!f.exists())
				return null;// 文件不存在则返回
			InputStream in = new BufferedInputStream(new FileInputStream(f));
			Properties properties = new Properties();
			properties.load(in);
			// 获取最下面的信息
			System.out.println(properties.getProperty("username"));
			ResultData rd = new ResultData();
			rd.setMapKey(param);
			return rd;
		} catch (IOException e) {
			e.printStackTrace();
		}
		return ResultData.error();
	}

	public static void main(String[] args) {
		try {
			// 获取当前类下面的文件
//			InputStream in = DataBaseProperties.class.getClass().getResourceAsStream("/DBProperties.properties");
			File f = new File("src/main/resources/DBProperties.properties");
//			System.out.println(f.exists());
//			System.out.println(f.getAbsolutePath());
			InputStream in = new BufferedInputStream(new FileInputStream(f));
			Properties properties = new Properties();
			properties.load(in);
			// 获取最下面的信息
			System.out.println(properties.getProperty("username"));

		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}
