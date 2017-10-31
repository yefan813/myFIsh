package com.frame.dao.impl;

import com.frame.dao.CommentDao;
import com.frame.dao.ReplyDao;
import com.frame.dao.base.BaseDaoImpl;
import com.frame.domain.Comment;
import com.frame.domain.Reply;
import org.springframework.stereotype.Repository;

@Repository("replyDao")
public class ReplyDaoImpl extends BaseDaoImpl<Reply, Long> implements ReplyDao {
	private final static String NAMESPACE = "com.frame.dao.ReplyDao.";

	@Override
	public String getNameSpace(String statement) {
		// TODO Auto-generated method stub
		return NAMESPACE + statement;
	}


}
