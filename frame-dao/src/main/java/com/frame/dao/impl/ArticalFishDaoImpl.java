package com.frame.dao.impl;

import com.frame.dao.ArticalFishDao;
import com.frame.dao.base.BaseDaoImpl;
import com.frame.domain.ArticalFish;
import org.springframework.stereotype.Repository;

@Repository("articalFishDao")
public class ArticalFishDaoImpl extends BaseDaoImpl<ArticalFish, Long> implements ArticalFishDao {

	private final static String NAMESPACE = "com.frame.dao.ArticalFishDao.";


	@Override
	public String getNameSpace(String statement) {
		return NAMESPACE + statement;
	}


}
