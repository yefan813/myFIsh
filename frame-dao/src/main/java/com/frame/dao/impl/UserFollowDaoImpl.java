/*
 * Copyright (c) 2018 www.xxxx.com All rights reserved.
 * 未经许可不得任意复制与传播.
 */
package com.frame.dao.impl;

import com.frame.dao.UserFollowDao;
import com.frame.dao.base.BaseDaoImpl;
import com.frame.domain.User;
import com.frame.domain.UserFollow;
import org.springframework.stereotype.Repository;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * UserFollowDao 实现类
 * @author Evan
 * @since 2018-02-23
 */
@Repository("userFollowDao")
public class UserFollowDaoImpl extends BaseDaoImpl<UserFollow,Long> implements UserFollowDao {
	private final static String NAMESPACE = "com.frame.dao.UserFollowDao.";

	private final static String SELECTFOLLOWUSERS ="selectFollowUsers";
	private final static String SELECTFOLLOWUSERSCOUNT ="selectFollowUsersCount";

	private final static String SELECTBYFOLLOWUSERS ="selectByFollowUsers";
	private final static String SELECTBYFOLLOWUSERSCOUNT ="selectByFollowUsersCount";

	private final static String ISFRIEND ="isFriend";




	//返回本DAO命名空间,并添加statement
	public String getNameSpace(String statement) {
		return NAMESPACE + statement;
	}

	@Override
	public List<User> selectFollowUsers(UserFollow userFollow) {
		Map<String,Object> param = new HashMap<>();
		param.put("fId",userFollow.getFid());
		param.put("startIndex",userFollow.getFid());
		param.put("pageSize",userFollow.getFid());

		return selectList(getNameSpace(SELECTFOLLOWUSERS),param);
	}

	@Override
	public Integer selectFollowUsersCount(Long fansId) {
		return select(getNameSpace(SELECTFOLLOWUSERSCOUNT),fansId);
	}

	@Override
	public List<User> selectByollowUsers(UserFollow userFollow) {
		Map<String,Object> param = new HashMap<>();
		param.put("uId",userFollow.getUid());
		param.put("startIndex",userFollow.getFid());
		param.put("pageSize",userFollow.getFid());
		return selectList(getNameSpace(SELECTBYFOLLOWUSERS),param);
	}

	@Override
	public Integer selectByollowUsersCount(Long uid) {
		return select(getNameSpace(SELECTBYFOLLOWUSERSCOUNT),uid);
	}

	@Override
	public List<User> isFriend(Long fansId, Long uid) {
		Map<String,Object> param = new HashMap<>();
		param.put("uId",uid);
		param.put("fId",fansId);
		return selectList(getNameSpace(ISFRIEND),param);
	}
}