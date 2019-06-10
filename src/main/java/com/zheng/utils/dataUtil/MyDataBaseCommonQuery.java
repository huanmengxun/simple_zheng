package com.zheng.utils.dataUtil;

import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import com.zheng.utils.mylog.MyLoggerInfo;

public class MyDataBaseCommonQuery {

	static MyLoggerInfo log = MyLoggerInfo.getInstance();

	/**
	 * 功能描述： 生成表语句表数据插入语句
	 * 
	 * @author: zheng
	 * @param filePath   文件所在路径
	 * @param fileName   文件名称
	 * @param tableNames 要导出数据以及建表语句的表
	 */
	public static List<String> genTabData(String... tableNames) {
		List<String> resultSql = new ArrayList<>();
		for (String tableName : tableNames) {
			List<String> createSqls = MyDataBaseCommonQuery.genCreateTab(tableName);
			resultSql.addAll(createSqls);
			List<String> exportSqls = MyDataBaseCommonQuery.genExportSql(tableName, "select * from " + tableName);
			resultSql.addAll(exportSqls);
		}
		return resultSql;
	}

	/**
	 * 功能描述：获取数据库中所有表
	 *
	 * @author: zheng
	 * @return
	 */
	public static List<String> getAllTables() {
		List<String> tableList = new ArrayList<>();
		String sql = "";
		switch (MyDataBaseConn.DATA_TYPE.toUpperCase()) {
		case "MYSQL":
			sql = "show tables";
			break;
		case "ORACLE":
			sql = "";
			break;
		default:
			return null;
		}
		try (ResultSet rs = MyDataBaseConn.getResultSet(sql, "query");) {
			if (rs == null) {
				return null;
			}
			while (rs.next()) {
				tableList.add(rs.getString(1));
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return tableList;
	}

	/**
	 * 功能描述： 生成建表语句
	 * 
	 * @author: zheng
	 * @date: 2019年6月3日 下午3:22:24
	 * @param tables 表名列表
	 * @return
	 */
	public static List<String> genCreateTab(String... tables) {
		List<String> exportCreateTabString = new ArrayList<>();
		for (String tab : tables) {
			List<Map<String, Object>> result = MyDataBaseConn.query("show create table " + tab);
			if (result != null && result.size() > 0) {
				exportCreateTabString.add(result.get(0).get("Create Table").toString());
			} else {
				exportCreateTabString.add("  --  表" + tab + "不存在");
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
		List<String> fieldName = getTabAllField(tableName);
		try (ResultSet rs = MyDataBaseConn.getResultSet(sql, "query");) {
			if (rs == null) {
				exportSql.add("  --  表" + tableName + "不存在");
				return exportSql;
			}
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
			exportSql.add("\r\n\r\n");
			return exportSql;
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return null;
	}

	/**
	 * 功能描述：获取表中所有字段
	 *
	 * @author: zheng
	 * @param tableName
	 * @return
	 */
	public static List<String> getTabAllField(String tableName) {
		List<String> fieldName = new ArrayList<>();
		String sql = "";
		if (MyDataBaseConn.DATA_TYPE.toUpperCase().equals("MYSQL")) {
			sql = "desc " + tableName;
			List<Map<String, Object>> fieldList = MyDataBaseConn.query(sql);
			if (fieldList == null)
				return null;
			for (Map<String, Object> field : fieldList) {
				fieldName.add(field.get("Field").toString());
			}
		} else if (MyDataBaseConn.DATA_TYPE.toUpperCase().equals("ORACLE")) {
			sql = "SELECT t.COLUMN_NAME FROM User_Tab_Cols t, User_Col_Comments t1 WHERE t.table_name = t1.table_name AND t.column_name = t1.column_name  and t1.table_name = '"
					+ tableName.toUpperCase() + "'";
			List<Map<String, Object>> fieldList = MyDataBaseConn.query(sql);
			for (Map<String, Object> field : fieldList) {
				fieldName.add(field.get("COLUMN_NAME").toString());
			}
		} else {
			return null;
		}
		return fieldName;
	}
}
