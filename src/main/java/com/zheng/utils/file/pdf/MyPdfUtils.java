package com.zheng.utils.file.pdf;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;

import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.rendering.PDFRenderer;

import com.itextpdf.text.pdf.PdfReader;
import com.zheng.utils.common.MyComUtils;
import com.zheng.utils.tool.mylog.MyLoggerInfo;

public class MyPdfUtils {
	static MyLoggerInfo log = MyLoggerInfo.getInstance();

//	https://www.cnblogs.com/goldenVip/p/6104051.html
//	public static void pdf2Html() {
//		
//	}
	public static void main(String[] args) {
		pdf2Image("D:\\《内外兼修(程序员的成长之路)》.pdf", "D:\\", 200);
	}

	/***
	 * PDF文件转PNG图片，全部页数
	 * 
	 * @param PdfFilePath pdf完整路径
	 * @param imgFilePath 图片存放的文件夹
	 * @param dpi         dpi越大转换后越清晰，相对转换速度越慢
	 * @return
	 */
	public static void pdf2Image(String PdfFilePath, String dstImgFolder, int dpi) {
		File file = new File(PdfFilePath);
		String imgPDFPath = file.getParent();
		int dot = file.getName().lastIndexOf('.');
		String imagePDFName = file.getName().substring(0, dot); // 获取图片文件名
		String imgFolderPath = null;
		if (MyComUtils.isEmpty(dstImgFolder)) {
			imgFolderPath = imgPDFPath + File.separator + imagePDFName;// 获取图片存放的文件夹路径
		} else {
			imgFolderPath = dstImgFolder + File.separator + imagePDFName;
		}
		new File(imgFolderPath).mkdirs();
		try (PDDocument pdDocument = PDDocument.load(file);) {
			PDFRenderer renderer = new PDFRenderer(pdDocument);
			/* dpi越大转换后越清晰，相对转换速度越慢 */
			PdfReader reader = new PdfReader(PdfFilePath);
			int pages = reader.getNumberOfPages();
			StringBuffer imgFilePath = null;
			for (int i = 0; i < pages; i++) {
				imgFilePath = new StringBuffer();
				imgFilePath.append(imgFolderPath);
				imgFilePath.append(File.separator);
				imgFilePath.append(imagePDFName);
				imgFilePath.append("_");
				imgFilePath.append(String.valueOf(i + 1));
				imgFilePath.append(".png");
				File dstFile = new File(imgFilePath.toString());
				BufferedImage image = renderer.renderImageWithDPI(i, dpi);
				ImageIO.write(image, "png", dstFile);
			}

		} catch (IOException e) {
			e.printStackTrace();
		}
		log.info("PDF文档转PNG图片成功！");

	}

}
