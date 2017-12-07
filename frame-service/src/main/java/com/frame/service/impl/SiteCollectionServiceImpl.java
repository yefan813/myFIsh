package com.frame.service.impl;

import com.frame.dao.SiteCollectionDao;
import com.frame.domain.FishSite;
import com.frame.domain.SiteCollection;
import com.frame.domain.base.YnEnum;
import com.frame.domain.common.Page;
import com.frame.domain.vo.SiteCollectionVO;
import com.frame.service.FishSiteService;
import com.frame.service.SiteCollectionService;
import com.frame.dao.base.BaseDao;
import com.frame.service.base.BaseServiceImpl;

import com.google.common.collect.Lists;
import org.apache.commons.beanutils.BeanUtils;
import org.slf4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import javax.annotation.Resource;
import java.util.List;

import static org.slf4j.LoggerFactory.getLogger;


/**
 * 
 * SiteCollectionServiceImpl业务层实现类
 * @author yefan
 * @date 2017-11-29 17:02:57
 **/

@Service("siteCollectionService")
public class SiteCollectionServiceImpl extends BaseServiceImpl<SiteCollection, Long> implements SiteCollectionService{
	private static final Logger LOGGER = getLogger(SiteCollectionServiceImpl.class);

	@Resource
	private SiteCollectionDao siteCollectionDao;

	@Autowired
	private FishSiteService fishSiteService;

	public BaseDao<SiteCollection, Long> getDao(){
		return siteCollectionDao;
	}

	@Override
	public Page<FishSite> getSiteCollectionById(SiteCollectionVO siteCollectionVO, Page<SiteCollection> page) {

		Page<FishSite> fishSitePage = new Page<>();
		fishSitePage.setCurrentPage(page.getCurrentPage());
		fishSitePage.setPageSize(page.getPageSize());

		try{

			SiteCollection siteCollection = new SiteCollection();
			BeanUtils.copyProperties(siteCollection, siteCollectionVO);
			siteCollection.setYn(YnEnum.Normal.getKey());
			siteCollection.setOrderField("modified");
			siteCollection.setOrderFieldType("DESC");

			page = selectPage(siteCollection, page);

			List<SiteCollection> list = page.getResult();
			if(CollectionUtils.isEmpty(list)){
				fishSitePage.setResult(Lists.<FishSite>newArrayList());
				return fishSitePage;
			}

			Long[] articalIds = new Long[]{};
			int index = 0;
			for(SiteCollection item : list){
				articalIds[index] = item.getShopSiteId();
				index++;
			}

			List<FishSite> articalFishList = fishSiteService.selectEntryList(articalIds);
			fishSitePage.setResult(articalFishList);
			fishSitePage.setTotalCount(page.getTotalCount());
		}catch (Exception e){
			LOGGER.error("getArticalCollectionById 异常" , e);
			return fishSitePage;
		}

		return fishSitePage;
	}
}