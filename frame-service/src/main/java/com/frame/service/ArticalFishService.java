package com.frame.service;

import com.frame.domain.ArticalFish;
import com.frame.domain.common.Page;
import com.frame.service.base.BaseService;

public interface ArticalFishService extends BaseService<ArticalFish, Long> {


    ArticalFish selectEntryDetail(Long articalFishId);

    Page<ArticalFish> selectBaseEntryList(ArticalFish condtion, Page<ArticalFish> page);
}
