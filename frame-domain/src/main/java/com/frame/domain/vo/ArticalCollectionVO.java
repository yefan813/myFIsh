package com.frame.domain.vo;

import com.frame.domain.base.BaseDomain;
import org.apache.commons.lang.builder.ToStringBuilder;


/**
 * 
 * 
 * @author yefan
 * @date 2017-11-29 16:41:12
 **/
public class ArticalCollectionVO{


	/****/
	private Long articalId;

	/****/
	private Long userId;

	/****/
	private String feature;

	/****/
	private java.util.Date modified;

	/****/
	private java.util.Date created;

	/****/
	private Integer yn;


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

	public void setFeature(String feature){
		this.feature = feature;
	}

	public String getFeature(){
		return this.feature;
	}

	public void setModified(java.util.Date modified){
		this.modified = modified;
	}

	public java.util.Date getModified(){
		return this.modified;
	}

	public void setCreated(java.util.Date created){
		this.created = created;
	}

	public java.util.Date getCreated(){
		return this.created;
	}

	public void setYn(Integer yn){
		this.yn = yn;
	}

	public Integer getYn(){
		return this.yn;
	}

	public String toString(){
		return ToStringBuilder.reflectionToString(this);
	}
}
