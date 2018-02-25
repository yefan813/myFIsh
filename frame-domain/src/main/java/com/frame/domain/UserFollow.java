/*
 * Copyright (c) 2018 www.xxxx.com All rights reserved.
 * 未经许可不得任意复制与传播.
 */
package com.frame.domain;


import com.frame.domain.base.BaseDomain;

/**
 * tb_user_follow
 *
 * @author Evan
 * @since 2018-02-23
 */
public class UserFollow extends BaseDomain {
    private static final long serialVersionUID = 1L;

    private Long uid;
    private Long fid;


    /**
     * 获取 用户ID
     *
     * @return
     */
    public Long getUid() {
        return uid;
    }

    /**
     * 设置 用户ID
     *
     * @param uid
     */
    public void setUid(Long uid) {
        this.uid = uid;
    }

    /**
     * 获取 关注用户ID
     *
     * @return
     */
    public Long getFid() {
        return fid;
    }

    /**
     * 设置 关注用户ID
     *
     * @param fid
     */
    public void setFid(Long fid) {
        this.fid = fid;
    }
}