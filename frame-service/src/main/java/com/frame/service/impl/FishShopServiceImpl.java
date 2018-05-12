package com.frame.service.impl;

import com.frame.common.exception.AppException;
import com.frame.dao.FishShopDao;
import com.frame.dao.base.BaseDao;
import com.frame.domain.ArticalFish;
import com.frame.domain.Collection;
import com.frame.domain.FishShop;
import com.frame.domain.ScanRecord;
import com.frame.domain.base.YnEnum;
import com.frame.domain.common.Page;
import com.frame.domain.enums.CollectionTypeEnum;
import com.frame.domain.enums.ScanRecordTypeEnum;
import com.frame.domain.vo.Response.ArticalFishListResponse;
import com.frame.domain.vo.Response.FishShopListResponse;
import com.frame.service.CollectionService;
import com.frame.service.FishShopService;
import com.frame.service.ScanRecordService;
import com.frame.service.base.BaseServiceImpl;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;


@Service("fishShopService")
public class FishShopServiceImpl extends BaseServiceImpl<FishShop, Long> implements FishShopService {
	private static final Logger LOGGER = LoggerFactory.getLogger(FishShopServiceImpl.class);

	@Resource
	private FishShopDao fishShopDao;

	@Autowired
	private ScanRecordService scanRecordService;

	@Autowired
	private CollectionService collectionService;

	@Value("${img.prefix}")
	private String IMAGEPREFIX;

	@Override
	public BaseDao<FishShop, Long> getDao() {
		// TODO Auto-generated method stub
		return fishShopDao;
	}


	@Override
	public FishShopListResponse selectEntryDetail(Long fishShopId, Long userId) {
		FishShopListResponse fish = fishShopDao.selectEntryDetail(fishShopId,userId);
		if (null == fish) {
			return null;
		}

		//访问钓点详情需要记录浏览记录
		ScanRecord scanRecord = new ScanRecord();
		scanRecord.setTargetId(fishShopId);
		scanRecord.setType(ScanRecordTypeEnum.FISH_SITE.getKey());
		scanRecord.setUniqueId(userId);
		scanRecord.setYn(YnEnum.Normal.getKey());
		scanRecordService.insertEntry(scanRecord);


		//获取浏览数量
		ScanRecord query = new ScanRecord();
		query.setTargetId(fishShopId);
		query.setYn(YnEnum.Normal.getKey());
		Integer scanCount = scanRecordService.selectEntryListCount(query);
		fish.setScanCount(scanCount.longValue());

		//获取收藏数量
		Collection collQuery = new Collection();
		collQuery.setSourceId(fishShopId);
		collQuery.setYn(YnEnum.Normal.getKey());
		collQuery.setSourceType(CollectionTypeEnum.FISH_SITE.getKey());
		Integer collCount = collectionService.selectEntryListCount(collQuery);
		fish.setCollectCount(collCount.longValue());

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
