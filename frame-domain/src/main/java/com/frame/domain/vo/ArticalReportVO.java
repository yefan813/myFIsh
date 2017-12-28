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
public class ArticalReportVO{

	@ApiModelProperty(value = "文章 id")
	private Long articalId;

	/****/
	@ApiModelProperty(value = "用户 id")
	private Long userId;



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
