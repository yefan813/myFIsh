package com.frame.service;

import com.frame.domain.ArticalFish;
import com.frame.domain.Collection;
import com.frame.domain.common.Page;
import com.frame.domain.vo.CollectionVO;
import com.frame.domain.vo.LikeVO;
import com.frame.service.base.BaseService;


/**
 * ArticalCollectionService业务层接口类
 *
 * @author yefan
 * @date 2017-11-29 17:01:38
 **/

public interface CollectionService extends BaseService<Collection, Long> {

    Page<ArticalFish> getArticalCollectionById(Long userId,CollectionVO articalCollectionVO, Page<Collection> page);


    int saveOrUpdate(CollectionVO collectionVO,Long userId);



}