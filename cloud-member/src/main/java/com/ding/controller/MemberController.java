package com.ding.controller;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.ding.core.Result;
import com.ding.order.vo.OrderInfoVo;
import com.ding.service.IMemberService;

@Controller
@RequestMapping("/member")
public class MemberController {
	
	@Resource
	private IMemberService memberService;
	/**
	 * 通过会员获取订单
	 * 测试feign get请求传递对象
	 * @return
	 */
	@RequestMapping("/getOrderListByMember")
	@ResponseBody
	public Result<List<OrderInfoVo>> getOrderListByMember(Long memberId){
		List<OrderInfoVo> orderList = memberService.getOrderListByMember(memberId);
		return Result.ok(orderList);
	} 

}
