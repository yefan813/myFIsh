package com.frame.service;

import com.frame.domain.ArticalFish;
import com.frame.domain.Report;
import com.frame.domain.common.Page;
import com.frame.domain.vo.ReportVO;
import com.frame.service.base.BaseService;


/**
 * ArticalReportService业务层接口类
 *
 * @author yefan
 * @date 2017-11-29 17:01:39
 **/

public interface ReportService extends BaseService<Report, Long> {

    Page<ArticalFish> getArticalReportById(ReportVO articalReportVO, Page<Report> page);

}