package com.ding.route;

import java.util.LinkedHashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.netflix.zuul.filters.RefreshableRouteLocator;
import org.springframework.cloud.netflix.zuul.filters.SimpleRouteLocator;
import org.springframework.cloud.netflix.zuul.filters.ZuulProperties;
import org.springframework.cloud.netflix.zuul.filters.ZuulProperties.ZuulRoute;
import org.springframework.util.StringUtils;

import com.ding.service.IZuulRouteService;

public class DynamicZuulRouteLocator extends SimpleRouteLocator implements RefreshableRouteLocator {

	@Autowired
	private ZuulProperties properties;

	@Autowired
	private IZuulRouteService zuulRouteService;

	public DynamicZuulRouteLocator(String servletPath, ZuulProperties properties) {
		super(servletPath, properties);
		this.properties = properties;
	}
	
	//实现RefreshableRouteLocator的refresh从而能实现动态路由
	@Override
	public void refresh() {
		doRefresh();
	}

	//重写locateRoutes方法从数据库加载路由配置
	@Override
	protected Map<String, ZuulRoute> locateRoutes() {
		LinkedHashMap<String, ZuulRoute> routesMap = new LinkedHashMap<>();
		routesMap.putAll(super.locateRoutes());//配置文件的静态路由
		routesMap.putAll(zuulRouteService.getZuulRoutes());//从数据库加载
		LinkedHashMap<String, ZuulRoute> values = new LinkedHashMap<>();
		routesMap.forEach((key, value) -> {//前缀处理
			String path = key;
			if (!path.startsWith("/")) {
				path = "/" + path;
			}
			if (StringUtils.hasText(this.properties.getPrefix())) {
				path = this.properties.getPrefix() + path;
				if (!path.startsWith("/")) {
					path = "/" + path;
				}
			}
			values.put(path, value);
		});
		return values;
	}
}
