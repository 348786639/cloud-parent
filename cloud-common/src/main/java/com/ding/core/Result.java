package com.ding.core;

import com.ding.enums.ErrorCodeEnum;

public class Result<T> {
	
	private boolean success = true;
	private String code;
	private String msg;
	private T	data;
	
	
	
	public Result(boolean success, String code, String msg, T data) {
		super();
		this.success = success;
		this.code = code;
		this.msg = msg;
		this.data = data;
	}
	
	public Result(T data) {
		super();
		this.data = data;
	}

	public Result(boolean success, String msg) {
		super();
		this.success = success;
		this.msg = msg;
	}

	public Result() {
		super();
	}

	public boolean isSuccess() {
		return success;
	}
	public void setSuccess(boolean success) {
		this.success = success;
	}
	public String getCode() {
		return code;
	}
	public void setCode(String code) {
		this.code = code;
	}
	public String getMsg() {
		return msg;
	}
	public void setMsg(String msg) {
		this.msg = msg;
	}
	public T getData() {
		return data;
	}
	public void setData(T data) {
		this.data = data;
	}
	
	public static <T> Result<T> ok(T data){
		return new Result<>(data);
	}
	
	public static <T> Result<T> error(){
		Result<T> res = new Result<>(false,ErrorCodeEnum.OD0000000.code()+"",ErrorCodeEnum.OD0000000.name(),null);
		return res;
	}
	
	public static <T> Result<T> error(String code){
		String msg = "";
		if(ErrorCodeEnum.getEnum(Integer.valueOf(code))!=null){
			msg = ErrorCodeEnum.getEnum(Integer.valueOf(code)).msg();
		}
		Result<T> res = new Result<>(false,code,msg,null);
		return res;
	}
	
	public static <T> Result<T> error(String code,String message){
		Result<T> res = new Result<>(false,code,message,null);
		return res;
	}
}
