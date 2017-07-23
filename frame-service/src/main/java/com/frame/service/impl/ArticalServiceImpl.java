package com.frame.service.impl;

import com.frame.dao.ArticalDao;
import com.frame.dao.base.BaseDao;
import com.frame.domain.Artical;
import com.frame.service.ArticalService;
import com.frame.service.UserService;
import com.frame.service.base.BaseServiceImpl;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;


@Service("articalService")
public class ArticalServiceImpl extends BaseServiceImpl<Artical, Long> implements ArticalService {
	private static final Logger LOGGER = LoggerFactory.getLogger(ArticalServiceImpl.class);

	@Resource
	private ArticalDao articalDao;

	@Resource
	private UserService UuerService;

	@Value("${img.prefix}")
	private String IMAGEPREFIX;

	@Override
	public BaseDao<Artical, Long> getDao() {
		// TODO Auto-generated method stub
		return articalDao;
	}


}
