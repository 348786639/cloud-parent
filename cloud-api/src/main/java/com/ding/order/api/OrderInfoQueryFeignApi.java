package com.ding.order.api;

import java.util.List;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.ding.core.Result;
import com.ding.member.dto.MemberDto;
import com.ding.order.dto.OrderInfoDto;

@FeignClient("cloud-order")
public interface OrderInfoQueryFeignApi {
	
	@RequestMapping(value="/orderInfoQuery/getOrderInfoByMember")
	public Result<List<OrderInfoDto>> getOrderInfoByMember(@RequestBody MemberDto memberDto);

}
