package com.ding.service.impl;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.Resource;

import org.modelmapper.ModelMapper;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;

import com.ding.core.Result;
import com.ding.entity.Member;
import com.ding.member.dto.MemberDto;
import com.ding.order.api.OrderInfoQueryFeignApi;
import com.ding.order.dto.OrderInfoDto;
import com.ding.order.vo.OrderInfoVo;
import com.ding.service.IMemberService;

@Service
public class MemberServiceImpl implements IMemberService{

	@Resource
	private OrderInfoQueryFeignApi orderInfoQueryApi;
	/**
	 * 根据会员id获取会员
	 * @param
	 */
	@Override
	public MemberDto getMemberById(Long memberId) {
		//去数据库查询
		if(memberId!=null){
			Member member = new Member();
			member.setId(memberId);
			member.setAge(28);
			member.setNickName("丁D");
			member.setPassword("123456");
			member.setUserName("dingd");
			ModelMapper modelMapper = new ModelMapper();
			return modelMapper.map(member, MemberDto.class);
		}
		return null;
	}

	/**
	 * 获取用户订单列表
	 * 测试feign get方式 参数对象
	 * @param
	 */
	@Override
	public List<OrderInfoVo> getOrderListByMember(Long memberId) {
		MemberDto memberDto = this.getMemberById(memberId);
		Result<List<OrderInfoDto>>  res = orderInfoQueryApi.getOrderInfoByMember(memberDto);
		List<OrderInfoVo> orders = new ArrayList<>();
		for(OrderInfoDto dto : res.getData()){
			OrderInfoVo orderVo = new OrderInfoVo();
			BeanUtils.copyProperties(dto, orderVo);
			orders.add(orderVo);
		}
		return orders;
	}
	
	

}
