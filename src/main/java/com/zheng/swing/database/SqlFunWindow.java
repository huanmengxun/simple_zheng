package com.zheng.swing.database;

import java.awt.EventQueue;

import javax.swing.JFrame;

/**
 * 功能描述：数据库功能界面
 * 
 * @Package: com.zheng.swing.database
 * @author: zheng
 */
public class SqlFunWindow {

	public JFrame frame;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
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
	}

	/**
	 * Create the application.
	 */
	public SqlFunWindow() {
		initialize();
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		frame = new JFrame();
		frame.setBounds(100, 100, 500, 500);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	}

}
