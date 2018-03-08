package com.frame.service.impl;

import com.frame.dao.CollectionDao;
import com.frame.dao.base.BaseDao;
import com.frame.domain.ArticalFish;
import com.frame.domain.Collection;
import com.frame.domain.base.YnEnum;
import com.frame.domain.common.Page;
import com.frame.domain.vo.CollectionVO;
import com.frame.service.ArticalFishService;
import com.frame.service.CollectionService;
import com.frame.service.base.BaseServiceImpl;
import com.google.common.collect.Lists;
import org.apache.commons.beanutils.BeanUtils;
import org.apache.commons.collections.CollectionUtils;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.Date;
import java.util.List;


/**
 * ArticalCollectionServiceImpl业务层实现类
 *
 * @author yefan
 * @date 2017-11-29 17:02:57
 **/

@Service("collectionService")
public class CollectionServiceImpl extends BaseServiceImpl<Collection, Long> implements CollectionService {
    @Resource
    private CollectionDao collectionDao;

    @Resource
    private ArticalFishService articalFishService;

    public BaseDao<Collection, Long> getDao() {
        return collectionDao;
    }


    @Override
    public int saveOrUpdate(CollectionVO collectionVO,Long userId) {
        try {
            Collection query = new Collection();
            query.setSourceId(collectionVO.getSourceId());
            query.setSourceType(collectionVO.getSourceType());
            query.setUserId(userId);

            List<Collection> list = collectionDao.selectEntryList(query);
            Collection collection = null;
            if (CollectionUtils.isEmpty(list)) {
                collection = new Collection();
                BeanUtils.copyProperties(collection, collectionVO);
                collection.setYn(YnEnum.Normal.getKey());
                collection.setCreated(new Date());
                collection.setModified(new Date());
            } else {
                Collection exist = list.get(0);

                collection = new Collection();
                BeanUtils.copyProperties(collection, collectionVO);
                collection.setId(exist.getId());
                collection.setModified(new Date());
            }
            return saveOrUpdate(collection);
        } catch (Exception e) {
            LOGGER.error("异常", e);
            return 0;
        }
    }

    @Override
    public Page<ArticalFish> getArticalCollectionById(Long userId,CollectionVO articalCollectionVO, Page<Collection> page) {

        Page<ArticalFish> articalFishPage = new Page<>();
        articalFishPage.setCurrentPage(page.getCurrentPage());
        articalFishPage.setPageSize(page.getPageSize());

        try {

            Collection collection = new Collection();
            BeanUtils.copyProperties(collection, articalCollectionVO);
            collection.setYn(YnEnum.Normal.getKey());
            collection.setOrderField("modified");
            collection.setOrderFieldType("DESC");
            collection.setUserId(userId);

            page = selectPage(collection, page);

            List<Collection> list = page.getResult();
            if (CollectionUtils.isEmpty(list)) {
                articalFishPage.setResult(Lists.<ArticalFish>newArrayList());
                return articalFishPage;
            }

            Long[] articalIds = new Long[list.size()];
            int index = 0;
            for (Collection item : list) {
                articalIds[index] = item.getSourceId();
                index++;
            }

            List<ArticalFish> articalFishList = articalFishService.selectEntryList(articalIds);
            articalFishPage.setResult(articalFishList);
            articalFishPage.setTotalCount(page.getTotalCount());
        } catch (Exception e) {
            LOGGER.error("getArticalReportById 异常", e);
            return articalFishPage;
        }

        return articalFishPage;
    }


}