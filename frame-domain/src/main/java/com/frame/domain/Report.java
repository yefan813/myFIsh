package com.frame.domain;
import java.io.Serializable;

import io.swagger.annotations.ApiModelProperty;
import org.apache.commons.lang.builder.ToStringBuilder;
import com.frame.domain.base.BaseDomain;


/**
 * 
 * 
 * @author yefan
 * @date 2017-11-29 16:41:12
 **/
public class Report  extends BaseDomain  {
	private Long sourceId;

	private Long userId;

	private Integer reportType;

	private Integer sourceType;


	public Long getSourceId() {
		return sourceId;
	}

	public void setSourceId(Long sourceId) {
		this.sourceId = sourceId;
	}

	public Long getUserId() {
		return userId;
	}

	public void setUserId(Long userId) {
		this.userId = userId;
	}

	public Integer getReportType() {
		return reportType;
	}

	public void setReportType(Integer reportType) {
		this.reportType = reportType;
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
