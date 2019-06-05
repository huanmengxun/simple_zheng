package com.zheng.utils.internet;

import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.ProtocolException;
import java.net.URL;

import com.zheng.utils.mylog.MyLoggerInfo;

/**
 * 功能描述：网络连接的处理
 * 
 * @Package: com.zheng.utils.internet
 * @author: zheng
 */
public class InternerUrl {
	static MyLoggerInfo log=MyLoggerInfo.getInstance();
	/**
	 * 功能描述：获取绝对路径
	 *
	 * @author: zheng
	 * @param url
	 * @return
	 */
	public String absUrl(String url) {
		return url;
	}

	public static HttpURLConnection urlConnection(String url) {
		URL urls = null;
		try {
			urls = new URL(url);
			HttpURLConnection connection = (HttpURLConnection) urls.openConnection();
			connection.setRequestProperty("User-Agent", "Mozilla/4.0 (compatible; MSIE 5.0; Windows NT; DigExt)");
			connection.setDoInput(true);
			connection.setRequestMethod("GET");
			connection.connect();
			return connection;
		} catch (MalformedURLException e) {
			log.warn(e.getMessage());
			e.printStackTrace();
		} catch (ProtocolException e) {
			log.warn(e.getMessage());
			e.printStackTrace();
		} catch (IOException e) {
			log.warn(e.getMessage());
			e.printStackTrace();
		}
		return null;
	}

}
