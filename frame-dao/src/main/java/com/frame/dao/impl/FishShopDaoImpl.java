package com.frame.dao.impl;

import com.frame.dao.FishShopDao;
import com.frame.dao.base.BaseDaoImpl;
import com.frame.domain.ArticalFish;
import com.frame.domain.FishShop;
import com.frame.domain.vo.Response.ArticalFishListResponse;
import com.frame.domain.vo.Response.FishShopListResponse;
import org.springframework.stereotype.Repository;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Repository("fishShopDao")
public class FishShopDaoImpl extends BaseDaoImpl<FishShop, Long> implements FishShopDao {

	private final static String NAMESPACE = "com.frame.dao.FishShopDao.";

	private final static String SELECTENTRYDETAIL  = "selectEntryDetail";
	private final static String SELECTBASEENTRYLIST  = "selectBaseEntryList";
	private final static String SELECTBASEENTRYLISTCOUNT  = "selectBaseEntryListCount";


	@Override
	public String getNameSpace(String statement) {
		return NAMESPACE + statement;
	}

	@Override
	public FishShopListResponse selectEntryDetail(Long fishShopId, Long userId) {
		Map<String,Long> param = new HashMap<>();
		param.put("fishShopId",fishShopId);
		param.put("userId",userId);
		return select(getNameSpace(SELECTENTRYDETAIL),param);
	}

	@Override
	public List<FishShopListResponse> selectBaseEntryList(FishShop condition) {
		return selectList(getNameSpace(SELECTBASEENTRYLIST),condition);
	}

	@Override
	public Integer selectBaseEntryListCount(FishShop condition) {
		return select(getNameSpace(SELECTBASEENTRYLISTCOUNT),condition);
	}


}
