package com.zheng.tool;

import java.io.File;
import java.io.IOException;
import java.util.Map;

import org.jsoup.Connection;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import com.zheng.utils.common.ResultData;
import com.zheng.utils.file.FileDownUtils;
import com.zheng.utils.file.FileNameUtils;

public class ReptileByJousp {
	public  static String titleTrim(String title) {
		return title.trim().replaceAll("\\<", "[").replaceAll("\\>","]").replaceAll("\\?", "!.")
				.replaceAll("\\:", "-").replaceAll("\\*", "..").replaceAll("\\|", "OR")
				.replaceAll("\\\\",  "AND ").replaceAll("\\/", " and ");
	}
	/**
	 * 功能描述：
	 *可以下载的资源有js文件，data里面所拥有的资源文件，img里面的图片文件，script里面的js文件，link里面的css文件，
	 *暂时不考虑a里面的页面跳转标签
	 * @author: zheng  
	 * @param url 需要爬的网页
	 * @param dateType  时间类型
	 * @param time 下载持续时间，具体根据dataType里面的时间类型来判断
	 * @throws IOException
	 */
	public static ResultData downAllResouceByUrl(String url,String filePath) throws IOException {
		System.out.println(url);
		url=FileNameUtils.validateStartUrl(url);
		Connection tempConn = Jsoup.connect(url);
		//怎么判断是否连接成功？
		Connection.Response demo = tempConn.ignoreContentType(true).method(Connection.Method.GET).execute();
		Document documentDemo = demo.parse();
//		获取图片标签
//		Elements imgElements = documentDemo.getElementsByTag("img");
//		downResultByTag(imgElements,"img");
		
		Element text=documentDemo.getElementById("text").parent();
		Elements aElements=text.getElementsByTag("a");
		for(Element e:aElements) {
			String href=e.attr("abs:href");
			String title=e.attr("title");
			String newFilePath=filePath+"//"+titleTrim(title);
			if(
					newFilePath.contains("恋足")
					
					) {
				new Runnable() {
					public void run() {
						try {
							downImage(href, newFilePath);
						} catch (IOException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}
					}
				}.run();
			}
		}
		
		Elements page=documentDemo.getElementsByClass("pagenow");
		Elements aPage=page.get(0).parent().getElementsByTag("a");
		int i=aPage.size();
		if("下一页".equals(aPage.get(i-2).text())) {
			downAllResouceByUrl(aPage.get(i-2).attr("abs:href"),filePath);
		}
		return new ResultData();
	}
	

	public static ResultData downImage(String url,String filePath) throws IOException {
		url=FileNameUtils.validateStartUrl(url);
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
							FileDownUtils.downUrlFileAndNameByTime(url, filePath);
						}
				}
			}.run();
			Map<String,String> data=e.dataset();
			for(String a:data.values()) {
				FileDownUtils.downUrlFileAndNameByTime(a, filePath);
				//需要验证是否为图片
			}
		}
		return new ResultData();
	}
	
	
	public static void main(String[] args) throws Exception {
		
		System.out.println("F://image\\恋足漫画17".contains("恋足"));
		String url="http://www.4mmmm2.xyz/?m=art-type-id-14-pg-81.html";
		
		downAllResouceByUrl(url,"F://image");
	}
	
	
	
	
	
}