package com.zheng.utils.file;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.net.URISyntaxException;
import java.net.URL;
import java.net.URLConnection;

import com.zheng.utils.mylog.MyLoggerInfo;

public class MyFileTypeUtil {
	// 缓存文件头信息-文件头信息
	static MyLoggerInfo log = MyLoggerInfo.getInstance();

	public static void main(String[] args) throws URISyntaxException, IOException {
	}

	/**
	 * 功能描述：判断是否图片
	 *
	 * @author: zheng
	 * @param filePath 图片路径
	 * @return
	 */
	public static Boolean isIamge(String filePath) {
		File file = new File(filePath);
		return isIamge(file);
	}

	/**
	 * 功能描述：判断是否图片
	 *
	 * @author: zheng
	 * @param file 图片
	 * @return
	 */
	public static Boolean isIamge(File file) {
		String type = getFileTypeByString(getFileHeader(file));
		if (type.equals("JPEG") || type.equals("GIF") || type.equals("PNG") || type.equals("TIFF")) {
			return true;
		}
		return false;
	}

	public static String getUrlType(String urlPath) {
		try {
			URL url = new URL(urlPath);
			return getUrlType(url);
		} catch (MalformedURLException e) {
			e.printStackTrace();
		}
		return "undefined";
	}

	public static String getUrlType(URL url) {
		try {
			URLConnection conn = url.openConnection();
			InputStream is = conn.getInputStream();
			byte[] b = new byte[4];
			is.read(b, 0, b.length);
			return getFileTypeByString(bytesToHexString(b));
		} catch (IOException e) {
			e.printStackTrace();
		}
		return "undefined";

	}

	/**
	 * 功能描述：
	 * 
	 * @author: zheng 获取文件类型
	 * @param filePath 文件路径（包括文件名称）
	 * @return
	 */
	public static String getFileType(String filePath) {
		File file = new File(filePath);
		return getFileTypeByString(getFileHeader(file));
	}

	public static String getFileType(File file) {
		return getFileTypeByString(getFileHeader(file));
	}

	/**
	 * 功能描述：获取文件后缀
	 *
	 * @author: zheng
	 * @param fileName 文件名称
	 * @return
	 */
	public static String getFileSuffix(String fileName) {
		int lastPoint = fileName.lastIndexOf(".");
		if (lastPoint == -1) {
			return "none";
		} else {
			return fileName.substring(lastPoint + 1);
		}
	}

//-------------私有方法-------------
	/**
	 * 功能描述： 根据文件路径获取文件头信息(主要是前面4个字节)
	 * 
	 * @author: zheng
	 * @param filePath
	 * @return
	 */
	private static String getFileHeader(File file) {
		if (!file.exists() || file.isDirectory()) {
			log.warn("文件不存在或该文件为目录");
			return "??";
		}
		String value = null;
		try (FileInputStream is = new FileInputStream(file);) {
			byte[] b = new byte[4];
			is.read(b, 0, b.length);
			value = bytesToHexString(b);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return value;
	}

	/**
	 * 
	 * 方法描述：将要读取文件头信息的文件的byte数组转换成string类型表示
	 * 
	 * @param bytes 要读取文件头信息的文件的byte数组
	 * @return 文件头信息
	 */
	private static String bytesToHexString(byte[] bytes) {
		StringBuilder builder = new StringBuilder();
		if (bytes == null || bytes.length < 1) {
			return null;
		}
		String hv;
		for (byte b : bytes) {
			// 以十六进制（基数 16）无符号整数形式返回一个整数参数的字符串表示形式，并转换为大写
			hv = Integer.toHexString(b & 0xFF).toUpperCase();
			if (hv.length() < 2) {
				builder.append(0);
			}
			builder.append(hv);
		}
		log.info(builder.toString());
		return builder.toString();
	}

	private static String getFileTypeByString(String headerString) {
		switch (headerString) {
		case "FFD8FFE0":
			return "JPEG";
		case "FFD8FFE1":
			return "JPEG";
		case "FFD8FFE8":
			return "JPEG";
		case "47494638":
			return "GIF";
		case "89504E47":
			return "PNG";
		case "49492A00":
			return "TIFF";
		case "424DC001":
			return "Windows Bitmap"; // bpm
		case "41433130":
			return "CAD";// dwg
		case "38425053":
			return "Adobe Photoshop";// psd
		case "7B5C727466":
			return "Rich Text Format"; // rtf
		case "3C3F786D6C":
			return "XML";
		case "68746D6C3E":
			return "HTML";
		case "44656C69766572792D646174653A":
			return "Email [thorough only]"; // eml
		case "CFAD12FEC5FD746F":
			return "Outlook Express";// dbx
		case "2142444E":
			return "Outlook";// pst
		case "D0CF11E0":
			return "MS Word/Excel"; // xls.or.doc
		case "5374616E64617264204A":
			return "MS Access";// mdb
		case "FF575043":
			return "WordPerfect";// wpd
		case "255044462D312E":
			return "Adobe Acrobat"; // pdf
		case "AC9EBD8F":
			return "Quicken"; // qdf
		case "E3828596":
			return "Windows Password";// pwl
		case "504B0304":
			return "ZIP Archive"; // 504B0304
		case "52617221":
			return "RAR Archive"; // rar
		case "57415645":
			return "Wave"; // wav
		case "41564920":
			return "AVI";// avi
		case "2E7261FD":
			return "Real Audio";// ram
		case "2E524D46":
			return "Real Media";// rm
		case "000001BA":
			return "MPEG";// mpg
		case "000001B3":
			return "MPEG";// mov
		case "6D6F6F76":
			return "Quicktime";
		case "3026B2758E66CF11":
			return "Windows Media";// asf
		case "4D546864":
			return "MIDI";// mid
		case "3C21444F":
			return "HTML";
		default:
			log.warn("未定义类型:{}", headerString);
			return "text?";
		}
	}
}