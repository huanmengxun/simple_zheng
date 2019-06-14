package com.zheng.utils.file.word;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStreamWriter;
import java.io.Writer;

import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.text.PDFTextStripper;
import org.apache.poi.hwpf.extractor.WordExtractor;
import org.apache.poi.ooxml.POIXMLDocument;
import org.apache.poi.ooxml.extractor.POIXMLTextExtractor;
import org.apache.poi.openxml4j.opc.OPCPackage;
import org.apache.poi.xwpf.extractor.XWPFWordExtractor;

import com.zheng.utils.common.MyComUtils;
import com.zheng.utils.tool.mylog.MyLoggerInfo;

public class MyWordUtils {
	static MyLoggerInfo log=MyLoggerInfo.getInstance();
	public static void main(String[] args) {
//		File file=new File("F://test.html");
//		BufferedWriter bw=null;
//		FileOutputStream fos=null;
//		try {
//			com.itextpdf.styledxmlparser.jsoup.nodes.Document ment=Jsoup.parse("F://test.doc");
//			fos=new FileOutputStream(file);
//			bw=new BufferedWriter(new OutputStreamWriter(new FileOutputStream(file),"utf-8"));
//			bw.write(ment.html());
//			log.info(ment.toString());
//			log.info(ment.html());
//		} catch (UnsupportedEncodingException | FileNotFoundException e) {
//			e.printStackTrace();
//		} catch (IOException e) {
//			e.printStackTrace();
//		}finally {
//			try {
//				bw.close();
//				fos.close();
//			} catch (IOException e) {
//				// TODO Auto-generated catch block
//				e.printStackTrace();
//			}
//		}
//		
	}
	//TODO
		public static void pdf2WordOnlyText(String pdfFile) {
			try {
				PDDocument doc = PDDocument.load(new File(pdfFile));
				int pagenumber = doc.getNumberOfPages();
				pdfFile = pdfFile.substring(0, pdfFile.lastIndexOf('.'));
				String fileName = pdfFile + ".doc";
				File file = new File(fileName);
				if (!file.exists()) {
					file.createNewFile();
				}
				FileOutputStream fos = new FileOutputStream(fileName);
				Writer writer = new OutputStreamWriter(fos, "UTF-8");
				PDFTextStripper stripper = new PDFTextStripper();
				stripper.setSortByPosition(true);// 排序
				stripper.setStartPage(1);// 设置转换的开始页
				stripper.setEndPage(pagenumber);// 设置转换的结束页
				stripper.writeText(doc, writer);
				writer.close();
				doc.close();
				System.out.println("pdf转换word成功！");
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		
	/**
	 * 功能描述： 文字抽取
	 * 
	 * @param filePath
	 * @return
	 * @throws Exception
	 */
	public static String fileExtractText(String filePath) throws Exception {
		String text;
		if (MyComUtils.isEmpty(filePath)) {
			log.error("文件路径错误");
			return "";
		}
		File inputfile=new File(filePath);
		if(!inputfile.exists()||inputfile.isDirectory()) {
			log.error("该文件路径并不存在文件");
			return "";
		}
		if (filePath.toLowerCase().endsWith("doc")) {
			InputStream file = new FileInputStream(inputfile);
			WordExtractor wordExtractor = new WordExtractor(file);
			text = wordExtractor.getText();
			file.close();
			wordExtractor.close();
		} else if (filePath.toLowerCase().endsWith("docx")) {
			OPCPackage opcPackage = POIXMLDocument.openPackage(filePath);
			POIXMLTextExtractor extractor = new XWPFWordExtractor(opcPackage);
			text = extractor.getText();
			opcPackage.close();
			extractor.close();
		} else if (filePath.toLowerCase().endsWith("pdf")) {
			PDDocument pdDocument;
			InputStream file = new FileInputStream(inputfile);
			pdDocument = PDDocument.load(file);
			PDFTextStripper stripper = new PDFTextStripper();
			text = stripper.getText(pdDocument);
			file.close();
			pdDocument.close();
		} else {
			return "error file";
		}
		return text;
	}
}
