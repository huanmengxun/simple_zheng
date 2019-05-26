package com.zheng;

import com.zheng.utils.dataUtil.JDBCUtils;
import com.zheng.utils.file.FileUtils;

public class AppTest {
	public static void main(String[] args) {
//		JDBCUtils.conn=JDBCUtils.getConnByProperties();
		try {
//			String sql="update test01 set id=1111 where id = 131";
//			System.out.println(JDBCUtils.update(sql));
			String sql="desc test01";
//			String sql="select * from test01";
//			System.out.println(JSON.toJSONString(JDBCUtils.query(sql)));
//			System.out.println(JDBCUtils.getTabAllField("test01"));
			FileUtils.writeAppointFile("f:/12323/1.sql", JDBCUtils.genExportSql("test01", "select * from test01"));
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
