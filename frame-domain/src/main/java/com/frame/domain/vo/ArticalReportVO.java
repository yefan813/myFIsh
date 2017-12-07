package com.frame.domain.vo;

import com.frame.domain.base.BaseDomain;
import org.apache.commons.lang.builder.ToStringBuilder;


/**
 * 
 * 
 * @author yefan
 * @date 2017-11-29 16:41:12
 **/
public class ArticalReportVO{

	private Long id;
	/****/
	private Long articalId;

	/****/
	private Long userId;


	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public void setArticalId(Long articalId){
		this.articalId = articalId;
	}

	public Long getArticalId(){
		return this.articalId;
	}

	public void setUserId(Long userId){
		this.userId = userId;
	}

	public Long getUserId(){
		return this.userId;
	}

	public String toString(){
		return ToStringBuilder.reflectionToString(this);
	}
}
