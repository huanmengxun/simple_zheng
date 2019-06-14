package com.zheng.utils.internet;

import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.ProtocolException;
import java.net.URL;

import com.zheng.utils.handle.MyException;
import com.zheng.utils.tool.mylog.MyLoggerInfo;

/**
 * 功能描述：网络连接的处理
 * 
 * @Package: com.zheng.utils.internet
 * @author: zheng
 */
public class InternUtils {
	static MyLoggerInfo log = MyLoggerInfo.getInstance();
	public static void main(String[] args) {
		System.out.println(absUrl("yanfa.bkcc.cm/upms/as/a2s", "./../ce"));
		System.out.println(absUrl("yanfa.bkcc.cm/upms/as/a2s", "./../ce"));
		System.out.println(absUrl("yanfa.bkcc.cm/upms/as/a2s", "./../ce"));
		System.out.println(absUrl("yanfa.bkcc.cm/upms/as/a2s", "./../ce"));
		System.out.println(absUrl("yanfa.bkcc.cm/upms/as/a2s", "./../ce"));
		System.out.println(absUrl("yanfa.bkcc.cm/upms/as/a2s", "./../ce"));
		System.out.println(absUrl("yanfa.bkcc.cm/upms/as/a2s", "./../ce"));
		System.out.println(absUrl("yanfa.bkcc.cm/upms/as/a2s", "./../ce"));
		System.out.println(absUrl("yanfa.bkcc.cm/upms/as/a2s", "./../ce"));
		System.out.println(absUrl("yanfa.bkcc.cm/upms/as/a2s", "./../ce"));
		System.out.println(absUrl("yanfa.bkcc.cm/upms/as/a2s", "./../ce"));
		System.out.println(absUrl("yanfa.bkcc.cm/upms/as/a2s", "./../ce"));
		System.out.println(absUrl("yanfa.bkcc.cm/upms/as/a2s", "./../ce"));
		System.out.println(absUrl("yanfa.bkcc.cm/upms/as/a2s", "./../ce"));
		System.out.println(absUrl("yanfa.bkcc.cm/upms/as/a2s", "./../ce"));
	}
	/**
	 * 功能描述：获取绝对路径
	 *
	 * @param url     当前url所存在的拥有绝对路径的url
	 * @param relPath 相对路径
	 * @return
	 */
	public static String absUrl(String url, String relPath) {
//		分别有三种 ./ ../ / 三种 前面还要加一个url验证才行？
		char header = relPath.charAt(0);
		switch (header) {
		case '.':
			if (relPath.charAt(1) == '.' && relPath.charAt(2) == '/') {
				if (relPath.charAt(3) != '.') {
					return getIndexUrl(url, 2) + '/' + relPath.substring(3);
				} else {
					return absUrl(getIndexUrl(url, 2), relPath.substring(3));
				}
			}else if(relPath.charAt(1) == '/' ){
				if (relPath.charAt(2) != '.') {
					return getIndexUrl(url, 1) + '/' + relPath.substring(2);
				} else {
					return absUrl(getIndexUrl(url, 1), relPath.substring(2));
				}
			}else {
				log.warn("{},{}不规范",url,relPath);
				throw new MyException("所取值不符规范");
			}
		case '/':
			return getIndexUrl(url, 1) + '/' + relPath.substring(2);
		default:
			if (url.lastIndexOf('/') + 1 == url.length()) {
				return url + relPath;
			}
			return url + '/' + relPath;
		}
	}

	/**
	 * 功能描述：
	 *
	 * @param url       处理的路径(是否需要对长度进行判断？？)
	 * @param backIndex 尾巴处最后一个的/所在的位置
	 * @return
	 */
	public static String getIndexUrl(String url, int backIndex) {
		if (backIndex == 0) {
			return url;
		} else {
			char[] urlChar = url.toCharArray();
			int limitIndex = 0;
			int i = urlChar.length - 1; // 不考虑最后一位
			if (url.startsWith("http")) {
				limitIndex = 8;
			}
			if (urlChar[i] == '/')
				++backIndex;
			for (; i > limitIndex; i--) {
				if (urlChar[i] == '/' && i > limitIndex && urlChar[i - 1] != '/') {
					--backIndex;
				}
				if (backIndex == 0) {
					break;
				}
			}
			if (i < limitIndex + 2) {
				throw new MyException("所取值不符合规范");
			}
			return url.substring(0, i);
		}
	}

	/**
	 * 功能描述： 建立连接  // User-Agent ???
	 * 
	 * @param url
	 * @return
	 */
	public static HttpURLConnection getUrlConn(String url) {
		URL urls;
		try {
			urls = new URL(url);
			HttpURLConnection urlConn = (HttpURLConnection) urls.openConnection();
			urlConn.setRequestProperty("User-Agent", "Mozilla/4.0 (compatible; MSIE 5.0; Windows NT; DigExt)");
			urlConn.setDoInput(true);
			urlConn.setRequestMethod("GET");
			urlConn.connect();
			return urlConn;
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
