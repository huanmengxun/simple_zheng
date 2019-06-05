package com.zheng.swing.controllers;

/**
 * Title: 手机检查箱<br>
 * Description: 具体的登陆操作<br>
 * Copyright: Copyright (c) 2016-11-24<br>
 * MSun<br>
 * 
 * @author jiujiya
 * @version 1.0
 */
public class LoginControl {
	
	
	/**
	 * 显示提示消息，可重新该方法
	 * @param msg
	 * @param isError
	 */
	public void msgShow(String msg, boolean isError){
		try {
			Thread.sleep(1000);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}
	
	/**
	 * @param userName
	 * @param password
	 */
	public boolean login(String userName, String password){
		// 效验用户名密码
		msgShow("正在效验账号密码...", false);
		try {
			Thread.sleep(10 * 1000);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		return true;
	}
	
}
