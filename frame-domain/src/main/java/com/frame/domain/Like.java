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
public class Like  extends BaseDomain  {

	/****/
	private Long sourceId;

	private Long userId;
	/****/

	/****/
	private Integer sourceType;
	/****/




	public void setSourceId(Long articalId){
		this.sourceId = articalId;
	}

	public Long getSourceId(){
		return this.sourceId;
	}

	public Integer getSourceType() {
		return sourceType;
	}

	public void setSourceType(Integer sourceType) {
		this.sourceType = sourceType;
	}

	public String toString(){
		return ToStringBuilder.reflectionToString(this);
	}

	public Long getUserId() {
		return userId;
	}

	public void setUserId(Long userId) {
		this.userId = userId;
	}
}
