package com.zheng;

import com.zheng.utils.dataUtil.JDBCUtils;
import com.zheng.utils.file.FileUtils;

public class AppTest {
	public static void main(String[] args) {
//		JDBCUtils.conn=JDBCUtils.getConnByProperties();
		try {
			JDBCUtils.CONN = JDBCUtils.getConnByProperties();
			FileUtils.writeAppointFile("f:/slpt_service.sql", 
					JDBCUtils.genExportSql("slpt_service", "select * from slpt_service where includeorgname like '%月坛%' and smalltypename like '财务' "));
			String sql2="select * from bpm_form_table where tableid in(" + 
					"select tableid from slpt_service where includeorgname like '%月坛%' and smalltypename like '财务' )";
			FileUtils.writeAppointFile("f:/bpm_form_table.sql", 
					JDBCUtils.genExportSql("bpm_form_table", sql2));
			String sql3="select * from bpm_form_field where tableid in(" + 
					"select tableid from slpt_service where includeorgname like '%月坛%' and smalltypename like '财务' )";
			FileUtils.writeAppointFile("f:/bpm_form_field.sql", 
					JDBCUtils.genExportSql("bpm_form_field", sql3));
			
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
