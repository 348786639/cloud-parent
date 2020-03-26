package com.ding.member.hystrix;

import org.springframework.stereotype.Component;


import com.ding.core.Result;
import com.ding.enums.ErrorCodeEnum;
import com.ding.member.api.MemberQueryFeignApi;
import com.ding.member.dto.MemberDto;

@Component
public class MemberQueryFeignHystrix implements MemberQueryFeignApi{

	@Override
	public Result<MemberDto> getMemberById(Long memberId) {
		System.out.println("test hsytrix");
		Result<MemberDto> res = new Result<>();
		res.setCode(ErrorCodeEnum.MEM9999404.code()+"");
		res.setSuccess(false);
		return res;
	}

}
