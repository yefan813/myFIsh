/*
 * Copyright (c) 2018 www.xxxx.com All rights reserved.
 * 未经许可不得任意复制与传播.
 */
package com.frame.service.impl;

import com.frame.dao.ScanRecordDao;
import com.frame.dao.base.BaseDao;
import com.frame.domain.ScanRecord;
import com.frame.service.ScanRecordService;
import com.frame.service.base.BaseServiceImpl;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

/**
 * ScanRecordService 实现类
 *
 * @author Evan
 * @since 2018-05-12
 */
@Service("scanRecordService")
public class ScanRecordServiceImpl extends BaseServiceImpl<ScanRecord, Long> implements ScanRecordService {

    @Resource
    private ScanRecordDao scanRecordDao;

    public BaseDao<ScanRecord, Long> getDao() {
        return scanRecordDao;
    }

}