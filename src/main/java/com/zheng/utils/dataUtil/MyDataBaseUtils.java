package com.zheng.utils.dataUtil;

import java.io.File;

import com.zheng.utils.file.MyFileNameUtils;
import com.zheng.utils.file.MyFileUtils;

public class MyDataBaseUtils {
	/**
	 * 功能描述：
	 *
	 * @author: zheng  
	 * @param exportFilePath 导出文件所在路径
	 * @param fileName 文件名称
	 * @return
	 */
	protected static File handleFile(String exportFilePath,String fileName) {
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
		int firstLeft = sql.indexOf("?");
		if (index < objects.length && firstLeft > 0) {
			sb.append(sql.substring(0, firstLeft));
			Object value = objects[index];
			if (value == null || value instanceof Number) {
				sb.append(value);
			} else {
				sb.append("'");
				sb.append(value);
				sb.append("'");
			}
			return hadleSql(sb, sql.substring(firstLeft + 1), ++index, objects);
		} else {
			sb.append(sql);
			return sb;
		}
	}

	
}
