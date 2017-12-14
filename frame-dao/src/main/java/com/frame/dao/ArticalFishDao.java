package com.frame.dao;

import com.frame.dao.base.BaseDao;
import com.frame.domain.ArticalFish;
import com.frame.domain.common.Page;

import java.util.List;

public interface ArticalFishDao extends BaseDao<ArticalFish, Long> {

    ArticalFish selectEntryDetail(Long articalFishId);

    List<ArticalFish> selectBaseEntryList(ArticalFish condition);

    Integer selectBaseEntryListCount(ArticalFish condition);

}
