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
public class SiteCollectionVO{

	/****/
	@ApiModelProperty(value = "站点，钓点 id")
	private Long shopSiteId;

	/****/
	@ApiModelProperty(value = "user id")
	private Long userId;

	/****/
	@ApiModelProperty(value = "1钓点， 2渔具店")
	private Integer type;



	public void setShopSiteId(Long shopSiteId){
		this.shopSiteId = shopSiteId;
	}

	public Long getShopSiteId(){
		return this.shopSiteId;
	}

	public void setUserId(Long userId){
		this.userId = userId;
	}

	public Long getUserId(){
		return this.userId;
	}

	public void setType(Integer type){
		this.type = type;
	}

	public Integer getType(){
		return this.type;
	}


	public String toString(){
		return ToStringBuilder.reflectionToString(this);
	}
}
