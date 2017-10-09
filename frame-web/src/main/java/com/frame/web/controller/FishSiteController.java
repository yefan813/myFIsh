package com.frame.web.controller;

import com.alibaba.fastjson.JSON;
import com.frame.domain.FishSite;
import com.frame.domain.User;
import com.frame.domain.base.YnEnum;
import com.frame.domain.common.Page;
import com.frame.domain.common.RemoteResult;
import com.frame.domain.enums.BusinessCode;
import com.frame.domain.vo.FishSiteVO;
import com.frame.service.FishSiteService;
import com.frame.service.ImgSysService;
import com.frame.service.UserService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.apache.commons.beanutils.BeanUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

/**
 * Created by yefan on 2017/7/18.
 */


@Controller
@RequestMapping(value = "/fishSite")
@Api(value = "fishSite", description = "钓点相关接口")
public class FishSiteController extends BaseController {
    private static final Logger LOGGER = LoggerFactory.getLogger(UserController.class);

    @Resource
    private ImgSysService imgSysService;

    @Resource
    private FishSiteService fishSiteService;

    @Resource
    private UserService userService;

    @Value("${img.prefix}")
    private String IMAGEPREFIX;


    @RequestMapping(value = "/fishSiteList", method = {RequestMethod.POST}, produces = "application/json;charset=UTF-8")
    @ApiOperation(value = "钓点列表", httpMethod = "POST", response = String.class, notes = "钓点列表")
    @ResponseBody
    public String getFishSiteList(HttpServletRequest request, @RequestParam(value = "currrentPage", required = true)Integer currrentPage , @ModelAttribute FishSiteVO fishSiteVO){
        RemoteResult result = null;
        try {
            Page<FishSite> page = new Page<>();
            page.setCurrentPage(currrentPage);

            FishSite fishSite = new FishSite();
            BeanUtils.copyProperties(fishSite, fishSiteVO);

            Page<FishSite> res = fishSiteService.selectPage(fishSite, page);
            result = RemoteResult.success(res);
            return JSON.toJSONString(result);
        }catch (Exception e) {
            LOGGER.error("失败:" + e.getMessage(), e);
            result = RemoteResult.failure("0001", "操作失败:" + e.getMessage());
        }
        return JSON.toJSONString(result);
    }

    @RequestMapping(value = "/fishSiteDetail", method = {RequestMethod.POST}, produces = "application/json;charset=UTF-8")
    @ApiOperation(value = "钓点详细", httpMethod = "POST", response = String.class, notes = "钓点详细")
    @ApiImplicitParams({
            @ApiImplicitParam(paramType="query", name = "siteId", value = "siteId", required = true, dataType = "Integer"),
    })
    @ResponseBody
    public String getFishSiteDetail(HttpServletRequest request,  @RequestParam(value = "siteId", required = true)Long siteId){

        RemoteResult result = null;
        if (null == siteId) {
            LOGGER.error("getFishSiteDetail artical 传入的参数错误 articalId【{}】", siteId);
            result = RemoteResult.failure(BusinessCode.PARAMETERS_ERROR.getCode(),
                    BusinessCode.PARAMETERS_ERROR.getValue());
            return JSON.toJSONString(result);
        }

        FishSite articalFish = fishSiteService.selectEntry(siteId);
        if(null == articalFish){
            LOGGER.error("getFishSiteDetail artical 传入的参数错误 articalId【{}】", siteId);
            result = RemoteResult.failure(BusinessCode.PARAMETERS_ERROR.getCode(),
                    BusinessCode.PARAMETERS_ERROR.getValue());
            return JSON.toJSONString(result);
        }
        result = RemoteResult.success(articalFish);
        return JSON.toJSONString(result);
    }



    @RequestMapping(value = "/publish", method = {RequestMethod.POST})
    @ApiOperation(value = "发布文章", httpMethod = "POST", response = String.class, notes = "发布文章")
    public  @ResponseBody String publish(HttpServletRequest request,@ModelAttribute FishSiteVO fishSiteVO) {
        RemoteResult result = null;
        try{
            if (null == fishSiteVO) {
                LOGGER.error("publish artical 传入的参数错");
                result = RemoteResult.failure(BusinessCode.PARAMETERS_ERROR.getCode(),
                        BusinessCode.PARAMETERS_ERROR.getValue());
                return JSON.toJSONString(result);
            }

            //valid user is valid
            User user = userService.selectEntry(fishSiteVO.getUserId());
            if(null == user){
                result = RemoteResult.failure(BusinessCode.PARAMETERS_ERROR.getCode(),
                        "此用户不存在");
                return JSON.toJSONString(result);
            }

            FishSite fishSite = new FishSite();
            BeanUtils.copyProperties(fishSite,fishSiteVO);

            fishSite.setYn(YnEnum.Normal.getKey());
           int res = fishSiteService.insertEntry(fishSite);
            if(res <= 0 ){
                result = RemoteResult.failure("0002", "artical publish is failed,server internal error");
            }else{
                result = RemoteResult.success();
            }
            return JSON.toJSONString(result);
        }catch (Exception e) {
            LOGGER.error("失败:" + e.getMessage(), e);
            result = RemoteResult.failure("0001", "操作失败:" + e.getMessage());
        }
        return JSON.toJSONString(result);
    }
}
