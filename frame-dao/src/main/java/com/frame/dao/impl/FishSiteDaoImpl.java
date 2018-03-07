package com.frame.dao.impl;

import com.frame.dao.FishSiteDao;
import com.frame.dao.base.BaseDaoImpl;
import com.frame.domain.FishSite;
import com.frame.domain.vo.Response.FishSiteListResponse;
import org.springframework.stereotype.Repository;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Repository("fishSiteDao")
public class FishSiteDaoImpl extends BaseDaoImpl<FishSite, Long> implements FishSiteDao {

	private final static String NAMESPACE = "com.frame.dao.FishSiteDao.";

	private final static String SELECTENTRYDETAIL  = "selectEntryDetail";
	private final static String SELECTBASEENTRYLIST  = "selectBaseEntryList";
	private final static String SELECTBASEENTRYLISTCOUNT  = "selectBaseEntryListCount";


	@Override
	public String getNameSpace(String statement) {
		return NAMESPACE + statement;
	}


	@Override
	public FishSiteListResponse selectEntryDetail(Long fishSiteId, Long userId) {
		Map<String,Long> param = new HashMap<>();
		param.put("fishSiteId",fishSiteId);
		param.put("userId",userId);
		return select(getNameSpace(SELECTENTRYDETAIL),param);
	}

	@Override
	public List<FishSiteListResponse> selectBaseEntryList(FishSite condition) {
		return selectList(getNameSpace(SELECTBASEENTRYLIST),condition);
	}

	@Override
	public Integer selectBaseEntryListCount(FishSite condition) {
		return select(getNameSpace(SELECTBASEENTRYLISTCOUNT),condition);
	}
}
