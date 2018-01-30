package com.upic.utils;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
/**
 * 流转换工具
 * @author ST
 *
 */
public class StreamUtil {
	/**
	 * 将流转成String
	 * 
	 * @param inputStream
	 * @return
	 * @throws IOException
	 */
	public static String stream2String(InputStream inputStream) throws IOException {
		int len = 0;
		byte[] buffer = new byte[10240];
		ByteArrayOutputStream baos = new ByteArrayOutputStream();
		while ((len = inputStream.read(buffer)) != -1) {
			baos.write(buffer, 0, len);
		}
		baos.flush();
		baos.close();
		inputStream.close();
		String result = new String(baos.toByteArray(), "UTF-8");
		return result;
	}
	/**
	 * 将流转化成文件
	 * @param is 输入流
	 * @param file 文件路径
	 * @throws IOException
	 */
	public static void saveFile(InputStream is, File file) throws IOException {
		FileOutputStream fos = new FileOutputStream(file);
		int len = 0;
		byte[] buffer = new byte[10240];
		while ((len = is.read(buffer)) != -1) {
			fos.write(buffer, 0, len);
		}
		fos.flush();
		fos.close();
		is.close();
	}
	
	
}
