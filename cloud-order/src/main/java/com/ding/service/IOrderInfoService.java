package com.ding.service;

import java.util.List;

import com.ding.member.dto.MemberDto;
import com.ding.member.vo.MemberVo;
import com.ding.order.dto.OrderInfoDto;
import com.ding.order.vo.OrderInfoVo;

public interface IOrderInfoService {
	
	public OrderInfoVo getByOrderInfo(String orderNo);

	public List<OrderInfoDto> getOrderInfoByMember(MemberDto memberDto);

	public MemberVo getMemberByOrderNo(String orderNo);

}
