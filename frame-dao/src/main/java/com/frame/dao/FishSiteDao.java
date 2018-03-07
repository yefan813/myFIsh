package com.frame.dao;

import com.frame.dao.base.BaseDao;
import com.frame.domain.FishShop;
import com.frame.domain.FishSite;
import com.frame.domain.vo.Response.FishShopListResponse;
import com.frame.domain.vo.Response.FishSiteListResponse;

import java.util.List;

public interface FishSiteDao extends BaseDao<FishSite, Long> {

    FishSiteListResponse selectEntryDetail(Long fishSiteId, Long userId);

    List<FishSiteListResponse> selectBaseEntryList(FishSite condition);

    Integer selectBaseEntryListCount(FishSite condition);
}
