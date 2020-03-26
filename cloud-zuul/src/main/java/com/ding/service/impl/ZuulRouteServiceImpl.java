package com.ding.service.impl;

import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.beans.BeanUtils;
import org.springframework.cloud.netflix.zuul.filters.ZuulProperties.ZuulRoute;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import com.ding.entity.ZuulRouteEntity;
import com.ding.mapper.ZuulRouteMapper;
import com.ding.service.IZuulRouteService;


@Service
public class ZuulRouteServiceImpl implements IZuulRouteService{

	@Resource
	private ZuulRouteMapper zuulRouteMapper;
	
	/**
	 * 动态路由
	 */
	@Override
	public Map<String, ZuulRoute> getZuulRoutes() {
		Map<String, ZuulRoute> routes = new LinkedHashMap<>();
		List<ZuulRouteEntity> list = zuulRouteMapper.getZuulRoutes();;//查询数据库
		list.forEach(entity -> {
			if (StringUtils.isEmpty(entity.getPath())) return;
			ZuulRoute zuulRoute = new ZuulRoute();
			BeanUtils.copyProperties(entity, zuulRoute);
			routes.put(zuulRoute.getPath(), zuulRoute);
		});
		return routes;
	}

	@Override
	public List<ZuulRouteEntity> getZuulRoutesList() {
		List<ZuulRouteEntity> list = zuulRouteMapper.getZuulRoutes();;//查询数据库
		return list;
	}


}
