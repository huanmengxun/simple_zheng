package com.zheng.utils.dataUtil;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.zheng.localProperties.Constants;
import com.zheng.localProperties.LoadProperties;

import lombok.extern.log4j.Log4j2;

@Log4j2
public class JDBCUtils {
	public static String MYSQL = "MYSQL";
	public static String ORACLE = "ORACLE";
	public static String H2 = "H2";
	public static Connection CONN = null;
	static {
		if (CONN == null) {
			CONN = JDBCUtils.getConnByProperties();
		}
	}

	public static void main(String[] args) throws Exception {
		query("show tables");
	}

	public static Connection getConnByProperties() {
		return getConnByProperties(null, null);
	}

	/**
	 * 功能描述：
	 *
	 * @param propertiesPath 配置文件
	 * @param isResources    是否是根据默认路径查找配置文件
	 * @return
	 */
	public static Connection getConnByProperties(Boolean isResources, String propertiesPath) {
		Map<Object, Object> prop = LoadProperties.GetDBDefaultSet(isResources, propertiesPath);
		if (prop != null) {
			if (prop.get("dataType") == null) {
				log.error("未指定dataType名称是为那个数据库");
				return null;
			}
			if (prop.get("url") == null) {
				log.error("未指定url代表具体ip");
				return null;
			}
			if (prop.get("dbName") == null) {
				log.error("未指定使用数据库dbName名称为");
				return null;
			}
			if (prop.get("password") == null) {
				log.error("未指定数据库密码password");
				return null;
			}
			return getConn(prop.get("url").toString(), prop.get("port").toString(), prop.get("dataType").toString(),
					prop.get("dbName").toString(), prop.get("username").toString(), prop.get("password").toString());
		} else {
			log.error("未存在配置信息");
			return null;
		}
	}

	/**
	 * 功能描述：
	 * 
	 * @author: zheng
	 * @param dbName   数据库名称
	 * @param username 用户名
	 * @param password 数据库密码
	 * @return
	 */
	public static Connection getMysqlConn(String url, String port, String dbName, String username, String password) {
		port = "3306";
		return getConn(url, port, "MYSQL", dbName, username, password);
	}

	public static Connection getOracleConn(String url, String port, String dbName, String username, String password) {
		return getConn(url, port, "ORACLE", dbName, username, password);
	}

	public static Connection getH2Conn(String url, String port, String dbName, String username, String password) {
		return getConn(url, port, "H2", dbName, username, password);
	}

