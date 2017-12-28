package com.frame.web.controller;


import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.frame.domain.ArticalFish;
import com.frame.domain.ArticalLike;
import com.frame.domain.User;
import com.frame.domain.base.YnEnum;
import com.frame.domain.common.RemoteResult;
import com.frame.domain.enums.BusinessCode;
import com.frame.domain.vo.ArticalLikeVO;
import com.frame.service.ArticalFishService;
import com.frame.service.ArticalLikeService;
import com.frame.service.UserService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.slf4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import static org.slf4j.LoggerFactory.getLogger;

@Controller
@RequestMapping(value = "/articalLike")
@Api(value = "articalLike", description = "文章点赞接口")
public class ArticalLikeController {
    private static final Logger LOGGER = getLogger(ArticalLikeController.class);


    @Resource
    private UserService userService;

    @Autowired
    private ArticalFishService articalFishService;

    @Autowired
    private ArticalLikeService articalLikeService;

    @RequestMapping(value = "/isLike", method = {RequestMethod.POST})
    @ApiOperation(value = "点赞,取消文章赞", httpMethod = "POST", response = String.class, notes = "点赞文章")
    public @ResponseBody
    String like(HttpServletRequest request, @RequestBody ArticalLikeVO articalLikeVO) {
        RemoteResult result = null;
        try {
            if (null == articalLikeVO || articalLikeVO.getUserId() == null || articalLikeVO.getArticalId() == null || null == articalLikeVO.getType()) {
                LOGGER.error("like artical 传入的参数错[{}]", JSON.toJSONString(articalLikeVO));
                result = RemoteResult.failure(BusinessCode.PARAMETERS_ERROR.getCode(),
                        BusinessCode.PARAMETERS_ERROR.getValue());
                return JSON.toJSONString(result);
            }

            //valid artical is valid

            ArticalFish articalFish = articalFishService.selectEntry(articalLikeVO.getArticalId());
            if (null == articalFish) {
                result = RemoteResult.failure(BusinessCode.PARAMETERS_ERROR.getCode(),
                        "此文章不存在");
                return JSON.toJSONString(result);
            }

            //valid user is valid
            User user = userService.selectEntry(articalLikeVO.getUserId());
            if (null == user) {
                result = RemoteResult.failure(BusinessCode.PARAMETERS_ERROR.getCode(),
                        "此user不存在");
                return JSON.toJSONString(result);
            }

            int res = articalLikeService.saveOrUpdate(articalLikeVO);
            if(res < 1){
                LOGGER.error("点赞失败");
                result = RemoteResult.failure(BusinessCode.PARAMETERS_ERROR.getCode(),
                        "点赞失败");
                return JSON.toJSONString(result);
            }
            result = RemoteResult.success();
            return JSON.toJSONString(result);
        } catch (Exception e) {
            LOGGER.error("失败:" + e.getMessage(), e);
            result = RemoteResult.failure("0001", "操作失败:" + e.getMessage());
        }
        return JSON.toJSONString(result);
    }

    @RequestMapping(value = "/likeCount", method = {RequestMethod.POST})
    @ApiOperation(value = "文章点赞数量", httpMethod = "POST", response = String.class, notes = "文章点赞数量")
    public @ResponseBody
    String likeCount(HttpServletRequest request, @RequestBody ArticalLikeVO articalLikeVO) {
        RemoteResult result = null;
        try {
            if (null == articalLikeVO || articalLikeVO.getArticalId() == null) {
                LOGGER.error("like count artical 传入的参数错[{}]", JSONObject.toJSONString(articalLikeVO));
                result = RemoteResult.failure(BusinessCode.PARAMETERS_ERROR.getCode(),
                        BusinessCode.PARAMETERS_ERROR.getValue());
                return JSON.toJSONString(result);
            }

            ArticalFish articalFish = articalFishService.selectEntry(articalLikeVO.getArticalId());
            if (null == articalFish) {
                result = RemoteResult.failure(BusinessCode.PARAMETERS_ERROR.getCode(),
                        "此文章不存在");
                return JSON.toJSONString(result);
            }

            ArticalLike articalLike = new ArticalLike();
            articalLike.setArticalId(articalLikeVO.getArticalId());
            articalLike.setType(1); //点赞
            articalLike.setYn(YnEnum.Normal.getKey());

            int count = articalLikeService.selectEntryListCount(articalLike);
            result.setData(count);

        } catch (Exception e) {
            LOGGER.error("失败:" + e.getMessage(), e);
            result = RemoteResult.failure("0001", "操作失败:" + e.getMessage());
        }
        return JSON.toJSONString(result);
    }

}
