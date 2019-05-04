package com.cy.member.mapper.entity;

import com.cy.base.BaseDo;
import lombok.Data;

@Data
public class UserTokenDo extends BaseDo {
	/**
	 * 用户token
	 */
	private String token;
	/**
	 * 登陆类型
	 */
	private String loginType;

	/**
	 * 设备信息
	 */
	private String deviceInfor;
	/**
	 * 用户userId
	 */
	private Integer userId;


}
