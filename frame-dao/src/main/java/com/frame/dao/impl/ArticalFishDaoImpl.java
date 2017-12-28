package com.frame.dao.impl;

import com.frame.dao.ArticalFishDao;
import com.frame.dao.base.BaseDaoImpl;
import com.frame.domain.ArticalFish;
import com.frame.domain.common.Page;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository("articalFishDao")
public class ArticalFishDaoImpl extends BaseDaoImpl<ArticalFish, Long> implements ArticalFishDao {

	private final static String NAMESPACE = "com.frame.dao.ArticalFishDao.";

	private final static String SELECT_ENTRY_DETAIL = "selectEntryDetail";
	private final static String selectBaseEntryList = "selectBaseEntryList";
	private final static String selectBaseEntryListCount = "selectBaseEntryListCount";




	@Override
	public String getNameSpace(String statement) {
		return NAMESPACE + statement;
	}


	@Override
	public ArticalFish selectEntryDetail(Long articalFishId) {
		return select(getNameSpace(SELECT_ENTRY_DETAIL),articalFishId);
	}

	@Override
	public List<ArticalFish> selectBaseEntryList(ArticalFish condition) {
		return selectList(getNameSpace(selectBaseEntryList),condition);
	}

	@Override
	public Integer selectBaseEntryListCount(ArticalFish condition) {
		return select(getNameSpace(selectBaseEntryListCount),condition);
	}


}
