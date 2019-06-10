package com.zheng.utils.file;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;

public class Test {
	
//    ("FFD8FF", "jpg");
//    ("89504E47", "png");
//    ("47494638", "gif");
//    ("49492A00", "tif");
//    ("424D", "bmp"); //
//    ("41433130", "dwg"); // CAD
//    ("38425053", "psd");
//    ("7B5C727466", "rtf"); // 日记本
//    ("3C3F786D6C", "xml");
//    ("68746D6C3E", "html");
//    ("44656C69766572792D646174653A", "eml"); // 邮件
//    ("D0CF11E0", "doc");  // word or xls
//    ("5374616E64617264204A", "mdb");
//    ("252150532D41646F6265", "ps");
//    ("255044462D312E", "pdf");
//    ("504B0304", "docx");  //word  or  xlsx
//    ("52617221", "rar");
//    ("57415645", "wav");
//    ("41564920", "avi");
//    ("2E524D46", "rm");
//    ("000001BA", "mpg");
//    ("000001B3", "mpg");
//    ("6D6F6F76", "mov");
//    ("3026B2758E66CF11", "asf");
//    ("4D546864", "mid");
//    ("1F8B08", "gz");
//    ("4D5A9000", "exe/dll");
//    ("75736167", "txt");
	/**
	 * 获得指定文件的byte数组
	 */
	public static byte[] getBytes(String filePath) {
		byte[] buffer = null;
		try {
			File file = new File(filePath);
			FileInputStream fis = new FileInputStream(file);
			ByteArrayOutputStream bos = new ByteArrayOutputStream(10);
			byte[] b = new byte[4];
			int n;
			while ((n = fis.read(b)) != -1) {
				bos.write(b, 0, n);
			}
			fis.close();
			bos.close();
			buffer = bos.toByteArray();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return buffer;
	}

	/**
	 * 将要读取文件头信息的文件的byte数组转换成string类型表示 * *
	 * 
	 * @param src 要读取文件头信息的文件的byte数组
	 * @return 文件头信息
	 */
	private static String bytesToHexString(byte[] src) {
		StringBuilder builder = new StringBuilder();
		if (src == null || src.length <= 0) {
			return null;
		}
		String hv;
		for (int i = 0; i < src.length; i++) {
			// 以十六进制（基数 16）无符号整数形式返回一个整数参数的字符串表示形式，并转换为大写
			hv = Integer.toHexString(src[i] & 0xFF).toUpperCase();
			if (hv.length() < 2) {
				builder.append(0);
			}
			builder.append(hv);
		}

		return builder.toString();
	}

	/**
	 * 判断文件是否为xls 或者 xlsx,是返回true
	 * 
	 * @return boolean
	 * 
	 * 
	 */
	public static boolean fileTypes(byte[] bytes) {
		// 文件的文件头信息
		byte[] b = new byte[4];

		for (int i = 0; i < b.length; i++) {
			b[i] = bytes[i];
		}
		System.out.println(b.length);
		// 将文件的文件头字节码转换成字符串
		String filetype = bytesToHexString(b);
		System.out.println(filetype);
		if ("504B0304".equals(filetype) || "D0CF11E0".equals(filetype)) {
			// doc 和 docx 文件
			return true;
		}

		return false;
	}

	public static void main(String[] args) throws Exception {
		// File file = new File("C:\\Users\\Administrator\\Desktop\\aaa\\aa.xlsx");
		byte[] bytes = getBytes("F:\\新建 PPTX 演示文稿.pptx");
		System.out.println(fileTypes(bytes));

	}
}
