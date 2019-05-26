package com.zheng.utils.file;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Date;
import java.util.List;

import com.zheng.localProperties.Constants;

import lombok.extern.log4j.Log4j2;

/**
 * 
 * 功能描述： 文件相关方法类
 * 
 * @Package: com.zheng.utils.file
 * @author: zheng
 */
@Log4j2
public class FileUtils {
	public static void main(String[] args) {

	}

	
	/**
	 * 功能描述：将list里面的字符串放置在指定文件夹之中
	 *
	 * @author: zheng
	 * @param file
	 * @param string
	 */
	public static void writeAppointFile(String file, List<String> string) {
		File newFile=new File(file);
		newFile.getParentFile().mkdirs();
		try(FileWriter fw=new FileWriter(newFile)) {
			for(String s:string) {
				fw.write("\t"+s+"\r\n");
				
			}
			log.info("{}文件写入成功",file);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
			
	}	
//	/**
//	 * 功能描述： 文件迁移
//	 * 
//	 * @author: zheng
//	 * @param oldFileName
//	 * @param newFileName
//	 * @throws Exception 
//	 */
//	public static void copyFileToNewPath(String oldFileName, String newFileName) throws Exception {
//		File oldFile=new File(oldFileName);
//		File newFile=new File(newFileName);
//		copyFileToNewPath(oldFile, newFile);
//	}
//
//	public static void copyFileToNewPath(File oldFile, File newFile) throws Exception {
//		if(oldFile.exists()) {
////			oldFile.createTempFile(prefix, suffix)
//		}else {
//			throw new Exception(oldFile+"文件找不到");
//		}
//	}

	/**
	 * 功能描述： 在该目录下删除含有指定名称的文件-遍历
	 */
	public static void deleteFileFolderByFolderPath(String folderPath, String fileKeyName, int fileFolderType) {
		deleteFileFolderByFolderPath(folderPath, fileKeyName, FileUtils.FileNameGetConstants.CHANGE_TO_HAS,
				fileFolderType);
	}

	/**
	 * 功能描述： 在该目录下删除含有指定名称的文件-遍历
	 */
	public static void deleteFileByFolderPath(String folderPath, String fileKeyName, int fileNameChangeType) {
		deleteFileFolderByFolderPath(folderPath, fileKeyName, fileNameChangeType,
				FileUtils.FileFolderTypeConstants.IS_FILE_ALL);
	}

	/**
	 * 功能描述： 在该目录下删除含有指定名称的文件夹-遍历
	 */
	public static void deleteFolderByFolderPath(String folderPath, String fileKeyName, int fileNameChangeType) {
		deleteFileFolderByFolderPath(folderPath, fileKeyName, fileNameChangeType,
				FileUtils.FileFolderTypeConstants.IS_FOLDER_ALL);
	}

	/**
	 * 功能描述： 在该目录下删除指定名称的文件
	 * 
	 * @author: zheng
	 * @date: 2019年5月25日 下午6:58:00
	 * @param folderPath         目录文件
	 * @param fileKeyName        关键名称
	 * @param fileNameChangeType 文件名指定类型
	 * @param fileFolderType     文件包括的范围
	 */
	public static void deleteFileFolderByFolderPath(String folderPath, String fileKeyName, int fileNameChangeType,
			int fileFolderType) {
		File file = new File(folderPath);
		if (file.exists() && file.isFile()) {
			switch (fileFolderType) {
			case FileUtils.FileFolderTypeConstants.IS_FILE:
				deleteFileByFolderPath(file, fileKeyName, fileNameChangeType, false);
				break;
			case FileUtils.FileFolderTypeConstants.IS_FILE_ALL:
				deleteFileByFolderPath(file, fileKeyName, fileNameChangeType, true);
				break;
			case FileUtils.FileFolderTypeConstants.IS_FOLDER:
				deleteFolderByFolderPath(file, fileKeyName, fileNameChangeType, false);
				break;
			case FileUtils.FileFolderTypeConstants.IS_FOLDER_ALL:
				deleteFolderByFolderPath(file, fileKeyName, fileNameChangeType, true);
				break;
			default:
				deleteFileByFolderPath(file, fileKeyName, fileNameChangeType, false);
			}
		}
	}

	private static void deleteFileByFolderPath(File file, String fileKeyName, int fileNameChangeType, boolean isAll) {
		for (File subFile : file.listFiles()) {
			if (isAll && subFile.isDirectory()) {
				deleteFileByFolderPath(subFile, fileKeyName, fileNameChangeType, isAll);
			} else {
				if (isHasKeyName(subFile.getName(), fileKeyName, fileNameChangeType)) {
					System.out.println(subFile.getName() + "--正在删除");
					subFile.delete();
				}
			}
		}
	}

	private static void deleteFolderByFolderPath(File file, String fileKeyName, int fileNameChangeType, boolean isAll) {
		for (File subFile : file.listFiles()) {
			if (subFile.isDirectory()) {
				if (isHasKeyName(subFile.getName(), fileKeyName, fileNameChangeType)) {
					System.out.println(subFile.getName() + "--正在删除");
					subFile.delete();
				} else if (isAll) {
					deleteFileByFolderPath(subFile, fileKeyName, fileNameChangeType, isAll);
				}
			}
		}
	}

	/**
	 * 功能描述：删除该目录下面的所有空文件夹，包括自身
	 *
	 * @author: zheng
	 * @param folderPath
	 */
	public static void deleteEmptyFolderByFolderPath(String folderPath) {
		File folder = new File(folderPath);
		deleteEmptyFolderByFolderPath(folder);
	}

	/**
	 * 功能描述：删除该目录下面的所有空文件夹，包括自身
	 *
	 * @author: zheng
	 * @param folderPath
	 */
	public static void deleteEmptyFolderByFolderPath(File folder) {
		if (folder.exists() && folder.isDirectory()) {
			for (File f : folder.listFiles()) {
				if (f.isDirectory()) {
					deleteEmptyFolderByFolderPath(f);
				}
			}
			if (folder.listFiles().length == 0) {
				System.out.println(folder.getAbsolutePath() + "删除成功");
				folder.delete();
			}
		}
	}

	/**
	 * 功能描述：
	 *
	 * @author: zheng
	 * @date: 2019年5月25日 下午12:13:52
	 * @param filePath 文件所在顶级目录
	 * @param fileName 文件名称
	 * @param type     后期为做文件名的分类而成
	 */
	public static void deleteByFileName(String filePath, String fileName, int type) {
		File pathFile = new File(filePath);
		System.out.println("文件夹" + filePath);
		if (!pathFile.exists()) {
			return;
		} else {
			for (File subFile : pathFile.listFiles()) {
				if (subFile.isDirectory()) {
					deleteByFileName(subFile.getAbsolutePath(), fileName, type);
				} else {
					if (subFile.getName().equals(fileName)) {
						subFile.deleteOnExit();
					}
				}
			}
		}
	}

	public static void removeAllFileToNewFolder(String oldFileFolderPath, String newfolderPath) {
		removeToNewPlaceByFileName(oldFileFolderPath, newfolderPath, "", 11, true);
		deleteEmptyFolderByFolderPath(oldFileFolderPath);
		System.out.println("全部移动成功");
	}

	/**
	 * 功能描述：
	 *
	 * @author: zheng
	 * @date: 2019年5月25日 下午3:39:38
	 * @param oldFileFolderPath 旧目录文件名
	 * @param newfolderPath     新目录文件名
	 * @param fileKeyName       关键文件名称
	 * @param moveType          移动类型
	 * @param isAll             是否包含下级目录
	 */
	public static void removeToNewPlaceByFileName(String oldFileFolderPath, String newfolderPath, String fileKeyName,
			int moveType, boolean isAll) {
		File path = new File(oldFileFolderPath);
		if (path.exists() && path.isDirectory()) {
			File resultPath = new File(newfolderPath);
			resultPath.mkdirs();
			for (File f : path.listFiles()) {
				if (f.isDirectory() && isAll) {
					removeToNewPlaceByFileName(f.getAbsolutePath(), newfolderPath, fileKeyName, moveType, isAll);
				} else {
					f.renameTo(new File(newfolderPath + File.separator + path.getName() + "-" + f.getName()));
				}

			}
		}

	}

	/**
	 * 功能描述：将文件名根据选择类型替换
	 *
	 * @author: zheng
	 * @param fileNameChangeType
	 * @param orginStr
	 * @param replaceStr
	 * @param oldName
	 * @return
	 */
	public static String replaceFileNameByType(int fileNameChangeType, String orginStr, String replaceStr,
			String oldName) {
		String newName = "";
		switch (fileNameChangeType) {
		case FileUtils.FileNameGetConstants.CHANGE_TO_START:
			if (oldName.startsWith(orginStr)) {
				newName = oldName.replaceFirst(orginStr, replaceStr);
			}
			break;
		case FileUtils.FileNameGetConstants.CHANGE_TO_END:
			if (oldName.endsWith(orginStr)) {
				newName = oldName.substring(0, oldName.length() - orginStr.length() - 1) + replaceStr;
			}
			break;
		case FileUtils.FileNameGetConstants.CHANGE_TO_ONLY:
			newName = replaceStr;
			break;
		default:
			newName = oldName.replaceAll(orginStr, replaceStr);
		}
		return newName;
	}

	/**
	 * 功能描述： 提供原路径，以及更换名对应的正则表达式修改该路径下的文件名
	 * 
	 * @author: zheng
	 * @param filePath           文件所在路径
	 * @param fileNameChangeType 文件名更换类型，默认为模糊替换
	 * @param orginStr           文件名所需要替换的部分
	 * @param replaceStr         文件名需要被替换成为的不笨
	 */
	public static void replaceFileName(String filePath, int fileNameChangeType, String orginStr, String replaceStr) {
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
					String oldName = subF.getName();
					String newName = replaceFileNameByType(fileNameChangeType, orginStr, replaceStr, oldName);
					if (newName.equals("")) {
						continue;
					}
					/**
					 * 记录替换信息日志
					 */
					fw.write(Constants.TimeConstant.TIMESDF.format(new Date()) + "-oldName:" + subF.getName()
							+ "-newName:" + newName + "\r\n");
					subF.renameTo(new File(subF.getParent() + File.separator + newName));
					if (subF.isDirectory()) {
						String newFilePath = subF.getParent() + File.separator + newName + File.separator;
						replaceFileName(newFilePath, fileNameChangeType, orginStr, replaceStr);
					}
				}
			}
		} catch (IOException e1) {
			e1.printStackTrace();
		}
	}

	/**
	 * 功能描述： 文件名的方式
	 * 
	 * @Package: com.zheng.utils.file
	 * @author: zheng
	 */
	public class FileNameGetConstants {
		/**
		 * 从开头开始替换
		 */
		public static final int CHANGE_TO_START = 1;
		/**
		 * 从结束开始替换
		 */
		public static final int CHANGE_TO_END = 2;
		/**
		 * 只有选择部分进行替换
		 */
		public static final int CHANGE_TO_ONLY = 3;
		/**
		 * 模糊查询是否存在此名称
		 */
		public static final int CHANGE_TO_HAS = 4;

	}

	/**
	 * 功能描述：使用文件类型判断
	 * 
	 * @Package: com.zheng.utils.file
	 * @author: zheng
	 */
	public class FileFolderTypeConstants {
		/**
		 * 指定为当前目录下面的所有文件夹
		 */
		public static final int IS_FOLDER = 1;
		/**
		 * 指定为当前目录下面的所有文件
		 */
		public static final int IS_FILE = 2;
		/**
		 * 指定为当前目录下面的所有文件夹，包括其中的文件夹之内的
		 */
		public static final int IS_FOLDER_ALL = 3;
		/**
		 * 指定为当前目录下面的所有文件，包括其中的文件夹之内的
		 */
		public static final int IS_FILE_ALL = 4;

	}

	/**
	 * 功能描述：是否含有指定关键词
	 *
	 * @author: zheng
	 * @param fileName
	 * @param fileKeyName
	 * @param fileNameChangeType
	 * @return
	 */
	private static boolean isHasKeyName(String fileName, String fileKeyName, int fileNameChangeType) {
		switch (fileNameChangeType) {
		case FileUtils.FileNameGetConstants.CHANGE_TO_HAS:
			return fileName.contains(fileKeyName);
		case FileUtils.FileNameGetConstants.CHANGE_TO_START:
			return fileName.startsWith(fileKeyName);
		case FileUtils.FileNameGetConstants.CHANGE_TO_END:
			return fileName.startsWith(fileKeyName);
		case FileUtils.FileNameGetConstants.CHANGE_TO_ONLY:
			return fileName.trim().equals(fileKeyName);
		default:
			return fileName.contains(fileKeyName);
		}
	}
}
