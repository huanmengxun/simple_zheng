package com.zheng.utils.file.image;

import java.awt.image.BufferedImage;
import java.io.File;

import javax.imageio.ImageIO;

import net.sourceforge.tess4j.ITesseract;
import net.sourceforge.tess4j.Tesseract;
import net.sourceforge.tess4j.TesseractException;
import net.sourceforge.tess4j.util.ImageHelper;

//https://blog.csdn.net/thc1987/article/details/76457474/
//http://tess4j.sourceforge.net/docs/docs-3.4/
public class TesseractExample {

	// 使用英文字库 - 识别图片
	public static void testEn() throws Exception {
		File imageFile = new File("C:\\Users\\nike\\Pictures\\201905051104363598.png");
		BufferedImage image = ImageIO.read(imageFile);
		// 对图片进行处理
		image = convertImage(image);
		ITesseract instance = new Tesseract();// JNA Interface Mapping
		instance.setDatapath("C:\\Users\\nike\\Downloads\\tessdata");// 设置你的Tess4J下的tessdata目录
		String result = instance.doOCR(image); // 识别
		System.out.println(result);
	}

	// 使用中文字库 - 识别图片
	public static void testZh() throws Exception {
		File imageFile = new File("E:\\pdf\\23种JAVA设计模式和15种J2EE设计模式\\23种JAVA设计模式和15种J2EE设计模式_14.png");
		BufferedImage image = ImageIO.read(imageFile);
		// 对图片进行处理
		// image = convertImage(image);
		ITesseract instance = new Tesseract();// JNA Interface Mapping
		instance.setDatapath("C:\\Users\\nike\\Downloads\\tessdata");// 设置你的Tess4J下的tessdata目录
		instance.setLanguage("chi_sim");// 指定需要识别的语种
		String result = instance.doOCR(image); // 识别
		System.out.println(result);
	}

	// 对图片进行处理 - 提高识别度
	public static BufferedImage convertImage(BufferedImage image) throws Exception {
		// 按指定宽高创建一个图像副本
		// image = ImageHelper.getSubImage(image, 0, 0, image.getWidth(),
		// image.getHeight());
		// 图像转换成灰度的简单方法 - 黑白处理
		image = ImageHelper.convertImageToGrayscale(image);
		// 图像缩放 - 放大n倍图像
		image = ImageHelper.getScaledInstance(image, image.getWidth() * 3, image.getHeight() * 3);
		return image;
	}

	public static void main(String[] args) throws Exception {
		testZh();
	}
}