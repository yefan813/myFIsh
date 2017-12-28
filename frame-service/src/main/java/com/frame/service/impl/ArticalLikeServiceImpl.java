package com.frame.service.impl;

import com.frame.dao.ArticalLikeDao;
import com.frame.domain.ArticalLike;
import com.frame.domain.base.YnEnum;
import com.frame.domain.common.RemoteResult;
import com.frame.domain.vo.ArticalLikeVO;
import com.frame.service.ArticalLikeService;
import com.frame.dao.base.BaseDao;
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
 * 
 * ArticalLikeServiceImpl业务层实现类
 * @author yefan
 * @date 2017-11-29 17:02:57
 **/

@Service("articalLikeService")
public class ArticalLikeServiceImpl extends BaseServiceImpl<ArticalLike, Long> implements ArticalLikeService{
	private static final Logger LOGGER = getLogger(ArticalLikeServiceImpl.class);

	@Resource
	private ArticalLikeDao articalLikeDao;

	public BaseDao<ArticalLike, Long> getDao(){
		return articalLikeDao;
	}

	@Override
	public int saveOrUpdate(ArticalLikeVO articalLikeVO) {
		try {
			ArticalLike query = new ArticalLike();
			query.setArticalId(articalLikeVO.getArticalId());
			query.setUserId(articalLikeVO.getUserId());

			List<ArticalLike> list = articalLikeDao.selectEntryList(query);
			ArticalLike articalLike = null;
			if(CollectionUtils.isEmpty(list)){
				articalLike = new ArticalLike();
				BeanUtils.copyProperties(articalLike, articalLikeVO);
				articalLike.setYn(YnEnum.Normal.getKey());
				articalLike.setCreated(new Date());
				articalLike.setModified(new Date());
			}else{
				ArticalLike exist = list.get(0);

				articalLike = new ArticalLike();
				BeanUtils.copyProperties(articalLike, articalLikeVO);
				articalLike.setId(exist.getId());
				articalLike.setModified(new Date());
			}
			return saveOrUpdate(articalLike);
		}catch (Exception e){
			LOGGER.error("异常", e);
			return 0;
		}
	}
}