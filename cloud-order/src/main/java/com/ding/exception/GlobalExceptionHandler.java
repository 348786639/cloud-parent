
package com.ding.exception;


import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import com.ding.core.Result;
import com.ding.enums.ErrorCodeEnum;


/**
 * 全局的的异常拦截器
 * 
 * 
 * */
@RestControllerAdvice
public class GlobalExceptionHandler {
	
	Logger log = LoggerFactory.getLogger(GlobalExceptionHandler.class);
	

	/**
	 * 参数非法异常.
	 */
	@ExceptionHandler(IllegalArgumentException.class)
	@ResponseStatus(HttpStatus.OK)
	@ResponseBody
	public Result<?> illegalArgumentException(IllegalArgumentException e) {
		log.error("参数非法异常={}", e.getMessage(), e);
		return Result.error(ErrorCodeEnum.OD0000001.code()+"", e.getMessage());
	}

	/**
	 * 业务异常.
	 */
	@ExceptionHandler(BusinessException.class)
	@ResponseStatus(HttpStatus.OK)
	@ResponseBody
	public Result<?> businessException(BusinessException e) {
		log.error("业务异常={}", e.getMessage(), e);
		return Result.error(ErrorCodeEnum.OD0000001.code()+"", e.getMessage());
	}


	/**
	 * 全局异常.
	 */
	@ExceptionHandler(Exception.class)
	@ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
	@ResponseBody
	public Result<?> exception(Exception e) {
		log.info("保存全局异常信息 ex={}", e.getMessage(), e);
		return Result.error();
	}
}
