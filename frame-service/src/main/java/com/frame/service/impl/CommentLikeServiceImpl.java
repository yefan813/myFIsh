package com.frame.service.impl;

import com.frame.dao.CommentLikeDao;
import com.frame.domain.ArticalLike;
import com.frame.domain.CommentLike;
import com.frame.domain.base.YnEnum;
import com.frame.domain.vo.ArticalLikeVO;
import com.frame.domain.vo.CommentLikeVO;
import com.frame.service.CommentLikeService;
import com.frame.dao.base.BaseDao;
import com.frame.service.base.BaseServiceImpl;

import org.apache.commons.beanutils.BeanUtils;
import org.apache.commons.collections.CollectionUtils;
import org.springframework.stereotype.Service;
import javax.annotation.Resource;
import java.util.Date;
import java.util.List;


/**
 * 
 * CommentLikeServiceImpl业务层实现类
 * @author yefan
 * @date 2017-11-29 17:02:57
 **/

@Service("commentLikeService")
public class CommentLikeServiceImpl extends BaseServiceImpl<CommentLike, Long> implements CommentLikeService{
	@Resource
	private CommentLikeDao commentLikeDao;

	public BaseDao<CommentLike, Long> getDao(){
		return commentLikeDao;
	}


	@Override
	public int saveOrUpdate(CommentLikeVO commentLikeVO) {
		try {
			CommentLike query = new CommentLike();
			query.setCommentId(commentLikeVO.getCommentId());
			query.setUserId(commentLikeVO.getUserId());

			List<CommentLike> list = commentLikeDao.selectEntryList(query);
			CommentLike commentLike = null;
			if(CollectionUtils.isEmpty(list)){
				commentLike = new CommentLike();
				BeanUtils.copyProperties(commentLike, commentLikeVO);
				commentLike.setYn(YnEnum.Normal.getKey());
				commentLike.setCreated(new Date());
				commentLike.setModified(new Date());
			}else{
				CommentLike exist = list.get(0);

				commentLike = new CommentLike();
				BeanUtils.copyProperties(commentLike, commentLikeVO);
				commentLike.setId(exist.getId());
				commentLike.setModified(new Date());
			}
			return saveOrUpdate(commentLike);
		}catch (Exception e){
			LOGGER.error("异常", e);
			return 0;
		}
	}

}