package com.frame.service.impl;

import com.frame.dao.ReportDao;
import com.frame.dao.base.BaseDao;
import com.frame.domain.ArticalFish;
import com.frame.domain.Report;
import com.frame.domain.base.YnEnum;
import com.frame.domain.common.Page;
import com.frame.domain.vo.ReportVO;
import com.frame.service.ArticalFishService;
import com.frame.service.ReportService;
import com.frame.service.base.BaseServiceImpl;
import com.google.common.collect.Lists;
import org.apache.commons.beanutils.BeanUtils;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import javax.annotation.Resource;
import java.util.List;


/**
 * ArticalReportServiceImpl业务层实现类
 *
 * @author yefan
 * @date 2017-11-29 17:02:57
 **/

@Service("reportService")
public class ReportServiceImpl extends BaseServiceImpl<Report, Long> implements ReportService {
    @Resource
    private ReportDao reportDao;
    @Resource
    private ArticalFishService articalFishService;

    public BaseDao<Report, Long> getDao() {
        return reportDao;
    }

    @Override
    public Page<ArticalFish> getArticalReportById(ReportVO articalReportVO, Page<Report> page) {
        Page<ArticalFish> articalFishPage = new Page<>();
        articalFishPage.setCurrentPage(page.getCurrentPage());
        articalFishPage.setPageSize(page.getPageSize());

        try {

            Report articalReport = new Report();
            BeanUtils.copyProperties(articalReport, articalReportVO);
            articalReport.setYn(YnEnum.Normal.getKey());
            articalReport.setOrderField("modified");
            articalReport.setOrderFieldType("DESC");

            page = selectPage(articalReport, page);

            List<Report> list = page.getResult();
            if (CollectionUtils.isEmpty(list)) {
                articalFishPage.setResult(Lists.<ArticalFish>newArrayList());
                return articalFishPage;
            }

            Long[] articalIds = new Long[list.size()];
            int index = 0;
            for (Report item : list) {
                articalIds[index] = item.getSourceId();
                index++;
            }

            List<ArticalFish> articalFishList = articalFishService.selectEntryList(articalIds);
            articalFishPage.setResult(articalFishList);
            articalFishPage.setTotalCount(page.getTotalCount());
        } catch (Exception e) {
            LOGGER.error("getArticalReportById 异常", e);
            return articalFishPage;
        }

        return articalFishPage;
    }
}