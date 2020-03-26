package com.ding.service;

import java.util.List;

import com.ding.member.dto.MemberDto;
import com.ding.order.vo.OrderInfoVo;

public interface IMemberService {

	MemberDto getMemberById(Long memberId);

	List<OrderInfoVo> getOrderListByMember(Long memberId);

}
