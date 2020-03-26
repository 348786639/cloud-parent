package com.ding.entity;

import java.io.Serializable;
import java.math.BigDecimal;

public class OrderInfo implements Serializable{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private Long id;
	
	/**订单号*/
	private String orderNo;
	
	/**下单会员id*/
	private Long memberId;   
	
	/**下单会员名字*/
	private String memberName;
	
	/**订单金额*/
	private BigDecimal orderSum;
	
	/**付款时间*/		
	private String payTime;
	
	/**订单创建时间*/	
	private String createTime;
	
	/**订单更新时间*/	
	private String updateTime;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Long getMemberId() {
		return memberId;
	}

	public void setMemberId(Long memberId) {
		this.memberId = memberId;
	}

	public String getMemberName() {
		return memberName;
	}

	public void setMemberName(String memberName) {
		this.memberName = memberName;
	}

	public BigDecimal getOrderSum() {
		return orderSum;
	}

	public void setOrderSum(BigDecimal orderSum) {
		this.orderSum = orderSum;
	}

	public String getPayTime() {
		return payTime;
	}

	public void setPayTime(String payTime) {
		this.payTime = payTime;
	}

	public String getCreateTime() {
		return createTime;
	}

	public void setCreateTime(String createTime) {
		this.createTime = createTime;
	}

	public String getUpdateTime() {
		return updateTime;
	}

	public void setUpdateTime(String updateTime) {
		this.updateTime = updateTime;
	}

	public String getOrderNo() {
		return orderNo;
	}

	public void setOrderNo(String orderNo) {
		this.orderNo = orderNo;
	}
	
	
}
