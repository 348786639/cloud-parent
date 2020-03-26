package com.ding.service;

import java.util.List;
import java.util.Map;

import org.springframework.cloud.netflix.zuul.filters.ZuulProperties.ZuulRoute;

import com.ding.entity.ZuulRouteEntity;



public interface IZuulRouteService {
	
	public Map<String, ZuulRoute> getZuulRoutes();
	
	public List<ZuulRouteEntity> getZuulRoutesList();

}
