package com.zheng.tool;

import java.io.IOException;
import java.util.Map;

import org.jsoup.Connection;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import com.alibaba.fastjson.JSON;
import com.zheng.utils.common.ResultData;
import com.zheng.utils.file.FileDownUtils;
import com.zheng.utils.file.FileNameUtils;

public class ReptileByJousp {
	public void test() throws IOException {
		// 这里是分享地址：
		String url = "https://kg2.qq.com/node/play?s=571cI75H1xb495Eq&shareuid=609c9b812624338a&topsource=a0_pn201001006_z11_u21643941_l0_t1534223843__";
		Connection tempConn = Jsoup.connect(url);
		// 模拟浏览器的请求头
		tempConn.header("User-Agent", "Mozilla/5.0 (Windows NT 6.1; WOW64; rv:29.0) Gecko/20100101 Firefox/29.0");
		// 开始连接HTTP请求。
		Connection.Response demo = tempConn.ignoreContentType(true).method(Connection.Method.GET).execute();
		Document documentDemo = demo.parse();
		// 这里就是获取该页面的HTML元素。
		System.out.println(documentDemo.toString());

		Elements scriptElements = documentDemo.getElementsByTag("script");
		String initScriptStr = scriptElements.get(2).toString();
		String jsonStr = initScriptStr.substring(initScriptStr.indexOf("{"), initScriptStr.indexOf("; </script>"));
		// 这就获得所有参数最终的json体了。
		System.out.println(jsonStr);
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
	public static ResultData downAllResouceByUrl(String url,int dateType,int time) throws IOException {
		url=FileNameUtils.validateStartUrl(url);
		Connection tempConn = Jsoup.connect(url);
		//怎么判断是否连接成功？
		Connection.Response demo = tempConn.ignoreContentType(true).method(Connection.Method.GET).execute();
		Document documentDemo = demo.parse();
//		获取图片标签
		Elements imgElements = documentDemo.getElementsByTag("img");
		downResultByTag(imgElements,"img");
		System.out.println("end");
//		获取a标签
//		Elements aElements = documentDemo.getElementsByTag("img");
		
		return new ResultData();
	}
	/**
	 * 功能描述：获取可下载资源资源通过标签
	 * @author: zheng  
	 * @param imgElements
	 * @return
	 */
	public static ResultData downResultByTag(Elements elements,String tagType) {
		
		switch(tagType.toUpperCase()) {
			case "IMG":
				long start=System.currentTimeMillis();
				long start2=System.nanoTime();
				for (Element e : elements) {
					new Runnable() {
						public void run() {
							FileDownUtils.downLoadByUrl(e.attr("abs:src"), "f://test");
						}
					}.run();
					Map<String,String> data=e.dataset();
					for(String a:data.values()) {
						new Runnable() {
							public void run() {
								FileDownUtils.downLoadByUrl(a, "f://test");
							}
						}.run();
						//需要验证是否为图片
						
					}
				}
				long end=System.currentTimeMillis();
				long end2=System.nanoTime();

			case "A":
			case "SCRIPT":
			case "LINK":
				break;
		}
		return new ResultData();
		
	}
	
	
	public static void main(String[] args) throws Exception {
//		String url="https://www.baidu.com/s?ie=utf-8&f=8&rsv_bp=1&tn=54002054_dg&wd=jsoup%20获取%20data-&oq=jsoup%2520%25E8%258E%25B7%25E5%258F%2596%2520data&rsv_pq=801c9384000c9368&rsv_t=c583hCuUEWFY9vpYlQ7N%2B8qk%2BOV8BJfZvxC1I0%2Bl7zHX8PutlDTpzLJ%2FT3gegaPlz6c&rqlang=cn&rsv_enter=1&rsv_sug3=2&rsv_sug1=2&rsv_sug7=100&rsv_sug2=0&inputT=203&rsv_sug4=1934";
		String url="http://www.4mmmm2.xyz/?m=art-detail-id-4123.html";
		downAllResouceByUrl(url,0,0);
	}
	
	
	
	
	
}