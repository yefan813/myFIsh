package com.frame.service.impl;

import com.frame.common.exception.AppException;
import com.frame.dao.FishSiteDao;
import com.frame.dao.base.BaseDao;
import com.frame.domain.FishShop;
import com.frame.domain.FishSite;
import com.frame.domain.common.Page;
import com.frame.domain.vo.Response.FishShopListResponse;
import com.frame.domain.vo.Response.FishSiteListResponse;
import com.frame.service.FishSiteService;
import com.frame.service.base.BaseServiceImpl;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;


@Service("fishSiteService")
public class FishSiteServiceImpl extends BaseServiceImpl<FishSite, Long> implements FishSiteService {
	private static final Logger LOGGER = LoggerFactory.getLogger(FishSiteServiceImpl.class);

	@Resource
	private FishSiteDao fishSiteDao;

	@Value("${img.prefix}")
	private String IMAGEPREFIX;

	@Override
	public BaseDao<FishSite, Long> getDao() {
		return fishSiteDao;
	}


	@Override
	public FishSiteListResponse selectEntryDetail(Long fishSiteId, Long userId) {
		FishSiteListResponse fish = fishSiteDao.selectEntryDetail(fishSiteId,userId);
		if (null == fish) {
			return null;
		}
		return fish;
	}

	@Override
	public Page<FishSiteListResponse> selectBaseEntryList(FishSite condition, Page<FishSiteListResponse> page) {
		try {
			Class<?> clz = condition.getClass();
			clz.getMethod("setStartIndex", Integer.class).invoke(condition, page.getStartIndex());
			clz.getMethod("setEndIndex", Integer.class).invoke(condition, page.getEndIndex());
		} catch (Exception e) {
			throw new AppException("设置分页参数失败", e);
		}
		Integer size = fishSiteDao.selectBaseEntryListCount(condition);
		if (size == null || size <= 0) {
			return page;
		}
		page.setTotalCount(size);
		page.setResult(fishSiteDao.selectBaseEntryList(condition));
		return page;
	}
}
