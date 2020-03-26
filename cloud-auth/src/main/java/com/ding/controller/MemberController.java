package com.ding.controller;

import java.security.Principal;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class MemberController {
	
	 /**
     * 获取授权用户的信息
     * @param member 当前用户
     * @return 授权信息
     */
    @RequestMapping("/member")
    public Principal member(Principal member){
        return member;
    } 
}
