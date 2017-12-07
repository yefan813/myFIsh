package com.frame.dao.impl;

import com.frame.dao.ShopSiteCommentDao;
import com.frame.domain.ShopSiteComment;
import com.frame.dao.base.BaseDao;
import com.frame.dao.base.BaseDaoImpl;

import org.springframework.stereotype.Repository;



/**
 * 
 * ShopSiteCommentDaoImpl数据库操作实现类
 * @author yefan
 * @date 2017-11-29 17:00:01
 **/

@Repository("shopSiteCommentDao")
public class ShopSiteCommentDaoImpl extends BaseDaoImpl<ShopSiteComment, Long> implements ShopSiteCommentDao {
	private final static String NAMESPACE = "com.frame.dao.ShopSiteCommentDao.";


	@Override
	public String getNameSpace(String statement) {
		return NAMESPACE + statement;
	}
}