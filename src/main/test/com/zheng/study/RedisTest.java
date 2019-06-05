package com.zheng.study;

import org.junit.Test;

import redis.clients.jedis.Jedis;

public class RedisTest {
	@Test
	public static void testConn() {
		// 连接本地的 Redis 服务
		Jedis jedis = new Jedis("127.0.0.1",8888);
		System.out.println("连接成功");
		jedis.connect();
		jedis.set("key","value");
		System.out.println("服务正在运行: " + jedis.ping());
		System.out.println(jedis.get("key"));
//		 查看服务是否运行
		
	}
}
