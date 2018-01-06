package com.frame.service.impl;

import com.frame.common.exception.AppException;
import com.frame.dao.ArticalFishDao;
import com.frame.dao.base.BaseDao;
import com.frame.domain.ArticalFish;
import com.frame.domain.Like;
import com.frame.domain.base.YnEnum;
import com.frame.domain.common.Page;
import com.frame.domain.enums.CommentTypeEnum;
import com.frame.domain.vo.Response.ArticalFishListResponse;
import com.frame.service.ArticalFishService;
import com.frame.service.LikeService;
import com.frame.service.UserService;
import com.frame.service.base.BaseServiceImpl;
import org.apache.commons.collections.CollectionUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;


@Service("articalFishService")
public class ArticalFishServiceImpl extends BaseServiceImpl<ArticalFish, Long> implements ArticalFishService {


    private static final Logger LOGGER = LoggerFactory.getLogger(ArticalFishServiceImpl.class);

    @Resource
    private ArticalFishDao articalFishDao;

    @Resource
    private UserService uerService;

    @Resource
    private LikeService likeService;

    @Value("${img.prefix}")
    private String IMAGEPREFIX;

    @Override
    public BaseDao<ArticalFish, Long> getDao() {
        // TODO Auto-generated method stub
        return articalFishDao;
    }


    @Override
    public ArticalFish selectEntryDetail(Long articalFishId) {

        ArticalFish fish = articalFishDao.selectEntryDetail(articalFishId);
        if (null == fish) {
            return null;
        }

        Like query = new Like();
        query.setSourceId(fish.getId().longValue());
        query.setType(CommentTypeEnum.ARTICAL.getKey());
        query.setYn(YnEnum.Normal.getKey());

        List<Like> res = likeService.selectEntryList(query);
        if (CollectionUtils.isNotEmpty(res)) {
            Like like = res.get(0);
            fish.setLiked(like.getType().longValue());
        }
        return fish;
    }


    @Override
    public Page<ArticalFishListResponse> selectBaseEntryList(ArticalFish condition, Page<ArticalFishListResponse> page) {
        try {
            Class<?> clz = condition.getClass();
            clz.getMethod("setStartIndex", Integer.class).invoke(condition, page.getStartIndex());
            clz.getMethod("setEndIndex", Integer.class).invoke(condition, page.getEndIndex());
        } catch (Exception e) {
            throw new AppException("设置分页参数失败", e);
        }
        Integer size = articalFishDao.selectBaseEntryListCount(condition);
        if (size == null || size <= 0) {
            return page;
        }
        page.setTotalCount(size);
        page.setResult(articalFishDao.selectBaseEntryList(condition));
        return page;
    }


}
