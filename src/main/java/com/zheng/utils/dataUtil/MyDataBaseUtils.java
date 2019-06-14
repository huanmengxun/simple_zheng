package com.zheng.utils.dataUtil;

import java.io.File;

import com.zheng.utils.common.MyComUtils;
import com.zheng.utils.common.constants.MyConstants;
import com.zheng.utils.file.MyFileNameUtils;

public class MyDataBaseUtils {
	/**
	 * 功能描述： 拼接mysql字符串
	 * 
	 * @author: zheng
	 * @return
	 */
	protected static StringBuilder splicMySqlUrl(StringBuilder url, String portStr, String dataIp, String dbName) {
		if (MyComUtils.isEmpty(portStr))
			portStr = ":3306";
		url.append(MyConstants.DataBaseConstants.MYSQL.getPrefixUrl()).append(dataIp).append(portStr)
				.append(MyConstants.DataBaseConstants.MYSQL.getUrlSplit()).append(dbName)
				.append("?serverTimezone=UTC&useUnicode=true&characterEncoding=utf8&useSSL=false");
		return url;
	}

	protected static StringBuilder splicOracleUrl(StringBuilder url, String portStr, String dataIp, String dbName) {
		if (portStr.equals(""))
			portStr = ":1521";
		url.append(MyConstants.DataBaseConstants.ORACLE.getPrefixUrl()).append(dataIp).append(portStr)
				.append(MyConstants.DataBaseConstants.ORACLE.getUrlSplit()).append(dbName);
		return url;
	}

	/**
	 * 功能描述：
	 *
	 * @author: zheng
	 * @param exportFilePath 导出文件所在路径
	 * @param fileName       文件名称
	 * @return
	 */
	protected static File handleFile(String exportFilePath, String fileName) {
		return MyFileNameUtils.getExistFile(exportFilePath, fileName, ".sql");
	}

	protected static String handleSql(String sql, Object... objects) {
		StringBuilder sb = new StringBuilder();
		return hadleSql(sb, sql, 0, objects).toString();
	}

	/**
	 * 功能描述：处理sql语句，使用参数代替?
	 *
	 * @author: zheng
	 * @param sb
	 * @param sql
	 * @param index
	 * @param objects
	 * @return
	 */
	private static StringBuilder hadleSql(StringBuilder sb, String sql, int index, Object... objects) {
		int firstLeft = sql.indexOf('?');
		if (index < objects.length && firstLeft > 0) {
			sb.append(sql.substring(0, firstLeft));
			Object value = objects[index];
			if (value == null || value instanceof Number) {
				sb.append(value);
			} else {
				sb.append("'").append(value).append("'");
			}
			return hadleSql(sb, sql.substring(firstLeft + 1), ++index, objects);
		} else {
			sb.append(sql);
			return sb;
		}
	}

}
