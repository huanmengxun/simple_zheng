package com.zheng.utils.dataUtil;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.IntStream;

import com.zheng.utils.common.MyCommonUtils;
import com.zheng.utils.file.action.MyFileUtils;
import com.zheng.utils.mylog.MyLoggerInfo;

public class MyDataBaseOperator {

	static MyLoggerInfo log = MyLoggerInfo.getInstance();

	/**
	 * 功能描述： 导出数据库所有数据
	 * 
	 * @author: zheng
	 * @param folderPath 导出文件存放路径
	 * @param fileName   导出数据库文件名称
	 */
	public static void exportDataBaseData(String folderPath, String fileName) {
		File file = MyDataBaseUtils.handleFile(folderPath, fileName);
		List<String> tables = MyDataBaseCommonQuery.getAllTables();
		if (tables.size() > 0) {
			try (FileWriter fw = new FileWriter(file)) {
				for (String table : tables) {
					List<String> sqls = MyDataBaseCommonQuery.genTabData(table);
					for (String sql : sqls) {
						fw.write(sql);
						fw.write("\r\n");
					}
					log.info("{}导出成功", table);
				}
				log.info("全部表导出成功");
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}

	/**
	 * 功能描述： 导出表数据到指定文件(不包含删表语句),默认文件名称为首个表的名称
	 * 
	 * @author: zheng
	 * @param folderPath 文件所在路径
	 * @param tableNames 要导出数据以及建表语句的表
	 */

	public static void exportTabDataByDetaultName(String folderPath, String... tableNames) {
		exportTabData(folderPath, tableNames[0], tableNames);
	}

	/**
	 * 功能描述： 导出表数据到指定文件(不包含删表语句)
	 * 
	 * @author: zheng
	 * @param folderPath 文件所在目录
	 * @param fileName   文件名称
	 * @param tableNames 要导出数据以及建表语句的表
	 */
	public static void exportTabData(String folderPath, String fileName, String... tableNames) {
		if (MyCommonUtils.isEmpty(folderPath)) {
			log.warn("未填写文件所在");
			return;
		}
		if (MyCommonUtils.isEmpty(fileName)) {
			log.warn("未填写指定文件名称");
			return;
		}
		List<String> sqls = MyDataBaseCommonQuery.genTabData(tableNames);
		if (sqls != null && sqls.size() > 0) {
			File file = MyDataBaseUtils.handleFile(folderPath, fileName);
			try (FileWriter fw = new FileWriter(file)) {
				for (String sql : sqls) {
					fw.write(sql);
					fw.write("\r\n");
				}
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}

	/**
	 * 功能描述： 通过sql导出可以插入到所指定的表的sql语句到指定文件
	 * 
	 * @author: zheng
	 * @param exportFile 导出文件名
	 * @param tableName  表名称
	 * @param sql        sql语句
	 */
	public static void exportSql(String exportFile, String tableName, String sql) {
		MyFileUtils.writeAppointFile(exportFile, MyDataBaseCommonQuery.genExportSql(tableName, sql));
	}

	public static void exportSql(String exportFile, String tableName, String sql, boolean flag) {
		MyFileUtils.writeAppointFile(exportFile, MyDataBaseCommonQuery.genExportSql(tableName, sql), flag);
	}

	/**
	 * 功能描述：
	 *
	 * @author: zheng
	 * @param exportFile 导出文件名
	 * @param tables     导出建表语句的表
	 * @return
	 */
	public static void exportCreateTabtoFileAndDefault(String exportFile, String... tables) {
		exportCreateTabtoFile(exportFile, tables[0], tables);
	}

	public static void exportCreateTabtoFile(String exportFile, String fileName, String... tableNames) {
		exportCreateTabtoFile(exportFile, fileName, false, tableNames);
	}

	/**
	 * 功能描述：
	 *
	 * @author: zheng
	 * @param exportFile     导出文件路径
	 * @param fileName       文件名
	 * @param tables         导出建表语句的表
	 * @param isContinue是否续写
	 * @return
	 */
	public static void exportCreateTabtoFile(String exportFile, String fileName, boolean isContinue,
			String... tableNames) {
		File file = MyDataBaseUtils.handleFile(exportFile, fileName);
		try (FileWriter fw = new FileWriter(file, isContinue)) {
			List<String> createSqls = MyDataBaseCommonQuery.genCreateTab(tableNames);
			for (String sql : createSqls) {
				fw.write(sql);
				fw.write("\r\n\r\n");
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * 功能描述：生成excel头
	 *
	 * @author: zheng
	 * @param sql
	 * @return
	 */
	public static Map<String, List<?>> getExcelData(String sql) {
		try (ResultSet rs = MyDataBaseConn.getResultSet(sql, "query");) {
			if (rs == null) {
				return null;
			}
			ResultSetMetaData md = rs.getMetaData();
			int col = md.getColumnCount();
			List<List<Object>> header = new ArrayList<>();
			List<List<Object>> tableData = new ArrayList<>();
			IntStream.rangeClosed(1, col).forEach(i -> {
				try {
					header.add(Arrays.asList(md.getColumnLabel(i)));
				} catch (SQLException e) {
					e.printStackTrace();
				}
			});
			while (rs.next()) {
				List<Object> lineData = new ArrayList<>();
				for (int i = 1; i <= col; i++) {
					lineData.add(rs.getObject(i));
				}
				tableData.add(lineData);
			}
			Map<String, List<?>> excelDataMap = new HashMap<>();
			excelDataMap.put("tableData", tableData);
			excelDataMap.put("header", header);

			return excelDataMap;
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return null;
	}
}
