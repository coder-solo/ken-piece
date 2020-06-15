package com.ken.utils;

import lombok.extern.slf4j.Slf4j;

import java.io.*;
import java.net.HttpURLConnection;
import java.net.URL;

@Slf4j
public class FileUtils {

	/**
	 * 从远程下载文件
	 *
	 * @param urlStr
	 * @param fileName
	 * @param savePath
	 * @throws IOException
	 */
	public static String downLoadFromUrl(String urlStr, String fileName, String savePath) {
		InputStream inputStream = null;
		FileOutputStream fos = null;
		try {
			URL url = new URL(urlStr);
			HttpURLConnection conn = (HttpURLConnection) url.openConnection();
			//设置超时间为5秒
			conn.setConnectTimeout(5 * 1000);
			//防止屏蔽程序抓取而返回403错误
			conn.setRequestProperty("User-Agent", "Mozilla/4.0 (compatible; MSIE 5.0; Windows NT; DigExt)");
			// 支持重定向
			conn.setInstanceFollowRedirects(false);

			//得到输入流
			inputStream = conn.getInputStream();
			//获取自己数组
			byte[] getData = readInputStream(inputStream);

			//文件保存位置
			createDirectory(savePath);
			File saveDir = new File(savePath);
			File file = new File(saveDir + File.separator + fileName);
			fos = new FileOutputStream(file);
			fos.write(getData);

			closeStream(inputStream);
			closeStream(fos);
			log.info("download file success:" + urlStr);
			return fileName;
		} catch (IOException e) {
			e.printStackTrace();
			log.error("download file failure:" + urlStr);
			return "";
		} finally {
			closeStream(inputStream);
			closeStream(fos);
		}
	}

	public static String downLoadFromRedirectUrl(String urlStr, String fileName, String savePath) {
		InputStream inputStream = null;
		FileOutputStream fos = null;
		try {
			URL url = new URL(urlStr);
			HttpURLConnection conn = (HttpURLConnection) url.openConnection();
			//设置超时间为5秒
			conn.setConnectTimeout(5 * 1000);
			//防止屏蔽程序抓取而返回403错误
			conn.setRequestProperty("User-Agent", "Mozilla/4.0 (compatible; MSIE 5.0; Windows NT; DigExt)");
			// 支持重定向
			conn.setInstanceFollowRedirects(false);
			int code = conn.getResponseCode();
			if(302 == code) {
				String redirectUrl = conn.getHeaderField("Location");
				if(redirectUrl != null && !redirectUrl.isEmpty()) {
					System.out.println("redirectUrl:" + redirectUrl);
					url = new URL(redirectUrl);
					conn = (HttpURLConnection) url.openConnection();
					//设置超时间为5秒
					conn.setConnectTimeout(5 * 1000);
					//防止屏蔽程序抓取而返回403错误
					conn.setRequestProperty("User-Agent", "Mozilla/4.0 (compatible; MSIE 5.0; Windows NT; DigExt)");
					// 支持重定向
					conn.setInstanceFollowRedirects(false);
				}
			}

			//得到输入流
			inputStream = conn.getInputStream();
			//获取自己数组
			byte[] getData = readInputStream(inputStream);

			//文件保存位置
			createDirectory(savePath);
			File saveDir = new File(savePath);
			File file = new File(saveDir + File.separator + fileName);
			fos = new FileOutputStream(file);
			fos.write(getData);

			closeStream(inputStream);
			closeStream(fos);
			log.info("download file success:" + urlStr);
			return fileName;
		} catch (IOException e) {
			e.printStackTrace();
			log.error("download file failure:" + urlStr);
			return "";
		} finally {
			closeStream(inputStream);
			closeStream(fos);
		}
	}

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

	public static void closeStream(Closeable c) {
		try {
			if (c != null) {
				c.close();
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	private static byte[] readInputStream(InputStream inputStream) throws IOException {
		byte[] buffer = new byte[1024];
		int len = 0;
		ByteArrayOutputStream bos = new ByteArrayOutputStream();
		while ((len = inputStream.read(buffer)) != -1) {
			bos.write(buffer, 0, len);
		}
		bos.close();
		return bos.toByteArray();
	}
}
