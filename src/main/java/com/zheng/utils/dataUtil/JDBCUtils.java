package com.zheng.utils.dataUtil;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.util.List;

import com.zheng.utils.common.Constants;

import lombok.extern.log4j.Log4j2;

@Log4j2
public class JDBCUtils {
	public static String MYSQL = "MYSQL";
	public static String ORACLE = "ORACLE";
	public static String H2 = "H2";

	public static Connection getMysqlConn(String dbName, String username, String password) {
		return getConn("MYSQL", dbName, username, password);
	}

	public static Connection getOracleConn(String dbName, String username, String password) {
		return getConn("ORACLE", dbName, username, password);
	}

	public static Connection getConn(String dataType, String dbName, String username, String password) {
		String url = "";
		String driverName = "";
		switch (dataType) {
		case "MYSQL":
			url = Constants.DataBaseConstants.MYSQL.getPrefixUrl() + Constants.DataBaseConstants.MYSQL.getUrlSplit()
					+ dbName + "?serverTimezone=GMT";
			driverName = Constants.DataBaseConstants.MYSQL.getDriverName();
			break;
		case "ORACLE":
			url = Constants.DataBaseConstants.ORACLE.getPrefixUrl() + Constants.DataBaseConstants.ORACLE.getUrlSplit()
					+ dbName;
			driverName = Constants.DataBaseConstants.ORACLE.getDriverName();
			break;
		case "H2":
			url = Constants.DataBaseConstants.ORACLE.getPrefixUrl() + Constants.DataBaseConstants.ORACLE.getUrlSplit()
					+ dbName;
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
