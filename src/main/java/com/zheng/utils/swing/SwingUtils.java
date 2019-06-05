package com.zheng.utils.swing;

import java.awt.AlphaComposite;
import java.awt.Color;
import java.awt.Component;
import java.awt.Container;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.RenderingHints;
import java.awt.Toolkit;
import java.awt.Window;
import java.awt.color.ColorSpace;
import java.awt.image.BufferedImage;
import java.awt.image.ColorConvertOp;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

import javax.imageio.ImageIO;
import javax.swing.AbstractButton;
import javax.swing.JComponent;

import sun.awt.SunHints;
import sun.print.ProxyPrintGraphics;

/**
 * Swing 工具类
 * */
public class SwingUtils {
			
    public final static Color POPMENU_BORDER_COLOR = new Color(102, 102, 102);

    public final static Color GRAY_COLOR = new Color(133, 133, 133);
    public final static Color MENUITEMICON_BACKGROUND_COLOR = new Color(233, 233, 233);
    public final static Color MENUITEM_SELECTEDBACKGROUND_COLOR = new Color(55, 142, 206);
    
    
    /**
     * 功能描述：
     *
     * @param path 图片所在路径 
     * @return
     */
    public static BufferedImage getImage(String path) {
        /**
         * 此方法不稳定 有可能获取不到图片 Image image =
         * Toolkit.getDefaultToolkit().getImage(SwingUtil
         * .class.getResource(path));
         * */
        BufferedImage image = null;
        InputStream input = null;
        try {
            input = getFileInputStream(path);
            image = ImageIO.read(input);
        } catch (IOException e) {
            System.err.println("路径为:" + path + " 未能正确读取");
            e.printStackTrace();
        } finally {
            if (input != null) {
                try {
                    input.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
        return image;
    }

    public static InputStream getFileInputStream(String path) {
        return SwingUtils.class.getResourceAsStream(path);
    }

    public static InputStream getFileInputStream(String path, Class<?> clazz) {
        return clazz.getResourceAsStream(path);
    }

    public static Image getImage(String path, Class<?> clazz) {
        Image image = null;
        InputStream input = null;
        try {
            input = getFileInputStream(path, clazz);
            image = ImageIO.read(input);
        } catch (IOException e) {
            System.err.println("路径为:" + path + " 未能正确读取");
            e.printStackTrace();
        } finally {
            if (input != null) {
                try {
                    input.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
        return image;
    }

    public static InputStream getFileInputStream(File file) throws FileNotFoundException {
        return new FileInputStream(file);
    }

    public static BufferedImage getImage(File file) {
        BufferedImage image = null;
        try {
            image = ImageIO.read(file);
        } catch (IOException e) {
            System.err.println("路径为:" + file + " 未能正确读取");
            e.printStackTrace();
        }
        if (image == null) {
            throw new RuntimeException();
        }
        return image;
    }

    public static BufferedImage getGrayImage(BufferedImage image) {
        int imageWidth = image.getWidth();
        int imageHeight = image.getHeight();

        BufferedImage newPic = new BufferedImage(imageWidth, imageHeight, BufferedImage.TYPE_3BYTE_BGR);

        ColorConvertOp cco = new ColorConvertOp(ColorSpace.getInstance(ColorSpace.CS_GRAY), null);
        cco.filter(image, newPic);
        return newPic;
    }

    public static void setWindowCenter(Component c) {
        Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
        if (c instanceof Window) {
            Dimension componSize = c.getSize();
            c.setLocation((screenSize.width - componSize.width) / 2, (screenSize.height - componSize.height) / 2);
        }
    }

    public static void setWindowRight(Component c) {
        Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
        if (c instanceof Window) {
            Dimension componSize = c.getSize();
            c.setLocation(screenSize.width - 50 - componSize.width, (screenSize.height - componSize.height) / 2);
        }
    }

    public static Graphics2D getGraphics2D(Graphics paramGraphics) {
        Graphics2D g2d = null;
        if (paramGraphics instanceof Graphics2D)
            g2d = ((Graphics2D) paramGraphics);
        if (paramGraphics instanceof ProxyPrintGraphics)
            g2d = ((Graphics2D) (Graphics2D) ((ProxyPrintGraphics) paramGraphics).getGraphics());
        return g2d;
    }

    /**
     * 获取文Shape显示优化的 Graphics2D
     * */
    public static Graphics2D getShapeGraphics2D(Graphics paramGraphics) {
        Graphics2D g2d = null;
        if (paramGraphics instanceof Graphics2D)
            g2d = ((Graphics2D) paramGraphics);
        if (paramGraphics instanceof ProxyPrintGraphics)
            g2d = ((Graphics2D) (Graphics2D) ((ProxyPrintGraphics) paramGraphics).getGraphics());
        if (g2d != null) {
            // 抗锯齿,绘制质量等可参考RenderingHints类的文档！
            g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        }
        return g2d;
    }

    /**
     * 获取文本字体显示优化的 Graphics2D 不建议试用
     * */
    public static Graphics2D getTextGraphics2D(Graphics paramGraphics) {
        Graphics2D g2d = null;
        if (paramGraphics instanceof Graphics2D)
            g2d = ((Graphics2D) paramGraphics);
        if (paramGraphics instanceof ProxyPrintGraphics)
            g2d = ((Graphics2D) (Graphics2D) ((ProxyPrintGraphics) paramGraphics).getGraphics());
        if (g2d != null) {
            g2d.setRenderingHint(SunHints.KEY_TEXT_ANTIALIAS_LCD_CONTRAST, 140);
            g2d.setRenderingHint(SunHints.KEY_ANTIALIASING, SunHints.VALUE_ANTIALIAS_OFF);
            g2d.setRenderingHint(SunHints.KEY_RENDERING, SunHints.VALUE_RENDER_DEFAULT);
            g2d.setRenderingHint(SunHints.KEY_TEXT_ANTIALIASING, SunHints.VALUE_TEXT_ANTIALIAS_DEFAULT);
            g2d.setRenderingHint(SunHints.KEY_STROKE_CONTROL, SunHints.VALUE_STROKE_DEFAULT);
            g2d.setRenderingHint(SunHints.KEY_FRACTIONALMETRICS, SunHints.VALUE_FRACTIONALMETRICS_OFF);
            if ("微软雅黑".equals(g2d.getFont().getName())) {
                g2d.setRenderingHint(RenderingHints.KEY_FRACTIONALMETRICS, RenderingHints.VALUE_FRACTIONALMETRICS_ON);
                g2d.setRenderingHint(RenderingHints.KEY_TEXT_ANTIALIASING, RenderingHints.VALUE_TEXT_ANTIALIAS_ON);
            }
        }
        return g2d;
    }

    // public static Image getStringImage(String s,Dimension dim){
    // BufferedImage
    //
    // return null;
    // }

    public static void handlrButton(AbstractButton b) {
        // 设置不绘制边框
        b.setBorderPainted(false);
        // 设置不绘制背景
        b.setContentAreaFilled(false);
        // 设置不绘制焦点是的边框
        b.setFocusPainted(false);
        b.setOpaque(false);
    }

    public static Image getBLineImageW(int width) {
        BufferedImage image = new BufferedImage(width, 1, BufferedImage.TYPE_INT_ARGB);
        Graphics2D g2d = image.createGraphics();
        int tempw = width / 2;
        float alpha = 1F / (float) tempw;
        for (int i = 0; i < tempw; i++) {
            g2d.setComposite(AlphaComposite.getInstance(AlphaComposite.DST_OVER, alpha * (float) i > 1 ? 1 : alpha
                    * (float) i));
            g2d.setColor(Color.BLACK);
            g2d.drawLine(i, 0, i + 1, 0);
        }

        for (int i = 0; i < tempw; i++) {
            g2d.setComposite(AlphaComposite.getInstance(AlphaComposite.DST_OVER, alpha * (float) i > 1 ? 1 : alpha
                    * (float) i));
            g2d.setColor(Color.BLACK);
            g2d.drawLine(width - i, 0, width - i - 1, 0);
        }

        g2d.dispose();
        return image;
    }

    public static Image getWBLineImageW(int width) {
        BufferedImage image = new BufferedImage(width, 2, BufferedImage.TYPE_INT_ARGB);
        Graphics2D g2d = image.createGraphics();
        int tempw = width / 2;
        float alpha = 1F / (float) tempw;
        for (int i = 0; i < tempw; i++) {
            g2d.setComposite(AlphaComposite.getInstance(AlphaComposite.DST_OVER, alpha * (float) i > 1 ? 1 : alpha
                    * (float) i));
            g2d.setColor(Color.WHITE);
            g2d.drawLine(i, 0, i + 1, 0);
            g2d.setColor(Color.BLACK);
            g2d.drawLine(i, 1, i + 1, 1);
        }

        for (int i = 0; i < tempw; i++) {
            g2d.setComposite(AlphaComposite.getInstance(AlphaComposite.DST_OVER, alpha * (float) i > 1 ? 1 : alpha
                    * (float) i));
            g2d.setColor(Color.WHITE);
            g2d.drawLine(width - i, 0, width - i - 1, 0);
            g2d.setColor(Color.BLACK);
            g2d.drawLine(width - i, 1, width - i - 1, 1);
        }

        g2d.dispose();
        return image;
    }

    public static Image getBWLineImageW(int width) {
        BufferedImage image = new BufferedImage(width, 2, BufferedImage.TYPE_INT_ARGB);
        Graphics2D g2d = image.createGraphics();
        int tempw = width / 2;
        float alpha = 1F / (float) tempw;
        for (int i = 0; i < tempw; i++) {
            g2d.setComposite(AlphaComposite.getInstance(AlphaComposite.DST_OVER, alpha * (float) i > 1 ? 1 : alpha
                    * (float) i));
            g2d.setColor(Color.BLACK);
            g2d.drawLine(i, 0, i + 1, 0);
            g2d.setColor(Color.WHITE);
            g2d.drawLine(i, 1, i + 1, 1);
        }

        for (int i = 0; i < tempw; i++) {
            g2d.setComposite(AlphaComposite.getInstance(AlphaComposite.DST_OVER, alpha * (float) i > 1 ? 1 : alpha
                    * (float) i));
            g2d.setColor(Color.BLACK);
            g2d.drawLine(width - i, 0, width - i - 1, 0);
            g2d.setColor(Color.WHITE);
            g2d.drawLine(width - i, 1, width - i - 1, 1);
        }

        g2d.dispose();
        return image;
    }

    public static Image getWBLineImageH(int height) {
        BufferedImage image = new BufferedImage(2, height, BufferedImage.TYPE_INT_ARGB);
        Graphics2D g2d = image.createGraphics();
        int temph = height / 2;
        float alpha = 1F / (float) temph;
        for (int i = 0; i < temph; i++) {
            g2d.setComposite(AlphaComposite.getInstance(AlphaComposite.DST_OVER, alpha * (float) i > 1 ? 1 : alpha
                    * (float) i));
            g2d.setColor(Color.WHITE);
            g2d.drawLine(0, i, 0, i + 1);
            g2d.setColor(Color.BLACK);
            g2d.drawLine(1, i, 1, i + 1);
        }

        for (int i = 0; i < temph; i++) {
            g2d.setComposite(AlphaComposite.getInstance(AlphaComposite.DST_OVER, alpha * (float) i > 1 ? 1 : alpha
                    * (float) i));
            g2d.setColor(Color.WHITE);
            g2d.drawLine(0, height - i, 0, height - i - 1);
            g2d.setColor(Color.BLACK);
            g2d.drawLine(1, height - i - 1, 1, height - i - 1);
        }

        g2d.dispose();
        return image;
    }

    public static BufferedImage createImage(int w, int h) {
        return new BufferedImage(w, h, BufferedImage.TYPE_INT_ARGB);
    }

    public static void buttonHandler(AbstractButton button) {
        button.setBorder(null);
        // 设置不绘制边框
        button.setBorderPainted(false);
        // 设置不绘制背景
        button.setContentAreaFilled(false);
        // 设置不绘制焦点是的边框
        button.setFocusPainted(false);
        button.setOpaque(false);
    }

    public static List<JComponent> getContainerJComponentChildAll(Container parent) {
        List<JComponent> list = new ArrayList<JComponent>();

        for (int i = 0; i < parent.getComponentCount(); i++) {
            Component c = parent.getComponent(i);
            if (c instanceof JComponent) {
                JComponent jc = (JComponent) parent.getComponent(i);
                List<JComponent> l = getContainerJComponentChildAll(jc);
                list.add(jc);

                list.addAll(l);
            } else if (c instanceof Container) {
                Container jc = (Container) parent.getComponent(i);
                List<JComponent> l = getContainerJComponentChildAll(jc);

                list.addAll(l);
            }
        }

        return list;
    }

    /**
     * 获得一个 字符串的 最佳宽度
     * 
     * @param str
     * @return
     */
    public static int getPerWidth(String str, int fontSize) {
        if (str == null || str.trim().equals("")) {
            return 0;
        }
        int count = 0;
        int chCharCount = 0;
        char[] charArray = str.toCharArray();
        for (int i = 0; i < charArray.length; i++) {
            char cn = charArray[i];
            byte[] bytes = (String.valueOf(cn)).getBytes();
            if (bytes.length == 2) { // 双字节字符
                count++;
                int hightByte = 256 + bytes[0];
                int lowByte = 256 + bytes[1];
                int ascii = (256 * hightByte + lowByte) - 256 * 256;
                if (ascii >= -20319 && ascii < -10247) { // 中文字符
                    chCharCount++;
                }
            }
        }
        return count * fontSize + (charArray.length - count) * fontSize / 2;
    }

    /**
     * 获得一个随机颜色
     * 
     * @return
     */
    public static Color getColor() {
        Color color = new Color((new Double(Math.random() * 128)).intValue() + 128,
                (new Double(Math.random() * 128)).intValue() + 128, (new Double(Math.random() * 128)).intValue() + 128);
        return color;

    }

}
