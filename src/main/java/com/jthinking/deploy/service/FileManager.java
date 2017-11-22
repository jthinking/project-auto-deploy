package com.jthinking.deploy.service;

import java.io.File;

/**
 * 文件工具类
 * @author JiaBochao
 * @version 2017-11-22 09:58:19
 */
public class FileManager {

	/**
	 * 在targetDir文件夹中查找名称为fileName的文件。找到返回fileName的File对象，找不到返回null
	 * @param fileName
	 * @param targetDir
	 * @return
	 */
	public static File search(String fileName, String targetDir) {
		return null;
	}


    /**
     * 删除目录
     * @param file
     * @return
     */
    public static boolean deletesFile(File file) {
        String[] files = file.list();
        if (files != null && files.length > 0) {
            for (String f : files) {
                boolean success = deletesFile(new File(file, f));
                if (!success) {
                    return false;
                }
            }
        }
        return file.delete();
    }

}
