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

import com.alibaba.fastjson.JSON;
import com.zheng.localProperties.Constants;
import com.zheng.localProperties.LoadProperties;
import com.zheng.localProperties.LoadYmal;
import com.zheng.utils.dataUtil.model.DataBaseModel;

import lombok.extern.log4j.Log4j2;

@Log4j2
public class DataBaseConn {
	public static String MYSQL = "MYSQL";
	public static String ORACLE = "ORACLE";
	public static String H2 = "H2";
	public static Connection CONN = null;

//	/* 查询数据库 ‘mammothcode’ 所有表注释 */
//	SELECT TABLE_NAME,TABLE_COMMENT FROM information_schema.TABLES WHERE table_schema='mammothcode';
	
//	static {
//		if (CONN == null) {
//			CONN = DataBaseConn.getConnByYml();
//		}
//	}


	public static void main(String[] args) throws Exception {
		System.out.println(JSON.toJSONString(getConnByProperties()));
	}

	public static Connection getConnByYml() {
		Map<String, Object> ymlMap = LoadYmal.getYmlMap();
		Object obj = ymlMap.get("dataSource.master");
		if (obj != null && obj instanceof Map) {
			return getConnByMap((Map<String, Object>) obj);
		} else {
			return null;
		}
	}

	public static Connection getConnByProperties() {
		return getConnByProperties(null);
	}

	/**
	 * 功能描述：
	 *
	 * @param propertiesPath 配置文件
	 * @return
	 */
	public static Connection getConnByProperties(String propertiesPath) {
		Map<Object, Object> prop = LoadProperties.GetDBDefaultSet(null);
		return getConnByMap(prop,"sql."+prop.get("sql.master")+".");
	}


	public static Connection getConnByMap(Map<?, Object> prop,String preFix) {
		if (prop != null) {
			if (prop.get(preFix+"dataType") == null) {
				log.error("未指定dataType数据库类型");
				return null;
			}
			
			if (prop.get(preFix+"dataIp") == null) {
				log.error("未指定ip代表具体ip");
				return null;
			}
			if (prop.get(preFix+"dbName") == null) {
				log.error("未指定使用数据库dbName名称为");
				return null;
			}
			if (prop.get(preFix+"password") == null) {
				log.error("未指定数据库密码password");
				return null;
			}
			return getConn(prop.get(preFix+"dataIp").toString(), prop.get(preFix+"port").toString(), prop.get(preFix+"dataType").toString(),
					prop.get(preFix+"dbName").toString(), prop.get(preFix+"username").toString(), prop.get(preFix+"password").toString());
		} else {
			log.error("未存在配置信息");
			return null;
		}
	}
	
	public static Connection getConnByMap(Map<?, Object> prop) {
		if (prop != null) {
			if (prop.get("dataType") == null) {
				log.error("未指定dataType名称是为那个数据库");
				return null;
			}
			if (prop.get("dataIp") == null) {
				log.error("未指定ip代表具体ip");
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
			return getConn(prop.get("dataIp").toString(), prop.get("port").toString(), prop.get("dataType").toString(),
					prop.get("dbName").toString(), prop.get("username").toString(), prop.get("password").toString());
		} else {
			log.error("未存在配置信息");
			return null;
		}
	}

	public static Connection getConnn(DataBaseModel model) {
		return getConn(model.getDataIp(), model.getPort(), model.getDateType(), model.getDbName(), model.getUsername(), model.getPassword());
	}
	
	public static Connection getConn(String dataIp, String port, String dataType, String dbName, String username,
			String password) {
		String driverName = "";
		if (dataIp == null || dataIp.equals(""))
			dataIp = "127.0.0.1";
		String portStr = "";
		if (port != null) {
			portStr = ":" + port;
		}
		StringBuilder url=new StringBuilder();
		switch (dataType.toUpperCase()) {
		case "MYSQL":
			if (portStr.equals(""))
				portStr = ":3306";
			url.append(Constants.DataBaseConstants.MYSQL.getPrefixUrl());
			url.append(dataIp);
			url.append(portStr);
			url.append(Constants.DataBaseConstants.MYSQL.getUrlSplit());
			url.append(dbName);
			url.append("?serverTimezone=UTC&useUnicode=true&characterEncoding=utf8&useSSL=false");
			driverName = Constants.DataBaseConstants.MYSQL.getDriverName();
			break;
		case "ORACLE":
			if (portStr.equals(""))
				portStr = ":1521";
			url.append(Constants.DataBaseConstants.ORACLE.getPrefixUrl());
			url.append(dataIp);
			url.append(portStr);
			url.append(Constants.DataBaseConstants.ORACLE.getUrlSplit());
			url.append(dbName);
			driverName = Constants.DataBaseConstants.ORACLE.getDriverName();
			break;
		case "H2":
			url.append(Constants.DataBaseConstants.H2.getPrefixUrl());
			url.append(dataIp);
			url.append(portStr);
			url.append(Constants.DataBaseConstants.H2.getUrlSplit());
			url.append(dbName);
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
			log.info("{},{},{}", url, username, password);
			CONN = DriverManager.getConnection(url.toString(), username, password);
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
			log.info(sql);
			if(rs==null) {
				return null;
			}
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
			log.info(result);
			return result;
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return null;
	}

	protected static ResultSet getResultSet(String sql, String type, Object... param) {
		PreparedStatement ps = null;
		type = type == null ? "query" : type;
		try {
			if (CONN == null) {
				throw new Exception("请创建连接");
			}
			ResultSet rs = null;
			switch (type) {
			case "query":
				ps = CONN.prepareStatement(sql);
				rs = ps.executeQuery();
				break;
			default:
				ps = CONN.prepareStatement(sql);
				rs = ps.executeQuery();
			}
			return rs;
		} catch (SQLException e) {
			e.printStackTrace();
		} catch (Exception e) {
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
