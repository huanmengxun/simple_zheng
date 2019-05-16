package com.zheng.utils.file;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Date;

import com.zheng.utils.common.TimeCommon;
public class FileUtils {
	
	/**
	 * 提供原路径，以及更换名对应的正则表达式修改该路径下的文件名
	 * @param filePath
	 * @param orginStr
	 * @param replaceStr
	 */
	public void resetFileName(String filePath,String orginStr,String replaceStr) {
		File f =new File(filePath);
		File writeFile=new File(getFileLogType(null,filePath));
		if(!writeFile.exists()) {
			try {
				writeFile.createNewFile();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		try (FileWriter fw = new FileWriter(writeFile,true);){ //true代表续写
			if(f.isDirectory()) {
				for(File subF:f.listFiles()) {
					String newName=subF.getName().replaceAll(orginStr, replaceStr);
					fw.write(TimeCommon.TIMESDF.format(new Date())+"-oldName:"+subF.getName()+"-newName:"+newName+"\r\n");
					subF.renameTo(new File(subF.getParent()+File.separator+newName));
					try {
						Thread.sleep(100);
						if(subF.isDirectory()) {
							resetFileName(subF.getParent()+File.separator+newName+File.separator,orginStr,replaceStr);
						}
					} catch (InterruptedException e) {
						e.printStackTrace();
					}
				}
			}
		} catch (IOException e1) {
			e1.printStackTrace();
		} 
	}

	/**
	 * 文件类型划分||作为后期的文件名定义则用
	 * @param type
	 * @param orgFilePath
	 * @return
	 */
	public String getFileLogType(String type,String orgFilePath) {
		return TimeCommon.SDF.format(new Date())+":"+orgFilePath+File.separator+"copyFile.log";
	}
}
