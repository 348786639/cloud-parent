package com.ding.member.api;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import com.ding.core.Result;
import com.ding.member.dto.MemberDto;
import com.ding.member.hystrix.MemberQueryFeignHystrix;

@FeignClient(value ="cloud-member",fallback=MemberQueryFeignHystrix.class)
public interface MemberQueryFeignApi {
	
	@RequestMapping(value = "/memberQueryFeign/getMemberById",method=RequestMethod.GET)
	Result<MemberDto> getMemberById(@RequestParam(value = "memberId")Long memberId);

}
