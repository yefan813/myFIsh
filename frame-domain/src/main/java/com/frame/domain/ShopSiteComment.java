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
public class ShopSiteComment  extends BaseDomain  {

	/****/
	private Integer type;

	/****/
	private Long localId;

	/****/
	private Long userId;

	/****/
	private String content;




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


	public String toString(){
		return ToStringBuilder.reflectionToString(this);
	}
}
