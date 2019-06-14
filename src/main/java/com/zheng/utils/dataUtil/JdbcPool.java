package com.zheng.utils.dataUtil;

import java.io.PrintWriter;
import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.SQLFeatureNotSupportedException;
import java.util.LinkedList;
import java.util.logging.Logger;

import javax.sql.DataSource;
/**
 * 功能描述：数据库连接池的实现
 * 
 */
public class JdbcPool implements DataSource {
	// 选用LinkedList方便移除链接
	public static LinkedList<Connection> list = new LinkedList<Connection>();
	static {
		try {
			Class.forName("com.mysql.jdbc.Driver");
			// 初始化20个链接
			for (int i = 0; i < 20; i++) {
				final Connection conn = DriverManager.getConnection("jdbc:mysql:///test", "root", "root");
				// 利用jdk动态代理实现增强Connection的close方法
				Connection proxy = (Connection) Proxy.newProxyInstance(
						JdbcPool.class.getClassLoader(),
						conn.getClass().getInterfaces(), new InvocationHandler() {
							public Object invoke(Object obj, Method m, Object[] arg) 
									throws Throwable {
								if ("close".equals(m.getName())) {
									// 加到池中供其他线程訪问
									list.addLast(conn);
								}
								return m.invoke(conn, arg);
							}
						});
				list.add(proxy);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public Connection getConnection() throws SQLException {
		if (list.size() <= 0) {
			new RuntimeException("数据库忙。稍后再来");
		}
		return list.removeFirst();
	}


	public Logger getParentLogger() throws SQLFeatureNotSupportedException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Connection getConnection(String username, String password) throws SQLException {
		// TODO Auto-generated method stub
		return null;
	}


	@Override
	public PrintWriter getLogWriter() throws SQLException {
		// TODO Auto-generated method stub
		return null;
	}


	@Override
	public int getLoginTimeout() throws SQLException {
		// TODO Auto-generated method stub
		return 0;
	}


	@Override
	public void setLogWriter(PrintWriter arg0) throws SQLException {
		// TODO Auto-generated method stub
		
	}


	@Override
	public void setLoginTimeout(int arg0) throws SQLException {
		// TODO Auto-generated method stub
		
	}

}