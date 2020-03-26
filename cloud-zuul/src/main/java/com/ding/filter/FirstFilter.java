package com.ding.filter;

import javax.servlet.http.HttpServletRequest;

import org.springframework.cloud.netflix.zuul.filters.support.FilterConstants;
import org.springframework.stereotype.Component;

import com.netflix.zuul.ZuulFilter;
import com.netflix.zuul.context.RequestContext;
import com.netflix.zuul.exception.ZuulException;


@Component
public class FirstFilter extends ZuulFilter{

	
	/**
	 * 表示当前的Filter执行不执行
	 * 返回true 执行run方法 false则不执行
	 */
	@Override
	public boolean shouldFilter() {
		return false;
	}
	/**
	 * 定义同类型的Filter的执行顺序
	 * 值越小  越早执行
	 */
	@Override
	public int filterOrder() {
		return FilterConstants.PRE_DECORATION_FILTER_ORDER -1 ;
	}
	/**
	 * 表示具体的Filter类型   
	 */
	@Override
	public String filterType() {
		return FilterConstants.PRE_TYPE;
	}
	/**
	 * 具体的Filter逻辑
	 */
	@Override
	public Object run() throws ZuulException {
		System.out.println("FirstFilter run....................pre.................");
		//RequestContext使用这个来功效状态
		RequestContext rc = RequestContext.getCurrentContext();
		HttpServletRequest req = rc.getRequest();
		
		String orderNo = req.getParameter("orderNo");		
		if(null == orderNo || "".equals(orderNo)){
			
			rc.setResponseBody("{\"message\":\"order is not null\"}");
			//禁止路由到下游服务
			rc.setSendZuulResponse(false);
			//false表示不执行同类型的  下面的Filter   但是post的还会执行
			rc.set("isExecFilter",false);
		}
		rc.set("isExecFilter",true);
		return null;
	}
}
