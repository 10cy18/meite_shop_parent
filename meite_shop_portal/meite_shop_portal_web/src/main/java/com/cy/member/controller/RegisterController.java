package com.cy.member.controller;

import com.alibaba.fastjson.JSONObject;
import com.cy.base.BaseResponse;
import com.cy.core.bean.MiteBeanUtils;
import com.cy.member.controller.req.vo.RegisterVo;
import com.cy.member.feign.MemberRegisterServiceFeign;
import com.cy.member.input.dto.UserInpDTO;
import com.cy.web.base.BaseWebController;
import com.cy.web.utils.RandomValidateCodeUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

import javax.servlet.http.HttpSession;

@Controller
@Slf4j
public class RegisterController extends BaseWebController {
	private static final String MB_REGISTER_FTL = "member/register";
	private static final String MEMBER_REGISTER_PAGE = "member/register";
	/**
	 * 跳转到登陆页面页面
	 */
	private static final String MB_LOGIN_FTL = "member/login";

	@Autowired
	private MemberRegisterServiceFeign memberRegisterServiceFeign;

	/**
	 * 跳转到注册页面
	 * 
	 * @return
	 */
	@GetMapping("/register")
	public String getRegister() {
		return MEMBER_REGISTER_PAGE;
	}

	/**
	 * 跳转到注册页面
	 * 
	 * @return
	 */
	@PostMapping("/register")
	public String postRegister(@ModelAttribute("registerVo") @Validated RegisterVo registerVo,BindingResult bindingResult, HttpSession httpSession, Model model) {
		// 1.参数验证
		if (bindingResult.hasErrors()) {
			// 获取第一个错误!
			String errorMsg = bindingResult.getFieldError().getDefaultMessage();
			setErrorMsg(model, errorMsg);
			return MB_REGISTER_FTL;
		}
		//2.判断图形验证码是否正确
		String graphicCode = registerVo.getGraphicCode();
		Boolean checkVerify = RandomValidateCodeUtil.checkVerify(graphicCode, httpSession);
		if(!checkVerify){
			setErrorMsg(model, "验证码错误！");
			return MB_REGISTER_FTL;
		}
		// 将VO转换DTO
		UserInpDTO voToDto = MiteBeanUtils.voToDto(registerVo, UserInpDTO.class);
		try {
			String registCode = registerVo.getRegistCode();
			BaseResponse<JSONObject> register = memberRegisterServiceFeign.register(voToDto, registCode);
			if(!isSuccess(register)){
				model.addAttribute("error", register.getMsg());
				return MB_REGISTER_FTL;
			}
		} catch (Exception e) {
			log.error(">>>>>", e);
			model.addAttribute("error", "系统出现错误!");
			return MB_REGISTER_FTL;
		}

		// 注册成功,跳转到登陆页面
		return MB_LOGIN_FTL;
	}
}
