package com.zheng.swing.models;

import java.io.Closeable;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Properties;

import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Title: 手机检查箱<br>
 * Description: 缓存<br>
 * Copyright: Copyright (c) 2015-7-28<br>
 * MSun<br>
 * 
 * @author jiujiya
 * @version 1.0
 */
public class Commons {
	
	public static String TITLE = "智能手机检测系统";

	/** 用户名密码 */
	public static String username = "";
	public static String password = "";
    
    private static Properties prop = new Properties();
    

    /** 存储账号密码的 */
    private static File accountConfigFile = new File(getbdPath() + "/account.ini");
    
    static{
    	// 读取配置文件
		FileInputStream fis = null;
        try {
        	fis = new FileInputStream(accountConfigFile);
			prop.load(fis);
			username = prop.getProperty("username");
			password = prop.getProperty("password");
			if(StringUtils.isNotEmpty(password)){
				password = Encode.decode(password);
			}
		} catch (IOException e) {
			e.printStackTrace();
		} finally{
			close(fis);
		}
    }
    
	
	/**
	 * 获取本地绝对路径
	 * @return
	 */
	public static String getbdPath(){
		return new File("src/main/config").getAbsolutePath();
	}
    
    /**
     * 获取配置文件里面的属性
     * @param code
     * @return
     */
    public static String getProperty(String code){
    	return prop.getProperty(code);
    }
    
    /**
     * 获取配置文件里面的属性
     * @param code
     * @return
     */
    public static void store(String username, String password){
    	// 读取配置文件
    	FileOutputStream fos = null;
        try {
        	Properties prop = new Properties();
			prop.put("username", username);
			prop.put("password", Encode.encode(password, true));
			fos = new FileOutputStream(accountConfigFile);
			prop.store(fos, "account");
		} catch (IOException e) {
			e.printStackTrace();
		} finally{
			close(fos);
		}
    }
    
    /**
     * 关闭资源
     * @param close
     */
    public static void close(Closeable obj) {
        if (obj != null) {
            try {
                obj.close();
            } catch (IOException e) {
                // 不可能存在的异常
                e.printStackTrace();
            }
        }
    }
}
