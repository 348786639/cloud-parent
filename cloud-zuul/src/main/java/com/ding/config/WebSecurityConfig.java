package com.ding.config;

import org.springframework.boot.autoconfigure.security.oauth2.client.EnableOAuth2Sso;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;

@Configuration
@EnableOAuth2Sso
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {


 @Override 
	protected void configure(HttpSecurity http) throws Exception { http
	 .authorizeRequests().anyRequest() .authenticated()
	 .antMatchers("/login/**","/callBack/**","/dynamicZuul/**","/callBack/login/**") .permitAll()
	 .and() .csrf() .disable(); }
 
//注释的才会放行
// http
//	.authorizeRequests()
//		.antMatchers("/callBack/**", "/order11/**", "/about").permitAll()
//		.antMatchers("/admin/**").hasRole("ADMIN")
//		.antMatchers("/db/**").access("hasRole('ADMIN') and hasRole('DBA')")
//		.anyRequest().authenticated();


}
