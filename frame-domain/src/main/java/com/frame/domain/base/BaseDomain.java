package com.frame.domain.base;

import java.util.Date;


/**
 * 领域模型基类(常规公共字段)<br/>
 * 一律使用引用类型
 * @author 
 * @since 2014-03-01
 */
public class BaseDomain extends BaseQuery {
	
	private static final long serialVersionUID = -2671530029171920798L;
	private Integer id;
	private String feature;
	private Date created;
	private Date modified;
	private Integer yn;
	
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	public String getFeature() {
		return feature;
	}
	public void setFeature(String feature) {
		this.feature = feature;
	}
	public Date getCreated() {
		return created;
	}
	public void setCreated(Date created) {
		this.created = created;
	}
	public Date getModified() {
		return modified;
	}
	public void setModified(Date modified) {
		this.modified = modified;
	}
	public Integer getYn() {
		return yn;
	}
	public void setYn(Integer yn) {
		this.yn = yn;
	}
	@Override
	public String toString() {
		return "BaseDomain [id=" + id + ", feature=" + feature + ", created="
				+ created + ", modified=" + modified + ", yn=" + yn + "]";
	}
	
	
}
