package com.ding.controller;

import java.util.Base64;
import java.util.HashMap;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.client.RestTemplate;

import com.ding.entity.token;
import com.ding.utils.HttpUtil;

@Controller
public class CallBackController {
	
	@Resource
	private RestTemplate restTemplate;
	
	@Value("${self.grant-type}")
    private String grantType;
	
	@Value("${security.oauth2.client.client-secret}")
    private String clientSecret;
	
	@Value("${security.oauth2.client.client-id}")
    private String clientId;

	//@Value("${security.oauth2.client.registered-redirect-uri}")
    private String redirectUri;
	
	@Value("${security.oauth2.client.access-token-uri}")
    private String tokenUri;
	
    
	@RequestMapping("/callBack")
	@ResponseBody
	public String callBack(String code,String state) throws Exception{
		System.out.println("code:::::"+code);
		if(code==null || state==null){
			return "error";
		}
		System.out.println("code:::::"+code);
		Map<String,Object> paramsMap = new HashMap<>();
		paramsMap.put("grant_type", grantType);
		paramsMap.put("code", code);
		paramsMap.put("redirect_uri", redirectUri);
		paramsMap.put("state", state);
		paramsMap.put("client_id", clientId);
		paramsMap.put("client_secret", clientSecret);
		String returnStr = HttpUtil.post(tokenUri, paramsMap, null, null);
		System.out.println("returnStrreturnStrreturnStrreturnStr"+returnStr);
		return returnStr;
	}
	
	@RequestMapping("/callBack/login")
	@ResponseBody
	public ResponseEntity<token> login(String user,String pass) throws Exception{
		Map<String,Object> paramsMap = new HashMap<>();
		paramsMap.put("grant_type", "password");
		paramsMap.put("username",user);
		paramsMap.put("password", pass);
		paramsMap.put("state", 1);
		paramsMap.put("client_id", clientId);
		paramsMap.put("client_secret", clientSecret);
		//String returnStr = HttpUtil.post(tokenUri, paramsMap, null, null);
		
		String clientAndSecret =clientId +":"+clientSecret;
        //这里需要注意为 Basic 而非 Bearer
        clientAndSecret = "Basic "+Base64.getEncoder().encodeToString(clientAndSecret.getBytes());
        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.set("Authorization",clientAndSecret);
		HttpEntity httpEntity = new HttpEntity(paramsMap);
        //获取 Token
        return restTemplate.exchange(tokenUri, HttpMethod.POST,httpEntity,token.class);
		
	}
}
