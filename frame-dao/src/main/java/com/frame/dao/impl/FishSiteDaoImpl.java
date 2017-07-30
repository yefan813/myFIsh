package com.frame.dao.impl;

import com.frame.dao.FishSiteDao;
import com.frame.dao.base.BaseDaoImpl;
import com.frame.domain.FishSite;
import org.springframework.stereotype.Repository;

@Repository("fishSiteDao")
public class FishSiteDaoImpl extends BaseDaoImpl<FishSite, Long> implements FishSiteDao {

	private final static String NAMESPACE = "com.frame.dao.FishSiteDao.";


	@Override
	public String getNameSpace(String statement) {
		return NAMESPACE + statement;
	}


}
