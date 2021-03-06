package com.frame.web.controller;


import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.frame.domain.ArticalFish;
import com.frame.domain.Like;
import com.frame.domain.User;
import com.frame.domain.base.YnEnum;
import com.frame.domain.common.RemoteResult;
import com.frame.domain.enums.BusinessCode;
import com.frame.domain.vo.LikeVO;
import com.frame.service.ArticalFishService;
import com.frame.service.LikeService;
import com.frame.service.UserService;
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

import java.util.List;

import static org.slf4j.LoggerFactory.getLogger;

@Controller
@RequestMapping(value = "/like")
@Api(value = "Like", description = "点赞接口")
public class LikeController extends  BaseController{
    private static final Logger LOGGER = getLogger(LikeController.class);


    @Resource
    private UserService userService;

    @Autowired
    private ArticalFishService articalFishService;

    @Autowired
    private LikeService likeService;

    @RequestMapping(value = "/like", method = {RequestMethod.POST})
    @ApiOperation(value = "点赞", httpMethod = "POST", response = String.class, notes = "点赞")
    public @ResponseBody
    String like(HttpServletRequest request, @RequestBody LikeVO likeVO) {
        RemoteResult result = null;
        try {
            if (null == likeVO  || likeVO.getSourceId() == null || null == likeVO.getSourceType()) {
                LOGGER.error("like artical 传入的参数错[{}]", JSON.toJSONString(likeVO));
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

            //valid artical is valid

            ArticalFish articalFish = articalFishService.selectEntry(likeVO.getSourceId());
            if (null == articalFish) {
                result = RemoteResult.failure(BusinessCode.PARAMETERS_ERROR.getCode(),
                        "此文章不存在");
                return JSON.toJSONString(result);
            }

            int res = likeService.saveOrUpdate(userId,likeVO);
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
    @ApiOperation(value = "点赞数量", httpMethod = "POST", response = String.class, notes = "点赞数量")
    public @ResponseBody
    String likeCount(HttpServletRequest request, @RequestBody LikeVO likeVO) {
        RemoteResult result = null;
        try {
            if (null == likeVO || likeVO.getSourceId() == null) {
                LOGGER.error("like count artical 传入的参数错[{}]", JSONObject.toJSONString(likeVO));
                result = RemoteResult.failure(BusinessCode.PARAMETERS_ERROR.getCode(),
                        BusinessCode.PARAMETERS_ERROR.getValue());
                return JSON.toJSONString(result);
            }


            ArticalFish articalFish = articalFishService.selectEntry(likeVO.getSourceId());
            if (null == articalFish) {
                result = RemoteResult.failure(BusinessCode.PARAMETERS_ERROR.getCode(),
                        "此文章不存在");
                return JSON.toJSONString(result);
            }

            Like like = new Like();
            like.setSourceId(likeVO.getSourceId());
            like.setSourceType(likeVO.getSourceType());
            like.setYn(YnEnum.Normal.getKey());

            int count = likeService.selectEntryListCount(like);
            result.setData(count);

        } catch (Exception e) {
            LOGGER.error("失败:" + e.getMessage(), e);
            result = RemoteResult.failure("0001", "操作失败:" + e.getMessage());
        }
        return JSON.toJSONString(result);
    }

    @RequestMapping(value = "/del", method = {RequestMethod.POST})
    @ApiOperation(value = "取消点赞", httpMethod = "POST", response = String.class, notes = "取消点赞")
    public  @ResponseBody String del(HttpServletRequest request, @RequestBody LikeVO param) {
        RemoteResult result = null;
        try {
            if (null == param || param.getSourceId() == null || param.getSourceType() == null ) {
                LOGGER.error("取消点赞  传入的参数错误 id【{}】", JSON.toJSONString(param));
                result = RemoteResult.failure(BusinessCode.PARAMETERS_ERROR.getCode(), BusinessCode.PARAMETERS_ERROR.getValue());
                return JSON.toJSONString(result);
            }
            Long userId = getLoginId();
            if(userId == null){
                LOGGER.error(BusinessCode.NO_LOGIN.getValue());
                result = RemoteResult.failure(BusinessCode.NO_LOGIN.getCode(),BusinessCode.NO_LOGIN.getValue());
                return JSON.toJSONString(result);
            }

            Like query = new Like();
            BeanUtils.copyProperties(query,param);
            query.setYn(YnEnum.Normal.getKey());
            query.setUserId(userId);
            int res = likeService.deleteByCondtion(query);
            if (res > 0) {
                result = RemoteResult.success("删除点赞成功");
            }
        } catch (Exception e) {

        }

        return JSON.toJSONString(result);
    }

}
