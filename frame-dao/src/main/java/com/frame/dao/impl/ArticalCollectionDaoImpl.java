package com.frame.dao.impl;

import com.frame.dao.ArticalCollectionDao;
import com.frame.domain.ArticalCollection;
import com.frame.dao.base.BaseDao;
import com.frame.dao.base.BaseDaoImpl;

import org.springframework.stereotype.Repository;



/**
 * 
 * ArticalCollectionDaoImpl数据库操作实现类
 * @author yefan
 * @date 2017-11-29 17:00:01
 **/

@Repository("articalCollectionDao")
public class ArticalCollectionDaoImpl extends BaseDaoImpl<ArticalCollection, Long> implements ArticalCollectionDao {
	private final static String NAMESPACE = "com.frame.dao.ArticalCollectionDao.";


	@Override
	public String getNameSpace(String statement) {
		return NAMESPACE + statement;
	}
}