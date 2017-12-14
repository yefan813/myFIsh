package com.frame.web.controller;


import com.alibaba.fastjson.JSON;
import com.frame.domain.*;
import com.frame.domain.base.YnEnum;
import com.frame.domain.common.Page;
import com.frame.domain.common.RemoteResult;
import com.frame.domain.enums.BusinessCode;
import com.frame.domain.vo.ArticalLikeVO;
import com.frame.domain.vo.ArticalReportVO;
import com.frame.domain.vo.FishShopVO;
import com.frame.service.ArticalFishService;
import com.frame.service.ArticalLikeService;
import com.frame.service.ArticalReportService;
import com.frame.service.UserService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.apache.commons.beanutils.BeanUtils;
import org.slf4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import java.util.Date;

import static org.slf4j.LoggerFactory.getLogger;

@Controller
@RequestMapping(value = "/articalReport")
@Api(value = "articalReport", description = "文章举报接口")
public class ArticalReportController {
    private static final Logger LOGGER = getLogger(ArticalReportController.class);

    @Resource
    private UserService userService;

    @Autowired
    private ArticalFishService articalFishService;

    @Autowired
    private ArticalReportService articalReportService;

    @RequestMapping(value = "/list", method = {RequestMethod.POST}, produces = "application/json;charset=UTF-8")
    @ApiOperation(value = "文章举报列表", httpMethod = "POST", response = String.class, notes = "文章举报列表")
    @ResponseBody
    public String list(HttpServletRequest request, @RequestParam(value = "currrentPage", required = true)Integer currrentPage , @ModelAttribute  ArticalReportVO articalReportVO){
        RemoteResult result = null;
        try {
            if (null == articalReportVO ) {
                LOGGER.error("文章举报 list 传入的参数错误 articalId【{}】" , JSON.toJSONString(articalReportVO));
                result = RemoteResult.failure(BusinessCode.PARAMETERS_ERROR.getCode(),
                        BusinessCode.PARAMETERS_ERROR.getValue());
                return JSON.toJSONString(result);
            }

            Page<ArticalReport> page = new Page<>();
            page.setCurrentPage(currrentPage);

            ArticalReport articalReport = new ArticalReport();
            BeanUtils.copyProperties(articalReport,articalReportVO);
            articalReport.setYn(YnEnum.Normal.getKey());
            Page<ArticalReport> res = articalReportService.selectPage(articalReport,page);
            result = RemoteResult.success(res);
            return JSON.toJSONString(result);
        }catch (Exception e) {
            LOGGER.error("失败:" + e.getMessage(), e);
            result = RemoteResult.failure("0001", "操作失败:" + e.getMessage());
        }
        return JSON.toJSONString(result);
    }

    @RequestMapping(value = "/report", method = {RequestMethod.POST})
    @ApiOperation(value = "收藏文章", httpMethod = "POST", response = String.class, notes = "收藏文章")
    public @ResponseBody
    String report(HttpServletRequest request, @ModelAttribute ArticalReportVO articalReportVO) {
        RemoteResult result = null;
        try {
            if (null == articalReportVO) {
                LOGGER.error("report artical 传入的参数错");
                result = RemoteResult.failure(BusinessCode.PARAMETERS_ERROR.getCode(),
                        BusinessCode.PARAMETERS_ERROR.getValue());
                return JSON.toJSONString(result);
            }

            //valid user is valid
            User user = userService.selectEntry(articalReportVO.getUserId());
            if (null == user) {
                result = RemoteResult.failure(BusinessCode.PARAMETERS_ERROR.getCode(),
                        "此用户不存在");
                return JSON.toJSONString(result);
            }

            //valid artical is valid

            ArticalFish articalFish = articalFishService.selectEntry(articalReportVO.getArticalId());
            if (null == articalFish) {
                result = RemoteResult.failure(BusinessCode.PARAMETERS_ERROR.getCode(),
                        "此文章不存在");
                return JSON.toJSONString(result);
            }

            ArticalReport articalReport = new ArticalReport();
            BeanUtils.copyProperties(articalReport, articalReportVO);

            articalReport.setYn(YnEnum.Normal.getKey());
            articalReport.setCreated(new Date());
            articalReport.setModified(new Date());


            int res = articalReportService.insertEntry(articalReport);
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
