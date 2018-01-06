package com.frame.dao.impl;

import com.frame.dao.LikeDao;
import com.frame.dao.base.BaseDaoImpl;
import com.frame.domain.Like;
import org.springframework.stereotype.Repository;


/**
 * ArticalLikeDaoImpl数据库操作实现类
 *
 * @author yefan
 * @date 2017-11-29 17:00:01
 **/

@Repository("likeDao")
public class LikeDaoImpl extends BaseDaoImpl<Like, Long> implements LikeDao {
    private final static String NAMESPACE = "com.frame.dao.LikeDao.";


    @Override
    public String getNameSpace(String statement) {
        return NAMESPACE + statement;
    }
}