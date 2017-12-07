package com.frame.web.controller;


import com.alibaba.fastjson.JSON;
import com.frame.domain.ArticalCollection;
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
import org.apache.commons.beanutils.BeanUtils;
import org.slf4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.util.Date;

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

    @RequestMapping(value = "/like", method = {RequestMethod.POST})
    @ApiOperation(value = "收藏文章", httpMethod = "POST", response = String.class, notes = "收藏文章")
    public @ResponseBody
    String like(HttpServletRequest request, @ModelAttribute ArticalLikeVO articalLikeVO) {
        RemoteResult result = null;
        try {
            if (null == articalLikeVO) {
                LOGGER.error("like artical 传入的参数错");
                result = RemoteResult.failure(BusinessCode.PARAMETERS_ERROR.getCode(),
                        BusinessCode.PARAMETERS_ERROR.getValue());
                return JSON.toJSONString(result);
            }

            //valid user is valid
            User user = userService.selectEntry(articalLikeVO.getUserId());
            if (null == user) {
                result = RemoteResult.failure(BusinessCode.PARAMETERS_ERROR.getCode(),
                        "此用户不存在");
                return JSON.toJSONString(result);
            }

            //valid artical is valid

            ArticalFish articalFish = articalFishService.selectEntry(articalLikeVO.getArticalId());
            if (null == articalFish) {
                result = RemoteResult.failure(BusinessCode.PARAMETERS_ERROR.getCode(),
                        "此文章不存在");
                return JSON.toJSONString(result);
            }

            ArticalLike articalLike = new ArticalLike();
            BeanUtils.copyProperties(articalLike, articalLikeVO);

            articalLike.setYn(YnEnum.Normal.getKey());
            articalLike.setCreated(new Date());
            articalLike.setModified(new Date());


            int res = articalLikeService.insertEntry(articalLike);
            if (res <= 0) {
                result = RemoteResult.failure("0002", "artical like is failed,server internal error");
            } else {
                result = RemoteResult.success();
                result.setMsg("点赞文章成功");
            }
            return JSON.toJSONString(result);
        } catch (Exception e) {
            LOGGER.error("失败:" + e.getMessage(), e);
            result = RemoteResult.failure("0001", "操作失败:" + e.getMessage());
        }
        return JSON.toJSONString(result);
    }
}
