package com.zheng.swing.models;
//package com.zheng.swing.model;
//
//import java.awt.Color;
//import java.awt.Graphics2D;
//import java.awt.Image;
//import java.awt.Toolkit;
//import java.awt.image.BufferedImage;
//import java.io.ByteArrayOutputStream;
//import java.io.File;
//import java.io.FileInputStream;
//import java.io.IOException;
//import java.util.ArrayList;
//import java.util.LinkedList;
//import java.util.List;
//
//import javax.imageio.ImageIO;
//import javax.imageio.ImageReader;
//import javax.imageio.stream.ImageInputStream;
//import javax.swing.ImageIcon;
//import javax.swing.JFrame;
//import javax.swing.JLabel;
//
//import org.w3c.dom.Node;
//
//import com.madgag.gif.fmsware.AnimatedGifEncoder;
//
///**
// * Title: L&F组件<br>
// * Description: GIFUtils工具类<br>
// * Copyright: Copyright (c) 2016-11-9<br>
// * MSun<br>
// * 
// * @author jiujiya
// * @version 1.0
// */
//public class GIFUtils {
//
//    private GIFUtils() {
//    }
//
//    public static List<BufferedImage> extractFrames(String filePath) throws IOException {
//        return extractFrames(new File(filePath));
//    }
//
//    public static List<BufferedImage> extractFrames(File file) throws IOException {
//        List<BufferedImage> imgs = new LinkedList<BufferedImage>();
//        ImageReader reader = ImageIO.getImageReadersBySuffix("GIF").next();
//        ImageInputStream in = null;
//        try {
//            in = ImageIO.createImageInputStream(new FileInputStream(file));
//            reader.setInput(in);
//            BufferedImage img = null;
//            int count = reader.getNumImages(true);
//            for(int i = 0; i < count; i++) {
//                Node tree = reader.getImageMetadata(i).getAsTree("javax_imageio_gif_image_1.0");
//                int x = Integer.valueOf(tree.getChildNodes().item(0).getAttributes()
//                        .getNamedItem("imageLeftPosition").getNodeValue());
//                int y = Integer.valueOf(tree.getChildNodes().item(0).getAttributes()
//                        .getNamedItem("imageTopPosition").getNodeValue());
//                BufferedImage image = reader.read(i);
//                if(img == null) {
//                    img = new BufferedImage(image.getWidth() + x, image.getHeight() + y,
//                            BufferedImage.TYPE_4BYTE_ABGR);
//                }
//
//                Graphics2D g = img.createGraphics();
//                g.drawImage(image, x, y, null);
//
//                BufferedImage copyImage = new BufferedImage(img.getWidth(), img.getHeight(),
//                        BufferedImage.TYPE_4BYTE_ABGR);
//                Graphics2D copyG = copyImage.createGraphics();
//                copyG.drawImage(img,0,0,null);
//
//                imgs.add(copyImage);
//
//                g.dispose();
//                copyG.dispose();
//            }
//        }
//        finally {
//            if(in != null) {
//                in.close();
//            }
//        }
//        return imgs;
//    }
//
//    public static Image createGif(List<BufferedImage> images, int millisForFrame) {
//        AnimatedGifEncoder g = new AnimatedGifEncoder();
//        g.setQuality(100);
//        g.setTransparent(new Color(0,0,0));
//        ByteArrayOutputStream out = new ByteArrayOutputStream(5 * 1024 * 1024);
//        g.start(out);
//        g.setDelay(millisForFrame);
//        g.setRepeat(1);
//        for(BufferedImage i : images) {
//            g.addFrame(i);
//        }
//        g.finish();
//        byte[] bytes = out.toByteArray();
//        return (Toolkit.getDefaultToolkit().createImage(bytes));
//    }
//
//    public static void main(String[] args) throws IOException {
////        File source = new File("G:\\软件\\QQ\\QQUserInfo\\503704309\\FileRecv\\加载.gif");
////        File targe = new File("F:\\workspace\\soft_client\\resource\\flowermsg\\images\\login_loading_icon.gif");
//        List<BufferedImage> list = new ArrayList<BufferedImage>(40);
//
//        for (int i = 1; i < 40 ; i++) {
//            BufferedImage image = ImageIO.read(new File("C:\\Users\\Jiang\\Desktop\\login_loading\\login_loading_" + i + ".gif"));
//            list.add(image);
//        }
//
////        list = extractFrames(source);
//
//        Image image = createGif(list ,100);
//
////        writeGif(list,targe,100);
//
////        ImageIO.write(image,"gif",targe);
//
////        BufferedImage im = new BufferedImage(30,30,BufferedImage.TYPE_INT_ARGB);
////        Graphics2D g2d = im.createGraphics();
////
////
//        JFrame f= new JFrame();
//        f.add(new JLabel(new ImageIcon(image)));
//        f.pack();
//        f.setVisible(true);
//
////        for (Image image: list) {
////            System.out.println(image);
////        }
//
////        System.out.println(list);
//        for (int i = 2; i < 41 ; i++) {
//            File file = new File("C:\\Users\\Jiang\\Desktop\\jiazai\\login_loading_" + i + ".gif");
//            file.renameTo(new File("C:\\Users\\Jiang\\Desktop\\jiazai\\login_loading_" + (i - 1) + ".gif"));
//        }
//
//    }
//
//}
