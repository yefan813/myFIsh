package com.frame.dao;

import com.frame.dao.base.BaseDao;
import com.frame.domain.ArticalFish;
import com.frame.domain.vo.Response.ArticalFishListResponse;

import java.util.List;

public interface ArticalFishDao extends BaseDao<ArticalFish, Long> {

    ArticalFishListResponse selectEntryDetail(Long articalFishId,Long userId);

    List<ArticalFishListResponse> selectBaseEntryList(ArticalFish condition);

    List<ArticalFishListResponse> selectBaseEntryArray(Long userId,Long[] articalids);


    Integer selectBaseEntryListCount(ArticalFish condition);

}
