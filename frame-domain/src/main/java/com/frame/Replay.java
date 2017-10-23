package com.frame;

import com.frame.domain.base.BaseDomain;

/**
 * Created by yefan on 2017/10/18.
 */
public class Replay extends BaseDomain {
    private Integer commentId;
    private Integer replyId;
    private Integer replyType;
    private String content;
    private Integer fromUserId;
    private String fromUserName;
    private String fromUserAvtor;

    public Integer getCommentId() {
        return commentId;
    }

    public void setCommentId(Integer commentId) {
        this.commentId = commentId;
    }

    public Integer getReplyId() {
        return replyId;
    }

    public void setReplyId(Integer replyId) {
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

    public Integer getFromUserId() {
        return fromUserId;
    }

    public void setFromUserId(Integer fromUserId) {
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
}
