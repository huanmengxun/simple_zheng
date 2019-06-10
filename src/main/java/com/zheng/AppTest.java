package com.zheng;

import java.io.File;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.OutputStream;
import java.util.List;
import java.util.Map;

import com.alibaba.excel.EasyExcelFactory;
import com.alibaba.excel.ExcelWriter;
import com.alibaba.excel.metadata.Sheet;
import com.zheng.utils.dataUtil.MyDataBaseCommonQuery;
import com.zheng.utils.dataUtil.MyDataBaseConn;
import com.zheng.utils.dataUtil.MyDataBaseOperator;
import com.zheng.utils.mylog.MyLoggerInfo;

public class AppTest {
	static MyLoggerInfo log = MyLoggerInfo.getInstance();

	public static void exportSqlTOLocalSunhe() {
		String[] sunheTables = {"sys_user","total_user","sys_org","sys_user_org","sys_role" ,"sys_user_role"};
		exportSql("孙河用户建表语句", sunheTables);
		
	}
	
	public static void exportSqlTOLocal() {
		String[] yuetan= {"INPUT_STORE","meeting_apply","meeting_apply_equipment","meeting_equipment","meeting_hasPublish","meeting_room","OFFICE_DETAIL","OFFICE_SUPPLIES"};
		File f=new File("F://月坛数据");
		f.mkdirs();
		exportSql("yuetan", yuetan);
//		String[] baseTables = { "base_codes", "base_datas", "base_hobby", "base_peoplestate", "base_shorturl",
//				"base_tuser_group", "bkcc_file", "base_job", "base_job_log", "s_city", "s_community", "s_district",
//				"s_grid", "s_province", "s_street", "slpt_service", "slpt_serviceannex", "slpt_filelink", "sys_script",
//				"node_input_field", "bpm_form_table", "bpm_form_rights", "bpm_form_field", "bpm_form_def",
//				"wechatapp_server", "wechatapp_userinfo" };
//		exportSql("base", baseTables);
//		String[] ucTables = { "total_user", "total_user_third" };
//		exportSql("uc", ucTables);
//		String[] upmsTables = { "base_org", "sys_org", "sys_position", "sys_res", "sys_role", "sys_role_res",
//				"sys_subsystem", "sys_desk_set", "sys_user", "sys_user_menu", "sys_user_org", "sys_user_pos",
//				"sys_user_baseorg", "sys_user_spaceid", "sys_user_role", "upms_community_res", "sys_company",
//				"sys_user_privileges", "user_res_conf" };
//		exportSql("upms", upmsTables);
//		String[] oaTables = { "sys_project", "sys_project_history", "sys_work_condition", "sys_version",
//				"slpt_acceptance", "slpt_comattend", "office_supplies", "office_detail", "input_store", "meeting_room",
//				"meeting_hasPublish", "meeting_equipment", "meeting_apply_equipment", "meeting_apply",
//				"budget_template", "sys_calendar", "oa_attendancedata", "wechat_clockdata", "oa_mobilelog", "oa_mqlog",
//				"oa_procedure" };
//		exportSql("oa", oaTables);
//		String[] oaTables = { "sys_project", "sys_project_history", "sys_work_condition", "sys_version",
//				"slpt_acceptance", "slpt_comattend", "office_supplies", "office_detail", "input_store", "meeting_room",
//				"meeting_hasPublish", "meeting_equipment", "meeting_apply_equipment", "meeting_apply",
//				"budget_template", "sys_calendar", "oa_attendancedata", "wechat_clockdata", "oa_mobilelog", "oa_mqlog",
//				"oa_procedure" };
//		exportSql("oa", oaTables);
	}

