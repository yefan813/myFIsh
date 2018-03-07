package com.frame.service.impl;

import com.frame.common.exception.AppException;
import com.frame.dao.FishShopDao;
import com.frame.dao.base.BaseDao;
import com.frame.domain.ArticalFish;
import com.frame.domain.FishShop;
import com.frame.domain.common.Page;
import com.frame.domain.vo.Response.ArticalFishListResponse;
import com.frame.domain.vo.Response.FishShopListResponse;
import com.frame.service.FishShopService;
import com.frame.service.base.BaseServiceImpl;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;


@Service("fishShopService")
public class FishShopServiceImpl extends BaseServiceImpl<FishShop, Long> implements FishShopService {
	private static final Logger LOGGER = LoggerFactory.getLogger(FishShopServiceImpl.class);

	@Resource
	private FishShopDao fishShopDao;

	@Value("${img.prefix}")
	private String IMAGEPREFIX;

	@Override
	public BaseDao<FishShop, Long> getDao() {
		// TODO Auto-generated method stub
		return fishShopDao;
	}


	@Override
	public FishShopListResponse selectEntryDetail(Long articalFishId, Long userId) {
		FishShopListResponse fish = fishShopDao.selectEntryDetail(articalFishId,userId);
		if (null == fish) {
			return null;
		}

		return fish;
	}

	@Override
	public Page<FishShopListResponse> selectBaseEntryList(FishShop condition, Page<FishShopListResponse> page) {
		try {
			Class<?> clz = condition.getClass();
			clz.getMethod("setStartIndex", Integer.class).invoke(condition, page.getStartIndex());
			clz.getMethod("setEndIndex", Integer.class).invoke(condition, page.getEndIndex());
		} catch (Exception e) {
			throw new AppException("设置分页参数失败", e);
		}
		Integer size = fishShopDao.selectBaseEntryListCount(condition);
		if (size == null || size <= 0) {
			return page;
		}
		page.setTotalCount(size);
		page.setResult(fishShopDao.selectBaseEntryList(condition));
		return page;
	}
}
