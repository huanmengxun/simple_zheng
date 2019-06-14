package com.zheng.tool;

import java.io.IOException;

public class Test1 {
	public static void main(String[] args) throws IOException {
		String url="http://mmas.xyz/cfmw/12/90.html";
		String endUrl="http://mmas.xyz/cfmw/12/91.html";
		MainTest.downAllResouceByUrl(url, "F://image/CG", "next", endUrl);
//		
	}
}
