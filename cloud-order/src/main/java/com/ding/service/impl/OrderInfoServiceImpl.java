package com.ding.service.impl;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import javax.annotation.Resource;

import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import com.ding.core.Result;
import com.ding.entity.OrderInfo;
import com.ding.enums.ErrorCodeEnum;
import com.ding.member.api.MemberQueryFeignApi;
import com.ding.member.dto.MemberDto;
import com.ding.member.vo.MemberVo;
import com.ding.order.dto.OrderInfoDto;
import com.ding.order.exception.OrderBizException;
import com.ding.order.vo.OrderInfoVo;
import com.ding.service.IOrderInfoService;
import com.google.common.collect.Lists;

@Service
public class OrderInfoServiceImpl implements IOrderInfoService{

	@Resource
	private MemberQueryFeignApi memberApi;
	/**
	 * 根据订单号获取订单
	 * @param orderNo 订单号
	 */
	@Override
	public OrderInfoVo getByOrderInfo(String orderNo) {
		if(orderNo!=null && !orderNo.equals("")){
			OrderInfo orderInfo = new OrderInfo();
			orderInfo.setId(1L);
			orderInfo.setMemberId(1L);
			Result<MemberDto> result= memberApi.getMemberById(orderInfo.getMemberId());
			orderInfo.setMemberName(result.getData().getNickName());
			orderInfo.setOrderSum(new BigDecimal(88.888));
			orderInfo.setCreateTime("2019-06-10 12:23:55");
			orderInfo.setPayTime("2019-06-10 12:28:55");
			orderInfo.setUpdateTime("2019-06-10 12:28:55");
			orderInfo.setOrderNo(orderNo);
			ModelMapper modelMapper = new ModelMapper();
			return modelMapper.map(orderInfo, OrderInfoVo.class);
		}
		return null;
	}
	
	/**
	 * 根据用户查询订单列表
	 */
	@Override
	public List<OrderInfoDto> getOrderInfoByMember(MemberDto memberDto) {
		
		if("丁D".equals(memberDto.getNickName()) && memberDto.getId()==1L){
			List<OrderInfoDto> orderList = new ArrayList<>();
			OrderInfoDto order1 = new OrderInfoDto();
			order1.setMemberId(1L);
			order1.setOrderNo("255555");
			orderList.add(order1);
			return orderList;
		}
		return null;
	}
	
	/**
	 * 通过订单号获取会员信息
	 * 模拟hystrix
	 */
	@Override
	public MemberVo getMemberByOrderNo(String orderNo) {
		OrderInfo orderInfo = new OrderInfo();
		orderInfo.setMemberId(1L);
		//member服务不启动  调用失败  模拟hystrix
		Result<MemberDto> result= memberApi.getMemberById(orderInfo.getMemberId());
		if(result!=null && ErrorCodeEnum.MEM9999404.code()==Integer.valueOf(result.getCode())){
			throw new OrderBizException(ErrorCodeEnum.MEM9999404);
		}
		ModelMapper modelMapper = new ModelMapper();
		return modelMapper.map(result.getData(), MemberVo.class);
	}
}
