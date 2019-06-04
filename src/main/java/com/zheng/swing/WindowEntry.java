package com.zheng.swing;

import java.awt.Color;
import java.awt.EventQueue;
import java.awt.Graphics;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.UIManager;
import javax.swing.plaf.ToolBarUI;

import org.jb2011.lnf.beautyeye.BeautyEyeLNFHelper;
import org.jb2011.lnf.beautyeye.ch3_button.BEButtonUI;

public class WindowEntry {

	private JFrame frame;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		try {
//			BeautyEyeLNFHelper.frameBorderStyle = BeautyEyeLNFHelper.FrameBorderStyle.osLookAndFeelDecorated;
			// 样式
//			BeautyEyeLNFHelper.frameBorderStyle = BeautyEyeLNFHelper.FrameBorderStyle.generalNoTranslucencyShadow;
			// 设置按钮
			UIManager.put("RootPane.setupButtonVisible", false);

			// 设置属性即可：true表示使用ToolBar.background颜色实现纯
			// 色填充背景，BeautyEye中此属性默认是false
			UIManager.put("ToolBar.isPaintPlainBackground", Boolean.TRUE);
			//使用ClientProperty单独设置控制每个toolbar：true表示使用ToolBar.background
			// 颜色实现纯色填充背景，BeautyEye中此属性默认是false
//			toolbarInstance.putClientProperty("ToolBar.isPaintPlainBackground", Boolean.TRUE);
			
//			BeautyEyeLNFHelper.translucencyAtFrameInactive = false;
			org.jb2011.lnf.beautyeye.BeautyEyeLNFHelper.launchBeautyEyeLNF();
//			JButton button=new JButton();
//			button.setUI(new BEButtonUI(). setNormalColor(BEButtonUI.NormalColor.normal));
		} catch (Exception e) {
		}
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					WindowEntry window = new WindowEntry();
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
	public WindowEntry() {
		initialize();
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		frame = new JFrame();
		frame.setTitle("程序主窗口");
		ImageIcon  image=new ImageIcon("src/main/resources/img/bg1.jpg");
		frame.setBounds(100, 100,image.getIconWidth(),image.getIconHeight());
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.getContentPane().setLayout(null);
		
		JPanel panel = new JPanel();
		panel.setBounds(10, 10, 414, 241);
		
//		panel.paintComponents(new Graphics().drawImage(img, x, y, width, height, bgcolor, observer));
		
		frame.getContentPane().add(panel);
		panel.setLocation(image.getIconWidth(),image.getIconHeight());
		panel.setLayout(null);
		
//		Color c=new Color(`cspace, components, alpha)
		
		JButton button = new JButton("进入主页面");
		button.setBounds(175, 208, 93, 23);
		button.setUI(new BEButtonUI(). setNormalColor(BEButtonUI.NormalColor.normal));

		panel.add(button);
		
		
	}
}
