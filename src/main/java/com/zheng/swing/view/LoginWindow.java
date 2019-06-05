package com.zheng.swing.view;

import java.awt.CardLayout;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.LinearGradientPaint;
import java.awt.MultipleGradientPaint;
import java.awt.Window;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.awt.geom.Point2D;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JComponent;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JTextField;
import javax.swing.SwingUtilities;
import javax.swing.SwingWorker;
import javax.swing.Timer;

import org.apache.commons.lang.StringUtils;

import com.zheng.swing.controllers.LoginControl;
import com.zheng.swing.models.Commons;
import com.zheng.swing.models.DefaultRes;
import com.zheng.swing.models.GrayShadowBorder;
import com.zheng.swing.models.PromptPasswordField;
import com.zheng.swing.models.PromptTextField;
import com.zheng.utils.dataUtil.MyDataBaseConn;
import com.zheng.utils.swing.SwingUtils;


/**
 * Title: 手机检查箱<br>
 * Description: 登录窗体操作类 提供基础的登陆操作 便于继承<br>
 * Copyright: Copyright (c) 2016-11-9<br>
 * MSun<br>
 * 
 * @author jiujiya
 * @version 1.0
 */
public class LoginWindow {
	/**
	 * 登陆界面
	 */
	protected static final String LOGIN_PANE = "LOGIN_PANE";
	
    protected static final String LOADING_PANE = "LOADING_PANE";
    protected static final String ERROR_PANE = "ERROR_PANE";

    protected JFrame loginWindow;
    protected JPanel contentPane;
    protected CardLayout cardLayout;
    protected JLabel userLogo;
    protected JLabel loadingLogo;
    protected JLabel loadingIcon;
    protected JLabel msgLable;
    protected JTextField usernameField;
    protected JPasswordField passwordField;
    protected JCheckBox rememberAccountBox;
    protected JCheckBox rememberPasswordBox;
    protected JButton loginButton;
    protected JButton loginGLPTButton;

    protected JButton registerLink;
    protected JButton findLink;

    private Timer loadingTimer = new Timer(15, null);
    

