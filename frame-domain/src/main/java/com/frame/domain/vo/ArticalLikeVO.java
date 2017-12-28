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
public class ArticalLikeVO{

	/****/
	@ApiModelProperty(value="文章ID")
	private Long articalId;

	/****/
	@ApiModelProperty(value=" 用户 ID")
	private Long userId;

	/****/

	/****/
	@ApiModelProperty(value="1,喜欢 2，不喜欢")
	private Integer type;

	public Integer getType() {
		return type;
	}

	public void setType(Integer type) {
		this.type = type;
	}

	public void setArticalId(Long articalId){
		this.articalId = articalId;
	}

	public Long getArticalId(){
		return this.articalId;
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
