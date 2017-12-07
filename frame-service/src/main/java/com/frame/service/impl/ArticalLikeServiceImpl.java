package com.frame.service.impl;

import com.frame.dao.ArticalLikeDao;
import com.frame.domain.ArticalLike;
import com.frame.service.ArticalLikeService;
import com.frame.dao.base.BaseDao;
import com.frame.service.base.BaseServiceImpl;

import org.springframework.stereotype.Service;
import javax.annotation.Resource;


/**
 * 
 * ArticalLikeServiceImpl业务层实现类
 * @author yefan
 * @date 2017-11-29 17:02:57
 **/

@Service("articalLikeService")
public class ArticalLikeServiceImpl extends BaseServiceImpl<ArticalLike, Long> implements ArticalLikeService{
	@Resource
	private ArticalLikeDao articalLikeDao;

	public BaseDao<ArticalLike, Long> getDao(){
		return articalLikeDao;
	}

}