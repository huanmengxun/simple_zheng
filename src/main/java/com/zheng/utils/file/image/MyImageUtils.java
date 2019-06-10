package com.zheng.utils.file.image;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;

import com.itextpdf.text.Document;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.Image;
import com.itextpdf.text.PageSize;
import com.itextpdf.text.pdf.PdfWriter;
import com.zheng.utils.file.MyFileTypeUtil;
import com.zheng.utils.mylog.MyLoggerInfo;

public class MyImageUtils {
	static MyLoggerInfo log = MyLoggerInfo.getInstance();

	public static void main(String[] args) throws IOException {
		allImgToPdf("E:\\IMG", "F:\\1.pdf");
	}

	/**
	 * 功能描述： 将指定图片文件转为pdf
	 * 
	 * @author: zheng
	 * @param pdfFilePath
	 * @param imgFolder
	 * @throws IOException
	 */
	public static void ImgToPdf(String pdfFilePath, String... imgFilePaths) throws IOException {
		Document document = new Document();
		FileOutputStream fos = null;
		try {
			fos = new FileOutputStream(pdfFilePath);
			PdfWriter.getInstance(document, fos);
			// 添加PDF文档的某些信息，比如作者，主题等等
			// 设置文档的大小
			document.setPageSize(PageSize.A4);
			// 打开文档
			document.open();
			for (String imagePath : imgFilePaths) {
				File imageFile = new File(imagePath);
				if (imageFile.exists() && MyFileTypeUtil.isIamge(imageFile)) {
					// 读取一个图片
					Image image = handlerImageToA4(Image.getInstance(imagePath));
					// //设置图片的绝对位置
					document.add(image);
					log.info("插入成功");
				} else {
					log.warn("文件{}不存在", imagePath);
				}
			}
		} catch (DocumentException de) {
			System.out.println(de.getMessage());
		} catch (IOException ioe) {
			System.out.println(ioe.getMessage());
		}
		document.close();
		fos.flush();
		fos.close();
	}

	/**
	 * 功能描述：将制定目录里面的图片文件转为pdf
	 *
	 * @author: zheng
	 * @param imgFolder图片目录
	 * @param pdfFilePath
	 * @throws IOException
	 */
	public static void allImgToPdf(String pdfFilePath, String imgFolder) throws IOException {
		File folder = new File(imgFolder);
		if (folder.exists()) {
			Document document = new Document();
			FileOutputStream fos = null;
			try {
				fos = new FileOutputStream(pdfFilePath);
				PdfWriter.getInstance(document, fos);
				// 添加PDF文档的某些信息，比如作者，主题等等
				// 设置文档的大小
				document.setPageSize(PageSize.A4);
				// 打开文档
				document.open();
				for (File imagePath : folder.listFiles()) {
					if(MyFileTypeUtil.isIamge(imagePath)) {
						// 读取一个图片
						Image image = handlerImageToA4(Image.getInstance(imagePath.getAbsolutePath()));
						// //设置图片的绝对位置
						document.add(image);
						log.info("插入成功");
					}else {
						log.warn("文件{}并不是图片",imagePath);
					}
				}
			} catch (DocumentException de) {
				System.out.println(de.getMessage());
			} catch (IOException ioe) {
				System.out.println(ioe.getMessage());
			}
			document.close();
			fos.flush();
			fos.close();
		}
	}

	/**
	 * 功能描述：处理图片符合A4纸张
	 *
	 * @author: zheng
	 * @param image
	 * @return
	 */
	protected static Image handlerImageToA4(Image image) {
		return HandlerImage(image, 500, 500);
	}

	private static Image HandlerImage(Image image, float height, float width) {
		float imageHeight = image.getScaledHeight();
		float imageWidth = image.getScaledWidth();
		int i = 0;
		while (imageHeight > height || imageWidth > width) { // 将图片整理为合适的高度
			image.scalePercent(100 - i);
			i++;
			imageHeight = image.getScaledHeight();
			imageWidth = image.getScaledWidth();
//			System.out.println("imageHeight->" + imageHeight);
//			System.out.println("imageWidth->" + imageWidth);
		}
		image.setAlignment(Image.ALIGN_CENTER);
		return image;
	}
}
