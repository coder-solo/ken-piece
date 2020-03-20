package com.ken.utils;

import java.io.File;

public class FileUtils {

	/**
	 * 创建目录
	 *
	 * @param path
	 */
	public static void createDirectory(String path) {

		File f = new File(path);
		if (!f.exists() || !f.isDirectory()) {
			createDirectory(f.getParent());
			f.mkdir();
		}
	}
}
