package com.frame.domain;

import com.frame.domain.base.BaseDomain;

/**
 * Created by yefan on 2017/10/18.
 */
public class Reply extends BaseDomain {
    private Long commentId;
    private Long replyId;
    private Integer replyType;
    private String content;
    private Long fromUserId;
    private String fromUserName;
    private String fromUserAvtor;
    private Boolean isReview;

    public Long getCommentId() {
        return commentId;
    }

    public void setCommentId(Long commentId) {
        this.commentId = commentId;
    }

    public Long getReplyId() {
        return replyId;
    }

    public void setReplyId(Long replyId) {
        this.replyId = replyId;
    }

    public Integer getReplyType() {
        return replyType;
    }

    public void setReplyType(Integer replyType) {
        this.replyType = replyType;
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

    public Boolean isReview() {
        return isReview;
    }

    public void setReview(Boolean review) {
        isReview = review;
    }
}
