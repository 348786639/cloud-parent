package com.ding.order.vo;

public class OrderInfoVo {
	
	private Long id;
	private String orderNo;
	
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public String getOrderNo() {
		return orderNo;
	}
	public void setOrderNo(String orderNo) {
		this.orderNo = orderNo;
	}
	@Override
	public String toString() {
		return "OrderInfoVo [id=" + id + ", orderNo=" + orderNo + "]";
	}
	
	
	

}
