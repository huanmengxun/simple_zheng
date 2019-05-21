package com.zheng.tool;

import java.io.IOException;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.jsoup.Connection;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import com.alibaba.fastjson.JSON;

public class QuanMinJousp {
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
	
	
	public void test2(String url) throws IOException {
		// 这里是分享地址：
		if(!url.startsWith("http")) {
			url="https://"+url;
		}
		System.out.println("完整连接名为:"+url);
		Connection tempConn = Jsoup.connect(url);
		// 模拟浏览器的请求头
		// 开始连接HTTP请求。
		Connection.Response demo = tempConn.ignoreContentType(true).method(Connection.Method.GET).execute();
		Document documentDemo = demo.parse();
		// 这里就是获取该页面的HTML元素。
		// 存在连接的一般为a标签以及img img主要是图片，a标签主要是图片
		Elements imgElements = documentDemo.getElementsByTag("img");
		for (Element e : imgElements) {
			System.out.println(e.absUrl("href"));
			System.out.println(e.attr("href"));
			System.out.println(e.attr("abs:src"));
//			String imgSrc=e.data();
//			Map<String,String> map=e.dataset();
//			for(String s:map.values()) {
//				System.out.println(s);
//			}
//			System.out.println(imgSrc);
		}
		System.out.println("end");

	}

	public static void main(String[] args) throws Exception {
//		String url="https://www.baidu.com/s?ie=utf-8&f=8&rsv_bp=1&tn=54002054_dg&wd=jsoup%20获取%20data-&oq=jsoup%2520%25E8%258E%25B7%25E5%258F%2596%2520data&rsv_pq=801c9384000c9368&rsv_t=c583hCuUEWFY9vpYlQ7N%2B8qk%2BOV8BJfZvxC1I0%2Bl7zHX8PutlDTpzLJ%2FT3gegaPlz6c&rqlang=cn&rsv_enter=1&rsv_sug3=2&rsv_sug1=2&rsv_sug7=100&rsv_sug2=0&inputT=203&rsv_sug4=1934";
		String url="http://www.4mmmm2.xyz/?m=art-detail-id-4123.html";
		new QuanMinJousp().test2(url);
	}
	
}