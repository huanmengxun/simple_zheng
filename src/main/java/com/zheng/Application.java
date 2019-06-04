package com.zheng;

import java.awt.EventQueue;
import java.io.File;
import java.io.IOException;

import javax.swing.JFrame;

import com.zheng.swing.database.SqlFunWindow;

public class Application {
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

	/**
	 * 功能描述：
	 *
	 * @author: zheng 该类作为本项目的所有方法的入口，启动类
	 * @param args
	 */
	public static void main(String[] args) {
		init();
		try {
			org.jb2011.lnf.beautyeye.BeautyEyeLNFHelper.launchBeautyEyeLNF();
		} catch (Exception e) {
			// TODO exception
		}
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					SqlFunWindow window = new SqlFunWindow();
					window.frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
		
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
