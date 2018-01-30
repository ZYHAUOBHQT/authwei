package com.upic.weixin.controller;

import java.net.URLDecoder;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import com.upic.api.OAuthApi;
import com.upic.po.SNSUserInfo;
import com.upic.po.WeixinOauth2Token;
import com.upic.utils.ValidateUtil;

@Controller
public class OAuthController  {
	private static final Logger LOG = LoggerFactory.getLogger(OAuthController.class);

//	@Autowired
//	private IUserService userService;
	@RequestMapping("/oAuth")
	public String oauth(HttpServletRequest request, HttpServletResponse response, HttpSession session, Model model) {
		// 用户同意授权后，能获取到code
		String code = request.getParameter("code");
		String state = request.getParameter("state");// 传人的自定义参数 用来判断点击了
														// 可以用来判断点击了哪个菜单

		LOG.debug("code=" + code);
		LOG.debug("state=" + state);

		// 用户同意授权
		if (ValidateUtil.isValid(code)) {// code 不为空 代表用户同意授权
			OAuthApi oAuthApi = new OAuthApi();
			try {
				WeixinOauth2Token wat = oAuthApi.getOauth2AccessToken(code);

				LOG.debug("WeixinOauth2Token=" + wat);

				String accessToken = wat.getAccessToken();
				String openId = wat.getOpenId();

				// 登录我们的网站
				if (ValidateUtil.isValid(openId)) {
					Map<String, Object> paramMap = new HashMap<String, Object>();
					paramMap.put("openId", openId);
//					User user = (User) userService.getBy(paramMap, "findByUserNo");
//					LOG.debug("user=" + user);
//					if (user == null) {// 如果是新关注的用户 则将用户信息保存到数据库中
//						user = new User();
						// 第四步：拉取用户信息(需scope为 snsapi_userinfo)
						SNSUserInfo snsUserInfo = oAuthApi.getSNSUserInfo(accessToken, openId);
						LOG.debug(
								"用户在我们数据库中不存在，一种原因是网络异常导致用户关注时，微信服务器未能正确推送到我们服务器，另一种原因是新用户，snsUserInfo=" + snsUserInfo);
//						user.setOpenId(snsUserInfo.getOpenId());
//						user.setNickName(snsUserInfo.getNickname());
//						user.setSex(((SexEntity.values()[snsUserInfo.getSex()]).equals("1") ? "男" : "女"));
//						user.setProvince(snsUserInfo.getProvince());
//						user.setCity(snsUserInfo.getCity());
//						user.setCountry(snsUserInfo.getCountry());
//						user.setHeadImagUrl(snsUserInfo.getHeadImgUrl());
//						userService.insert(user);
//						user = (User) userService.getBy(paramMap, "findByUserNo");
						LOG.debug("数据库用户为空 插入用户数据 user=" + snsUserInfo);
					}

//					session.setAttribute("user", user);// 将登录信息存到Session中

//					LOG.debug("将登录信息存到Session中=" + user);

					if (ValidateUtil.isValid(state)) {// 带了自定义参数过来
						state = URLDecoder.decode(state, "UTF-8");
//						 response.sendRedirect(state);// 重定向到某个网址去
						LOG.debug("重定向到=" + state);
					} else {
						// response.sendRedirect("/");
						LOG.debug("重定向到首页");
					}
//				}
//				response.sendRedirect(state);
			} catch (Exception e) {
				e.printStackTrace();
				LOG.error("OAuthServlet发生错误", e);
			}
		}
		 return "redirect:"+ state;
	}
}
