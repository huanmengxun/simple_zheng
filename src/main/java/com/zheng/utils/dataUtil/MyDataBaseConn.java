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

import com.zheng.localProperties.LoadMyProperties;
import com.zheng.localProperties.LoadMyYmal;
import com.zheng.localProperties.commons.MyConstants;
import com.zheng.utils.dataUtil.model.MyDataBaseModel;
import com.zheng.utils.mylog.MyLoggerInfo;

public class MyDataBaseConn {
	public static String MYSQL = "MYSQL";
	public static String ORACLE = "ORACLE";
	public static String H2 = "H2";
	public static Connection CONN = null;
	public static String DATA_TYPE = null;
	static MyLoggerInfo log = MyLoggerInfo.getInstance();

//	/* 查询数据库 ‘mammothcode’ 所有表注释 */
//	SELECT TABLE_NAME,TABLE_COMMENT FROM information_schema.TABLES WHERE table_schema='mammothcode';

	static {
//		if (CONN == null) {
//			MyDataBaseConn.getConnByYml();
//		}
	}

	/**
	 * 功能描述：根据默认配置文件获取连接
	 *
	 * @author: zheng
	 * @return
	 */
	public static void getConnByYml() {
		Object obj = LoadMyYmal.getConfigValueByKey("dataSource.master");
		if (obj != null && obj instanceof Map) {
			getConnByMap((Map<String, Object>) obj);
		} else {
			log.warn("链接失败");
		}

	}

	public static void getConnByProperties() {
		getConnByProperties(null);
	}

	/**
	 * 功能描述：
	 *
	 * @param propertiesPath 配置文件
	 * @return
	 */
	public static void getConnByProperties(String propertiesPath) {
		Map<Object, Object> prop = LoadMyProperties.getConfigMsg();
		getConnByMap(prop, "sql." + prop.get("sql.master") + ".");
	}

	public static void getConnn(MyDataBaseModel model) {
		getConn(model.getDataIp(), model.getPort(), model.getDateType(), model.getDbName(), model.getUsername(),
				model.getPassword());
	}

	public static void getConn(String dataIp, String port, String dataType, String dbName, String username,
			String password) {
		String driverName = "";
		if (dataIp == null || dataIp.equals(""))
			dataIp = "127.0.0.1";
		String portStr = "";
		if (port != null) {
			portStr = ":" + port;
		}
		StringBuilder url = new StringBuilder();
		switch (dataType.toUpperCase()) {
		case "MYSQL":
			if (portStr.equals(""))
				portStr = ":3306";
			url.append(MyConstants.DataBaseConstants.MYSQL.getPrefixUrl());
			url.append(dataIp);
			url.append(portStr);
			url.append(MyConstants.DataBaseConstants.MYSQL.getUrlSplit());
			url.append(dbName);
			url.append("?serverTimezone=UTC&useUnicode=true&characterEncoding=utf8&useSSL=false");
			driverName = MyConstants.DataBaseConstants.MYSQL.getDriverName();
			break;
		case "ORACLE":
			if (portStr.equals(""))
				portStr = ":1521";
			url.append(MyConstants.DataBaseConstants.ORACLE.getPrefixUrl());
			url.append(dataIp);
			url.append(portStr);
			url.append(MyConstants.DataBaseConstants.ORACLE.getUrlSplit());
			url.append(dbName);
			driverName = MyConstants.DataBaseConstants.ORACLE.getDriverName();
			break;
		case "H2":
			url.append(MyConstants.DataBaseConstants.H2.getPrefixUrl());
			url.append(dataIp);
			url.append(portStr);
			url.append(MyConstants.DataBaseConstants.H2.getUrlSplit());
			url.append(dbName);
			driverName = MyConstants.DataBaseConstants.ORACLE.getDriverName();
			break;
		default:
			return;
		}
		if (username == null || username.equals("")) {
			username = "root";
		}
		if (password == null || password.equals("")) {
			password = "root";
		}
		try {
			System.out.println("开始连接");
			Class.forName(driverName);
			log.info("{},{},{}", url, username, password);
			CONN = DriverManager.getConnection(url.toString(), username, password);
			DATA_TYPE = dataType;
			log.info("{}数据库连接成功", dbName.toUpperCase());
		} catch (ClassNotFoundException e) {
			log.error(e.getMessage());
			e.printStackTrace();
		} catch (SQLException e) {
			log.error(e.getMessage());
			e.printStackTrace();
		}
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

	public static List<Map<String, Object>> query(String sql) {
		return queryByParams(sql);
	}

	/**
	 * 功能描述：查询接口
	 *
	 * @author: zheng
	 * @param sql
	 * @return
	 * @throws Exception
	 */
	public static List<Map<String, Object>> queryByParams(String sql, Object... param) {
		sql = MyDataBaseUtils.handleSql(sql, param);
		try (ResultSet rs = getResultSet(sql, "query");) {
			log.info(sql);
			if (rs == null) {
				return null;
			}
			ResultSetMetaData md = rs.getMetaData();
			int col = md.getColumnCount();
			List<Map<String, Object>> resultList = new ArrayList<>();
			while (rs.next()) {
				Map<String, Object> resultVal = new HashMap<>();
				for (int i = 1; i <= col; i++) {
					resultVal.put(md.getColumnLabel(i), rs.getObject(i));
				}
				resultList.add(resultVal);
			}
			log.info(resultList);
			return resultList;
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

//	--------------私有方法------------------

	private static void getConnByMap(Map<?, Object> prop, String preFix) {
		if (!prop.isEmpty()) {
			if (prop.get(preFix + "dataType") == null) {
				log.error("未指定dataType数据库类型");
			}
			if (prop.get(preFix + "dataIp") == null) {
				log.error("未指定ip代表具体ip");
			}
			if (prop.get(preFix + "dbName") == null) {
				log.error("未指定使用数据库dbName名称为");
			}
			if (prop.get(preFix + "password") == null) {
				log.error("未指定数据库密码password");
			}
			getConn(prop.get(preFix + "dataIp").toString(), prop.get(preFix + "port").toString(),
					prop.get(preFix + "dataType").toString(), prop.get(preFix + "dbName").toString(),
					prop.get(preFix + "username").toString(), prop.get(preFix + "password").toString());
		} else {
			log.error("未存在配置信息");
		}
	}

	private static void getConnByMap(Map<?, Object> prop) {
		if (prop != null) {
			if (prop.get("dataType") == null) {
				log.error("未指定dataType名称是为那个数据库");
			}
			if (prop.get("dataIp") == null) {
				log.error("未指定ip代表具体ip");
			}
			if (prop.get("dbName") == null) {
				log.error("未指定使用数据库dbName名称为");
			}
			if (prop.get("password") == null) {
				log.error("未指定数据库密码password");
			}
			getConn(prop.get("dataIp").toString(), prop.get("port").toString(), prop.get("dataType").toString(),
					prop.get("dbName").toString(), prop.get("username").toString(), prop.get("password").toString());
		} else {
			log.error("未存在配置信息");
		}
	}
}
