package com.zheng.utils.file.html;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;

import com.itextpdf.html2pdf.ConverterProperties;
import com.itextpdf.html2pdf.HtmlConverter;
import com.itextpdf.styledxmlparser.css.media.MediaDeviceDescription;
import com.itextpdf.styledxmlparser.css.media.MediaType;

public class MyHtmlUtils {
//	public static final String BASEURI = "src/main/resources/html/";
//	public static final String SRC = String.format("%ssxsw.html", BASEURI);
//	public static final String TARGET = "target/results/ch03/";
//	public static final String DEST = String.format("%ssxsw_print.pdf", TARGET);

	public static void main(String[] args) throws IOException {
		String baseUrl="C:\\Users\\nike\\Pictures";
		String src="C:\\Users\\nike\\Pictures\\The Central Repository Search Engine.html";
		String dest="f://test.pdf";
		createPdf(baseUrl,src,dest);
	}

	
	public static void createPdf(String baseUri, String src, String dest) throws IOException {
		ConverterProperties properties = new ConverterProperties();
		properties.setBaseUri(baseUri);
		// 设置媒体的描述方式
		MediaDeviceDescription mediaDeviceDescription = new MediaDeviceDescription(MediaType.PRINT);
		properties.setMediaDeviceDescription(mediaDeviceDescription);
		HtmlConverter.convertToPdf(new FileInputStream(src), new FileOutputStream(dest), properties);
	}
}
