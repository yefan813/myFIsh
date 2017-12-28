/*
 * Copyright (c) 2017 www.xxxx.com All rights reserved.
 * 未经许可不得任意复制与传播.
 */
package com.frame.domain.vo;


import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

/**
 * tb_shop_site_comment
 *
 * @author Evan
 * @since 2017-12-14
 */
@ApiModel
public class ShopSiteCommentVO {

    @ApiModelProperty(value = "1 钓点 2 渔具店")
    private Integer type;   //钓点还是渔具店

    @ApiModelProperty(value = " 钓点， 渔具店ID")
    private Long localId;    //钓点或者渔具店的 id

    @ApiModelProperty(value = "评论者头像")
    private String fromUavtor;
    @ApiModelProperty(value = "评论者名称")
    private String fromUname;
    @ApiModelProperty(value = "被评论者头像")
    private String toUavtor;
    @ApiModelProperty(value = "被评论者名称")
    private String toUname;
    @ApiModelProperty(value = "被评论者ID")
    private Long toUid;
    @ApiModelProperty(value = "评论者ID")
    private Long fromUid;
    @ApiModelProperty(value = "评论内容")
    private String content;
    @ApiModelProperty(value = "是否评审")
    private Boolean isReview;


    /**
     * 获取 type
     *
     * @return
     */
    public Integer getType() {
        return type;
    }

    /**
     * 设置 type
     *
     * @param type
     */
    public void setType(Integer type) {
        this.type = type;
    }

    /**
     * 获取 localId
     *
     * @return
     */
    public Long getLocalId() {
        return localId;
    }

    /**
     * 设置 localId
     *
     * @param localId
     */
    public void setLocalId(Long localId) {
        this.localId = localId;
    }

    /**
     * 获取 fromUavtor
     *
     * @return
     */
    public String getFromUavtor() {
        return fromUavtor;
    }

    /**
     * 设置 fromUavtor
     *
     * @param fromUavtor
     */
    public void setFromUavtor(String fromUavtor) {
        this.fromUavtor = fromUavtor;
    }

    /**
     * 获取 fromUname
     *
     * @return
     */
    public String getFromUname() {
        return fromUname;
    }

    /**
     * 设置 fromUname
     *
     * @param fromUname
     */
    public void setFromUname(String fromUname) {
        this.fromUname = fromUname;
    }

    /**
     * 获取 toUavtor
     *
     * @return
     */
    public String getToUavtor() {
        return toUavtor;
    }

    /**
     * 设置 toUavtor
     *
     * @param toUavtor
     */
    public void setToUavtor(String toUavtor) {
        this.toUavtor = toUavtor;
    }

    /**
     * 获取 toUname
     *
     * @return
     */
    public String getToUname() {
        return toUname;
    }

    /**
     * 设置 toUname
     *
     * @param toUname
     */
    public void setToUname(String toUname) {
        this.toUname = toUname;
    }

    /**
     * 获取 toUid
     *
     * @return
     */
    public Long getToUid() {
        return toUid;
    }

    /**
     * 设置 toUid
     *
     * @param toUid
     */
    public void setToUid(Long toUid) {
        this.toUid = toUid;
    }

    /**
     * 获取 fromUid
     *
     * @return
     */
    public Long getFromUid() {
        return fromUid;
    }

    /**
     * 设置 fromUid
     *
     * @param fromUid
     */
    public void setFromUid(Long fromUid) {
        this.fromUid = fromUid;
    }

    /**
     * 获取 content
     *
     * @return
     */
    public String getContent() {
        return content;
    }

    /**
     * 设置 content
     *
     * @param content
     */
    public void setContent(String content) {
        this.content = content;
    }

    /**
     * 获取 isReview
     *
     * @return
     */
    public Boolean getIsReview() {
        return isReview;
    }

    /**
     * 设置 isReview
     *
     * @param isReview
     */
    public void setIsReview(Boolean isReview) {
        this.isReview = isReview;
    }
}