package com.cy.member.controller;

import com.cy.member.feign.MemberLoginServiceFeign;
import com.cy.web.base.BaseWebController;
import com.qq.connect.oauth.Oauth;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpServletRequest;

@Controller
@Slf4j
public class QQAuthoriController extends BaseWebController {
	/*@Autowired
	private QQAuthoriFeign qqAuthoriFeign;*/

	private static final String MB_QQ_QQLOGIN = "member/qqlogin";

	@Autowired
	private MemberLoginServiceFeign memberLoginServiceFeign;
	/**
	 * 重定向到首页
	 */
	private static final String REDIRECT_INDEX = "redirect:/";

	/**
	 * 生成QQ授权回调地址
	 *
	 * @return
	 */
	@RequestMapping("/qqAuth")
	public String qqAuth(HttpServletRequest request) {
		try {
			String authorizeURL = new Oauth().getAuthorizeURL(request);
			log.info("authorizeURL",authorizeURL);
			return "redirect:" + authorizeURL;
		} catch (Exception e) {
			return ERROR_500_FTL;
		}
	}

	/**
	 *
	 * @param code
	 * @return
	 */
	/*@RequestMapping("/qqLoginBack")
	public String qqLoginBack(String code, HttpServletRequest request, HttpServletResponse response, HttpSession httpSession) {
		try {
			// 使用授权码获取accessToken
			AccessToken accessTokenObj = (new Oauth()).getAccessTokenByRequest(request);
			if (accessTokenObj == null) {
				return ERROR_500_FTL;
			}
			String accessToken = accessTokenObj.getAccessToken();
			if (StringUtils.isEmpty(accessToken)) {
				return ERROR_500_FTL;
			}
			// 使用accessToken获取用户openid
			OpenID openIDObj = new OpenID(accessToken);
			String openId = openIDObj.getUserOpenID();
			if (StringUtils.isEmpty(openId)) {
				return ERROR_500_FTL;
			}
			// 使用openid 查询数据库是否已经关联账号信息
			BaseResponse<JSONObject> findByOpenId = qqAuthoriFeign.findByOpenId(openId);
			if (!isSuccess(findByOpenId)) {
				return ERROR_500_FTL;
			}
			// 如果调用接口返回203 ,跳转到关联账号页面
			if (findByOpenId.getCode().equals(Constants.HTTP_RES_CODE_NOTUSER_203)) {
				// 页面需要展示 QQ头像
				UserInfo qzoneUserInfo = new UserInfo(accessToken, openId);
				UserInfoBean userInfoBean = qzoneUserInfo.getUserInfo();
				if (userInfoBean == null) {
					return ERROR_500_FTL;
				}
				// 用户的QQ头像
				String avatarURL100 = userInfoBean.getAvatar().getAvatarURL100();
				request.setAttribute("avatarURL100", avatarURL100);
				// 需要将openid存入在session中
				httpSession.setAttribute(WebConstants.LOGIN_QQ_OPENID, openId);
				return MB_QQ_QQLOGIN;
			}
			// 如果能够查询到用户信息的话,直接自动登陆
			// 自动实现登陆
			JSONObject data = findByOpenId.getData();
			String token = data.getString("token");
			CookieUtils.setCookie(request, response, WebConstants.LOGIN_TOKEN_COOKIENAME, token);
			return REDIRECT_INDEX;

		} catch (Exception e) {
			return ERROR_500_FTL;
		}

	}*/
	// 回调接口中 无法获取AccessToken
}