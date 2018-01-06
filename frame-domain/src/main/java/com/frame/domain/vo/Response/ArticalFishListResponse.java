package com.frame.domain.vo.Response;


import com.frame.domain.vo.ArticalFishVO;
import com.frame.domain.vo.UserBaseVO;

/**
 * Created by yefan on 2017/7/23.
 */
public class ArticalFishListResponse extends ArticalFishVO {

    private UserBaseVO user;

    private Long likeCount;
    private Long commentCount;


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
}
