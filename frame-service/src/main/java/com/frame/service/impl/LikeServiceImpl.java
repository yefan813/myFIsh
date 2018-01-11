package com.frame.service.impl;

import com.frame.dao.LikeDao;
import com.frame.dao.base.BaseDao;
import com.frame.domain.Like;
import com.frame.domain.base.YnEnum;
import com.frame.domain.vo.LikeVO;
import com.frame.service.LikeService;
import com.frame.service.base.BaseServiceImpl;
import org.apache.commons.beanutils.BeanUtils;
import org.apache.commons.collections.CollectionUtils;
import org.slf4j.Logger;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.Date;
import java.util.List;

import static org.slf4j.LoggerFactory.getLogger;


/**
 * ArticalLikeServiceImpl业务层实现类
 *
 * @author yefan
 * @date 2017-11-29 17:02:57
 **/

@Service("likeService")
public class LikeServiceImpl extends BaseServiceImpl<Like, Long> implements LikeService {
    private static final Logger LOGGER = getLogger(LikeServiceImpl.class);

    @Resource
    private LikeDao likeDao;

    public BaseDao<Like, Long> getDao() {
        return likeDao;
    }

    @Override
    public int saveOrUpdate(LikeVO likeVO) {
        try {
            Like query = new Like();
            query.setSourceId(likeVO.getSourceId());
            query.setUserId(likeVO.getUserId());

            List<Like> list = likeDao.selectEntryList(query);
            Like articalLike = null;
            if (CollectionUtils.isEmpty(list)) {
                articalLike = new Like();
                BeanUtils.copyProperties(articalLike, likeVO);
                articalLike.setYn(YnEnum.Normal.getKey());
                articalLike.setCreated(new Date());
                articalLike.setModified(new Date());
            } else {
                Like exist = list.get(0);

                articalLike = new Like();
                BeanUtils.copyProperties(articalLike, likeVO);
                articalLike.setId(exist.getId());
                articalLike.setModified(new Date());
            }
            return saveOrUpdate(articalLike);
        } catch (Exception e) {
            LOGGER.error("异常", e);
            return 0;
        }
    }
}