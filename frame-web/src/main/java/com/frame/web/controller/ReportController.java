package com.frame.web.controller;


import com.alibaba.fastjson.JSON;
import com.frame.domain.ArticalFish;
import com.frame.domain.Report;
import com.frame.domain.User;
import com.frame.domain.base.YnEnum;
import com.frame.domain.common.Page;
import com.frame.domain.common.RemoteResult;
import com.frame.domain.enums.BusinessCode;
import com.frame.domain.vo.ReportVO;
import com.frame.service.ArticalFishService;
import com.frame.service.ReportService;
import com.frame.service.UserService;
import com.frame.web.entity.request.ReportListParam;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.apache.commons.beanutils.BeanUtils;
import org.slf4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.util.Date;

import static org.slf4j.LoggerFactory.getLogger;

@Controller
@RequestMapping(value = "/report")
@Api(value = "report", description = "报接口")
public class ReportController extends BaseController{
    private static final Logger LOGGER = getLogger(ReportController.class);

    @Resource
    private UserService userService;

    @Autowired
    private ArticalFishService articalFishService;

    @Autowired
    private ReportService reportService;

    @RequestMapping(value = "/list", method = {RequestMethod.POST}, produces = "application/json;charset=UTF-8")
    @ApiOperation(value = "文章举报列表", httpMethod = "POST", response = String.class, notes = "文章举报列表")
    @ResponseBody
    public RemoteResult list(HttpServletRequest request, @RequestBody ReportListParam listParam) {
        RemoteResult result = null;
        try {
            if (null == listParam) {
                LOGGER.error("文章举报 list 传入的参数错误 articalId【{}】", JSON.toJSONString(listParam));
                result = RemoteResult.failure(BusinessCode.PARAMETERS_ERROR.getCode(),
                        BusinessCode.PARAMETERS_ERROR.getValue());
                return result;
            }

            Page<Report> page = new Page<>();
            page.setCurrentPage(listParam.getCurrrentPage());

            ReportVO articalReport = new ReportVO();
            BeanUtils.copyProperties(articalReport, listParam);


            Page<ArticalFish> res = reportService.getArticalReportById(articalReport, page);
            result = RemoteResult.success(res);
            return result;
        } catch (Exception e) {
            LOGGER.error("失败:" + e.getMessage(), e);
            result = RemoteResult.failure("0001", "操作失败:" + e.getMessage());
        }
        return result;
    }

    @RequestMapping(value = "/report", method = {RequestMethod.POST})
    @ApiOperation(value = "举报文章", httpMethod = "POST", response = String.class, notes = "举报文章")
    public @ResponseBody
    String report(HttpServletRequest request, @RequestBody ReportVO articalReportVO) {
        RemoteResult result = null;
        try {
            if (null == articalReportVO) {
                LOGGER.error("report artical 传入的参数错");
                result = RemoteResult.failure(BusinessCode.PARAMETERS_ERROR.getCode(),
                        BusinessCode.PARAMETERS_ERROR.getValue());
                return JSON.toJSONString(result);
            }
            Long userId = getLoginId();
            if(userId == null){
                LOGGER.error(BusinessCode.NO_LOGIN.getValue());
                result = RemoteResult.failure(BusinessCode.NO_LOGIN.getCode(),BusinessCode.NO_LOGIN.getValue());
                return JSON.toJSONString(result);
            }



            ArticalFish articalFish = articalFishService.selectEntry(articalReportVO.getSourceId());
            if (null == articalFish) {
                result = RemoteResult.failure(BusinessCode.PARAMETERS_ERROR.getCode(),
                        "此文章不存在");
                return JSON.toJSONString(result);
            }

            Report articalReport = new Report();
            BeanUtils.copyProperties(articalReport, articalReportVO);
            articalReport.setUserId(userId);
            articalReport.setYn(YnEnum.Normal.getKey());
            articalReport.setCreated(new Date());
            articalReport.setModified(new Date());


            int res = reportService.insertEntry(articalReport);
            if (res <= 0) {
                result = RemoteResult.failure("0002", "artical report is failed,server internal error");
            } else {
                result = RemoteResult.success();
                result.setMsg("举报文章成功");
            }
            return JSON.toJSONString(result);
        } catch (Exception e) {
            LOGGER.error("失败:" + e.getMessage(), e);
            result = RemoteResult.failure("0001", "操作失败:" + e.getMessage());
        }
        return JSON.toJSONString(result);
    }
}
