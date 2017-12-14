package com.frame.domain.vo;

import com.frame.domain.base.BaseDomain;
import org.apache.commons.lang.builder.ToStringBuilder;


/**
 * 
 * 
 * @author yefan
 * @date 2017-11-29 16:41:12
 **/
public class ArticalLikeVO{


	private Long id;
	/****/
	private Long articalId;

	/****/
	private Long userId;

	/****/
	private Integer like;

	/****/
	private Integer unliked;

	/****/

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


	public void setLike(Integer like){
		this.like = like;
	}

	public Integer getLike(){
		return this.like;
	}

	public void setUnliked(Integer unliked){
		this.unliked = unliked;
	}

	public Integer getUnliked(){
		return this.unliked;
	}

	public Long getUserId() {
		return userId;
	}

	public void setUserId(Long userId) {
		this.userId = userId;
	}

	public String toString(){
		return ToStringBuilder.reflectionToString(this);
	}
}
