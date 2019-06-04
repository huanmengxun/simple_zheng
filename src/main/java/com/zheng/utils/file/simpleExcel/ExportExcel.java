package com.zheng.utils.file.simpleExcel;
/**
 * 功能描述：excel文件的导出
 * 
 * @Package: com.zheng.utils.file.excel 
 * @author: zheng  
 */

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.util.List;
import java.util.Map;

import com.alibaba.excel.EasyExcelFactory;
import com.alibaba.excel.ExcelWriter;
import com.alibaba.excel.metadata.Sheet;
import com.zheng.utils.dataUtil.DataBaseOperator;
import com.zheng.utils.dataUtil.DataBaseConn;
import com.zheng.utils.mylog.MyLoggerInfo;

public class ExportExcel {
	static MyLoggerInfo log = MyLoggerInfo.getInstance();
	public static void main(String[] args) throws Exception {
		exportSqlToExcel("show tables", "F://", "test");
		log.debug("测试进行中");
	}
	/**
	 * 功能描述：
	 *
	 * @author: zheng  
	 * @date: 2019年5月29日 上午8:39:22 
	 * @param sql 查询sql语句
	 * @param filePath 文件存放路径
	 * @param fileName 文件名称
	 * @throws Exception 
	 */
	public static void exportSqlToExcel(String sql, String filePath,String fileName) throws Exception {
		Map<String, List<?>> result = DataBaseOperator.getExcelData(sql);
		if(result==null) {
			log.error("存在错误");
			return;
		}
		File path=new File(filePath);
		if(!path.exists()) {
			throw new Exception("所选目录不存在");
		}
		File file=new File(filePath+File.separator+fileName+".xls");
		if (!file.exists()) {
			file.createNewFile();
		}
		try (OutputStream out = new FileOutputStream(file);) {
			ExcelWriter writer = EasyExcelFactory.getWriter(out);
			Sheet sheet1 = new Sheet(1, 3);
			// 设置列宽 设置每列的宽度
			sheet1.setHead((List<List<String>>) result.get("header"));
			// or 设置自适应宽度
			writer.write1((List<List<Object>>) result.get("tableData"), sheet1);
			writer.finish();
		} catch (Exception e) {
			log.warn(e.getMessage());
			e.printStackTrace();
		}
	}


}
