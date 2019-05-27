package com.zheng;

import java.io.File;

import com.zheng.localProperties.SystemConstants;
import com.zheng.utils.file.FileUtils;

public class Application {
	public static void init() {
		File protectProp = new File("src/main/resources/zhengApplication.yml");
//		不考虑项目内文件的配置是否存在
		if(!protectProp.exists()) {
			File test=new File("f://23/123");
			test.mkdirs();
		}
		File localProp = new File(SystemConstants.USER_HOME + File.separator + "zhengApplication.yml");
		if (!localProp.exists()) {
			try {
				FileUtils.copyFileToNewPath(protectProp, localProp, true);
				System.out.println("本地不存在配置文件，加载配置文件");
			} catch (Exception e) {
				e.printStackTrace();
			}
		}else {
			System.out.println("本地存在配置文件，加载配置文件");
		}
	}

	/**
	 * 功能描述：
	 *
	 * @author: zheng 该类作为本项目的所有方法的入口，启动类
	 * @param args
	 */
	public static void main(String[] args) {
		init();

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
}
