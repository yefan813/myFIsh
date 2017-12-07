package com.frame.service.impl;

import com.frame.dao.ArticalCollectionDao;
import com.frame.domain.ArticalCollection;
import com.frame.domain.ArticalFish;
import com.frame.domain.base.YnEnum;
import com.frame.domain.common.Page;
import com.frame.domain.vo.ArticalCollectionVO;
import com.frame.service.ArticalCollectionService;
import com.frame.dao.base.BaseDao;
import com.frame.service.ArticalFishService;
import com.frame.service.base.BaseServiceImpl;

import com.google.common.collect.Lists;
import org.apache.commons.beanutils.BeanUtils;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import javax.annotation.Resource;
import java.util.List;


/**
 * 
 * ArticalCollectionServiceImpl业务层实现类
 * @author yefan
 * @date 2017-11-29 17:02:57
 **/

@Service("articalCollectionService")
public class ArticalCollectionServiceImpl extends BaseServiceImpl<ArticalCollection, Long> implements ArticalCollectionService{
	@Resource
	private ArticalCollectionDao articalCollectionDao;

	@Resource
	private ArticalFishService articalFishService;

	public BaseDao<ArticalCollection, Long> getDao(){
		return articalCollectionDao;
	}

	@Override
	public Page<ArticalFish> getArticalCollectionById(ArticalCollectionVO articalCollectionVO, Page<ArticalCollection> page) {

		Page<ArticalFish> articalFishPage = new Page<>();
		articalFishPage.setCurrentPage(page.getCurrentPage());
		articalFishPage.setPageSize(page.getPageSize());

		try{

			ArticalCollection articalCollection = new ArticalCollection();
			BeanUtils.copyProperties(articalCollection, articalCollectionVO);
			articalCollection.setYn(YnEnum.Normal.getKey());
			articalCollection.setOrderField("modified");
			articalCollection.setOrderFieldType("DESC");

			page = selectPage(articalCollection, page);

			List<ArticalCollection> list = page.getResult();
			if(CollectionUtils.isEmpty(list)){
				articalFishPage.setResult(Lists.<ArticalFish>newArrayList());
				return articalFishPage;
			}

			Long[] articalIds = new Long[]{};
			int index = 0;
			for(ArticalCollection item : list){
				articalIds[index] = item.getArticalId();
				index++;
			}

			List<ArticalFish> articalFishList = articalFishService.selectEntryList(articalIds);
			articalFishPage.setResult(articalFishList);
			articalFishPage.setTotalCount(page.getTotalCount());
		}catch (Exception e){
			LOGGER.error("getArticalCollectionById 异常" , e);
			return articalFishPage;
		}

		return articalFishPage;
	}
}