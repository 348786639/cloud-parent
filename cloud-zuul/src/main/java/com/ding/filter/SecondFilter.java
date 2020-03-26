package com.ding.filter;

import org.springframework.stereotype.Component;

import com.netflix.zuul.ZuulFilter;
import com.netflix.zuul.context.RequestContext;
import com.netflix.zuul.exception.ZuulException;

@Component
public class SecondFilter extends ZuulFilter{


	/**
	 * 表示当前的Filter执行不执行
	 * 返回true 执行run方法 false则不执行
	 */
	@Override
	public boolean shouldFilter() {
		RequestContext rc = RequestContext.getCurrentContext();
		System.out.println("SecondFilter======================="+rc.get("isExecFilter"));
		return false;
	}

	/**
	 * 定义同类型的Filter的执行顺序
	 * 值越小  越早执行
	 */
	@Override
	public int filterOrder() {
		// TODO Auto-generated method stub
		return 1;
	}

	/**
	 * 表示具体的Filter类型   
	 */
	@Override
	public String filterType() {
		return "pre";
	}

	/**
	 * 具体的Filter逻辑
	 */
	@Override
	public Object run() throws ZuulException {
		System.out.println("SecondFilter run ..............pre............");
		return null;
	}
}
