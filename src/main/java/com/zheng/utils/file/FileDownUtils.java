package com.zheng.utils.file;

import java.io.ByteArrayOutputStream;
import java.io.DataInputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.net.HttpURLConnection;

import com.zheng.utils.internet.InternerUrl;

import lombok.extern.log4j.Log4j2;

/**
 * 功能描述：文件下载类
 * 
 * @Package: com.zheng.utils.file
 * @author: zheng
 * @date: 2019年5月21日 上午8:44:42
 */
@Log4j2
public class FileDownUtils {
	public static void downUrlFileAndName(String url, String path, String nameRegular) {
		FileNameUtils.ensureFileExists(path);
		String filename = "";
		switch (nameRegular.toUpperCase()) {
		case "orgin":
			filename = path + File.separator + url.substring(url.lastIndexOf("/"));
			break;
		default://	默认为time
			int lastPoint=url.lastIndexOf(".");
			if(url.lastIndexOf("/")>lastPoint) {
				filename = path + File.separator + System.currentTimeMillis()  ;
			}else {
				filename = path + File.separator + System.currentTimeMillis()  +url.substring(lastPoint);
			}
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
