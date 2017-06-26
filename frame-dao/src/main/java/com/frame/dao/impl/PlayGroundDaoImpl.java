package com.frame.dao.impl;

import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Repository;

import com.frame.dao.PlayGroundDao;
import com.frame.dao.base.BaseDaoImpl;
import com.frame.domain.Playground;

@Repository("playGroundDao")
public class PlayGroundDaoImpl extends BaseDaoImpl<Playground, Long> implements PlayGroundDao {

	private final static String NAMESPACE = "com.frame.dao.PlayGroundDao.";
	
	private final static String SELECT_PLAYGROUNBINFO = "selectPlaygroundInfo";
	
	private final static String SELECTP_LAYGROUNDBY_LOCATION = "selectPlayGroundByLocation";
	
	@Override
	public List<Playground> getPlaygroundInfo(Map<String, Object> parameters) {
		return this.select(getNameSpace(SELECT_PLAYGROUNBINFO), parameters);
	}

	@Override
	public String getNameSpace(String statement) {
		// TODO Auto-generated method stub
		return NAMESPACE + statement;
	}

	@Override
	public List<Playground> getPlayGroundByLocation(Map<String, Object> params) {
		return selectList(getNameSpace(SELECTP_LAYGROUNDBY_LOCATION), params);
	}
	
	
	


}
