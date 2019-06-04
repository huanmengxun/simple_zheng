package com.zheng.swing.beautyWindowStudy;


/*
 * @(#)SwingSet2Applet.java	1.10 05/11/17
 */


import java.awt.BorderLayout;
import java.net.URL;

import javax.swing.JApplet;

import org.jb2011.lnf.beautyeye.BeautyEyeLNFHelper;

// TODO: Auto-generated Javadoc
/**
 * The Class SwingSet2Applet.
 *
 * @version 1.10 11/17/05
 * @author Jeff Dinkins
 */

public class SwingSetBaseApplet extends JApplet {
    
    /* (non-Javadoc)
     * @see java.applet.Applet#init()
     */
    public void init() {
    	try
		{
    		BeautyEyeLNFHelper.launchBeautyEyeLNF();
		}
		catch (Exception e)
		{
			System.err.println("BeautyEyeLNF运行失败，原因是："+e.getMessage());
		}
    	
        getContentPane().setLayout(new BorderLayout());
        getContentPane().add(new MySwingEntry(this), BorderLayout.CENTER);
    }

    /**
     * Gets the uRL.
     *
     * @param filename the filename
     * @return the uRL
     */
    public URL getURL(String filename) {
        URL codeBase = this.getCodeBase();
        URL url = null;
	
        try {
            url = new URL(codeBase, filename);
	    System.out.println(url);
        } catch (java.net.MalformedURLException e) {
            System.out.println("Error: badly specified URL");
            return null;
        }

        return url;
    }


}
