package com.cy.portal.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class IndexController {

	/**
	 * 跳转到首页
	 * 
	 * @return
	 */
	@RequestMapping("/")
	public String index() {
		return "index";
	}

	/**
	 * 跳转到首页
	 * 
	 * @return
	 */
	@RequestMapping("/index.html")
	public String indexHtml() {
		return index();
	}
}
