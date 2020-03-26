package com.ding;


import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.netflix.hystrix.EnableHystrix;
import org.springframework.cloud.openfeign.EnableFeignClients;

//訂單服務
@SpringBootApplication(scanBasePackages = { "com.ding" })
@EnableDiscoveryClient
@EnableFeignClients
@EnableHystrix
public class OrderApplication{
	
	public static void main(String[] args) {
		SpringApplication.run(OrderApplication.class, args);
	}
}


