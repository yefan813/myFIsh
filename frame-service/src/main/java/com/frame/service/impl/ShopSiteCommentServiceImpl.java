package com.frame.service.impl;

import com.frame.dao.ShopSiteCommentDao;
import com.frame.domain.ShopSiteComment;
import com.frame.service.ShopSiteCommentService;
import com.frame.dao.base.BaseDao;
import com.frame.service.base.BaseServiceImpl;

import org.springframework.stereotype.Service;
import javax.annotation.Resource;


/**
 * 
 * ShopSiteCommentServiceImpl业务层实现类
 * @author yefan
 * @date 2017-11-29 17:02:57
 **/

@Service("shopSiteCommentService")
public class ShopSiteCommentServiceImpl extends BaseServiceImpl<ShopSiteComment, Long> implements ShopSiteCommentService{
	@Resource
	private ShopSiteCommentDao shopSiteCommentDao;

	public BaseDao<ShopSiteComment, Long> getDao(){
		return shopSiteCommentDao;
	}

}