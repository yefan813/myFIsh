package com.frame.service.impl;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.frame.dao.AppSecretDao;
import com.frame.dao.base.BaseDao;
import com.frame.domain.AppSecret;
import com.frame.service.AppSecretService;
import com.frame.service.base.BaseServiceImpl;


@Service("appSecretService")
public class AppSecretServiceImpl extends BaseServiceImpl<AppSecret, Long> implements AppSecretService {

	@Resource
	private AppSecretDao appSecretDao;

	@Override
	public BaseDao<AppSecret, Long> getDao() {
		// TODO Auto-generated method stub
		return appSecretDao;
	}
	

}
