package com.frame.dao.impl;

import com.frame.dao.ArticalLikeDao;
import com.frame.domain.ArticalLike;
import com.frame.dao.base.BaseDao;
import com.frame.dao.base.BaseDaoImpl;

import org.springframework.stereotype.Repository;



/**
 * 
 * ArticalLikeDaoImpl数据库操作实现类
 * @author yefan
 * @date 2017-11-29 17:00:01
 **/

@Repository("articalLikeDao")
public class ArticalLikeDaoImpl extends BaseDaoImpl<ArticalLike, Long> implements ArticalLikeDao {
	private final static String NAMESPACE = "com.frame.dao.ArticalLikeDao.";


	@Override
	public String getNameSpace(String statement) {
		return NAMESPACE + statement;
	}
}