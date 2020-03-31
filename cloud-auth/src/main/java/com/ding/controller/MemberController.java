package com.ding.controller;

import java.security.Principal;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.oauth2.provider.token.ConsumerTokenServices;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.ding.core.Result;


@Controller
public class MemberController {
	
	@Autowired
    private ConsumerTokenServices consumerTokenServices;
	
	 /**
	  * 获取授权用户的信息
     * @param member 当前用户
     * @return 授权信息
     */
    @RequestMapping("/member")
    @ResponseBody
    public Principal member(Principal member){
        return member;
    }
    
    /**
     *	 注销access_token，注销access_token的同时refresh_token也会被注销掉
     * @param access_token
     * @return
     */
    @RequestMapping(value = "/member/exit",method=RequestMethod.DELETE)
    @ResponseBody
    public Result<String> revokeToken(String access_token) {
        if (access_token!=null && !"".equals(access_token) && consumerTokenServices.revokeToken(access_token)) {
        	return Result.ok("成功注销");
        } else {
        	return Result.error("注销失败");
        }
    }
}
 