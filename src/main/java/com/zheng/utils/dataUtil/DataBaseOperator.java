package com.zheng.utils.dataUtil;

import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.IntStream;

import com.zheng.utils.file.action.FileReadAndWrite;

public class DataBaseOperator {

	public static void exportSql(String exportFile, String tableName, String sql) {
		FileReadAndWrite.writeAppointFile(exportFile, genExportSql(tableName, sql));
	}
	
	/**
	 * 功能描述：
	 *
	 * @author: zheng  
	 * @date: 2019年6月3日 下午3:22:24 
	 * @param tables 表名列表
	 * @return
	 */
	public static List<String> exportCreateTab(String... tables){
		List<String> exportCreateTabString=new ArrayList<>();
		for(String tab:tables) { 
			List<Map<String, Object>> result=DataBaseConn.query("show create table "+tab);
			if(result!=null&&result.size()>0) {
				exportCreateTabString.add(result.get(0).get("Create Table").toString());
			}else {
				exportCreateTabString.add("  --  表"+tab+"不存在");
			}
		}
		return exportCreateTabString;
	}
	
	/**
	 * 功能描述：根据查询语句生成表sql
	 *
	 * @author: zheng
	 * @param tableName
	 * @param sql
	 * @return
	 */
	public static List<String> genExportSql(String tableName, String sql) {
		List<String> exportSql = new ArrayList<>();
		List<String> fieldName = DataBaseConn.getTabAllField(tableName);
		try (ResultSet rs = DataBaseConn.getResultSet(sql, "query");) {
			ResultSetMetaData md = rs.getMetaData();
			int col = md.getColumnCount();
			String insertSql = "insert into `" + tableName + "`";
			while (rs.next()) {
				String insertSqlField = "(";
				String insertSqlValue = "values(";
				for (int i = 1; i <= col; i++) {
					if (fieldName.contains(md.getColumnLabel(i))) {
						insertSqlField += "`" + md.getColumnLabel(i) + "`,";
						Object obj = rs.getObject(i);
						if (obj == null) {
							insertSqlValue += "null,";
						} else if (obj instanceof Number) {
							insertSqlValue += obj + ",";
						} else {
							insertSqlValue += "'" + obj + "',";
						}
					}
				}
				exportSql.add(insertSql + insertSqlField.substring(0, insertSqlField.length() - 1) + ") "
						+ insertSqlValue.substring(0, insertSqlValue.length() - 1) + ");");
			}
			return exportSql;
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return null;
	}

	public static Map<String, List<?>> getExcelData(String sql) {
		try (ResultSet rs = DataBaseConn.getResultSet(sql, "query");) {
			if(rs==null) {
				return null;
			}
			ResultSetMetaData md = rs.getMetaData();
			int col = md.getColumnCount();
			List<List<Object>> header = new ArrayList<>();
			List<List<Object>> tableData = new ArrayList<>();
			IntStream.rangeClosed(1, col).forEach(i -> {
				try {
					header.add(Arrays.asList(md.getColumnLabel(i)));
				} catch (SQLException e) {
					e.printStackTrace();
				}
			});
			while (rs.next()) {
				List<Object> lineData = new ArrayList<>();
				for (int i = 1; i <= col; i++) {
					lineData.add(rs.getObject(i));
				}
				tableData.add(lineData);
			}
			Map<String, List<?>> excelDataMap = new HashMap<>();
			excelDataMap.put("tableData", tableData);
			excelDataMap.put("header", header);

			return excelDataMap;
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return null;
	}
}
