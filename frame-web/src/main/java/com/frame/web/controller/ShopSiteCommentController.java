package com.frame.web.controller;

import com.alibaba.fastjson.JSON;
import com.frame.domain.*;
import com.frame.domain.base.YnEnum;
import com.frame.domain.common.Page;
import com.frame.domain.common.RemoteResult;
import com.frame.domain.enums.BusinessCode;
import com.frame.domain.vo.CommentVO;
import com.frame.domain.vo.ShopSiteCommentVO;
import com.frame.service.FishShopService;
import com.frame.service.FishSiteService;
import com.frame.service.ShopSiteCommentService;
import com.frame.service.UserService;
import com.frame.web.entity.request.IdParam;
import com.frame.web.entity.request.ShopSiteCommentListParam;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.apache.commons.beanutils.BeanUtils;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

@Controller
@RequestMapping(value = "/shopSitecomment")
@Api(value = "shopSitecomment", description = "钓点渔具店相关接口")
public class ShopSiteCommentController {
    private static final Logger LOGGER = LoggerFactory.getLogger(ShopSiteCommentController.class);

    @Resource
    private ShopSiteCommentService shopSiteCommentService;

    @Resource
    private FishSiteService fishSiteService;

    @Autowired
    private FishShopService fishShopService;

    @Resource
    private UserService userService;


    @Value("${img.prefix}")
    private String IMAGEPREFIX;

