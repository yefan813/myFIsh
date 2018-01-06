package com.frame.domain;
import org.apache.commons.lang.builder.ToStringBuilder;
import com.frame.domain.base.BaseDomain;


/**
 * 
 * 
 * @author yefan
 * @date 2017-11-29 16:41:12
 **/
public class Collection  extends BaseDomain  {

	/****/
	private Long sourceId;
	/****/
	private Long userId;

	private Integer sourceType;


	public Long getSourceId() {
		return sourceId;
	}

	public void setSourceId(Long sourceId) {
		this.sourceId = sourceId;
	}

	public Integer getSourceType() {
		return sourceType;
	}

	public void setSourceType(Integer sourceType) {
		this.sourceType = sourceType;
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
