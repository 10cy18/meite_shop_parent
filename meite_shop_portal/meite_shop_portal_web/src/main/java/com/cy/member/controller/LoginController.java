package com.cy.member.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
public class LoginController {
	private static final String MEMBER_LOGIN_PAGE = "member/login";

	/**
	 * 跳转到注册页面
	 * 
	 * @return
	 */
	@GetMapping("/login")
	public String getLogin() {
		return MEMBER_LOGIN_PAGE;
	}

	/**
	 * 跳转到注册页面
	 * 
	 * @return
	 */
	@PostMapping("/login")
	public String postLogin() {
		return null;
	}

}