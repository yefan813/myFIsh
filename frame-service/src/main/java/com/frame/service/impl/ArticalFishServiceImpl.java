package com.frame.service.impl;

import com.frame.dao.ArticalFishDao;
import com.frame.dao.base.BaseDao;
import com.frame.domain.ArticalFish;
import com.frame.service.ArticalFishService;
import com.frame.service.UserService;
import com.frame.service.base.BaseServiceImpl;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;


@Service("articalFishService")
public class ArticalFishServiceImpl extends BaseServiceImpl<ArticalFish, Long> implements ArticalFishService {
	private static final Logger LOGGER = LoggerFactory.getLogger(ArticalFishServiceImpl.class);

	@Resource
	private ArticalFishDao articalFishDao;

	@Resource
	private UserService uerService;

	@Value("${img.prefix}")
	private String IMAGEPREFIX;

	@Override
	public BaseDao<ArticalFish, Long> getDao() {
		// TODO Auto-generated method stub
		return articalFishDao;
	}
}
