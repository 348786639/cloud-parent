
package com.ding.order.exception;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.ding.enums.ErrorCodeEnum;
import com.ding.exception.BusinessException;

public class OrderBizException extends BusinessException {

	private static final long serialVersionUID = -6552248511084911254L;
	Logger log = LoggerFactory.getLogger(OrderBizException.class);
	
	public OrderBizException() {
	}

	
	public OrderBizException(int code, String msgFormat, Object... args) {
		super(code, msgFormat, args);
		log.info("<== OrderBizException, code:{}, message:{}", this.code, super.getMessage());
	}

	
	public OrderBizException(int code, String msg) {
		super(code, msg);
		log.info("<== OrderBizException, code:{}, message:{}", this.code, super.getMessage());
	}

	
	public OrderBizException(ErrorCodeEnum codeEnum) {
		super(codeEnum.code(), codeEnum.msg());
		log.info("<== OrderBizException, code:{}, message:{}", this.code, super.getMessage());
	}

	
	public OrderBizException(ErrorCodeEnum codeEnum, Object... args) {
		super(codeEnum, args);
		log.info("<== OrderBizException, code:{}, message:{}", this.code, super.getMessage());
	}
}
