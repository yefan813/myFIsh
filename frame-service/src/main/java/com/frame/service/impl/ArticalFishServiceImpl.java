package com.frame.service.impl;

import com.frame.dao.ArticalFishDao;
import com.frame.dao.base.BaseDao;
import com.frame.domain.ArticalFish;
import com.frame.service.ArticalFishService;
import com.frame.service.UserService;
import com.frame.service.base.BaseServiceImpl;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;


@Service("articalFishService")
public class ArticalFishServiceImpl extends BaseServiceImpl<ArticalFish, Long> implements ArticalFishService {


	private static final Logger LOGGER = LoggerFactory.getLogger(ArticalFishServiceImpl.class);

	@Resource
	private ArticalFishDao articalFishDao;

	@Resource
	private UserService uerService;

	@Value("${img.prefix}")
	private String IMAGEPREFIX;

	@Override
	public BaseDao<ArticalFish, Long> getDao() {
		// TODO Auto-generated method stub
		return articalFishDao;
	}

	@Override
	public synchronized Long likeArtical(Long articalId, Integer count) {
		ArticalFish articalFish = articalFishDao.selectEntry(articalId);
		if(articalFish == null){
			return 0l;
		}

		long likeCount  =  0;
		if(null == articalFish.getLiked()){
			likeCount = count;
		}else{
			likeCount = articalFish.getLiked() + count;
		}

		ArticalFish fishTmp = new ArticalFish();
		fishTmp.setId(articalFish.getId());
		fishTmp.setLiked(likeCount);

		int res = articalFishDao.updateByKey(fishTmp);
		if(res < 0){
			LOGGER.error("artical like is error");
		}

		return likeCount;
	}
}
