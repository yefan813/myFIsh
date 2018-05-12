/*
 * Copyright (c) 2018 www.xxxx.com All rights reserved.
 * 未经许可不得任意复制与传播.
 */
package com.frame.domain;


import com.frame.domain.base.BaseDomain;

/**
 * tb_scan_record
 * @author Evan
 * @since 2018-05-12
 */
public class ScanRecord extends BaseDomain {
	private static final long serialVersionUID = 1L;

			private Integer type;
		private Long uniqueId;
		private Long targetId;
					
		
	/**
	 * 获取 浏览类型
	 * @return
	 */
	public Integer getType(){
		return type;
	}

	/**
	 * 设置 浏览类型
	 * @param type
	 */
	public void setType(Integer type){
		this.type = type;
	}
	
	/**
	 * 获取 发起者 id
	 * @return
	 */
	public Long getUniqueId(){
		return uniqueId;
	}

	/**
	 * 设置 发起者 id
	 * @param uniqueId
	 */
	public void setUniqueId(Long uniqueId){
		this.uniqueId = uniqueId;
	}
	
	/**
	 * 获取 目标 id
	 * @return
	 */
	public Long getTargetId(){
		return targetId;
	}

	/**
	 * 设置 目标 id
	 * @param targetId
	 */
	public void setTargetId(Long targetId){
		this.targetId = targetId;
	}
					}