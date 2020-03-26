/*
 * Copyright (c) 2018. paascloud.net All Rights Reserved.
 * 项目名称：paascloud快速搭建企业级分布式微服务平台
 * 类名称：ErrorCodeEnum.java
 * 创建人：刘兆明
 * 联系方式：paascloud.net@gmail.com
 * 开源地址: https://github.com/paascloud
 * 博客地址: http://blog.paascloud.net
 * 项目官网: http://paascloud.net
 */
package com.ding.enums;


public enum ErrorCodeEnum {
	/*** 会员服务 */
	MEM9999404(9999404, "会员服务不能访问"),
	
	/*** 订单服务 */
	OD0000000(0000000, "系统内部异常"),
	OD0000001(0000001, "非法参数"),
	;
	private int code;
	private String msg;

	
	public String msg() {
		return msg;
	}

	
	public int code() {
		return code;
	}

	ErrorCodeEnum(int code, String msg) {
		this.code = code;
		this.msg = msg;
	}
	
	public static ErrorCodeEnum getEnum(int code) {
		for (ErrorCodeEnum ele : ErrorCodeEnum.values()) {
			if (ele.code() == code) {
				return ele;
			}
		}
		return null;
	}
}
