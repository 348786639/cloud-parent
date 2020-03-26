package com.ding.controller;

import java.util.Enumeration;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.ding.core.Result;
import com.ding.member.dto.MemberDto;
import com.ding.member.vo.MemberVo;
import com.ding.order.vo.OrderInfoVo;
import com.ding.service.IOrderInfoService;


@Controller
@RequestMapping("/orderInfo")
public class OrderInfoController {
	
	@Resource
	private IOrderInfoService orderInfoService;
	
	/**
	 * 测试使用Feign获取会员昵称
	 * @param orderNo
	 * @return
	 */
	@RequestMapping("/getByOrderNo")
	@ResponseBody
	public Result<OrderInfoVo> getOrderInfoByOrderNo(String orderNo,HttpServletRequest request){
		
		System.out.println("----------------header----------------");
		Enumeration headerNames = request.getHeaderNames();
		while (headerNames.hasMoreElements()) {
			String key = (String) headerNames.nextElement();
			System.out.println(key + ": " + request.getHeader(key));
		}
		System.out.println("----------------header----------------");
		OrderInfoVo orderInfo = orderInfoService.getByOrderInfo(orderNo);
		return Result.ok(orderInfo);
	}
	
	/**
	 * 通过订单号获取会员信息
	 * 模拟hystrix
	 * @return
	 */
	@RequestMapping("/getMemberByOrderNo")
	@ResponseBody
	public Result<MemberVo> getMemberByOrderNo(String orderNo){
		MemberVo memberVo = orderInfoService.getMemberByOrderNo(orderNo);
		return Result.ok(memberVo);
	}
	
	
	/**
	 * 测试安全token
	 * @return
	 */
	@RequestMapping("/testAuth")
	@ResponseBody
	public Result<MemberVo> testAuth(String orderNo){
		System.out.println("===============================");
		return Result.ok(null);
	}
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	//http://127.0.0.1:10092/orderInfo/test2?id=1&orderNo=45613  可以
	@RequestMapping(value="/test1", method = RequestMethod.GET)
	public Result test1( OrderInfoVo orderInfoVo){
		System.out.println(orderInfoVo);
		return Result.ok(null);
	}
	
	//http://127.0.0.1:10092/orderInfo/test2?orderInfoVo.id=1&orderInfoVo.orderNo=45613  报错orderInfoVo is not present
	//http://127.0.0.1:10092/orderInfo/test2?id=1&orderNo=45613  报错orderInfoVo is not present
	@RequestMapping(value="/test2", method = RequestMethod.GET)
	public Result test2( @RequestParam OrderInfoVo orderInfoVo){
		System.out.println(orderInfoVo);
		return Result.ok(null);
	}
	
	
	//@RequestBody http://127.0.0.1:10092/orderInfo/test3?orderInfoVo.id=1&orderNo=45613  报错说body miss
	//http://127.0.0.1:10092/orderInfo/test3    可以
	/*{
	    "orderNo": "45613", 
	    "id":1 
	}*/
	@RequestMapping(value="/test3", method = RequestMethod.GET)
	public Result test3( @RequestBody OrderInfoVo orderInfoVo){
		System.out.println(orderInfoVo);
		return Result.ok(null);
	}
}
