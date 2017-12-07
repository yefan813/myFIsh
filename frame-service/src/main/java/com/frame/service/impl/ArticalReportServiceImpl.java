package com.frame.service.impl;

import com.frame.dao.ArticalReportDao;
import com.frame.domain.ArticalReport;
import com.frame.service.ArticalReportService;
import com.frame.dao.base.BaseDao;
import com.frame.service.base.BaseServiceImpl;

import org.springframework.stereotype.Service;
import javax.annotation.Resource;


/**
 * 
 * ArticalReportServiceImpl业务层实现类
 * @author yefan
 * @date 2017-11-29 17:02:57
 **/

@Service("articalReportService")
public class ArticalReportServiceImpl extends BaseServiceImpl<ArticalReport, Long> implements ArticalReportService{
	@Resource
	private ArticalReportDao articalReportDao;

	public BaseDao<ArticalReport, Long> getDao(){
		return articalReportDao;
	}

}