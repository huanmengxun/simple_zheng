package com.zheng.swing;


/*
 * @(#)SwingSet2Applet.java	1.10 05/11/17
 */


import java.awt.BorderLayout;
import java.net.URL;

import javax.swing.JApplet;

import org.jb2011.lnf.beautyeye.BeautyEyeLNFHelper;

import com.zheng.swing.beautyWindowStudy.MySwingEntry;


public class SwingSetBaseApplet extends JApplet {
	// Possible Look & Feels
	// private static final String mac =
	// "com.sun.java.swing.plaf.mac.MacLookAndFeel";
	/** The Constant metal. */
	public static final String metal = "javax.swing.plaf.metal.MetalLookAndFeel";
	// private static final String motif =
	// "com.sun.java.swing.plaf.motif.MotifLookAndFeel";
	/** The Constant windows. */
	public static final String windows = "com.sun.java.swing.plaf.windows.WindowsLookAndFeel";

	/** The Constant gtk. */
	public static final String gtk = "org.jb2011.lnf.windows2.Windows2LookAndFeel";
	
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
