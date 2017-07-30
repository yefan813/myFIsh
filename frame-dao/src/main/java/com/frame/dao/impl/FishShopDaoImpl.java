package com.frame.dao.impl;

import com.frame.dao.FishShopDao;
import com.frame.dao.base.BaseDaoImpl;
import com.frame.domain.FishShop;
import org.springframework.stereotype.Repository;

@Repository("fishShopDao")
public class FishShopDaoImpl extends BaseDaoImpl<FishShop, Long> implements FishShopDao {

	private final static String NAMESPACE = "com.frame.dao.FishShopDao.";


	@Override
	public String getNameSpace(String statement) {
		return NAMESPACE + statement;
	}


}
