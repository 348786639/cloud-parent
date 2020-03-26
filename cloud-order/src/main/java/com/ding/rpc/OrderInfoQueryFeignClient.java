package com.ding.rpc;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.ding.core.Result;
import com.ding.member.dto.MemberDto;
import com.ding.order.api.OrderInfoQueryFeignApi;
import com.ding.order.dto.OrderInfoDto;
import com.ding.service.IOrderInfoService;

@Controller
@RequestMapping("/orderInfoQuery")
public class OrderInfoQueryFeignClient implements OrderInfoQueryFeignApi{
	
	@Resource
	private IOrderInfoService orderInfoService;
	
	
	/**
	 * 根据会员获取订单列表
	 * 测试 feign  get传递对象
	 */
	@Override
	@RequestMapping(value="/getOrderInfoByMember")
	@ResponseBody
	public Result<List<OrderInfoDto>> getOrderInfoByMember(@RequestBody MemberDto memberDto) {
		List<OrderInfoDto> orderList = orderInfoService.getOrderInfoByMember(memberDto);
		return Result.ok(orderList);
	}

}
