package com.ding.controller;

import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.ding.core.Result;
import com.ding.entity.ZuulRouteEntity;
import com.ding.service.IZuulRouteService;

@Controller
@RequestMapping("/dynamicZuul")
public class DynamicZuulController {
	
	@Resource
	private IZuulRouteService zuulRouteService;
	
	/**
	 * 测试mybatis是否成功集成
	 * 查询数据库路由 
	 * @return
	 * @throws Exception
	 * http://127.0.0.1:10086/dynamicZuul/getZuulRoutes
	 */
	@RequestMapping("/getZuulRoutes")
	@ResponseBody
	public Result<List<ZuulRouteEntity>> getZuulRoutes() throws Exception{
		System.out.println("getZuulRoutes:::::");
		Result<List<ZuulRouteEntity>> res = new Result<>();
		List<ZuulRouteEntity> routeList = zuulRouteService.getZuulRoutesList();
		res.setData(routeList);
		return res;
	}
}
