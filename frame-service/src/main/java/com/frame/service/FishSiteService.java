package com.frame.service;

import com.frame.domain.FishShop;
import com.frame.domain.FishSite;
import com.frame.domain.common.Page;
import com.frame.domain.vo.Response.FishShopListResponse;
import com.frame.domain.vo.Response.FishSiteListResponse;
import com.frame.service.base.BaseService;

public interface FishSiteService extends BaseService<FishSite, Long> {

    FishSiteListResponse selectEntryDetail(Long fishSiteId, Long userId);


    Page<FishSiteListResponse> selectBaseEntryList(FishSite condtion, Page<FishSiteListResponse> page);
	
}
