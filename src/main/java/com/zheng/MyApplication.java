package com.zheng;

import java.io.File;
import java.io.IOException;

import com.zheng.swing.view.LoginWindow;
import com.zheng.utils.dataUtil.MyDataBaseConn;

public class MyApplication {
	/**
	 * 功能描述：初始化基础信息
	 *
	 * @author: zheng
	 * @date: 2019年5月27日 下午4:20:19
	 */
	public static void init() {
		File protectProp = new File("zhengApplication.yml");
		System.out.println(protectProp.getPath());
		try {
			protectProp.createNewFile();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public static void main(String[] args) {
    	MyDataBaseConn.getConnByProperties();
    	System.out.println("测试");
		new LoginWindow().startup();
	}

//		//首先需要加载配置文件，判断是否存在，不存在则将项目中的配置文件放置在路径之下
//		File file=new File("src/main/resources/zhengApplication.yml");
//		
//		try {
//			File f=new File(System.getProperties().getProperty("user.home")+File.separator+"zhengApplication.yml");
//			if(!f.exists()) {
//				f.createNewFile();
//			}
//		} catch (IOException e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		}

}
