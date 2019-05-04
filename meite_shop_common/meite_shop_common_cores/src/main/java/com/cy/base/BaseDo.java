package com.cy.base;

import lombok.Data;

import java.util.Date;

/**
 * 
 * 
 * 
 * @description:BaseDo
 * @date: 2019年1月3日 下午3:03:17
 * @version V1.0
 * @Copyright 该项目“基于SpringCloud2.x构建微服务电商项目”
 */
@Data
public class BaseDo {
	/**
	 * id
	 */
	private Long id;
	/**
	 * 注册时间
	 */
	private Date createTime;
	/**
	 * 修改时间
	 *
	 */
	private Date updateTime;
	/**
	 * 是否可用 0可用 1不可用
	 */
	private Long isAvailability;
}
