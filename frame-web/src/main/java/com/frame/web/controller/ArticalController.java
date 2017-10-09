package com.frame.web.controller;

import com.alibaba.fastjson.JSON;
import com.frame.domain.Artical;
import com.frame.domain.User;
import com.frame.domain.base.YnEnum;
import com.frame.domain.common.Page;
import com.frame.domain.common.RemoteResult;
import com.frame.domain.enums.BusinessCode;
import com.frame.domain.vo.ArticalVO;
import com.frame.service.ArticalService;
import com.frame.service.ImgSysService;
import com.frame.service.UserService;
import io.swagger.annotations.*;
import org.apache.commons.beanutils.BeanUtils;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

/**
 * Created by yefan on 2017/7/18.
 *
 *
 */


@Controller
@RequestMapping(value = "/artical")
@Api(value = "artical", description = "普通文章发帖相关接口")
public class ArticalController extends BaseController {
    private static final Logger LOGGER = LoggerFactory.getLogger(ArticalController.class);

    @Resource
    private ImgSysService imgSysService;

    @Resource
    private ArticalService articalService;

    @Resource
    private UserService userService;

    @Value("${img.prefix}")
    private String IMAGEPREFIX;


    @RequestMapping(value = "/articalFishList", method = {RequestMethod.POST}, produces = "application/json;charset=UTF-8")
    @ApiOperation(value = "渔获列表", httpMethod = "POST", response = String.class, notes = "普通文章列表")
    @ResponseBody
    public String getArticalFishList(HttpServletRequest request, @ApiParam(value="currrentPage",required = true)@RequestParam(value = "currrentPage", required = true)Integer currrentPage ,@ModelAttribute ArticalVO articalVO){
        RemoteResult result = null;
        try {
            if (null == articalVO) {
                LOGGER.error("getArticalFishDetail artical 传入的参数错误 articalId【{}】");
                result = RemoteResult.failure(BusinessCode.PARAMETERS_ERROR.getCode(),
                        BusinessCode.PARAMETERS_ERROR.getValue());
                return JSON.toJSONString(result);
            }


            Page<Artical> page = new Page<Artical>();
            page.setCurrentPage(currrentPage);


            Artical artical = new Artical();
            BeanUtils.copyProperties(artical, articalVO);
            artical.setYn(YnEnum.Normal.getKey());
            artical.setOrderField("modified");
            artical.setOrderFieldType("DESC");

            Page<Artical> res = articalService.selectPage(artical, page);
            result = RemoteResult.success(res);
            return JSON.toJSONString(result);
        }catch (Exception e) {
            LOGGER.error("失败:" + e.getMessage(), e);
            result = RemoteResult.failure("0001", "操作失败:" + e.getMessage());
        }
        return JSON.toJSONString(result);
    }

    @RequestMapping(value = "/articalFishDetail", method = {RequestMethod.POST}, produces = "application/json;charset=UTF-8")
    @ApiOperation(value = "渔获文章详细", httpMethod = "POST", response = String.class, notes = "普通文章详细")
    @ApiImplicitParams({
            @ApiImplicitParam(paramType="query", name = "articalId", value = "articalId", required = true, dataType = "Integer"),
    })
    @ResponseBody
    public String getArticalFishDetail(HttpServletRequest request,  @RequestParam(value = "articalId", required = true)Long articalId){

        RemoteResult result = null;
        if (null == articalId) {
            LOGGER.error("getArticalFishDetail artical 传入的参数错误 articalId【{}】", articalId);
            result = RemoteResult.failure(BusinessCode.PARAMETERS_ERROR.getCode(),
                    BusinessCode.PARAMETERS_ERROR.getValue());
            return JSON.toJSONString(result);
        }

        Artical artical = articalService.selectEntry(articalId);
        if(null == artical){
            LOGGER.error("getArticalFishDetail artical 传入的参数错误 articalId【{}】", articalId);
            result = RemoteResult.failure(BusinessCode.SUCCESS.getCode(),
                    BusinessCode.SUCCESS.getValue());
            return JSON.toJSONString(result);
        }
        result = RemoteResult.success(artical);
        return JSON.toJSONString(result);
    }


    @RequestMapping(value = "/publish", method = {RequestMethod.POST})
    @ApiOperation(value = "发布文章", httpMethod = "POST", response = String.class, notes = "发布文章")
    @ApiImplicitParams({
            @ApiImplicitParam(paramType="query", name = "userId", value = "userId", required = true, dataType = "Long"),
            @ApiImplicitParam(paramType="query", name = "title", value = "title", required = true, dataType = "String"),
            @ApiImplicitParam(paramType="query", name = "articleType", value = "articleType", required = true, dataType = "Integer"),
            @ApiImplicitParam(paramType="query", name = "isPublish", value = "isPublish", required = true, dataType = "Integer"),
            @ApiImplicitParam(paramType="query", name = "content", value = "content", required = false, dataType = "String"),
    })
    public  @ResponseBody String publish(HttpServletRequest request,
                                         @RequestParam(value = "userId", required = true) Long userId,
                                         @RequestParam(value = "title", required = true)String title,
                                         @RequestParam(value = "articleType", required = true)Integer articleType,
                                         @RequestParam(value = "isPublish", required = true)Integer isPublish,
                                         @RequestParam(value = "content", required = true)String content) {
        RemoteResult result = null;
        if (null == userId || StringUtils.isEmpty(title) ||
                null == articleType || StringUtils.isEmpty(content)) {
            LOGGER.error("publish artical 传入的参数错误 userId【{}】,title【{}】", userId, title);
            result = RemoteResult.failure(BusinessCode.PARAMETERS_ERROR.getCode(),
                    BusinessCode.PARAMETERS_ERROR.getValue());
            return JSON.toJSONString(result);
        }

        //valid user is valid
        User user = userService.selectEntry(userId);
        if(null == user){
            result = RemoteResult.failure(BusinessCode.PARAMETERS_ERROR.getCode(),
                    "此用户不存在");
            return JSON.toJSONString(result);
        }


        Artical artical = new Artical();
        artical.setUserId(userId);
        artical.setTitle(title);
        artical.setContent(content);
        artical.setIsPublish(isPublish);
        artical.setArticleType(articleType);
        artical.setYn(YnEnum.Normal.getKey());
       int res = articalService.insertEntry(artical);
        if(res <= 0 ){
            result = RemoteResult.failure("0002", "artical publish is failed,server internal error");
        }else{
            result = RemoteResult.success();
        }
        return JSON.toJSONString(result);
    }
}