    @RequestMapping(value = "/getComment", method = {RequestMethod.POST})
    @ApiOperation(value = "获取comment", httpMethod = "POST", response = String.class, notes = "获取comment")
    public @ResponseBody
    String getCommentByArticalId(HttpServletRequest request, @RequestBody ShopSiteCommentListParam param) {
        RemoteResult result = null;
        try {
            if (null == param || param.getLocalId() < 0  || null == param.getType() || param.getType() < 0  ) {
                LOGGER.error("get commentVO is error , param is error prarm:[{}] ", JSON.toJSONString(param));
                result = RemoteResult.failure(BusinessCode.PARAMETERS_ERROR.getCode(), BusinessCode.PARAMETERS_ERROR.getValue());
                return JSON.toJSONString(result);
            }
            if(param.getType() == 1){
                FishSite fishSite = fishSiteService.selectEntry(param.getLocalId());
                if (null == fishSite) {
                    LOGGER.error("fish site is  not exist , param is error[{}]", JSON.toJSONString(param));
                    result = RemoteResult.failure(BusinessCode.PARAMETERS_ERROR.getCode(), "钓点不存在！");
                    return JSON.toJSONString(result);
                }
            }else{
                FishShop fishShop = fishShopService.selectEntry(param.getLocalId());
                if (null == fishShop) {
                    LOGGER.error("fish shop is  not exist , param is error[{}]", JSON.toJSONString(param));
                    result = RemoteResult.failure(BusinessCode.PARAMETERS_ERROR.getCode(), "钓点不存在！");
                    return JSON.toJSONString(result);
                }

            }




            Page<ShopSiteComment> page = new Page<>();
            page.setCurrentPage(param.getCurrrentPage());

            ShopSiteComment comment = new ShopSiteComment();
            comment.setLocalId(param.getLocalId());
            comment.setType(param.getType());
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

    @RequestMapping(value = "/publish", method = {RequestMethod.POST})
    @ApiOperation(value = "发布钓点comment", httpMethod = "POST", response = String.class, notes = "发布钓点comment")
    public  @ResponseBody String publish(HttpServletRequest request, @RequestBody ShopSiteCommentVO shopSiteCommentVO) {
        RemoteResult result = null;
        try{
            if(null == shopSiteCommentVO || StringUtils.isBlank(shopSiteCommentVO.getContent())|| shopSiteCommentVO.getFromUid() == null ||
                    shopSiteCommentVO.getLocalId() == null || shopSiteCommentVO.getType() == null){
                LOGGER.error("publish  shop site commentVO is error , param is error[{}]",JSON.toJSONString(shopSiteCommentVO));
                result = RemoteResult.failure(BusinessCode.PARAMETERS_ERROR.getCode(),BusinessCode.PARAMETERS_ERROR.getValue());
                return JSON.toJSONString(result);
            }

            if(shopSiteCommentVO.getFromUid() != null && shopSiteCommentVO.getFromUid().longValue() == shopSiteCommentVO.getToUid().longValue()){
                LOGGER.error("不能回复自己的评论 , param is error[{}]",JSON.toJSONString(shopSiteCommentVO));
                result = RemoteResult.failure(BusinessCode.PARAMETERS_ERROR.getCode(),"不能回复自己的评论");
                return JSON.toJSONString(result);
            }

            if(shopSiteCommentVO.getType() == 1){
                FishSite fishSite = fishSiteService.selectEntry(shopSiteCommentVO.getLocalId());
                if (null == fishSite) {
                    LOGGER.error("fish site is  not exist , param is error[{}]", JSON.toJSONString(shopSiteCommentVO));
                    result = RemoteResult.failure(BusinessCode.PARAMETERS_ERROR.getCode(), "钓点不存在！");
                    return JSON.toJSONString(result);
                }
            }else{
                FishShop fishShop = fishShopService.selectEntry(shopSiteCommentVO.getLocalId());
                if (null == fishShop) {
                    LOGGER.error("fish shop is  not exist , param is error[{}]", JSON.toJSONString(shopSiteCommentVO));
                    result = RemoteResult.failure(BusinessCode.PARAMETERS_ERROR.getCode(), "渔具店不存在！");
                    return JSON.toJSONString(result);
                }

            }

            ShopSiteComment comment = new ShopSiteComment();
            BeanUtils.copyProperties(comment,shopSiteCommentVO);

            User fromUser = userService.selectEntry(shopSiteCommentVO.getFromUid());
            if(shopSiteCommentVO.getToUid() != null ){
                User toUser = userService.selectEntry(shopSiteCommentVO.getFromUid());
                if(toUser == null){
                    LOGGER.error("publish to userId  is not exist , param is error[{}]", JSON.toJSONString(shopSiteCommentVO));
                    result = RemoteResult.failure(BusinessCode.PARAMETERS_ERROR.getCode(),"回复评论用户不存在");
                    return JSON.toJSONString(result);
                }
                comment.setToUname(toUser.getNickName());
                comment.setToUavtor(toUser.getAvatarUrl());
            }

            comment.setToUname(fromUser.getNickName());
            comment.setFromUavtor(fromUser.getAvatarUrl());
            comment.setYn(YnEnum.Normal.getKey());
            comment.setIsReview(false);                            //default is false;

            int res = shopSiteCommentService.insertEntry(comment);
            if(res > 0){
                result = RemoteResult.success("publish commentVO is success");
            }

        }catch (Exception e){
            LOGGER.error("失败:" + e.getMessage(), e);
            result = RemoteResult.failure("0001", "操作失败:" + e.getMessage());
        }

        return JSON.toJSONString(result);
    }


    @RequestMapping(value = "/del", method = {RequestMethod.DELETE})
    @ApiOperation(value = "delete commentVO", httpMethod = "DELETE", response = String.class, notes = "delete commentVO")
    public @ResponseBody
    String del(HttpServletRequest request, @RequestBody IdParam id) {
        RemoteResult result = null;
        try {
            if (null == id || id.getId() == null) {
                LOGGER.error("delete commentVO  传入的参数错误 id【{}】", id);
                result = RemoteResult.failure(BusinessCode.PARAMETERS_ERROR.getCode(), BusinessCode.PARAMETERS_ERROR.getValue());
                return JSON.toJSONString(result);
            }

            int res = shopSiteCommentService.deleteByKey(id.getId());
            if (res > 0) {
                result = RemoteResult.success("删除评论成功");
            }
        } catch (Exception e) {

        }

        return JSON.toJSONString(result);
    }


}
