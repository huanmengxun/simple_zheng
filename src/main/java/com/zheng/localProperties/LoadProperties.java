package com.zheng.localProperties;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Map;
import java.util.Properties;

import lombok.extern.log4j.Log4j2;

/**
 * 功能描述：读取本地数据库配置信息
 * 
 * @Package: com.zheng.localProperties
 * @author: zheng
 */
@Log4j2
public class LoadProperties {

	/**
	 * 功能描述：
	 *
	 * @author: zheng
	 * @param isResources    是否是根据默认路径查找
	 * @param propertiesPath 配置文件路径
	 * @return
	 */
	public static Map<Object,Object> GetDBDefaultSet(Boolean isResources, String propertiesPath) {
		try {
			if (propertiesPath==null||propertiesPath.equals("")) {
				propertiesPath = "src/main/resources/JDBCProperties.properties";
			} else if (isResources==null||isResources) {
				propertiesPath = "src/main/resources/" + propertiesPath;
			}
			File f = new File(propertiesPath);
			if (!f.exists()) {
				log.error("文件"+propertiesPath+"不存在");
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
