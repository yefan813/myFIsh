package com.frame.service;

import com.frame.domain.vo.ArticalLikeVO;
import com.frame.service.base.BaseService;
import com.frame.domain.ArticalLike;



/**
 * 
 * ArticalLikeService业务层接口类
 * @author yefan
 * @date 2017-11-29 17:01:38
 **/

public interface ArticalLikeService extends BaseService<ArticalLike , Long> {

    int saveOrUpdate(ArticalLikeVO articalLikeVO);

}