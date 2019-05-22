package com.zheng.utils.dataUtil;

import java.io.IOException;
import java.io.InputStream;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;

import com.zheng.utils.common.Constants;

import lombok.extern.log4j.Log4j2;

@Log4j2
public class JDBCUtils {
	public static String MYSQL = "MYSQL";
	public static String ORACLE = "ORACLE";
	public static String H2 = "H2";
	
	public static void main(String[] args) {
		JDBCUtils.getDefaultConn();
	}
	public static Connection getDefaultConn() {
		try {
			InputStream in = JDBCUtils.class.getClass().getResourceAsStream("/properties/basecom.properties");
			
			Properties properties = new Properties();
			properties.load(in);
			properties.getProperty("property_name");
		} catch (IOException e) {
			System.out.println(e.getMessage());
			System.out.println(e.getLocalizedMessage());
			e.printStackTrace();
		}
		return null;
		
	}
	
	/**
	 * 功能描述：
	 * @author: zheng
	 * @param dbName   数据库名称
	 * @param username 用户名
	 * @param password 数据库密码
	 * @return
	 */
	public static Connection getMysqlConn(String url, Integer port, String dbName, String username, String password) {
		url = "localhost";
		port = 3306;
		return getConn("localhost", port, "MYSQL", dbName, username, password);
	}

	public static Connection getOracleConn(String url, Integer port, String dbName, String username, String password) {
		url = "localhost";
		return getConn(url, port, "ORACLE", dbName, username, password);
	}

	public static Connection getH2Conn(String url, Integer port, String dbName, String username, String password) {
		return getConn(url, port, "H2", dbName, username, password);
	}

	public static Connection getConn(String url, Integer port, String dataType, String dbName, String username,
			String password) {
		String driverName = "";
		if (url == null || url.equals(""))
			url = "127.0.0.1";
		String portStr = "";
		if (port != null) {
			portStr = ":" + port;
		}
		switch (dataType) {
		case "MYSQL":
			if(portStr.equals(""))portStr=":3306";
			url = Constants.DataBaseConstants.MYSQL.getPrefixUrl() + url + portStr
					+ Constants.DataBaseConstants.MYSQL.getUrlSplit() + dbName + "?serverTimezone=GMT";
			driverName = Constants.DataBaseConstants.MYSQL.getDriverName();
			break;
		case "ORACLE":
			if(portStr.equals(""))portStr=":1521";
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
		log.info("账号{},密码{},数据库连接{},驱动类{}", username, password, url, driverName);
		try {
			Class.forName(driverName);
			Connection conn = DriverManager.getConnection(url, username, password);
			log.info("{}数据库连接成功", dbName.toUpperCase());
			return conn;
		} catch (ClassNotFoundException e) {
			log.error(e.getMessage());
			e.printStackTrace();
		} catch (SQLException e) {
			log.error(e.getMessage());
			e.printStackTrace();
		}
		return null;
	}

//	public static List query(Connection conn,String sql) {
//		PreparedStatement pstmt;
//	    try {
//	        pstmt = (PreparedStatement)conn.prepareStatement(sql);
//	        ResultSet rs = pstmt.executeQuery();
//	        int col = rs.getMetaData().getColumnCount();
//	        ResultSetMetaData md=rs.getMetaData();
//	        while (rs.next()) {
//	            for (int i = 1; i <= col; i++) {
//	                if ((i == 2) && (rs.getString(i).length() < 8)) {
//	                    System.out.print("\t");
//	                }
//	             }
//	            System.out.println("");
//	        }
//	            System.out.println("============================");
//	    } catch (SQLException e) {
//	        e.printStackTrace();
//	    }
//	    return null;
//	}
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
