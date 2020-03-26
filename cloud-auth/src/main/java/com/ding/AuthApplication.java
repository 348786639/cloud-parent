package com.ding;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.context.annotation.Bean;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.BeanIds;

@SpringBootApplication(scanBasePackages = { "com.ding" })
@EnableDiscoveryClient
public class AuthApplication{
	public static void main(String[] args) {
		SpringApplication.run(AuthApplication.class, args);
	}
}
