package com.ding.rpc;

import javax.annotation.Resource;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.ding.core.Result;
import com.ding.member.api.MemberQueryFeignApi;
import com.ding.member.dto.MemberDto;
import com.ding.service.IMemberService;

@Controller
@RequestMapping("/memberQueryFeign")
public class MemberQueryFeignClient implements MemberQueryFeignApi{

	@Resource
	private IMemberService memberService;
	
	@RequestMapping(value="/getMemberById",method=RequestMethod.GET)
	@ResponseBody
	@Override
	public Result<MemberDto> getMemberById(Long memberId) {
		System.out.println("查询的会员id==========="+memberId);
		MemberDto memberDto = memberService.getMemberById(memberId);
		return Result.ok(memberDto);
	}
	

}
