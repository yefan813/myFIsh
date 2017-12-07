package com.frame.service;

import com.frame.domain.ArticalFish;
import com.frame.domain.common.Page;
import com.frame.domain.ro.ArticalCollectionRO;
import com.frame.domain.vo.ArticalCollectionVO;
import com.frame.service.base.BaseService;
import com.frame.domain.ArticalCollection;



/**
 * 
 * ArticalCollectionService业务层接口类
 * @author yefan
 * @date 2017-11-29 17:01:38
 **/

public interface ArticalCollectionService extends BaseService<ArticalCollection , Long> {

    Page<ArticalFish> getArticalCollectionById(ArticalCollectionVO articalCollectionVO, Page<ArticalCollection> page);




}