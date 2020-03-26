package com.ding.controller;

import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@EnableAutoConfiguration
@RequestMapping("/test")
public class TestController {

	/**
	 * 测试使用Feign获取会员昵称
	 * @param orderNo
	 * @return
	 */
	@RequestMapping("/xxx")
	public String getOrderInfoByOrderNo(String orderNo){
		System.out.println("===================");
		return "hhahahhahahahahha";
	}
	
	/**
	 * 测试使用Feign获取会员昵称
	 * @param orderNo
	 * @return
	 */
	@RequestMapping("/login")
	public String login(){
		System.out.println("========loginloginloginloginlogin===========");
		return "login";
	}
}
