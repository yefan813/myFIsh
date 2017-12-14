package com.frame.domain.vo;

import com.frame.domain.base.BaseDomain;
import org.apache.commons.lang.builder.ToStringBuilder;


/**
 * 
 * 
 * @author yefan
 * @date 2017-11-29 16:41:12
 **/
public class CommentLikeVO{


	private Long id;
	/****/
	private Long commentId;

	/****/

	/****/
	private Integer like;

	/****/
	private Integer unliked;

	private Boolean isMineLiked;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public void setCommentId(Long commentId){
		this.commentId = commentId;
	}

	public Long getCommentId(){
		return this.commentId;
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


	public String toString(){
		return ToStringBuilder.reflectionToString(this);
	}


	public Boolean getMineLiked() {
		return isMineLiked;
	}

	public void setMineLiked(Boolean mineLiked) {
		isMineLiked = mineLiked;
	}
}
