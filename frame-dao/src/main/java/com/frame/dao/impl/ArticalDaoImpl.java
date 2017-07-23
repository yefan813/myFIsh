package com.frame.dao.impl;

import com.frame.dao.ArticalDao;
import com.frame.dao.base.BaseDaoImpl;
import com.frame.domain.Artical;
import org.springframework.stereotype.Repository;

@Repository("articalDao")
public class ArticalDaoImpl extends BaseDaoImpl<Artical, Long> implements ArticalDao {

	private final static String NAMESPACE = "com.frame.dao.ArticalDao.";


	@Override
	public String getNameSpace(String statement) {
		return NAMESPACE + statement;
	}


}
