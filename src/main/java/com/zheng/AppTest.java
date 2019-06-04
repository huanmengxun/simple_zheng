package com.zheng;

import java.io.File;
import java.io.FileFilter;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.IntStream;

import com.alibaba.excel.EasyExcelFactory;
import com.alibaba.excel.ExcelWriter;
import com.alibaba.excel.metadata.Sheet;
import com.alibaba.fastjson.JSON;
import com.google.common.base.Optional;
import com.zheng.utils.dataUtil.DataBaseConn;
import com.zheng.utils.dataUtil.DataBaseOperator;
import com.zheng.utils.mylog.MyLoggerInfo;

import lombok.Data;

public class AppTest {
	static MyLoggerInfo log = MyLoggerInfo.getInstance();

	public static void exportSqlTOLocal() {
		File f=new File("F://北科数据");
		f.mkdirs();
		String[] baseTables = { "base_codes", "base_datas", "base_hobby", "base_peoplestate", "base_shorturl",
				"base_tuser_group", "bkcc_file", "base_job", "base_job_log", "s_city", "s_community", "s_district",
				"s_grid", "s_province", "s_street", "slpt_service", "slpt_serviceannex", "slpt_filelink", "sys_script",
				"node_input_field", "bpm_form_table", "bpm_form_rights", "bpm_form_field", "bpm_form_def",
				"wechatapp_server", "wechatapp_userinfo" };
		exportSql("base", baseTables);
		String[] ucTables = { "total_user", "total_user_third" };
		exportSql("uc", ucTables);
		String[] upmsTables = { "base_org", "sys_org", "sys_position", "sys_res", "sys_role", "sys_role_res",
				"sys_subsystem", "sys_desk_set", "sys_user", "sys_user_menu", "sys_user_org", "sys_user_pos",
				"sys_user_baseorg", "sys_user_spaceid", "sys_user_role", "upms_community_res", "sys_company",
				"sys_user_privileges", "user_res_conf" };
		exportSql("upms", upmsTables);
		String[] oaTables = { "sys_project", "sys_project_history", "sys_work_condition", "sys_version",
				"slpt_acceptance", "slpt_comattend", "office_supplies", "office_detail", "input_store", "meeting_room",
				"meeting_hasPublish", "meeting_equipment", "meeting_apply_equipment", "meeting_apply",
				"budget_template", "sys_calendar", "oa_attendancedata", "wechat_clockdata", "oa_mobilelog", "oa_mqlog",
				"oa_procedure" };
		exportSql("oa", oaTables);
	}

	/**
	 * 功能描述：导出测试表
	 *
	 * @author: zheng
	 * @date: 2019年6月3日 下午5:39:37
	 */
	public static  void exportSql(String fileNames,String... tables) {
		List<String> sqlString = DataBaseOperator.exportCreateTab(tables);
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
		Map<String, List<?>> result = DataBaseOperator.getExcelData(sql);
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
		DataBaseConn.CONN = DataBaseConn.getConnByProperties();
		
//		select table_name from user_tables// 获取oracle所有表
		System.out.println(JSON.toJSONString(DataBaseConn.query("select * from SYS_USER").get(0)));
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
