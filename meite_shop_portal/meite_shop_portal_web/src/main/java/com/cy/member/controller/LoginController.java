package com.cy.member.controller;

import com.alibaba.fastjson.JSONObject;
import com.cy.base.BaseResponse;
import com.cy.constants.Constants;
import com.cy.core.bean.MiteBeanUtils;
import com.cy.member.controller.req.vo.LoginVo;
import com.cy.member.feign.MemberLoginServiceFeign;
import com.cy.member.input.dto.UserLoginInpDTO;
import com.cy.web.base.BaseWebController;
import com.cy.web.constans.WebConstants;
import com.cy.web.utils.CookieUtils;
import com.cy.web.utils.RandomValidateCodeUtil;
import nl.bitwalker.useragentutils.Browser;
import nl.bitwalker.useragentutils.UserAgent;
import nl.bitwalker.useragentutils.Version;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

@Controller
public class LoginController extends BaseWebController {
	/**
	 * 跳转到登陆页面页面
	 */
	private static final String MB_LOGIN_FTL = "member/login";
	@Autowired
	private MemberLoginServiceFeign memberLoginServiceFeign;
	/**
	 * 重定向到首页
	 */
	private static final String REDIRECT_INDEX = "redirect:/";
	/**
	 * 跳转到登录页面
	 * 
	 * @return
	 */
	@GetMapping("/login")
	public String getLogin() {
		return MB_LOGIN_FTL;
	}

	/**
	 * 跳转到登录页面
	 * 
	 * @return
	 */
	@PostMapping("/login")
	public String postLogin(@ModelAttribute("loginVo") LoginVo loginVo, Model model, HttpServletRequest request,
							HttpServletResponse response, HttpSession httpSession) {
		// 1.图形验证码判断
		String graphicCode = loginVo.getGraphicCode();
		if (!RandomValidateCodeUtil.checkVerify(graphicCode, httpSession)) {
			setErrorMsg(model, "图形验证码不正确!");
			return MB_LOGIN_FTL;
		}

		// 2.将vo转换dto调用会员登陆接口
		UserLoginInpDTO userLoginInpDTO = MiteBeanUtils.voToDto(loginVo, UserLoginInpDTO.class);
		userLoginInpDTO.setLoginType(Constants.MEMBER_LOGIN_TYPE_PC);
		String info = webBrowserInfo(request);
		userLoginInpDTO.setDeviceInfor(info);
		BaseResponse<JSONObject> login = memberLoginServiceFeign.login(userLoginInpDTO);
		if (!isSuccess(login)) {
			setErrorMsg(model, login.getMsg());
			return MB_LOGIN_FTL;
		}
		// 3.登陆成功之后如何处理 保持会话信息 将token存入到cookie 里面 首页读取cookietoken 查询用户信息返回到页面展示
		JSONObject data = login.getData();
		String token = data.getString("token");
		CookieUtils.setCookie(request, response, WebConstants.LOGIN_TOKEN_COOKIENAME, token);
		return REDIRECT_INDEX;
	}

	/**
	 * 获取浏览器信息
	 *
	 * @return
	 */
	public String webBrowserInfo(HttpServletRequest request) {
		// 获取浏览器信息
		Browser browser = UserAgent.parseUserAgentString(request.getHeader("User-Agent")).getBrowser();
		// 获取浏览器版本号
		Version version = browser.getVersion(request.getHeader("User-Agent"));
		String info = browser.getName() + "/" + version.getVersion();
		return info;
	}
}
