package com.frame.dao.impl;
import com.frame.dao.CommentLikeDao;
import com.frame.dao.base.BaseDao;
import com.frame.domain.CommentLike;
import com.frame.dao.base.BaseDaoImpl;

import org.springframework.stereotype.Repository;

/**
 * 
 * CommentLikeDaoImpl数据库操作实现类
 * @author yefan
 * @date 2017-11-29 17:00:01
 **/

@Repository("commentLikeDao")
public class CommentLikeDaoImpl extends BaseDaoImpl<CommentLike, Long> implements CommentLikeDao{
	private final static String NAMESPACE = "com.frame.dao.CommentLikeDao.";


	@Override
	public String getNameSpace(String statement) {
		return NAMESPACE + statement;
	}
}