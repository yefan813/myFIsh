package com.frame.service.impl;

import com.frame.dao.FishSiteDao;
import com.frame.dao.base.BaseDao;
import com.frame.domain.FishSite;
import com.frame.service.FishSiteService;
import com.frame.service.base.BaseServiceImpl;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;


@Service("fishSiteService")
public class FishSiteServiceImpl extends BaseServiceImpl<FishSite, Long> implements FishSiteService {
	private static final Logger LOGGER = LoggerFactory.getLogger(FishSiteServiceImpl.class);

	@Resource
	private FishSiteDao fishSiteDao;

	@Value("${img.prefix}")
	private String IMAGEPREFIX;

	@Override
	public BaseDao<FishSite, Long> getDao() {
		// TODO Auto-generated method stub
		return fishSiteDao;
	}


}
