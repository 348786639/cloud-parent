package com.ding.config;

import org.springframework.boot.autoconfigure.security.oauth2.client.EnableOAuth2Sso;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;

@Configuration
@EnableOAuth2Sso
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {


	@Override 
	protected void configure(HttpSecurity http) throws Exception { 
		//错误写法  放行不了会报401  不知道为什么放行不了  不知道为什么会401
		//http
	 	//.authorizeRequests().anyRequest() .authenticated()
	 	//.antMatchers("/login/**","/callBack/**","/dynamicZuul/**","/callBack/login/**","/order/**") .permitAll()
	 	//.and() .csrf() .disable(); 
	    //正确写法，不知道为什么
		http
		.authorizeRequests()
		.antMatchers("/callBack/**", "/order/**","/auth2/**").permitAll().and().csrf().disable();
		//.antMatchers("/admin/**").hasRole("ADMIN")
		//.antMatchers("/db/**").access("hasRole('ADMIN') and hasRole('DBA')")
		//.anyRequest().authenticated();
		
		//.csrf().disable(); 不写这个 403 post
	}
 
//注释的才会放行
// http
//	.authorizeRequests()
//		.antMatchers("/callBack/**", "/order11/**", "/about").permitAll()
//		.antMatchers("/admin/**").hasRole("ADMIN")
//		.antMatchers("/db/**").access("hasRole('ADMIN') and hasRole('DBA')")
//		.anyRequest().authenticated();


}
