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
public class CollectionVO{

	@ApiModelProperty(value="来源id")
	/**文章 id**/
	private Long sourceId;


	/**
	 * 1 文章 2 钓点 3 渔具店
	 */
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



	public String toString(){
		return ToStringBuilder.reflectionToString(this);
	}
}