    public void startup() {
        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                loadingTimer.addActionListener(new ActionListener() {
                    int i = 1;
                    @Override
                    public void actionPerformed(ActionEvent e) {
                        loadingLogo.setBounds(10 + (i * 5), 16 + 66, 71, 71);
                        if (i++ == 28) {
                            loadingTimer.stop();
                            loadingLogo.setBounds(150, 16 + 66, 71, 71);
                            i = 1;
                        }
                    }
                });

                loginWindow = new JFrame();
                loginWindow.setResizable(false);

                cardLayout = new CardLayout();
                contentPane = new JPanel(cardLayout);
                contentPane.setOpaque(false);
                //登陆面板
                JComponent loginPane = createLoginPane();
//                JComponent errorPane = createErrorPane();
//                JComponent loadingPane = createLoadingPane();

                contentPane.add(loginPane, LOGIN_PANE);
//                contentPane.add(loadingPane, LOADING_PANE);
//                contentPane.add(errorPane, ERROR_PANE);

                loginWindow.setContentPane(contentPane);
                loginWindow.setTitle(Commons.TITLE);
//                loginWindow.setIconImage(DefaultRes.getImage("WINDOW_ICON"));
                loginWindow.setSize(374, 278);
                loginWindow.setLocationRelativeTo(null);
                loginWindow.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
                loginWindow.setVisible(true);
            }
        });
    }

    protected JComponent createErrorPane() {
        return new JPanel();
    }

    protected JComponent createLoadingPane() {
        JPanel loadingPane = new JPanel(null);
        loadingPane.setOpaque(false);

        JPanel loadingBackgroundPane = new JPanel(null) {
            /**
			 * 
			 */
			private static final long serialVersionUID = 4296779625604737107L;

			@Override
            protected void paintComponent(Graphics g) {
                int w = getWidth();
                int h = getHeight();
                //绘制背景
                Point2D start = new Point2D.Float(0, 0);
                Point2D end = new Point2D.Float(w, h);
                float[] dist = {0.0F, 0.25F, 0.75F, 1.0F};
                Color[] colors = {
                        new Color(255, 255, 255, 100),
                        new Color(255, 255, 255, 170),
                        new Color(255, 255, 255, 255),
                        new Color(255, 255, 255, 255)
                };
                LinearGradientPaint p = new LinearGradientPaint(start, end, dist, colors, MultipleGradientPaint.CycleMethod.NO_CYCLE);

                Graphics2D g2d = SwingUtils.getGraphics2D(g);
                g2d.setPaint(p);
                g2d.fillRect(0, 0, w, h);
                
                new ImageIcon(DefaultRes.getImage("LOGIN_LOADING_ICON")).paintIcon(this, g2d, 170, 60);
                super.paintComponent(g);
            }
        };
        loadingBackgroundPane.setOpaque(false);

        JPanel loadingBorderPane = new JPanel(null);
        loadingBorderPane.setOpaque(false);
        loadingBorderPane.setBorder(new GrayShadowBorder());
        
        int offset = 66;
        
        msgLable = new JLabel("", JLabel.CENTER);
        loadingLogo.setBounds(10, 16 + offset, 71, 71);
        loadingLogo.setBounds(150, 20 + offset, 71, 71);
        loadingIcon.setBounds(170,100 + offset,30,30);
        msgLable.setBounds(0, offset + 100, 372, 30);
        loadingBackgroundPane.setBounds(0, offset, 372, 177);
        loadingBorderPane.setBounds(0, offset - 3, 372, 3);
        loadingPane.add(loadingLogo);
        loadingPane.add(loadingIcon);
        loadingPane.add(msgLable);
        loadingPane.add(loadingBackgroundPane);
        loadingPane.add(loadingBorderPane);

        return loadingPane;
    }

    protected JComponent createLoginPane() {
        JPanel loginBackgroundPane = new JPanel(null) {
			private static final long serialVersionUID = -859946038016038668L;

			@Override
            protected void paintComponent(Graphics g) {
                int w = getWidth();
                int h = getHeight();
                //绘制背景
                Point2D start = new Point2D.Float(0, 0);
                Point2D end = new Point2D.Float(w, h);
                float[] dist = {0.0F, 0.3F, 0.75F, 1.0F};
                Color[] colors = {
                        new Color(255, 255, 255, 100),
                        new Color(255, 255, 255, 170),
                        new Color(255, 255, 255, 255),
                        new Color(255, 255, 255, 255)
                };
                LinearGradientPaint p = new LinearGradientPaint(start, end, dist, colors, MultipleGradientPaint.CycleMethod.NO_CYCLE);

                Graphics2D g2d = (Graphics2D) g;
                g2d.setPaint(p);
                g2d.fillRect(0, 0, w, h);
                super.paintComponent(g);
            }
        };
        loginBackgroundPane.setOpaque(false);

        JPanel loginBorderPane = new JPanel(null);
        loginBorderPane.setOpaque(false);
        loginBorderPane.setBorder(new GrayShadowBorder());

        userLogo = new JLabel();
        loadingLogo = new JLabel();
        loadingIcon = new JLabel();
        usernameField = new PromptTextField("请输入用户名", new Color(185,185,185));
        usernameField.setOpaque(false);
        passwordField = new PromptPasswordField("请输入密码", new Color(185,185,185));
        passwordField.setOpaque(false);
        rememberAccountBox = new JCheckBox("记住账号");
        rememberAccountBox.setOpaque(false);
        rememberPasswordBox = new JCheckBox("记住密码");
        rememberPasswordBox.setOpaque(false);
        loginButton = new JButton("登陆");

        JPanel loginPane = new JPanel(null);
        loginPane.setOpaque(false);
        int offset = 66;

        userLogo.setBounds(10,16 + offset,71,71);
        usernameField.setBounds(73,21 + offset,224,30);
        passwordField.setBounds(73,63 + offset,224,30);
        rememberAccountBox.setBounds(73,102 + offset,80,20);
        rememberPasswordBox.setBounds(222,102 + offset,80,20);
        loginButton.setBounds(280,143 + offset,82,26);
        loginBackgroundPane.setBounds(0, offset, 372, 177);
        loginBorderPane.setBounds(0, offset - 3, 372, 3);

        loginPane.add(userLogo);
        loginPane.add(usernameField);
        loginPane.add(passwordField);
        loginPane.add(rememberAccountBox);
        loginPane.add(rememberPasswordBox);
        loginPane.add(loginButton);
        loginPane.add(loginBackgroundPane);
        loginPane.add(loginBorderPane);

        ActionListener loginAction = new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                login();
            }
        };
        loginButton.addActionListener(loginAction);
        usernameField.addActionListener(loginAction);
        passwordField.addActionListener(loginAction);
        rememberPasswordBox.addItemListener(new ItemListener() {
            @Override
            public void itemStateChanged(ItemEvent e) {
                if (e.getStateChange() == ItemEvent.SELECTED)
                    rememberAccountBox.setSelected(true);
            }
        });
        rememberAccountBox.addItemListener(new ItemListener() {
            @Override
            public void itemStateChanged(ItemEvent e) {
                if (e.getStateChange() == ItemEvent.DESELECTED)
                    rememberPasswordBox.setSelected(false);
            }
        });
        
		// 初始化账号密码
        if(StringUtils.isNotEmpty(Commons.username)){
        	usernameField.setText(Commons.username);
        	rememberAccountBox.setSelected(true);
        }
        if(StringUtils.isNotEmpty(Commons.password)){
        	passwordField.setText(Commons.password);
        	rememberPasswordBox.setSelected(true);
        }

        return loginPane;
    }

    public String getUsername() {
        return usernameField.getText();
    }

    public String getPassword() {
        return new String(passwordField.getPassword());
    }

    public boolean isAutoLogin() {
        return rememberPasswordBox.isSelected();
    }

    public boolean isRememberPassword() {
        return rememberAccountBox.isSelected();
    }

    public Window getWindow() {
        return loginWindow;
    }

    public JFrame getFrame() {
        return loginWindow;
    }

    public JDialog getDialog() {
        return null;
    }

    protected void login() {
        if (!loginValidateBefore()) {
            return ;
        }
        showPane(LoginWindow.LOADING_PANE);
        SwingWorker<Boolean,Boolean> worker = new SwingWorker<Boolean, Boolean>() {
        	
            @Override
            protected Boolean doInBackground() throws Exception {
                return true;
            }

            @Override
            protected void done() {
                try {
                    boolean loginSuccessful = get();
                    if (loginSuccessful) {
                    	new Thread(){
                    		public void run() {
                            	// 处理登陆
                    			final StringBuffer sb = new StringBuffer();
                            	LoginControl loginControl = new LoginControl(){
                            		
                            		public void msgShow(String msg, boolean isError) {
                            			if(isError){
                            				sb.append(msg + "\r\n");
                            			}
                            			msgLable.setText(msg);
                            			super.msgShow(msg, isError);
                            		};
                            	};
                            	boolean isOK = loginControl.login(getUsername(), getPassword());
                            	loginAfter(sb.toString(), isOK);
                    		};
                    	}.start();
                    }else{
                    	
                    }
                } catch (Exception e) {
                	e.printStackTrace();
                }
            }
        };
        worker.execute();
    }
    
    /**
     * 登陆后的操作
     * @param errorMsg
     * @param isOk 是否登录成功
     */
    private void loginAfter(String errorMsg, boolean isOk){
    	if(StringUtils.isNotEmpty(errorMsg)){
            JOptionPane.showMessageDialog(loginWindow, errorMsg);
    	}
    	if(isOk){
    		loginWindow.dispose();
    		System.out.println("开启第二个界面");
    		//new MainWindow().setVisible(true);
    		// 如果记住账号或记住密码，保存信息
			Commons.username = getUsername();
			Commons.password = getPassword();
    		String username = rememberAccountBox.isSelected() ? getUsername() : "";
    		String password = rememberPasswordBox.isSelected() ? getPassword() : "";
    		Commons.store(username, password);
    	}else{
            showPane(LoginWindow.LOGIN_PANE);
            passwordField.requestFocusInWindow();
    	}
    }

    protected boolean loginValidateBefore() {
        String username = getUsername();
        if (username == null || username.trim().equals("")) {
            JOptionPane.showMessageDialog(loginWindow, "请输入用户名！");
            return false;
        }
         String password = getPassword();
        if (password == null || password.trim().equals("")) {
            JOptionPane.showMessageDialog(loginWindow, "请输入登录密码！");
            return false;
        }
        return true;
    }

    protected boolean loginValidateAfter() {
        return true;
    }

    protected void showPane(String s) {
        cardLayout.show(contentPane,s);
        if (s.equals(LOADING_PANE)) {
            loadingLogo.setBounds(10, 16 + 66, 71, 71);
            loadingTimer.restart();
        }
    }
    
}
