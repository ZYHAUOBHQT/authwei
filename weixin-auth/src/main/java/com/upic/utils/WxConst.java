package com.upic.utils;

public class WxConst {
	
	//需要修改的部分
	
	public static final String AppID = "wx574f410b5a91c33d";
//	public static final String AppID = "wx59668ba5fc3cbf57";
	public static final String AppSecret = "d4624c36b6795d1d99dcf0547af5443d";
//	public static final String AppSecret = "39924e722e936f7e0e8a3a6028b51120";
	/* 服务器网址 主要是图片路径显示*/
//	public static String ServerUrl = "http://ningbotechan.jd-app.com";
//	public static String ServerUrl = "http://www.aishangnong.cn/upic-web-client";
	public static String ServerUrl = "http://127.0.0.1:9090";
//	public static String ServerUrls = "http://121.42.193.94";
//	public static String ServerUrls = "http://182.254.147.171";
//	public static String ServerUrl = "http://www.baidu.com";
	// Token可由开发者可以任意填写，用作生成签名（该Token会和接口URL中包含的Token进行比对，从而验证安全性）。
	public static final String TOKEN = "weixin";
	
	
	//下面无需修改
	
	//全局变量，记录获取的access_token，便于复用
	public static String access_token = "";
	//过期时间，一般为7200秒，程序从微信服务器自动获得
	public static long expires_in = 0;
	//上一次获得access_token时间，便于比对access_token是否过期
	public static long oldtime = 0;
	/* Tomcat项目部署路径,程序会动态获得 */
	public static String ROOTPATH = null;

}
