package com.frame.dao.impl;

import com.frame.dao.SiteCollectionDao;
import com.frame.domain.SiteCollection;
import com.frame.dao.base.BaseDao;
import com.frame.dao.base.BaseDaoImpl;

import org.springframework.stereotype.Repository;



/**
 * 
 * SiteCollectionDaoImpl数据库操作实现类
 * @author yefan
 * @date 2017-11-29 17:00:01
 **/

@Repository("siteCollectionDao")
public class SiteCollectionDaoImpl extends BaseDaoImpl<SiteCollection, Long> implements SiteCollectionDao {
	private final static String NAMESPACE = "com.frame.dao.SiteCollectionDao.";


	@Override
	public String getNameSpace(String statement) {
		return NAMESPACE + statement;
	}
}