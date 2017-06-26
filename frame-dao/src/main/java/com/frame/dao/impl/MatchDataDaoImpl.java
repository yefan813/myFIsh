package com.frame.dao.impl;

import com.frame.dao.base.BaseDao;
import org.springframework.stereotype.Repository;

import com.frame.dao.MatchDataDao;
import com.frame.dao.base.BaseDaoImpl;
import com.frame.domain.MatchData;

@Repository("matchDataDao")
public class MatchDataDaoImpl extends BaseDaoImpl<MatchData, Long> implements BaseDao<MatchData, Long> {

	private final static String NAMESPACE = "com.frame.dao.MatchDataDao.";
	

	@Override
	public String getNameSpace(String statement) {
		// TODO Auto-generated method stub
		return NAMESPACE + statement;
	}


}
