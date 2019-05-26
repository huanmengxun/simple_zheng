package com.zheng;

import java.io.File;
import java.io.IOException;

public class Application {
	/**
	 * 功能描述：
	 *
	 * @author: zheng  
	 * 该类作为本项目的所有方法的入口，启动类
	 * @param args
	 */
	public static void main(String[] args) {
		//首先需要加载配置文件，判断是否存在，不存在则将项目中的配置文件放置在路径之下
		File file=new File("src/main/resources/zhengApplication.yml");
		
		try {
			File f=new File(System.getProperties().getProperty("user.home")+File.separator+"zhengApplication.yml");
			if(!f.exists()) {
				f.createNewFile();
			}
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}
}
