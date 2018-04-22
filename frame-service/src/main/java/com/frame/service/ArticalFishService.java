package com.frame.service;

import com.frame.domain.ArticalFish;
import com.frame.domain.common.Page;
import com.frame.domain.vo.ArticleTypeInfo;
import com.frame.domain.vo.Response.ArticalFishListResponse;
import com.frame.service.base.BaseService;

import java.util.List;

public interface ArticalFishService extends BaseService<ArticalFish, Long> {


    ArticalFishListResponse selectEntryDetail(Long articalFishId,Long userId);


    Page<ArticalFishListResponse> selectBaseEntryList(ArticalFish condtion, Page<ArticalFishListResponse> page);

    List<ArticalFishListResponse> selectBaseEntryArray(Long userId,Long[] articalIds);

    List<ArticleTypeInfo> selectArticleTypeCount();


}
