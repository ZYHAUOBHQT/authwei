package com.upic.api;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public abstract class BaseApi {
	protected static final Logger LOG = LoggerFactory.getLogger(BaseApi.class);
	protected static final String BASE_URI = "https://api.weixin.qq.com";
//	static {
//		WxConst.access_token = WxTokenUtil.getValue("access_token");
//		String expires_inStr = WxTokenUtil.getValue("expires_in");
//		String oldtimeStr = WxTokenUtil.getValue("oldtime");
//
//		System.out.println(WxConst.access_token);
//		System.out.println(expires_inStr);
//		System.out.println(oldtimeStr);
//
//		try {
//			if (expires_inStr != null && !"".equals(expires_inStr)) {
//				WxConst.expires_in = Long.parseLong(expires_inStr);
//			}
//		} catch (NumberFormatException e) {
//			e.printStackTrace();
//		}
//
//		try {
//			if (oldtimeStr != null && !"".equals(oldtimeStr)) {
//				WxConst.oldtime = Long.parseLong(oldtimeStr);
//			}
//		} catch (NumberFormatException e) {
//			e.printStackTrace();
//		}
//	}
}
