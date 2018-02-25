package com.frame.service;


import com.frame.domain.User;
import com.frame.domain.UserFollow;
import com.frame.domain.common.Page;
import com.frame.domain.common.RemoteResult;
import com.frame.service.base.BaseService;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * UserFollowService接口
 * @author Evan
 * @since 2018-02-23
 */
public interface UserFollowService extends BaseService<UserFollow,Long> {
    /**
     * 查询关注列表
     * @param fansId
     * @return
     */
    Page<User> selectFollowUsers(@Param(value = "fId") Long fansId, Integer currentPage, Integer pageSize);


    /**
     * 查询粉丝列表
     * @param uid
     * @return
     */
    Page<User> selectByollowUsers(@Param(value = "uId") Long uid, Integer currentPage, Integer pageSize);


    List<User> isFriend(@Param(value = "fId") Long fansId, @Param(value = "uId") Long uid);

    RemoteResult follow(UserFollow userFollow);

    RemoteResult cancelFollow(UserFollow userFollow);
}