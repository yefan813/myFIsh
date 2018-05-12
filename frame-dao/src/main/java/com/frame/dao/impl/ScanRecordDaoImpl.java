/*
 * Copyright (c) 2018 www.xxxx.com All rights reserved.
 * 未经许可不得任意复制与传播.
 */
package com.frame.dao.impl;

import com.frame.dao.ScanRecordDao;
import com.frame.dao.base.BaseDaoImpl;
import com.frame.domain.ScanRecord;
import org.springframework.stereotype.Repository;

/**
 * ScanRecordDao 实现类
 *
 * @author Evan
 * @since 2018-05-12
 */
@Repository("scanRecordDao")
public class ScanRecordDaoImpl extends BaseDaoImpl<ScanRecord, Long> implements ScanRecordDao {
    private final static String NAMESPACE = "com.frame.dao.ScanRecordDao.";

    //返回本DAO命名空间,并添加statement
    public String getNameSpace(String statement) {
        return NAMESPACE + statement;
    }

}