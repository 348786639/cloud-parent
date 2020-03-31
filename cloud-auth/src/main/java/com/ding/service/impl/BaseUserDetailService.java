package com.ding.service.impl;

import java.util.ArrayList;
import java.util.List;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.ding.entity.BaseUser;


@Service
public class BaseUserDetailService implements UserDetailsService {

	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		List<GrantedAuthority> authorities = new ArrayList();
		GrantedAuthority authority = new SimpleGrantedAuthority("ADMIN1");
		authorities.add(authority);
		org.springframework.security.core.userdetails.User user =  new org.springframework.security.core.userdetails.User(username,
                "123456", true, true, true, true, authorities);
		return new BaseUser(user);
	}
}
