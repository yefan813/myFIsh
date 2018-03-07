package com.frame.dao;

import com.frame.dao.base.BaseDao;
import com.frame.domain.ArticalFish;
import com.frame.domain.FishShop;
import com.frame.domain.vo.Response.FishShopListResponse;

import java.util.List;

public interface FishShopDao extends BaseDao<FishShop, Long> {

    FishShopListResponse selectEntryDetail(Long fishShopId, Long userId);

    List<FishShopListResponse> selectBaseEntryList(FishShop condition);

    Integer selectBaseEntryListCount(FishShop condition);
}
