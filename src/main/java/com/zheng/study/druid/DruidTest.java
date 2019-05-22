package com.zheng.study.druid;

import java.io.File;
import java.io.FileInputStream;
import java.sql.Connection;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;

import javax.sql.DataSource;

import com.alibaba.druid.pool.DruidDataSourceFactory;

public class DruidTest {
	public static Connection getConnByMap() {
		// 通过Map作为参数获取连接
		Map<String, String> map = new HashMap<String, String>();
		// 向map中传递参数
		map.put("url", "jdbc:mysql://127.0.0.1:3306/javatest");// 数据库地址
		map.put("username", "root");// 用户名
		map.put("password", "huan");// 密码
		// 创建数据源
		DataSource dataSource;
		try {
			dataSource = DruidDataSourceFactory.createDataSource(map);
			Connection conn = dataSource.getConnection();
			return conn;
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
	}

	public static Connection getConnByFile() {
		// 从项目目录下的config文件夹中读取DBProperties.properties文件作为数据源的配置（config与src同级）
		File file = new File("DBProperties.properties");
//		file.exists();
		Properties ps = new Properties();
		// 创建数据源
		DataSource dataSource;
		try {
			ps.load(new FileInputStream(file));
			dataSource = DruidDataSourceFactory.createDataSource(ps);
			Connection conn= dataSource.getConnection();
			return conn;
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
	}

	public static void main(String[] args) {
		Connection conn=getConnByMap();
		System.err.println("链接成功");
	}
}
