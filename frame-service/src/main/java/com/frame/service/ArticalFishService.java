package com.frame.service;

import com.frame.domain.ArticalFish;
import com.frame.domain.common.Page;
import com.frame.domain.vo.Response.ArticalFishListResponse;
import com.frame.service.base.BaseService;

public interface ArticalFishService extends BaseService<ArticalFish, Long> {


    ArticalFish selectEntryDetail(Long articalFishId);


    Page<ArticalFishListResponse> selectBaseEntryList(ArticalFish condtion, Page<ArticalFishListResponse> page);
}
