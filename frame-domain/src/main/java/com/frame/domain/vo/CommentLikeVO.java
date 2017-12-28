package com.frame.domain.vo;

import com.frame.domain.base.BaseDomain;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import org.apache.commons.lang.builder.ToStringBuilder;


/**
 * 
 * 
 * @author yefan
 * @date 2017-11-29 16:41:12
 **/
@ApiModel
public class CommentLikeVO{


	/****/
	@ApiModelProperty(value = "评论 id")
	private Long commentId;

	@ApiModelProperty(value = "用户 id")
	private Long userId;

	/****/

	/****/
	@ApiModelProperty(value = "1 喜欢 2不喜欢")
	private Integer type;



	public void setCommentId(Long commentId){
		this.commentId = commentId;
	}

	public Long getCommentId(){
		return this.commentId;
	}


	public Integer getType() {
		return type;
	}

	public Long getUserId() {
		return userId;
	}

	public void setUserId(Long userId) {
		this.userId = userId;
	}

	public void setType(Integer type) {
		this.type = type;
	}

	public String toString(){
		return ToStringBuilder.reflectionToString(this);
	}

}
