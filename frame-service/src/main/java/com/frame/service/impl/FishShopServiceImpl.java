package com.frame.service.impl;

import com.frame.dao.FishShopDao;
import com.frame.dao.base.BaseDao;
import com.frame.domain.FishShop;
import com.frame.service.FishShopService;
import com.frame.service.base.BaseServiceImpl;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;


@Service("fishShopService")
public class FishShopServiceImpl extends BaseServiceImpl<FishShop, Long> implements FishShopService {
	private static final Logger LOGGER = LoggerFactory.getLogger(FishShopServiceImpl.class);

	@Resource
	private FishShopDao fishShopDao;

	@Value("${img.prefix}")
	private String IMAGEPREFIX;

	@Override
	public BaseDao<FishShop, Long> getDao() {
		// TODO Auto-generated method stub
		return fishShopDao;
	}


}
