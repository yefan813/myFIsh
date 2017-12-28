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
	private Long userId;

	/****/
	private Integer type;

	public void setCommentId(Long commentId){
		this.commentId = commentId;
	}

	public Long getCommentId(){
		return this.commentId;
	}

	public Long getUserId() {
		return userId;
	}

	public void setUserId(Long userId) {
		this.userId = userId;
	}

	public Integer getType() {
		return type;
	}

	public void setType(Integer type) {
		this.type = type;
	}

	public String toString(){
		return ToStringBuilder.reflectionToString(this);
	}
}
