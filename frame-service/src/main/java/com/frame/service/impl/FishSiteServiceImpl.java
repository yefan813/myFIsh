package com.frame.service.impl;

import com.frame.common.exception.AppException;
import com.frame.dao.FishSiteDao;
import com.frame.dao.base.BaseDao;
import com.frame.domain.Collection;
import com.frame.domain.FishShop;
import com.frame.domain.FishSite;
import com.frame.domain.ScanRecord;
import com.frame.domain.base.YnEnum;
import com.frame.domain.common.Page;
import com.frame.domain.enums.CollectionTypeEnum;
import com.frame.domain.enums.ScanRecordTypeEnum;
import com.frame.domain.vo.Response.FishShopListResponse;
import com.frame.domain.vo.Response.FishSiteListResponse;
import com.frame.service.CollectionService;
import com.frame.service.FishSiteService;
import com.frame.service.ScanRecordService;
import com.frame.service.base.BaseServiceImpl;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;


@Service("fishSiteService")
public class FishSiteServiceImpl extends BaseServiceImpl<FishSite, Long> implements FishSiteService {
	private static final Logger LOGGER = LoggerFactory.getLogger(FishSiteServiceImpl.class);

	@Resource
	private FishSiteDao fishSiteDao;

	@Autowired
	private ScanRecordService scanRecordService;

	@Autowired
	private CollectionService collectionService;

	@Value("${img.prefix}")
	private String IMAGEPREFIX;

	@Override
	public BaseDao<FishSite, Long> getDao() {
		return fishSiteDao;
	}


	@Override
	@Transactional
	public FishSiteListResponse selectEntryDetail(Long fishSiteId, Long userId) {
		FishSiteListResponse fish = fishSiteDao.selectEntryDetail(fishSiteId,userId);
		if (null == fish) {
			return null;
		}

		//访问钓点详情需要记录浏览记录
		ScanRecord scanRecord = new ScanRecord();
		scanRecord.setTargetId(fishSiteId);
		scanRecord.setType(ScanRecordTypeEnum.FISH_SITE.getKey());
		scanRecord.setUniqueId(userId);
		scanRecord.setYn(YnEnum.Normal.getKey());
		scanRecordService.insertEntry(scanRecord);

		//获取浏览数量
		ScanRecord query = new ScanRecord();
		query.setTargetId(fishSiteId);
		query.setYn(YnEnum.Normal.getKey());
		Integer scanCount = scanRecordService.selectEntryListCount(query);
		fish.setScanCount(scanCount.longValue());

		//获取收藏数量
		Collection collQuery = new Collection();
		collQuery.setSourceId(fishSiteId);
		collQuery.setYn(YnEnum.Normal.getKey());
		collQuery.setSourceType(CollectionTypeEnum.FISH_SITE.getKey());
		Integer collCount = collectionService.selectEntryListCount(collQuery);
		fish.setCollectCount(collCount.longValue());
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
