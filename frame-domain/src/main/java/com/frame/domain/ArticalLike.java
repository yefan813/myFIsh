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
public class ArticalLike  extends BaseDomain  {

	/****/
	private Long articalId;

	/****/

	/****/
	private Long like;

	/****/
	private Long unliked;




	public void setArticalId(Long articalId){
		this.articalId = articalId;
	}

	public Long getArticalId(){
		return this.articalId;
	}


	public void setLike(Long like){
		this.like = like;
	}

	public Long getLike(){
		return this.like;
	}

	public void setUnliked(Long unliked){
		this.unliked = unliked;
	}

	public Long getUnliked(){
		return this.unliked;
	}

	public String toString(){
		return ToStringBuilder.reflectionToString(this);
	}
}
