package com.frame.web.controller;

import com.alibaba.fastjson.JSON;
import com.frame.domain.FishSite;
import com.frame.domain.User;
import com.frame.domain.base.YnEnum;
import com.frame.domain.common.Page;
import com.frame.domain.common.RemoteResult;
import com.frame.domain.enums.BusinessCode;
import com.frame.domain.vo.FishSiteVO;
import com.frame.domain.vo.Response.FishSiteListResponse;
import com.frame.service.FishSiteService;
import com.frame.service.ImgSysService;
import com.frame.service.UserService;
import com.frame.web.entity.request.FishSiteListParam;
import com.frame.web.entity.request.SiteSearchParam;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.apache.commons.beanutils.BeanUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.util.Date;

/**
 * Created by yefan on 2017/7/18.
 * 钓点相关接口
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
    public String getFishSiteList(HttpServletRequest request, @RequestBody FishSiteListParam param){
        RemoteResult result = null;
        try {
            Page<FishSiteListResponse> page = new Page<>();
            page.setCurrentPage(param.getCurrentPage());

            Long userId = getLoginId();

            FishSite fishSite = new FishSite();
            BeanUtils.copyProperties(fishSite, param);
            fishSite.setYn(YnEnum.Normal.getKey());
            fishSite.setUserId(userId);

            Page<FishSiteListResponse> res = fishSiteService.selectBaseEntryList(fishSite, page);
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
    @ResponseBody
    public String getFishSiteDetail(HttpServletRequest request, @RequestBody SiteSearchParam param){

        RemoteResult result = null;
        if (null == param || param.getSiteId() == null) {
            LOGGER.error("getFishSiteDetail artical 传入的参数错误 articalId【{}】", JSON.toJSONString(param));
            result = RemoteResult.failure(BusinessCode.PARAMETERS_ERROR.getCode(),
                    BusinessCode.PARAMETERS_ERROR.getValue());
            return JSON.toJSONString(result);
        }
        Long userId = getLoginId();

        FishSiteListResponse articalFish = fishSiteService.selectEntryDetail(param.getSiteId(),userId);
        if(null == articalFish){
            LOGGER.error("getFishSiteDetail artical 传入的参数错误 articalId【{}】", JSON.toJSONString(param));
            result = RemoteResult.failure(BusinessCode.PARAMETERS_ERROR.getCode(),
                    BusinessCode.PARAMETERS_ERROR.getValue());
            return JSON.toJSONString(result);
        }
        result = RemoteResult.success(articalFish);
        return JSON.toJSONString(result);
    }



    @RequestMapping(value = "/publish", method = {RequestMethod.POST})
    @ApiOperation(value = "发布钓点", httpMethod = "POST", response = String.class, notes = "发布钓点")
    public  @ResponseBody String publish(HttpServletRequest request,@RequestBody FishSiteVO fishSiteVO) {
        RemoteResult result = null;
        if (null == fishSiteVO) {
            LOGGER.error("publish artical 传入的参数错");
            result = RemoteResult.failure(BusinessCode.PARAMETERS_ERROR.getCode(),
                    BusinessCode.PARAMETERS_ERROR.getValue());
            return JSON.toJSONString(result);
        }
        try{
            Long userId = getLoginId();
            if(userId == null){
                LOGGER.error(BusinessCode.NO_LOGIN.getValue());
                result = RemoteResult.failure(BusinessCode.NO_LOGIN.getCode(),BusinessCode.NO_LOGIN.getValue());
                return JSON.toJSONString(result);
            }

            FishSite fishSite = new FishSite();
            BeanUtils.copyProperties(fishSite,fishSiteVO);

            fishSite.setUserId(userId);
            fishSite.setYn(YnEnum.Normal.getKey());
            fishSite.setCreated(new Date());
            fishSite.setModified(new Date());
           int res = fishSiteService.insertEntry(fishSite);
            if(res <= 0 ){
                result = RemoteResult.failure("0002", "artical publish is failed,server internal error");
            }else{
                result = RemoteResult.success();
                result.setMsg("钓点上传成功");
            }
            return JSON.toJSONString(result);
        }catch (Exception e) {
            LOGGER.error("失败:" + e.getMessage(), e);
            result = RemoteResult.failure("0001", "操作失败:" + e.getMessage());
        }
        return JSON.toJSONString(result);
    }

    @RequestMapping(value = "/del", method = {RequestMethod.DELETE})
    @ApiOperation(value = "delete", httpMethod = "DELETE", response = String.class, notes = "delete commentVO")
    public  @ResponseBody String del(HttpServletRequest request, @RequestParam Long id) {
        RemoteResult result = null;

        if(null == id ){
            LOGGER.error("delete 传入参数错误，传入的参数为:id:[{}]", id );
            result = RemoteResult.failure(BusinessCode.PARAMETERS_ERROR.getCode(),
                    BusinessCode.PARAMETERS_ERROR.getValue());
            return JSON.toJSONString(result);
        }
        try {

            //valid fishShop  is valid
            FishSite fishSite = fishSiteService.selectEntry(id);
            if(null == fishSite){
                result = RemoteResult.failure(BusinessCode.PARAMETERS_ERROR.getCode(),
                        "此渔具店不存在");
                return JSON.toJSONString(result);
            }

            FishSite condition = new FishSite();
            condition.setId(id.intValue());
            condition.setYn(YnEnum.Deleted.getKey());
            int res = fishSiteService.updateByKey(condition);
            if(res <= 0 ){
                result = RemoteResult.failure("0002", " fish site is failed,server internal error");
            }else{
                result = RemoteResult.success();
            }
        } catch (Exception e) {
            LOGGER.error("del exception" , e);
            result = RemoteResult.failure("0001", "操作失败:" + e.getMessage());
        }
        return JSON.toJSONString(result);
    }
}
