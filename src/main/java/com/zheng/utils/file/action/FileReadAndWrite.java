package com.zheng.utils.file.action;

import java.io.ByteArrayOutputStream;
import java.io.DataInputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.net.HttpURLConnection;
import java.util.List;

import com.zheng.utils.dataUtil.FileNameUtils;
import com.zheng.utils.internet.InternerUrl;
import com.zheng.utils.mylog.MyLoggerInfo;

/**
 * 功能描述：文件读写
 * 
 * @Package: com.zheng.utils.file.action
 * @author: zheng
 */
public class FileReadAndWrite {
	static MyLoggerInfo log=MyLoggerInfo.getInstance();
	public static void writeAppointFile(String file, List<String> string) {
		writeAppointFile(file, string, false);
	}
	/**
	 * 功能描述：将list里面的字符串放置在指定文件夹之中
	 *
	 * @author: zheng
	 * @param file 写文件
	 * @param string 字符串
	 * @param isLevelOrgin 文件写方式，覆盖或者添加
	 */
	public static void writeAppointFile(String file, List<String> string,boolean isLevelOrgin) {
		File newFile = new File(file);
		newFile.getParentFile().mkdirs();
		try (FileWriter fw = new FileWriter(newFile,isLevelOrgin)) {
			for (String s : string) {
				fw.write("\t" + s + "\r\n");
			}
//			log.info("{}文件写入成功", file);
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
		FileNameUtils.ensureFileExists(path);
		String filename = "";
		switch (nameRegular.toUpperCase()) {
		case "orgin":
			filename = path + File.separator + url.substring(url.lastIndexOf("/"));
			break;
		default:// 默认为time
			filename = FileNameUtils.getTimeName(url, path);
		}
		File file = new File(filename);
		if (file.exists()) {
			return;
		}
		HttpURLConnection connection = InternerUrl.urlConnection(FileNameUtils.validateStartUrl(url));
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
