package com.zheng.utils.file;

import java.io.ByteArrayOutputStream;
import java.io.DataInputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.net.HttpURLConnection;

import com.zheng.utils.internet.InternerUrl;

/**
 * 功能描述：文件下载类
 * 
 * @Package: com.zheng.utils.file
 * @author: zheng
 * @date: 2019年5月21日 上午8:44:42
 */
public class FileDown {
	
	/**
	 * 功能描述：通过给与的url菜单下载文件
	 *
	 * @author: zheng
	 * @param url
	 * @param path
	 */
	static void downLoadByUrl(String url, String path) {
		FileNameUtils.ensureFileExists(path);
		HttpURLConnection connection = InternerUrl.urlConnection(FileNameUtils.validateStartUrl(url));
		try (DataInputStream dataInputStream = new DataInputStream(connection.getInputStream());
				FileOutputStream fileOutputStream = new FileOutputStream(
						new File(path + File.separator + FileNameUtils.getFileNameByUrl(url)));) {
			ByteArrayOutputStream output = new ByteArrayOutputStream();
			byte[] buffer = new byte[1024];
			int length;
			while ((length = dataInputStream.read(buffer)) > 0) {
				output.write(buffer, 0, length);
			}
			fileOutputStream.write(output.toByteArray());
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			connection.disconnect();
		}
	}
}
