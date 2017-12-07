package com.frame.service.impl;

import com.frame.dao.CommentLikeDao;
import com.frame.domain.CommentLike;
import com.frame.service.CommentLikeService;
import com.frame.dao.base.BaseDao;
import com.frame.service.base.BaseServiceImpl;

import org.springframework.stereotype.Service;
import javax.annotation.Resource;


/**
 * 
 * CommentLikeServiceImpl业务层实现类
 * @author yefan
 * @date 2017-11-29 17:02:57
 **/

@Service("commentLikeService")
public class CommentLikeServiceImpl extends BaseServiceImpl<CommentLike, Long> implements CommentLikeService{
	@Resource
	private CommentLikeDao commentLikeDao;

	public BaseDao<CommentLike, Long> getDao(){
		return commentLikeDao;
	}

}