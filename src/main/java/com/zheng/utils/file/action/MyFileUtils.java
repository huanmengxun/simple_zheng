package com.zheng.utils.file.action;

import java.io.ByteArrayOutputStream;
import java.io.DataInputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.net.HttpURLConnection;
import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import com.zheng.utils.file.MyFileNameUtils;
import com.zheng.utils.internet.InternUtils;
import com.zheng.utils.tool.mylog.MyLoggerInfo;

/**
 * 功能描述：文件读写
 * 
 * @Package: com.zheng.utils.file.action
 * @author: zheng
 */
public class MyFileUtils {
	static MyLoggerInfo log=MyLoggerInfo.getInstance();
	/**
	 * 功能描述：按 文件大小 排序递增
	 *
	 * @param files
	 * @return
	 */
	public static File[] orderByLength(File[] files) {
		List<File> fileList = Arrays.asList(files);
		Collections.sort(fileList, new Comparator<File>() {
			public int compare(File f1, File f2) {
				long diff = f1.length() - f2.length();
				return diff > 0 ? 1 : diff < 0 ? -1 : 0;
			}

			public boolean equals(Object obj) {
				return true;
			}
		});
		return files;
	}

	/**
	 * 功能描述：按 文件修改日期：递增
	 * 
	 * @param files
	 * @return
	 */
	public static File[] orderByDate(File[] files) {
		Arrays.sort(files, new Comparator<File>() {
			public int compare(File f1, File f2) {
				long diff = f1.lastModified() - f2.lastModified();
				return diff > 0 ? 1 : diff < 0 ? -1 : 0;
			}

			public boolean equals(Object obj) {
				return true;
			}
		});
		return files;
	}

	/**
	 * 功能描述： 按照文件名排序
	 * 
	 * @param files
	 * @return
	 */
	public static File[] orderByName(File[] files) {
		List<File> fileList = Arrays.asList(files);
		Collections.sort(fileList, new Comparator<File>() {
			@Override
			public int compare(File o1, File o2) {
				if (o1.isDirectory() && o2.isFile())
					return -1;
				if (o1.isFile() && o2.isDirectory())
					return 1;
				return o1.getName().compareTo(o2.getName());
			}
		});
		return files;
	}
	/**
	 * 功能描述：将list里面的字符串放置在指定文件夹之中
	 * @author: zheng
	 * @param fileName 文件名
	 * @param string 字符串
	 */
	public static void writeAppointFile(String fileName, List<String> string) {
		writeAppointFile(fileName, string, false);
	}
	/**
	 * 功能描述：将list里面的字符串放置在指定文件夹之中
	 *
	 * @author: zheng
	 * @param fileName 文件名
	 * @param string 字符串
	 * @param isLevelOrgin 文件写方式，覆盖或者添加
	 */
	public static void writeAppointFile(String fileName, List<String> string,boolean isLevelOrgin) {
		File newFile = new File(fileName);
		newFile.getParentFile().mkdirs();
		try (FileWriter fw = new FileWriter(newFile,isLevelOrgin)) {
			for (String s : string) {
				fw.write("\t" + s + "\r\n\r\n");
			}
			log.info("{}文件写入成功", fileName);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}
	/**
	 * 功能描述：通过url下载文件到制定本路径
	 *
	 * @author: zheng  
	 * @param url
	 * @param path
	 * @param nameRegular
	 */
	public static void downUrlFileAndName(String url, String path, String nameRegular) {
		MyFileNameUtils.ensureFileExists(path);
		String filename = "";
		switch (nameRegular.toUpperCase()) {
		case "orgin":
			filename = path + File.separator + url.substring(url.lastIndexOf('/'));
			break;
		default:// 默认为time
//			filename = FileNameUtils.getTimeName(url, path);
			filename = path + File.separator + System.currentTimeMillis()+".png";
		}
		File file = new File(filename);
		if (file.exists()) {
			return;
		}
		HttpURLConnection connection = InternUtils.getUrlConn(MyFileNameUtils.validateStartUrl(url));
		if (connection == null)
			return;
		try (DataInputStream dataInputStream = new DataInputStream(connection.getInputStream());
				FileOutputStream fileOutputStream = new FileOutputStream(file);) {
			ByteArrayOutputStream output = new ByteArrayOutputStream();
			byte[] buffer = new byte[1024];
			int length;
			while ((length = dataInputStream.read(buffer)) > 0) {
				output.write(buffer, 0, length);
			}
			fileOutputStream.write(output.toByteArray());
			log.info("{}下载成功", filename);
		} catch (IOException e) {
			e.printStackTrace();
//			log.warn(e.getMessage());
		} finally {
			connection.disconnect();
		}
	}

	public static void downUrlFileAndNameByTime(String url, String path) {
		downUrlFileAndName(url, path, "time");
	}

	public static void downUrlFileAndNameByOrgin(String url, String path) {
		downUrlFileAndName(url, path, "orgin");
	}
}