	public static Connection getConn(String url, String port, String dataType, String dbName, String username,
			String password) {
		String driverName = "";
		if (url == null || url.equals(""))
			url = "127.0.0.1";
		String portStr = "";
		if (port != null) {
			portStr = ":" + port;
		}
		switch (dataType.toUpperCase()) {
		case "MYSQL":
			if (portStr.equals(""))
				portStr = ":3306";
			url = Constants.DataBaseConstants.MYSQL.getPrefixUrl() + url + portStr
					+ Constants.DataBaseConstants.MYSQL.getUrlSplit() + dbName
					+ "?serverTimezone=UTC&useUnicode=true&characterEncoding=utf8&useSSL=false";
			driverName = Constants.DataBaseConstants.MYSQL.getDriverName();
			break;
		case "ORACLE":
			if (portStr.equals(""))
				portStr = ":1521";
			url = Constants.DataBaseConstants.ORACLE.getPrefixUrl() + url + portStr
					+ Constants.DataBaseConstants.ORACLE.getUrlSplit() + dbName;
			driverName = Constants.DataBaseConstants.ORACLE.getDriverName();
			break;
		case "H2":
			url = Constants.DataBaseConstants.ORACLE.getPrefixUrl() + url + portStr
					+ Constants.DataBaseConstants.ORACLE.getUrlSplit() + dbName;
			driverName = Constants.DataBaseConstants.ORACLE.getDriverName();
			break;
		default:
			return null;
		}
		if (username == null || username.equals("")) {
			username = "root";
		}
		if (password == null || password.equals("")) {
			password = "root";
		}
//		log.info("账号{},密码{},数据库连接{},驱动类{}", username, password, url, driverName);
		try {
			System.out.println("开始连接");
			Class.forName(driverName);
//			log.info("{}{}{}",url, username, password);
			CONN = DriverManager.getConnection(url, username, password);
			log.info("{}数据库连接成功", dbName.toUpperCase());
			return CONN;
		} catch (ClassNotFoundException e) {
			log.error(e.getMessage());
			e.printStackTrace();
		} catch (SQLException e) {
			log.error(e.getMessage());
			e.printStackTrace();
		}
		return CONN;
	}

//	public static void update(String sql,Map<String,Object> param) {
//		try {
//			ConnStatus();
//		} catch (Exception e1) {
//			e1.printStackTrace();
//		}
//		try(PreparedStatement pstmt= (PreparedStatement) CONN.prepareStatement(sql)){
//			pstmt.executeUpdate(sql);
//		} catch (SQLException e) {
//			e.printStackTrace();
//		}
//	}
//	public static int update(String sql) {
//		int i=-1;
//		try {
//			ConnStatus();
//		} catch (Exception e1) {
//			e1.printStackTrace();
//		}
//		try(PreparedStatement pstmt= (PreparedStatement) CONN.prepareStatement(sql)){
//			i=pstmt.executeUpdate(sql);
//		} catch (SQLException e) {
//			e.printStackTrace();
//		}
//		return i;
//	}
	/**
	 * 功能描述：根据查询语句生成表sql
	 *
	 * @author: zheng 
	 * @param tableName
	 * @param sql
	 * @return
	 */
	public static List<String> genExportSql(String tableName, String sql) {
		List<String> exportSql=new ArrayList<>();
		List<String> fieldName = getTabAllField(tableName);
		try (ResultSet rs = getResultSet(sql, "query");) {
			ResultSetMetaData md = rs.getMetaData();
			int col = md.getColumnCount();
			String insertSql="insert into `"+ tableName+"`" ;
			while (rs.next()) {
				String insertSqlField="(";
				String insertSqlValue="values(";
				for (int i = 1; i <= col; i++) {s
					if(fieldName.contains(md.getColumnLabel(i))) {
						insertSqlField+="`"+md.getColumnLabel(i)+"`,";
						insertSqlValue+="'"+rs.getObject(i)+"',";
					}
					exportSql.add(insertSql+insertSqlField.substring(0,insertSqlField.length()-1)+") "+insertSqlValue.substring(0,insertSqlValue.length()-1)+");");
				}
			}
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
		List<Map<String, Object>> fieldList = query("desc " + tableName);
		for (Map<String, Object> field : fieldList) {
			fieldName.add(field.get("Field").toString());
		}
		return fieldName;
	}

	/**
	 * 功能描述：查询接口
	 *
	 * @author: zheng
	 * @param sql
	 * @return
	 * @throws Exception
	 */
	public static List<Map<String, Object>> query(String sql) {
		try (ResultSet rs = getResultSet(sql, "query");) {
			ResultSetMetaData md = rs.getMetaData();
			int col = md.getColumnCount();
			List<Map<String, Object>> result = new ArrayList<>();
			while (rs.next()) {
				Map<String, Object> param = new HashMap<>();
				for (int i = 1; i <= col; i++) {
					param.put(md.getColumnLabel(i), rs.getObject(i));
				}
				result.add(param);
			}
			return result;
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return null;
	}

	private static ResultSet getResultSet(String sql, String type) {
		try {
			if (CONN == null) {
				throw new Exception("请创建连接");
			}
		} catch (Exception e1) {
			e1.printStackTrace();
		}
		PreparedStatement pstmt = null;
		try {
			switch (type) {
			case "query":
				pstmt = (PreparedStatement) CONN.prepareStatement(sql);
				break;
			default:
				pstmt = (PreparedStatement) CONN.prepareStatement(sql);
			}
			ResultSet rs = pstmt.executeQuery();
			return rs;
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return null;
	}
//	private static int delete(String name) {
//	    Connection conn = getConn();
//	    int i = 0;
//	    String sql = "delete from students where Name='" + name + "'";
//	    PreparedStatement pstmt;
//	    try {
//	        pstmt = (PreparedStatement) conn.prepareStatement(sql);
//	        i = pstmt.executeUpdate();
//	        System.out.println("resutl: " + i);
//	        pstmt.close();
//	        conn.close();
//	    } catch (SQLException e) {
//	        e.printStackTrace();
//	    }
//	    return i;
//	}
}
