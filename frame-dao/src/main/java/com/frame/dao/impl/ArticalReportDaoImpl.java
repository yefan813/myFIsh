package com.frame.dao.impl;

import com.frame.dao.ArticalReportDao;
import com.frame.domain.ArticalReport;
import com.frame.dao.base.BaseDao;
import com.frame.dao.base.BaseDaoImpl;

import org.springframework.stereotype.Repository;



/**
 * 
 * ArticalReportDaoImpl数据库操作实现类
 * @author yefan
 * @date 2017-11-29 17:00:01
 **/

@Repository("articalReportDao")
public class ArticalReportDaoImpl extends BaseDaoImpl<ArticalReport, Long> implements ArticalReportDao {
	private final static String NAMESPACE = "com.frame.dao.ArticalReportDao.";


	@Override
	public String getNameSpace(String statement) {
		return NAMESPACE + statement;
	}
}