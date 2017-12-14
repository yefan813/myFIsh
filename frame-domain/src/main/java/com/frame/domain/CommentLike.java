package com.frame.domain;
import java.io.Serializable;
import org.apache.commons.lang.builder.ToStringBuilder;
import com.frame.domain.base.BaseDomain;


/**
 * 
 * 
 * @author yefan
 * @date 2017-11-29 16:41:12
 **/
public class CommentLike  extends BaseDomain  {

	/****/
	private Long commentId;

	/****/

	/****/
	private Integer like;

	/****/
	private Integer unliked;

	private Boolean isLiked;

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

	public Boolean getLiked() {
		return isLiked;
	}

	public void setLiked(Boolean liked) {
		isLiked = liked;
	}

	public String toString(){
		return ToStringBuilder.reflectionToString(this);
	}
}
