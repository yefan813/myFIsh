package com.frame.service.impl;


import com.frame.common.exception.AppException;
import com.frame.dao.UserFollowDao;
import com.frame.dao.base.BaseDao;
import com.frame.domain.User;
import com.frame.domain.UserFollow;
import com.frame.domain.base.YnEnum;
import com.frame.domain.common.Page;
import com.frame.domain.common.RemoteResult;
import com.frame.domain.enums.BusinessCode;
import com.frame.service.UserFollowService;
import com.frame.service.base.BaseServiceImpl;
import org.apache.commons.collections.CollectionUtils;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.Collections;
import java.util.List;

/**
 * UserFollowService 实现类
 * @author Evan
 * @since 2018-02-23
 */
@Service("userFollowService")
public class UserFollowServiceImpl extends BaseServiceImpl<UserFollow,Long> implements UserFollowService {
	
	@Resource
	private UserFollowDao userFollowDao;
	
	public BaseDao<UserFollow,Long> getDao() {
		return userFollowDao;
	}


	@Override
	public Page<User> selectFollowUsers(Long fansId, Integer currentPage, Integer pageSize) {
		Page<User> page = new Page<>();
		page.setPageSize(pageSize);
		page.setCurrentPage(currentPage);

		UserFollow condition = new UserFollow();
		condition.setFid(fansId);

		try {
			Class<?> clz = condition.getClass();
			clz.getMethod("setStartIndex", Integer.class).invoke(condition, page.getStartIndex());
			clz.getMethod("setEndIndex", Integer.class).invoke(condition, page.getEndIndex());
		} catch (Exception e) {
			throw new AppException("设置分页参数失败", e);
		}

		Integer size = userFollowDao.selectFollowUsersCount(fansId);
		if(size == null || size <= 0) {
			return page;
		}
		page.setTotalCount(size);
		page.setResult(userFollowDao.selectFollowUsers(condition));
		return page;
	}

	@Override
	public Page<User> selectByollowUsers(Long uid, Integer currentPage, Integer pageSize) {
		Page<User> page = new Page<>();
		page.setPageSize(pageSize);
		page.setCurrentPage(currentPage);

		UserFollow condition = new UserFollow();
		condition.setUid(uid);

		try {
			Class<?> clz = condition.getClass();
			clz.getMethod("setStartIndex", Integer.class).invoke(condition, page.getStartIndex());
			clz.getMethod("setEndIndex", Integer.class).invoke(condition, page.getEndIndex());
		} catch (Exception e) {
			throw new AppException("设置分页参数失败", e);
		}

		Integer size = userFollowDao.selectByollowUsersCount(uid);
		if(size == null || size <= 0) {
			return page;
		}
		page.setTotalCount(size);
		page.setResult(userFollowDao.selectByollowUsers(condition));
		return page;
	}

	@Override
	public List<User> isFriend(Long fansId, Long uid) {
		return userFollowDao.isFriend(fansId, uid);
	}

	@Override
	public RemoteResult follow(UserFollow userFollow) {
		UserFollow query = new UserFollow();
		query.setFid(userFollow.getFid());
		query.setUid(userFollow.getUid());
		query.setYn(YnEnum.Normal.getKey());
		List<UserFollow> res = userFollowDao.selectEntryList(query);
		if(CollectionUtils.isNotEmpty(res)){
			LOGGER.error("已经关注过了不能在关注");
			return RemoteResult.failure(BusinessCode.FOLLOWED.getCode(),BusinessCode.FOLLOWED.getValue());
		}

		UserFollow condtion = new UserFollow();
		condtion.setFid(userFollow.getFid());
		condtion.setUid(userFollow.getUid());
		condtion.setYn(YnEnum.Normal.getKey());
		int count = userFollowDao.insertEntry(condtion);
		if(count > 0){
			return RemoteResult.success();
		}
		return RemoteResult.failure("-1");
	}

	@Override
	public RemoteResult cancelFollow(UserFollow userFollow) {
		UserFollow query = new UserFollow();
		query.setFid(userFollow.getFid());
		query.setUid(userFollow.getUid());
		query.setYn(YnEnum.Normal.getKey());
		List<UserFollow> res = userFollowDao.selectEntryList(query);
		if(CollectionUtils.isEmpty(res)){
			LOGGER.error("还未关注过了不能操作");
			return RemoteResult.failure(BusinessCode.NOT_FOLLOWED.getCode(),BusinessCode.NOT_FOLLOWED.getValue());
		}

		UserFollow condtion = new UserFollow();
		condtion.setId(res.get(0).getId());
		condtion.setYn(YnEnum.Deleted.getKey());
		int count = userFollowDao.updateByKey(condtion);
		if(count > 0){
			return RemoteResult.success();
		}
		return RemoteResult.failure("-1");
	}
}