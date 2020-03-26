package com.ding.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;

import com.ding.entity.ZuulRouteEntity;

@Mapper
public interface ZuulRouteMapper {
	
	public List<ZuulRouteEntity> getZuulRoutes();

}
