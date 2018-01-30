package com.upic.weixin.filter;

import java.io.IOException;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.upic.po.User;
import com.upic.utils.WxConst;

public class UserPrivilegeFilter implements Filter {
	private static final Logger LOG = LoggerFactory.getLogger(UserPrivilegeFilter.class);
	/**
	 * 应用授权作用域，snsapi_base （不弹出授权页面，直接跳转，只能获取用户openid），snsapi_userinfo
	 * （弹出授权页面，可通过openid拿到昵称、性别、所在地。并且，即使在未关注的情况下，只要用户授权，也能获取其信息）
	 */
	private String scope = "snsapi_userinfo";

	private List<String> allowurls = new ArrayList<String>();// 白名单

	@Override
	public void init(FilterConfig arg0) throws ServletException {
		//allowurls.add("/mobile/ActivityAction_show.do");
//		allowurls.add("/init/initIndex.do");
		allowurls.add("/oAuth");
	}

	@Override
	public void destroy() {

	}

	private String redirect_uri = WxConst.ServerUrl + "/oAuth";
	
	@Override
	public void doFilter(ServletRequest req, ServletResponse res, FilterChain chain) throws IOException, ServletException {

		HttpServletRequest request = (HttpServletRequest) req;
		HttpServletResponse response = (HttpServletResponse) res;
		User user = (User) request.getSession().getAttribute("user");
		HttpSession session = request.getSession();

		String path = request.getServletPath();

		if (allowurls.contains(path)) {// 如果在白名单里面 则放行
			chain.doFilter(request, response);
			LOG.debug("白名单放行：" + path);
			return;
		}

		String redirectURL = request.getRequestURL() + (request.getQueryString() == null ? "" : "?" + request.getQueryString());

		if (user == null) {// 用户没有登录 引导用户去OAuth授权
			LOG.debug("用户没有登录 引导用户去OAuth授权" + "SESSIONID=" + session.getId());

			String url = "https://open.weixin.qq.com/connect/oauth2/authorize?appid=" 
			+ WxConst.AppID + "&redirect_uri=" + URLEncoder.encode(redirect_uri, "UTF-8") + 
			"&response_type=code&scope=" + scope + "&state=" + URLEncoder.encode(redirectURL, "UTF-8") + "#wechat_redirect";
			
//			String url = "https://open.weixin.qq.com/connect/oauth2/authorize?appid=" 
//					+ WxConst.AppID + "&redirect_uri=" + URLEncoder.encode(redirect_uri, "UTF-8") + 
//					"&response_type=code&scope=" + scope + "&state=ok#wechat_redirect";
//			LOG.debug("重定向微信客户端OAUTH地址:" + url);
			response.sendRedirect(url);
			return;
		}
		LOG.debug("用户已经登录 放行" + "SESSIONID=" + session.getId() + " User=" + user);
		chain.doFilter(request, response);
	}

}
