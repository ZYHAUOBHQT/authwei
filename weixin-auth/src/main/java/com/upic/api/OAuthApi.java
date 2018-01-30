package com.upic.api;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.upic.po.SNSUserInfo;
import com.upic.po.WeixinOauth2Token;
import com.upic.utils.HttpProtocolHandler;
import com.upic.utils.WxConst;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

//import net.sf.json.JSONArray;
//import net.sf.json.JSONObject;

public class OAuthApi extends BaseApi {
	private static final Logger LOG = LoggerFactory.getLogger(OAuthApi.class);
	public WeixinOauth2Token getOauth2AccessToken(String code) throws Exception {
		WeixinOauth2Token wat = null;
		// 拼接请求地址
		String requestUrl = "https://api.weixin.qq.com/sns/oauth2/access_token?appid=" + WxConst.AppID + "&secret=" + WxConst.AppSecret + "&code=" + code
				+ "&grant_type=authorization_code";
		LOG.debug("getOauth2AccessTokenRequestUrl :"+requestUrl);
		// 获取网页授权凭证
		String json = HttpProtocolHandler.getInstance().executeGET(requestUrl);
		LOG.debug("getOauth2AccessToken json");
		JSONObject jsonObject = JSONObject.fromObject(json);
		if (null != jsonObject) {
			try {
				wat = new WeixinOauth2Token();
				wat.setAccessToken(jsonObject.getString("access_token"));
				wat.setExpiresIn(jsonObject.getInt("expires_in"));
				wat.setRefreshToken(jsonObject.getString("refresh_token"));
				wat.setOpenId(jsonObject.getString("openid"));
				wat.setScope(jsonObject.getString("scope"));
			} catch (Exception e) {
				wat = null;
				int errorCode = jsonObject.getInt("errcode");
				String errorMsg = jsonObject.getString("errmsg");
				// log.error("获取网页授权凭证失败 errcode:{} errmsg:{}", errorCode,
				// errorMsg);
				LOG.error("getOauth2AccessToken发生错误", e);
				throw e;
			}
		}
		return wat;
	}

	public SNSUserInfo getSNSUserInfo(String accessToken, String openId) throws Exception {
		SNSUserInfo snsUserInfo = null;
		// 拼接请求地址
		String requestUrl = "https://api.weixin.qq.com/sns/userinfo?access_token=" + accessToken + "&openid=" + openId;
		// 通过网页授权获取用户信息
		String json = HttpProtocolHandler.getInstance().executeGET(requestUrl);
		JSONObject jsonObject = JSONObject.fromObject(json);
		if (null != jsonObject) {
			try {
				snsUserInfo = new SNSUserInfo();
				// 用户的标识
				snsUserInfo.setOpenId(jsonObject.getString("openid"));
				// 昵称
				snsUserInfo.setNickname(jsonObject.getString("nickname"));
				// 性别（1是男性，2是女性，0是未知）
				snsUserInfo.setSex(jsonObject.getInt("sex"));
				// 用户所在国家
				snsUserInfo.setCountry(jsonObject.getString("country"));
				// 用户所在省份
				snsUserInfo.setProvince(jsonObject.getString("province"));
				// 用户所在城市
				snsUserInfo.setCity(jsonObject.getString("city"));
				// 用户头像
				snsUserInfo.setHeadImgUrl(jsonObject.getString("headimgurl"));
				// 用户特权信息
				snsUserInfo.setPrivilegeList(JSONArray.toList(jsonObject.getJSONArray("privilege"), List.class));
			} catch (Exception e) {
				snsUserInfo = null;
				int errorCode = jsonObject.getInt("errcode");
				String errorMsg = jsonObject.getString("errmsg");
				// log.error("获取用户信息失败 errcode:{} errmsg:{}", errorCode,
				// errorMsg);
				LOG.error("getSNSUserInfo发生错误", e);
				throw e;
			}
		}
		return snsUserInfo;
	}
}
