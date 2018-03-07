package com.frame.domain.vo.Response;


import com.frame.domain.vo.FishSiteVO;
import com.frame.domain.vo.UserBaseVO;

/**
 * Created by yefan on 2017/7/23.
 */
public class FishSiteListResponse extends FishSiteVO {

    private UserBaseVO user;

    private Long likeCount = 0l;
    private Long commentCount = 0l;
    private boolean isCollectioned = false;
    private boolean isLiked = false;


    public UserBaseVO getUser() {
        return user;
    }

    public void setUser(UserBaseVO user) {
        this.user = user;
    }

    public Long getLikeCount() {
        return likeCount;
    }

    public void setLikeCount(Long likeCount) {
        this.likeCount = likeCount;
    }

    public Long getCommentCount() {
        return commentCount;
    }

    public void setCommentCount(Long commentCount) {
        this.commentCount = commentCount;
    }

    public boolean isCollectioned() {
        return isCollectioned;
    }

    public void setCollectioned(boolean collectioned) {
        isCollectioned = collectioned;
    }

    public boolean isLiked() {
        return isLiked;
    }

    public void setLiked(boolean liked) {
        isLiked = liked;
    }
}
