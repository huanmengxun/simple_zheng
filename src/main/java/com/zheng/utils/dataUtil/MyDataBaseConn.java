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

import com.zheng.utils.common.MyComUtils;
import com.zheng.utils.common.constants.MyConstants;
import com.zheng.utils.tool.myProp.LoadMyProp;
import com.zheng.utils.tool.myProp.LoadMyYmal;
import com.zheng.utils.tool.mylog.MyLoggerInfo;

public class MyDataBaseConn {
	protected static String MYSQL = "MYSQL";
	protected static String ORACLE = "ORACLE";
	protected static String H2 = "H2";

	public static Connection CONN = null;
	protected static String DATA_TYPE ;
	protected static String USER_NAME ;

	protected static String DATA_IP ;
	protected static String PORT ;

	protected static String DBNAME ;
	protected static String PASS_WORD ;


	static MyLoggerInfo log = MyLoggerInfo.getInstance();

	private MyDataBaseConn() {
	}

	private static class MyDataBaseInstance {
		private final static MyDataBaseConn INSTANCE = new MyDataBaseConn();
	}

	public static MyDataBaseConn getInstance() {
		return MyDataBaseInstance.INSTANCE;
	}

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
	public void getConnByYml() {
		Object obj = LoadMyYmal.getInstance().getConfigValueByKey("dataSource.master");
		if (obj != null && obj instanceof Map) {
			getConnByMap((Map<String, Object>) obj);
		} else {
			log.warn("链接失败");
		}
	}

	public void getConnByProp() {
		getConnByProp(null);
	}

	/**
	 * 功能描述：
	 *
	 * @param propertiesPath 配置文件
	 * @return
	 */
	public void getConnByProp(String propertiesPath) {
		Map<Object, Object> prop = LoadMyProp.getInstance().getConfigMsg();
		getConnByMap(prop, "sql." + prop.get("sql.master") + ".");
	}

	public void getConn(String dataIp, String port, String dataType, String dbName, String username, String password) {
		String driverName = "";
		if (MyComUtils.isEmpty(dataIp))
			dataIp = "127.0.0.1";
		String portStr = port == null ? "" : ":" + port;
		StringBuilder url = new StringBuilder();
		switch (dataType.toUpperCase()) {
		case "MYSQL":
			url = MyDataBaseUtils.splicMySqlUrl(url, portStr, dataIp, dbName);
			driverName = MyConstants.DataBaseConstants.MYSQL.getDriverName();
			break;
		case "ORACLE":
			url = MyDataBaseUtils.splicOracleUrl(url, portStr, dataIp, dbName);
			driverName = MyConstants.DataBaseConstants.ORACLE.getDriverName();
			break;
		case "H2":
			url.append(MyConstants.DataBaseConstants.H2.getPrefixUrl())
				.append(dataIp).append(portStr)
				.append(MyConstants.DataBaseConstants.H2.getUrlSplit())
				.append(dbName);
			driverName = MyConstants.DataBaseConstants.ORACLE.getDriverName();
			break;
		default:
			return;
		}
		if (MyComUtils.isEmpty(username)) {
			username = "root";
		}
		if (MyComUtils.isEmpty(password)) {
			password = "root";
		}
		try {
			Class.forName(driverName);
//			log.info("{},{},{}", url, username, password);
			CONN = DriverManager.getConnection(url.toString(), username, password);
			DATA_TYPE = dataType;
			USER_NAME = username;
			DATA_IP =dataIp;
			PORT =port;
			DBNAME =dbName;
			PASS_WORD =password;
			
			log.info("{}数据库连接成功", dbName.toUpperCase());
		} catch (ClassNotFoundException e) {
			log.error(e.getMessage());
			e.printStackTrace();
		} catch (SQLException e) {
			log.error(e.getMessage());
			e.printStackTrace();
		}
	}

	public List<Map<String, Object>> query(String sql) {
		return queryParam(sql, 0);
	}

	/**
	 * 功能描述：查询接口
	 *
	 * @author: zheng
	 * @param sql
	 * @return
	 * @throws Exception
	 */
	public List<Map<String, Object>> queryParam(String sql, Object... param) {
		try (ResultSet rs = getResultSetByQuery(sql, param);) {
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
			log.info(resultList.size());
			return resultList;
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return null;
	}

	protected ResultSet getResultSetByQuery(String sql, Object... param) {
		return getResultSetByQuery(sql, null, null, param);
	}

	protected ResultSet getResultSetByQuery(String sql, Long limitForm, Long limitNum, Object... param) {
		PreparedStatement ps = null;
		sql = MyDataBaseUtils.handleSql(sql, param);
		if (limitNum != null) {
			if (limitForm != null) {

			}
		}
		log.info(sql);
		try {
			if (CONN == null) {
				throw new Exception("请创建连接");
			}
			ResultSet rs = null;
			ps = CONN.prepareStatement(sql);
			rs = ps.executeQuery();
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

	private void getConnByMap(Map<?, Object> prop, String preFix) {
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

	private void getConnByMap(Map<?, Object> prop) {
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
