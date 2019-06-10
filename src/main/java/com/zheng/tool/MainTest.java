package com.zheng.tool;

import java.io.File;
import java.io.IOException;
import java.util.Map;

import org.jsoup.Connection;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import com.zheng.utils.file.MyFileNameUtils;
import com.zheng.utils.file.action.MyFileUtils;

public class MainTest {
	public  static String titleTrim(String title) {
		return title.trim().replaceAll("\\<", "[").replaceAll("\\>","]").replaceAll("\\?", "!.")
				.replaceAll("\\:", "-").replaceAll("\\*", "..").replaceAll("\\|", "OR")
				.replaceAll("\\\\",  "AND ").replaceAll("\\/", " and ");
	}
	
	
	public static boolean isFileRegularTrue(String fileName,String title) {
		File file=new File(fileName);

		if(file.exists()||!title.contains("CG")
//				||fileName.contains("姐弟")
//				||fileName.contains("韩漫")
//				||fileName.contains("母子")
//				||fileName.contains("母姐")
//				||fileName.contains("妈")
//				||fileName.contains("母亲")
//				||fileName.contains("人妻")
//				||fileName.contains("扶她")
//				||fileName.contains("美式画风")
				) {
			return false;
		}else {
			return true;
		}
//		if(file.exists()||!fileName.contains("童话故事")) {
//			return false;
//		}else {
//			return true;
//		}
//		return false;
	}
	public static void downAllResouceByUrl(String url,String filePath,String sort,String endUrl) throws IOException {
		System.out.println(url);
		url=MyFileNameUtils.validateStartUrl(url);
		Connection tempConn = Jsoup.connect(url);
		Connection.Response demo = tempConn.ignoreContentType(true).method(Connection.Method.GET).execute();
		Document documentDemo = demo.parse();
		Elements list=documentDemo.getElementsByClass("listbox");
		Elements imgPage=list.get(0).getElementsByTag("ul").get(1).getElementsByTag("a");
		if(sort.equals("desc")) {
			for(int i=imgPage.size();i>=0;i--) {
				Element e=imgPage.get(i-1);
				new Runnable() {
					public void run() {
						try {
							String fileName=filePath+File.separator+titleTrim( e.text());
							if(isFileRegularTrue(fileName,e.text())) {
								downImage( e.attr("abs:href"), fileName);
							}
						} catch (IOException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}
					}
				}.run();
			}
		}else {
			for(Element e:imgPage) {
				new Runnable() {
					public void run() {
						try {
							String fileName=filePath+File.separator+titleTrim( e.text());
							if(isFileRegularTrue(fileName,e.text())) {
								downImage( e.attr("abs:href"), fileName);
							}
						} catch (IOException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}
					}
				}.run();
			}	
		}
		String next="next";
		if(sort.equals("desc")) {
			next="prev";	
		}
		for(Element e:list.get(0).getElementsByClass(next)) {
			String newurl=e.attr("abs:href");
			if(!newurl.equals(endUrl)) {
				downAllResouceByUrl(newurl, filePath, next,endUrl);
			}
		}
	}
	
	
	public static void testConn(String url,String next,String path) {
		next=next==null||next.equals("")?"next":next;
		url=MyFileNameUtils.validateStartUrl(url);
		Connection tempConn = Jsoup.connect(url);
		//怎么判断是否连接成功？
		Connection.Response demo;
		try {
			demo = tempConn.ignoreContentType(true).method(Connection.Method.GET).execute();
			Document documentDemo = demo.parse();
//			System.out.println(documentDemo.toString());
			Elements imgs= documentDemo.getElementsByTag("img");
			for(Element img:imgs) {
				String url1=img.attr("abs:src");
				String url2=img.attr("abs:data-original");
				if(!url1.equals("")) {
					MyFileUtils.downUrlFileAndNameByTime(url1, path);
				}
				if(!url2.equals("")) {
					MyFileUtils.downUrlFileAndNameByTime(url2,path);
				}
			}
//			Elements list=documentDemo.getElementsByClass("listbox");
//			for(Element e:list.get(0).getElementsByTag("ul").get(1).getElementsByTag("a")) {
//				System.out.println(e.attr("abs:href"));
//				System.out.println(e.text());
//			}
//			for(Element e:list.get(0).getElementsByClass(next)) {
//				System.out.println(e.attr("abs:href"));
//			}
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public static void downImage(String url,String filePath) throws IOException {
		url=MyFileNameUtils.validateStartUrl(url);
		Connection tempConn = Jsoup.connect(url);
		//怎么判断是否连接成功？
		Connection.Response demo = tempConn.ignoreContentType(true).method(Connection.Method.GET).execute();
		Document documentDemo = demo.parse();
		Elements imgElements = documentDemo.getElementsByTag("img");
		for (Element e : imgElements) {
			new Runnable() {
				public void run() {
						String url=e.attr("abs:src");
						if(url!=null&&!url.equals("")&&!url.endsWith("248x355.jpg")) {
							MyFileUtils.downUrlFileAndNameByTime(url, filePath);
						}
				}
			}.run();
			Map<String,String> data=e.dataset();
			for(String a:data.values()) {
				MyFileUtils.downUrlFileAndNameByTime(a, filePath);
			}
		}
	}
	public static void main(String[] args) {
//		String url="https://18comic.cc/photo/69779/";
//		http://mmas.xyz/jhna/12.html
//		testConn(url,"next","F://image/1");
		
//		https://18comic.cc/photo/113712/
//		https://18comic.cc/photo/113716/
//		https://18comic.cc/photo/113603/
	}
//	
}
