package com.upic.utils;
//package com.weixin.util;
//
//import java.io.File;
//import java.io.FileInputStream;
//import java.io.FileOutputStream;
//import java.io.IOException;
//import java.io.InputStream;
//import java.util.Properties;
//
//public class WxTokenUtil {
//
//	private static Properties properties = new Properties();
//	private static String filepath = "/WEB-INF/conf/token.properties";
//	static {
//		try {
//			InputStream inStream = new FileInputStream(new File(WxConst.ROOTPATH, filepath));
//			properties.load(inStream);
//			inStream.close();
//		} catch (IOException e) {
//			e.printStackTrace();
//			throw new ExceptionInInitializerError(e);
//		}
//	}
//
//	public static void write(String access_token, long expires_in, long oldtime) throws Exception {
//		Properties properties = new Properties();
//		properties.setProperty("access_token", access_token);
//		properties.setProperty("expires_in", expires_in + "");
//		properties.setProperty("oldtime", oldtime + "");
//		FileOutputStream fos = new FileOutputStream(new File(WxConst.ROOTPATH, filepath));
//		properties.store(fos, "存储Access_Token");
//		fos.flush();
//		fos.close();
//	}
//
//	public static String getValue(String key) {
//		String value = properties.getProperty(key);
//		return value;
//	}
//}
