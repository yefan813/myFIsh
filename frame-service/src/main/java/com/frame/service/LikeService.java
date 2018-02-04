package com.frame.service;

import com.frame.domain.Like;
import com.frame.domain.vo.LikeVO;
import com.frame.service.base.BaseService;


/**
 * ArticalLikeService业务层接口类
 *
 * @author yefan
 * @date 2017-11-29 17:01:38
 **/

public interface LikeService extends BaseService<Like, Long> {

    int saveOrUpdate(Long userId,LikeVO likeVO);

}