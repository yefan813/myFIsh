package com.frame.dao.impl;

import com.frame.dao.AppSecretDao;
import com.frame.dao.CommentDao;
import com.frame.dao.base.BaseDaoImpl;
import com.frame.domain.AppSecret;
import com.frame.domain.Comment;
import org.springframework.stereotype.Repository;

@Repository("commentDao")
public class CommentDaoImpl extends BaseDaoImpl<Comment, Long> implements CommentDao {

	private final static String NAMESPACE = "com.frame.dao.CommentDao.";

	@Override
	public String getNameSpace(String statement) {
		// TODO Auto-generated method stub
		return NAMESPACE + statement;
	}


}
