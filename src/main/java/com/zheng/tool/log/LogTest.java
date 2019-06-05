package com.zheng.tool.log;

public class LogTest {
	@LocalLogAnnotation(info="Asd")
	public static void test() {
		System.out.println("test");
	}
}
