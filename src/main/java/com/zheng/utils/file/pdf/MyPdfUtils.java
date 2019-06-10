package com.zheng.utils.file.pdf;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;

import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.rendering.PDFRenderer;

import com.itextpdf.text.pdf.PdfReader;
import com.zheng.utils.common.MyCommonUtils;

public class MyPdfUtils {
//	https://www.cnblogs.com/goldenVip/p/6104051.html
	public static void pdf2Html() {
		
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
		if (MyCommonUtils.isEmpty(dstImgFolder)) {
			imgFolderPath = imgPDFPath + File.separator + imagePDFName;// 获取图片存放的文件夹路径
		} else {
			imgFolderPath = dstImgFolder + File.separator + imagePDFName;
		}
		if (new File(imgFolderPath).mkdirs()) {
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
			System.out.println("PDF文档转PNG图片成功！");
		} else {
			System.out.println("PDF文档转PNG图片失败：" + "创建" + imgFolderPath + "失败");
		}

	}

}
