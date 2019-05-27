package com.zheng.swing.database;

import java.awt.EventQueue;

import javax.swing.JFrame;
/**
 * 功能描述：拼接sql语句打印出sql
 * 
 * @Package: com.zheng.swing 
 * @author: zheng  
 */
public class SqlExportWindow {

	private JFrame frame;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					SqlExportWindow window = new SqlExportWindow();
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
	public SqlExportWindow() {
		initialize();
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		frame = new JFrame();
		frame.setTitle("数据库连接");
		frame.setBounds(100, 100, 450, 200);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	}

}
