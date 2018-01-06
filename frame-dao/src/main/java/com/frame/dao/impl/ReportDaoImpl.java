package com.frame.dao.impl;

import com.frame.dao.ReportDao;
import com.frame.dao.base.BaseDaoImpl;
import com.frame.domain.Report;
import org.springframework.stereotype.Repository;


/**
 * ArticalReportDaoImpl数据库操作实现类
 *
 * @author yefan
 * @date 2017-11-29 17:00:01
 **/

@Repository("reportDao")
public class ReportDaoImpl extends BaseDaoImpl<Report, Long> implements ReportDao {
    private final static String NAMESPACE = "com.frame.dao.ArticalReportDao.";


    @Override
    public String getNameSpace(String statement) {
        return NAMESPACE + statement;
    }
}