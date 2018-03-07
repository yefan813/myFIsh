package com.frame.service;

import com.frame.domain.FishShop;
import com.frame.domain.common.Page;
import com.frame.domain.vo.Response.FishShopListResponse;
import com.frame.service.base.BaseService;

public interface FishShopService extends BaseService<FishShop, Long> {
    FishShopListResponse selectEntryDetail(Long articalFishId, Long userId);


    Page<FishShopListResponse> selectBaseEntryList(FishShop condtion, Page<FishShopListResponse> page);

}
