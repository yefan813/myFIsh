package com.frame.domain.vo;

import com.frame.domain.base.BaseDomain;
import org.apache.commons.lang.builder.ToStringBuilder;


/**
 * 
 * 
 * @author yefan
 * @date 2017-11-29 16:41:12
 **/
public class ShopSiteCommentVO{


	private Long id;
	/****/
	private Integer type;

	/****/
	private Long localId;

	/****/
	private Long userId;

	/****/
	private String content;

	/****/
	private Integer isReview;

	/****/
	private String feature;

	/****/
	private java.util.Date created;

	/****/
	private java.util.Date modified;

	/****/
	private Integer yn;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public void setType(Integer type){
		this.type = type;
	}

	public Integer getType(){
		return this.type;
	}

	public void setLocalId(Long localId){
		this.localId = localId;
	}

	public Long getLocalId(){
		return this.localId;
	}

	public void setUserId(Long userId){
		this.userId = userId;
	}

	public Long getUserId(){
		return this.userId;
	}

	public void setContent(String content){
		this.content = content;
	}

	public String getContent(){
		return this.content;
	}

	public void setIsReview(Integer isReview){
		this.isReview = isReview;
	}

	public Integer getIsReview(){
		return this.isReview;
	}

	public void setFeature(String feature){
		this.feature = feature;
	}

	public String getFeature(){
		return this.feature;
	}

	public void setCreated(java.util.Date created){
		this.created = created;
	}

	public java.util.Date getCreated(){
		return this.created;
	}

	public void setModified(java.util.Date modified){
		this.modified = modified;
	}

	public java.util.Date getModified(){
		return this.modified;
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
