package com.frame.service.impl;

import com.frame.dao.ArticalDao;
import com.frame.dao.CommentDao;
import com.frame.dao.base.BaseDao;
import com.frame.domain.Artical;
import com.frame.domain.Comment;
import com.frame.service.ArticalService;
import com.frame.service.CommentService;
import com.frame.service.UserService;
import com.frame.service.base.BaseServiceImpl;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

@Service("commentService")
public class CommentServiceImpl extends BaseServiceImpl<Comment, Long> implements CommentService {
	private static final Logger LOGGER = LoggerFactory.getLogger(CommentServiceImpl.class);

	@Resource
	private CommentDao commentDao;

	@Resource
	private UserService userService;

	@Value("${img.prefix}")
	private String IMAGEPREFIX;

	@Override
	public BaseDao<Comment, Long> getDao() {
		// TODO Auto-generated method stub
		return commentDao;
	}


}
