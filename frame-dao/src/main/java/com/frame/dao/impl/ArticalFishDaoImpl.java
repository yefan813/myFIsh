package com.frame.dao.impl;

import com.frame.dao.ArticalFishDao;
import com.frame.dao.base.BaseDaoImpl;
import com.frame.domain.ArticalFish;
import com.frame.domain.vo.Response.ArticalFishListResponse;
import org.springframework.stereotype.Repository;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Repository("articalFishDao")
public class ArticalFishDaoImpl extends BaseDaoImpl<ArticalFish, Long> implements ArticalFishDao {

	private final static String NAMESPACE = "com.frame.dao.ArticalFishDao.";

	private final static String SELECT_ENTRY_DETAIL = "selectEntryDetail";
	private final static String selectBaseEntryList = "selectBaseEntryList";
	private final static String selectEntryArrayDetail = "selectEntryArrayDetail";
	private final static String selectBaseEntryListCount = "selectBaseEntryListCount";




	@Override
	public String getNameSpace(String statement) {
		return NAMESPACE + statement;
	}


	@Override
	public ArticalFishListResponse selectEntryDetail(Long articalFishId,Long userId) {
		Map<String,Long> param = new HashMap<>();
		param.put("articalFishId",articalFishId);
		param.put("userId",userId);
		return select(getNameSpace(SELECT_ENTRY_DETAIL),param);
	}

	@Override
	public List<ArticalFishListResponse> selectBaseEntryList(ArticalFish condition) {
		return selectList(getNameSpace(selectBaseEntryList),condition);
	}

	@Override
	public List<ArticalFishListResponse> selectBaseEntryArray(Long userId,Long[] articalids) {
		Map<String,Object> param = new HashMap<>();
		param.put("userId",userId);
		param.put("array",articalids);
		return selectList(getNameSpace(selectEntryArrayDetail), param);
	}

	@Override
	public Integer selectBaseEntryListCount(ArticalFish condition) {
		return select(getNameSpace(selectBaseEntryListCount),condition);
	}


}
