/*
 * Copyright (c) 2018 www.xxxx.com All rights reserved.
 * 未经许可不得任意复制与传播.
 */
package com.frame.dao;


import com.frame.dao.base.BaseDao;
import com.frame.domain.User;
import com.frame.domain.UserFollow;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * UserFollowDao 接口
 * @author Evan
 * @since 2018-02-23
 */
public interface UserFollowDao extends BaseDao<UserFollow,Long> {
	//自定义扩展

    /**
     * 查询关注列表
     * @param fansId
     * @return
     */
    List<User> selectFollowUsers(UserFollow userFollow);

    Integer selectFollowUsersCount(Long fansId);

    /**
     * 查询粉丝列表
     * @param uid
     * @return
     */
    List<User> selectByollowUsers(UserFollow userFollow);
    Integer selectByollowUsersCount(Long uid);



    List<User> isFriend(Long fansId, Long uid);

}