package com.frame.web.controller;

import com.alibaba.fastjson.JSON;
import com.frame.domain.*;
import com.frame.domain.base.YnEnum;
import com.frame.domain.common.Page;
import com.frame.domain.common.RemoteResult;
import com.frame.domain.enums.BusinessCode;
import com.frame.domain.vo.CommentVO;
import com.frame.service.FishSiteService;
import com.frame.service.ShopSiteCommentService;
import com.frame.service.UserService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.apache.commons.beanutils.BeanUtils;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

@Controller
@RequestMapping(value = "/shopSitecomment")
@Api(value = "shopSitecomment", description = "钓点相关接口")
public class ShopSiteCommentController {
    private static final Logger LOGGER = LoggerFactory.getLogger(ShopSiteCommentController.class);

    @Resource
    private ShopSiteCommentService shopSiteCommentService;

    @Resource
    private FishSiteService fishSiteService;

    @Resource
    private UserService userService;


    @Value("${img.prefix}")
    private String IMAGEPREFIX;

    @RequestMapping(value = "/getComment", method = {RequestMethod.POST})
    @ApiOperation(value = "获取comment", httpMethod = "POST", response = String.class, notes = "获取comment")
    public @ResponseBody
    String getCommentByArticalId(HttpServletRequest request, @RequestParam(value = "currrentPage", required = true) Integer currrentPage
            , @RequestParam(value = "topicId", required = true) Long topicId) {
        RemoteResult result = null;
        try {
            if (null == topicId || topicId < 0) {
                LOGGER.error("get commentVO is error , param is error[{}]", JSON.toJSONString(topicId));
                result = RemoteResult.failure(BusinessCode.PARAMETERS_ERROR.getCode(), BusinessCode.PARAMETERS_ERROR.getValue());
                return JSON.toJSONString(result);
            }

            FishSite fishSite = fishSiteService.selectEntry(topicId);
            if (null == fishSite) {
                LOGGER.error("fish site is  not exist , param is error[{}]", JSON.toJSONString(topicId));
                result = RemoteResult.failure(BusinessCode.PARAMETERS_ERROR.getCode(), "钓点不存在！");
                return JSON.toJSONString(result);
            }


            Page<ShopSiteComment> page = new Page<>();
            page.setCurrentPage(currrentPage);

            ShopSiteComment comment = new ShopSiteComment();
            comment.setLocalId(topicId);
            comment.setYn(YnEnum.Normal.getKey());
            comment.setOrderField("created");
            comment.setOrderFieldType("ASC");

            Page<ShopSiteComment> res = shopSiteCommentService.selectPage(comment, page);

            result = RemoteResult.success(res);
            return JSON.toJSONString(result);
        } catch (Exception e) {
            LOGGER.error("失败:" + e.getMessage(), e);
            result = RemoteResult.failure("0001", "操作失败:" + e.getMessage());
        }
        return JSON.toJSONString(result);
    }


    @RequestMapping(value = "/del", method = {RequestMethod.POST})
    @ApiOperation(value = "delete commentVO", httpMethod = "POST", response = String.class, notes = "delete commentVO")
    public @ResponseBody
    String del(HttpServletRequest request, @RequestParam Long id) {
        RemoteResult result = null;
        try {
            if (null == id) {
                LOGGER.error("delete commentVO  传入的参数错误 id【{}】", id);
                result = RemoteResult.failure(BusinessCode.PARAMETERS_ERROR.getCode(), BusinessCode.PARAMETERS_ERROR.getValue());
                return JSON.toJSONString(result);
            }

            int res = shopSiteCommentService.deleteByKey(id);
            if (res > 0) {
                result = RemoteResult.success("删除评论成功");
            }
        } catch (Exception e) {

        }

        return JSON.toJSONString(result);
    }


}
