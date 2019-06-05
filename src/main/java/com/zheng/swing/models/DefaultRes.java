package com.zheng.swing.models;

import java.awt.Image;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.ResourceBundle;

import javax.imageio.ImageIO;
import javax.swing.ImageIcon;


/**
 * Title: L&F组件<br>
 * Description: To change this template use File | Settings | File Templates.<br>
 * Copyright: Copyright (c) 2016-11-9<br>
 * MSun<br>
 * 
 * @author jiujiya
 * @version 1.0
 */
public class DefaultRes {

    private static final Map<String, String> resCache = new HashMap<String, String>();
    private static HashMap<String, ImageIcon> iconCache = new HashMap<String, ImageIcon>();
    private static ClassLoader cl = DefaultRes.class.getClassLoader();

    private static ClassLoader classLoader = cl;
    

    static {
        try {
            java.util.List<BufferedImage> images = new ArrayList<BufferedImage>(39);
            for (int i = 1; i < 40 ; i++) {
            	
//                BufferedImage image = ImageIO.read(
//                		cl.getResourceAsStream("static/flowermsg/images/login_loading/login_loading_" + i + ".gif"));

                BufferedImage image = ImageIO.read(
                		new FileInputStream(
                				new File("src/main/resources/static/flowermsg/images/login_loading/login_loading_" + i + ".gif")
                				));

                images.add(image);
            }
//            Image image = GIFUtils.createGif(images ,80);
//            ImageIcon icon = new ImageIcon(image);
//            iconCache.put("LOGIN_LOADING_ICON",icon);
            images.clear();
        } catch (Exception e) {
        	e.printStackTrace();
        }

        ResourceBundle prb = ResourceBundle.getBundle("default");
        for (String key : prb.keySet()) {
            putRes(key, prb.getString(key));
        }
    }

    public static String getString(String key) {
        return resCache.get(key);
    }

    public static URL getURL(String key) {
        String value = resCache.get(key);
        return (classLoader != null && value != null) ? classLoader.getResource(value) : null;
    }

    public static ImageIcon getIcon(String key) {
        if (!iconCache.containsKey(key)) {
        	URL url=getURL(key);
        	if(url!=null) {
                ImageIcon icon = new ImageIcon(getURL(key));
                iconCache.put(key,icon);
        	}
        }
        return iconCache.get(key);
    }

    public static Image getImage(String key) {
    	ImageIcon ii=getIcon(key);
    	if(ii!=null) {
    		return getIcon(key).getImage();
    	}else {
    		return null;
    	}
    }

    public static void putRes(String key, String value) {
        resCache.put(key, value);
    }

}
