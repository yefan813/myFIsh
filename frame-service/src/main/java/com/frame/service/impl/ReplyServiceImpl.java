package com.frame.service.impl;

import com.frame.dao.ArticalDao;
import com.frame.dao.ReplyDao;
import com.frame.dao.base.BaseDao;
import com.frame.domain.Artical;
import com.frame.domain.Reply;
import com.frame.service.ArticalService;
import com.frame.service.ReplyService;
import com.frame.service.UserService;
import com.frame.service.base.BaseServiceImpl;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;


@Service("ReplyService")
public class ReplyServiceImpl extends BaseServiceImpl<Reply, Long> implements ReplyService {
	private static final Logger LOGGER = LoggerFactory.getLogger(ReplyServiceImpl.class);

	@Resource
	private ReplyDao replyDao;

	@Resource
	private UserService UuerService;

	@Value("${img.prefix}")
	private String IMAGEPREFIX;

	@Override
	public BaseDao<Reply, Long> getDao() {
		// TODO Auto-generated method stub
		return replyDao;
	}


}
