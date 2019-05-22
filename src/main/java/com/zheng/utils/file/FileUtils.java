package com.zheng.utils.file;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Date;

import com.zheng.utils.common.Constants;
import com.zheng.utils.common.ResultData;

/**
 * 
 * 功能描述： 文件相关方法类
 * 
 * @Package: com.zheng.utils.file
 * @author: zheng
 * @date: 2019年5月17日 下午2:45:14
 */
public class FileUtils {

	/**
	 * 功能描述： 提供原路径，以及更换名对应的正则表达式修改该路径下的文件名
	 * 
	 * @author: zheng
	 * @date: 2019年5月17日 下午2:47:18
	 * @param filePath 文件所在路径
	 * @param orginStr 文件名所需要替换的部分
	 * @param replaceStr  文件名需要被替换成为的不笨
	 */
	public static void replaceFileName(String filePath, String orginStr, String replaceStr) {
		File f = new File(filePath);
		File writeFile = new File(FileNameUtils.getFileLogType(null, filePath));
		if (!writeFile.exists()) {
			try {
				writeFile.createNewFile();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		try (FileWriter fw = new FileWriter(writeFile, true);) { // true代表续写
			if (f.isDirectory()) {
				for (File subF : f.listFiles()) {
					String newName = subF.getName().replaceAll(orginStr, replaceStr);
					fw.write(Constants.TimeConstant.TIMESDF.format(new Date()) + "-oldName:" + subF.getName()
							+ "-newName:" + newName + "\r\n");
					subF.renameTo(new File(subF.getParent() + File.separator + newName));
					try {
						Thread.sleep(100);
						if (subF.isDirectory()) {
							replaceFileName(subF.getParent() + File.separator + newName + File.separator, orginStr,
									replaceStr);
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


//	public boolean isImage(String fileName) {
//	int i =fileName.lastIndexOf(".");
//	int length=fileName.length();
//	if(i<length) {
//		
//	}else {
//		return false;
//	}
//}

	public static void main(String[] args) {
//		FileDown.downLoadByUrl(url, path);
	}

	public static ResultData test1() {
//	System.err.println(JSON.toJSONString(new ResultData()));
		return new ResultData();
	}

// 链接url下载图片

}
