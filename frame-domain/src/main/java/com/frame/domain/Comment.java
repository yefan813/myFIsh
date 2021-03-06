package com.frame.domain;

import com.frame.domain.base.BaseDomain;

/**
 * Created by yefan on 2017/10/18.
 */
public class Comment extends BaseDomain {
    private Long topicId;
    private Integer topicType;
    private String content;
    private Long fromUserId;
    private String fromUserName;
    private String fromUserAvtor;
    private Boolean isReView;

    private Long toUserId;
    private String toUserName;
    private String toUserAvtor;

    public Long getTopicId() {
        return topicId;
    }

    public void setTopicId(Long topicId) {
        this.topicId = topicId;
    }

    public Integer getTopicType() {
        return topicType;
    }

    public void setTopicType(Integer topicType) {
        this.topicType = topicType;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public Long getFromUserId() {
        return fromUserId;
    }

    public void setFromUserId(Long fromUserId) {
        this.fromUserId = fromUserId;
    }

    public String getFromUserName() {
        return fromUserName;
    }

    public void setFromUserName(String fromUserName) {
        this.fromUserName = fromUserName;
    }

    public String getFromUserAvtor() {
        return fromUserAvtor;
    }

    public void setFromUserAvtor(String fromUserAvtor) {
        this.fromUserAvtor = fromUserAvtor;
    }

    public Boolean getReView() {
        return isReView;
    }

    public void setReView(Boolean reView) {
        isReView = reView;
    }


    public Long getToUserId() {
        return toUserId;
    }

    public void setToUserId(Long toUserId) {
        this.toUserId = toUserId;
    }

    public String getToUserName() {
        return toUserName;
    }

    public void setToUserName(String toUserName) {
        this.toUserName = toUserName;
    }

    public String getToUserAvtor() {
        return toUserAvtor;
    }

    public void setToUserAvtor(String toUserAvtor) {
        this.toUserAvtor = toUserAvtor;
    }
}
