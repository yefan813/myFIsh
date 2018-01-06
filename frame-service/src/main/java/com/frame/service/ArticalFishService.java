package com.frame.service;

import com.frame.domain.ArticalFish;
import com.frame.domain.common.Page;
import com.frame.domain.vo.ArticalFishVO;
import com.frame.service.base.BaseService;

public interface ArticalFishService extends BaseService<ArticalFish, Long> {


    ArticalFish selectEntryDetail(Long articalFishId);


    Page<ArticalFishVO> selectList(ArticalFish condtion, Page<ArticalFish> page);
}
