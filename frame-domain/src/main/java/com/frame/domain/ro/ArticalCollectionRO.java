package com.frame.domain.ro;

import com.frame.domain.vo.ArticalFishVO;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

public class ArticalCollectionRO  implements Serializable{

    private Long userId;
    private String userName;
    private List<ArticalFishVO> articalFishVOS;
    private Date createTime;

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public List<ArticalFishVO> getArticalFishVOS() {
        return articalFishVOS;
    }

    public void setArticalFishVOS(List<ArticalFishVO> articalFishVOS) {
        this.articalFishVOS = articalFishVOS;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }
}