	/**
	 * 功能描述：导出测试表
	 *
	 * @author: zheng
	 * @date: 2019年6月3日 下午5:39:37
	 */
	public static  void exportSql(String fileNames,String... tables) {
		List<String> sqlString = MyDataBaseCommonQuery.genCreateTab(tables);
		File file = new File("F://北科数据//"+fileNames+".sql");
		try (FileWriter fw = new FileWriter(file)) {
			for (String sql : sqlString) {
				fw.write("\t" + sql + ";\r\n\n\n");
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	public static String getResSql() {
		StringBuilder builder=new StringBuilder();
		builder.append("select resname '菜单名称',defaulturl '菜单路径','oa项目' as '来源'  from sys_res where defaulturl like '/oa%'");
		builder.append(" UNION ");
		builder.append("select resname '菜单名称',defaulturl '菜单路径','oa项目' as '来源'  from sys_res where defaulturl like 'http%'");
		builder.append(" UNION ");
		builder.append("select resname '菜单名称',defaulturl '菜单路径','base项目' as '来源'  from sys_res where defaulturl like '/base%'");
		builder.append(" UNION ");
		builder.append("select resname '菜单名称',defaulturl '菜单路径','upms项目' as '来源'  from sys_res where defaulturl like '/upms%'");
		builder.append(" UNION ");
		builder.append("select resname '菜单名称',defaulturl '菜单路径','uc项目' as '来源'  from sys_res where defaulturl like '/uc%'");
		return builder.toString();
	}
	
	public static void exportSqlToExcel(String sql, String filePath,String fileName) throws Exception {
		Map<String, List<?>> result = MyDataBaseOperator.getExcelData(sql);
		File path=new File(filePath);
		if(!path.exists()) {
			throw new Exception("所选目录不存在");
		}
		File file=new File(filePath+File.separator+fileName+".xls");
		if (!file.exists()) {
			file.createNewFile();
		}
		try (OutputStream out = new FileOutputStream(file);) {
			ExcelWriter writer = EasyExcelFactory.getWriter(out);
			Sheet sheet1 = new Sheet(1, 3);
			// 设置列宽 设置每列的宽度
			sheet1.setHead((List<List<String>>) result.get("header"));
			// or 设置自适应宽度
			writer.write1((List<List<Object>>) result.get("tableData"), sheet1);
			writer.finish();
		} catch (Exception e) {
			log.warn(e.getMessage());
			e.printStackTrace();
		}
	}
	
	public static void main(String[] args) throws Exception {
		 MyDataBaseConn.getConnByProperties();
//		exportSqlTOLocal();
		
//		MyDataBaseOperator.exportSql("F://月坛数据//ceshi.sql", "slpt_service", "select * from slpt_service where includeorgname like '%月坛%' and smalltypename != '财务'");
//		MyDataBaseOperator.exportSql("F://月坛数据//ceshi.sql", "bpm_form_table", "select * from bpm_form_table where tableid in (select tableid from slpt_service where includeorgname like '%月坛%' and smalltypename != '财务') or maintableid in (select tableid from slpt_service where includeorgname like '%月坛%' and smalltypename != '财务')",true);
//		MyDataBaseOperator.exportSql("F://月坛数据//ceshi.sql", "bpm_form_field", " select * from bpm_form_field where tableid in (select tableid from slpt_service where includeorgname like '%月坛%' and smalltypename != '财务' union  select tableid from bpm_form_table where maintableid in (select tableid from slpt_service where includeorgname like '%月坛%' and smalltypename != '财务')) ",true);
		
		
//		MyDataBaseOperator.exportSql("F://科立数据//gz.sql", "w_gzsp", "select * from w_gzsp where id = 4437916717810688");
//		MyDataBaseOperator.exportSql("F://科立数据//gz.sql", "w_gzspzb", "select * from w_gzspzb where pid = 4437916717810688",true);
//		

//		exportSqlTOLocalSunhe();
//		DataBaseOperator.exportSql("F://北科数据//用户表sys_user.sql", "SYS_USER", "select UPDATEUSER,ACCOUNT,ISLOCK,STATUS,CREATEUSER,USERID,ISEXPIRED,CREATETIME,FULLNAME,PASSWORD from SYS_USER");
//		DataBaseOperator.exportSql("F://北科数据//用户表total_user.sql", "SYS_USER", "SELECT USERID,ACCOUNT,CREATETIME,UPDATETIME FROM SYS_USER");
//		select table_name from user_tables// 获取oracle所有表
//		DataBaseOperator.exportSql("F://北科数据//孙河组织sys_org(需修改).sql", "SYS_ORG", "SELECT * FROM SYS_ORG");
//		DataBaseOperator.exportSql("F://北科数据//菜单数据sys_user_role.sql", "SYS_USER_ROLE", "select USERROLEID,ROLEID,USERID,ORGNAME  from SYS_USER_ROLE");
//		MyDataBaseOperator.exportSql("F://北科数据//菜单数据sys_res.sql", "sys_res", "select * from sys_res where defaulturl not like 'http%' and defaulturl not like '%defid'");
//		DataBaseOperator.exportSql("F://北科数据//孙河用户角色sys_role.sql", "SYS_ROLE", "select ROLEID,ALIAS,ROLENAME,MEMO,ALLOWDEL,ALLOWEDIT,ENABLED,ISADD,ALLOWADD,CREATEORGNAME,CREATEORGID from sys_role");
//		DataBaseOperator.exportSql("F://北科数据//孙河用户组织关联sys_user_org（需修改）.sql", "sys_user_org", "SELECT * FROM SYS_USER_ORG");
		
//		DataBaseOperator.exportSql("F://北科数据//孙河用户岗位sys_position（需修改）.sql", "SYS_POSITION", "SELECT * FROM SYS_POSITION");
		
//		
//		String tabledesc="SELECT t.colUMN_NAME FROM User_Tab_Cols t, User_Col_Comments t1 WHERE t.table_name = t1.table_name AND t.column_name = t1.column_name  "
//				+ "and t1.table_name = 'SYS_POSITION'";
////		String getAlltab="select TABLE_NAME from user_tables where table_name like 'SYS_%'"; 
//		List<Map<String, Object>> table=DataBaseConn.query(tabledesc);
//		StringBuilder sb=new StringBuilder();
//		for(Map m:table) {
//			sb.append(m.get("COLUMN_NAME"));
//			sb.append(",");
//		}
//		System.out.println(sb.toString());
		
//		System.out.println(JSON.toJSONString(DataBaseConn.query("select * from SYS_POSITION where PARENTID = 1")));
		
//		String sql="select * from sys_user";
//		System.out.println(JSON.toJSONString(DataBaseConn.getTabAllField("sys_user")));
		
//		select UPDATEUSER,ACCOUNT,ISLOCK,STATUS,CREATEUSER,USERID,ISEXPIRED,CREATETIME,FULLNAME,PASSWORD from SYS_USER;
		
//		exportSqlTOLocal();
//		exportSqlToExcel(getResSql().toString(), "F://北科数据", "菜单");
//		for (String tab : tables) {
//			List<Map<String, Object>> result = DataBaseConn.query("show create table " + tab);
//			if (result != null && result.size() > 0) {
//				result.get(0).get("Create Table");
//			}
//		}
	}
}
