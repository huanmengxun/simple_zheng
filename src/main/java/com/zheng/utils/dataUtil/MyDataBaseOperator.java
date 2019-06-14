package com.zheng.utils.dataUtil;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.List;

import com.zheng.utils.common.MyComUtils;
import com.zheng.utils.file.action.MyFileUtils;
import com.zheng.utils.tool.mylog.MyLoggerInfo;

public class MyDataBaseOperator {

	static MyLoggerInfo log = MyLoggerInfo.getInstance();

	/**
	 * 功能描述： 导出数据库所有数据
	 * 
	 * @author: zheng
	 * @param folderPath 导出文件存放路径
	 * @param fileName   导出数据库文件名称
	 */
	public static void exportDataBaseData(MyDataBaseConn myQuery,String folderPath, String fileName) {
		File file = MyDataBaseUtils.handleFile(folderPath, fileName);
		List<String> tables = MyDataBaseComQuery.getAllTables(myQuery);
		if (tables.size() > 0) {
			try (FileWriter fw = new FileWriter(file)) {
				for (String table : tables) {
					List<String> sqls = MyDataBaseComQuery.genTabData(myQuery,table);
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

	public static void exportTabDataByDetaultName(MyDataBaseConn myQuery,String folderPath, String... tableNames) {
		exportTabData(myQuery,folderPath, tableNames[0], tableNames);
	}

	/**
	 * 功能描述： 导出表数据到指定文件(不包含删表语句)
	 * 
	 * @author: zheng
	 * @param folderPath 文件所在目录
	 * @param fileName   文件名称
	 * @param tableNames 要导出数据以及建表语句的表
	 */
	public static void exportTabData(MyDataBaseConn myQuery,String folderPath, String fileName, String... tableNames) {
		if (MyComUtils.isEmpty(folderPath)) {
			log.warn("未填写文件所在");
			return;
		}
		if (MyComUtils.isEmpty(fileName)) {
			log.warn("未填写指定文件名称");
			return;
		}
		List<String> sqls = MyDataBaseComQuery.genTabData(myQuery,tableNames);
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
	public static void exportSql(MyDataBaseConn myQuery,String exportFile, String tableName, String sql) {
		MyFileUtils.writeAppointFile(exportFile, MyDataBaseComQuery.genExportSql(myQuery,tableName, sql));
	}

	public static void exportSql(MyDataBaseConn myQuery,String exportFile, String tableName, String sql, boolean flag) {
		MyFileUtils.writeAppointFile(exportFile, MyDataBaseComQuery.genExportSql(myQuery,tableName, sql), flag);
	}

	/**
	 * 功能描述：
	 *
	 * @author: zheng
	 * @param exportFile 导出文件名
	 * @param tables     导出建表语句的表
	 * @return
	 */
	public static void exportCreateTabtoFileAndDefault(MyDataBaseConn myQuery,String exportFile, String... tables) {
		exportCreateTabtoFile(myQuery,exportFile, tables[0], tables);
	}

	public static void exportCreateTabtoFile(MyDataBaseConn myQuery,String exportFile, String fileName, String... tableNames) {
		exportCreateTabtoFile(myQuery,exportFile, fileName, false, tableNames);
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
	public static void exportCreateTabtoFile(MyDataBaseConn myQuery,String exportFile, String fileName, boolean isContinue,
			String... tableNames) {
		File file = MyDataBaseUtils.handleFile(exportFile, fileName);
		try (FileWriter fw = new FileWriter(file, isContinue)) {
			List<String> createSqls = MyDataBaseComQuery.genCreateTab(myQuery,tableNames);
			for (String sql : createSqls) {
				fw.write(sql);
				fw.write("\r\n\r\n");
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

}
