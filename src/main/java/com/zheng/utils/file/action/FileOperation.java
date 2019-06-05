package com.zheng.utils.file.action;
/**
 * 功能描述：文件操作的类
 * 
 * @Package: com.zheng.utils.file.action 
 * @author: zheng  
 */

import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Date;

import com.zheng.localProperties.MyConstants;
import com.zheng.utils.dataUtil.FileNameUtils;
import com.zheng.utils.file.FileUtils;
import com.zheng.utils.mylog.MyLoggerInfo;

public class FileOperation {
	MyLoggerInfo log = MyLoggerInfo.getInstance();

	/**
	 * 功能描述：合并文件
	 *
	 * @author: zheng
	 * @param fileName         合并后文件名
	 * @param hasLevel是否保存原有文件
	 * @param fileNames        所有文件名称
	 * @throws Exception
	 */
	public static void mergeMultiFile(String fileName, boolean hasLevel, String... fileNames) throws Exception {
		File aferFile = new File(fileName);
		for (String name : fileNames) {
			copyFileToNewPath(aferFile, new File(name), hasLevel);
		}
	}

	/**
	 * 功能描述：
	 * 
	 * @author: zheng
	 * @date: 2019年5月27日 上午12:01:27
	 * @param filePath1      合并的文件夹一
	 * @param filePath2      合并的文件夹二
	 * @param newFileName    是否保留就有文件
	 * @param isLevelOldFile
	 */
	public static void mergeTowFile(String filePath1, String filePath2, String newFileName, boolean isLevelOldFile) {
		try {
			copyFileToNewPath(filePath1, newFileName, isLevelOldFile);
			copyFileToNewPath(filePath2, newFileName, isLevelOldFile);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public static void mergeTowFile(String filePath1, String filePath2, String newFileName) {
		mergeTowFile(filePath1, filePath2, newFileName, false);
	}

	public static void copyFileToNewPath(String oldFileName, String newFileName) throws Exception {
		copyFileToNewPath(oldFileName, newFileName, true);
	}

	public static void copyFileToNewPath(String oldFileName, String newFileName, boolean isLevelOldFile)
			throws Exception {
		File oldFile = new File(oldFileName);
		File newFile = new File(newFileName);
		copyFileToNewPath(oldFile, newFile, isLevelOldFile);
	}

	/**
	 * 功能描述： * 功能描述： 文件迁移
	 * 
	 * @author: zheng
	 * @date: 2019年5月27日 上午12:05:01
	 * @param oldFileName
	 * @param newFileName
	 * @param isLevelOldFile 是否保留原有文件
	 * @throws Exception
	 */
	public static void copyFileToNewPath(File oldFile, File newFile, boolean isLevelOldFile) throws Exception {
		if (oldFile.exists()) {
			try (FileReader fr = new FileReader(oldFile); FileWriter fw = new FileWriter(newFile, true);) {
				char[] c = new char[1024];
				while (fr.read(c) != -1) {
					fw.write(c);
				}
				fw.write("\r\n");
				System.out.println("复制成功");
			} catch (Exception e) {
			}
			if (!isLevelOldFile) {
				oldFile.delete();
			}
		} else {
			try {
				throw new Exception(oldFile + "文件找不到");
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
	}

	/**
	 * 功能描述： 在该目录下删除含有指定名称的文件-遍历
	 */
	public static void deleteFileFolderByFolderPath(String folderPath, String fileKeyName, int fileFolderType) {
		deleteFileFolderByFolderPath(folderPath, fileKeyName, FileUtils.FileNameGetConstants.FIND_AND_CONTAINS,
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
		if (file == null || fileKeyName == null) {
			return;
		}
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
		if (file == null) {
			return;
		}
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
		if (folder == null) {
			return;
		}
		if (folder.exists() && folder.isDirectory()) {
			for (File f : folder.listFiles()) {
				if (f.isDirectory()) {
					deleteEmptyFolderByFolderPath(f);
				}
			}
			if (folder.list().length == 0) {
				System.out.println(folder.getAbsolutePath() + "删除成功");
				folder.delete();
			}
		}
	}

	/**
	 * 功能描述：
	 *
	 * @author: zheng
	 * @param oldFileFolderPath 旧有文件所在顶级路径
	 * @param newfolderPath     文件迁移之后的路径
	 */
	public static void removeAllFileToNewFolder(String oldFileFolderPath, String newfolderPath) {
		removeToNewPlaceByFileName(oldFileFolderPath, newfolderPath, null, 11, true);
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
	 * @param fileKeyName       关键文件名称 默认为全部
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
					String subffix = f.getName().substring(f.getName().lastIndexOf("."));
					String newName = newfolderPath + File.separator + System.currentTimeMillis() + subffix;
//					String newName=
//					newfolderPath + File.separator + System.currentTimeMillis()  +FileNameUtils.getFileSuffix(f.getName());
//					String newName=newfolderPath + File.separator + path.getName() + "-" + f.getName();
					f.renameTo(new File(newName));
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
		case FileUtils.FileNameGetConstants.FIND_FROM_START:
			if (oldName.startsWith(orginStr)) {
				newName = oldName.replaceFirst(orginStr, replaceStr);
			}
			break;
		case FileUtils.FileNameGetConstants.FIND_FROM_END:
			if (oldName.endsWith(orginStr)) {
				newName = oldName.substring(0, oldName.length() - orginStr.length() - 1) + replaceStr;
			}
			break;
		case FileUtils.FileNameGetConstants.FIND_AND_ONLY:
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

					fw.write(MyConstants.TimeConstantFormatter.format(new Date(),
							MyConstants.TimeConstantFormatter.TIME_PATTERN) + "-oldName:" + subF.getName() + "-newName:"
							+ newName + "\r\n");
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
		case FileUtils.FileNameGetConstants.FIND_AND_CONTAINS:
			return fileName.contains(fileKeyName);
		case FileUtils.FileNameGetConstants.FIND_FROM_START:
			return fileName.startsWith(fileKeyName);
		case FileUtils.FileNameGetConstants.FIND_FROM_END:
			return fileName.startsWith(fileKeyName);
		case FileUtils.FileNameGetConstants.FIND_AND_ONLY:
			return fileName.trim().equals(fileKeyName);
		default:
			return fileName.contains(fileKeyName);
		}
	}

}
