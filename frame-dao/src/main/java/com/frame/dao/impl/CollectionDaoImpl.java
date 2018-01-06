package com.frame.dao.impl;

import com.frame.dao.CollectionDao;
import com.frame.dao.base.BaseDaoImpl;
import com.frame.domain.Collection;
import org.springframework.stereotype.Repository;


/**
 * ArticalCollectionDaoImpl数据库操作实现类
 *
 * @author yefan
 * @date 2017-11-29 17:00:01
 **/

@Repository("collectionDao")
public class CollectionDaoImpl extends BaseDaoImpl<Collection, Long> implements CollectionDao {
    private final static String NAMESPACE = "com.frame.dao.CollectionDao.";


    @Override
    public String getNameSpace(String statement) {
        return NAMESPACE + statement;
    }
}