package com.frame.web.controller;

import com.alibaba.fastjson.JSON;
import com.frame.domain.ArticalFish;
import com.frame.domain.FishShop;
import com.frame.domain.User;
import com.frame.domain.base.YnEnum;
import com.frame.domain.common.Page;
import com.frame.domain.common.RemoteResult;
import com.frame.domain.enums.BusinessCode;
import com.frame.domain.vo.FishShopVO;
import com.frame.service.FishShopService;
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
@RequestMapping(value = "/fishShop")
@Api(value = "fishShop", description = "渔具店相关接口")
public class FishShopController extends BaseController {
    private static final Logger LOGGER = LoggerFactory.getLogger(UserController.class);

    @Resource
    private ImgSysService imgSysService;

    @Resource
    private FishShopService fishShopService;

    @Resource
    private UserService userService;

    @Value("${img.prefix}")
    private String IMAGEPREFIX;


    @RequestMapping(value = "/fishShopList", method = {RequestMethod.POST}, produces = "application/json;charset=UTF-8")
    @ApiOperation(value = "渔具店列表", httpMethod = "POST", response = String.class, notes = "渔具店列表")
    @ResponseBody
    public String getFishShopList(HttpServletRequest request, @RequestParam(value = "currrentPage", required = true)Integer currrentPage ,@RequestBody FishShopVO fishShopVO){
        RemoteResult result = null;
        try {
            if (null == fishShopVO) {
                LOGGER.error("getArticalFishDetail artical 传入的参数错误 articalId【{}】");
                result = RemoteResult.failure(BusinessCode.PARAMETERS_ERROR.getCode(),
                        BusinessCode.PARAMETERS_ERROR.getValue());
                return JSON.toJSONString(result);
            }
            Page<FishShop> page = new Page<>();
            page.setCurrentPage(currrentPage);

            FishShop fishShop = new FishShop();
            BeanUtils.copyProperties(fishShop,fishShopVO);
            fishShop.setYn(YnEnum.Normal.getKey());
            Page<FishShop> res = fishShopService.selectPage(fishShop,page);
            result = RemoteResult.success(res);
            return JSON.toJSONString(result);
        }catch (Exception e) {
            LOGGER.error("失败:" + e.getMessage(), e);
            result = RemoteResult.failure("0001", "操作失败:" + e.getMessage());
        }
        return JSON.toJSONString(result);
    }

    @RequestMapping(value = "/fishShopDetail", method = {RequestMethod.POST}, produces = "application/json;charset=UTF-8")
    @ApiOperation(value = "钓点详细", httpMethod = "POST", response = String.class, notes = "钓点详细")
    @ApiImplicitParams({
            @ApiImplicitParam(paramType="query", name = "siteId", value = "siteId", required = true, dataType = "Integer"),
    })
    @ResponseBody
    public String getFishShopDetail(HttpServletRequest request,  @RequestParam(value = "siteId", required = true)Long siteId){

        RemoteResult result = null;
        if (null == siteId) {
            LOGGER.error("getFishShopDetail artical 传入的参数错误 articalId【{}】", siteId);
            result = RemoteResult.failure(BusinessCode.PARAMETERS_ERROR.getCode(),
                    BusinessCode.PARAMETERS_ERROR.getValue());
            return JSON.toJSONString(result);
        }

        FishShop fishShop = fishShopService.selectEntry(siteId);
        if(null == fishShop){
            LOGGER.error("getFishShopDetail artical 传入的参数错误 articalId【{}】", siteId);
            result = RemoteResult.failure(BusinessCode.PARAMETERS_ERROR.getCode(),
                    BusinessCode.PARAMETERS_ERROR.getValue());
            return JSON.toJSONString(result);
        }

        result = RemoteResult.success(fishShop);
        return JSON.toJSONString(result);
    }



    @RequestMapping(value = "/publish", method = {RequestMethod.POST})
    @ApiOperation(value = "发布文章", httpMethod = "POST", response = String.class, notes = "发布文章")
    public  @ResponseBody String publish(HttpServletRequest request,@RequestBody FishShopVO fishShopVO) {
        RemoteResult result = null;
        try{
            if (null == fishShopVO) {
                LOGGER.error("publish artical 传入的参数错");
                result = RemoteResult.failure(BusinessCode.PARAMETERS_ERROR.getCode(),
                        BusinessCode.PARAMETERS_ERROR.getValue());
                return JSON.toJSONString(result);
            }

            //valid user is valid
            User user = userService.selectEntry(fishShopVO.getUserId());
            if(null == user){
                result = RemoteResult.failure(BusinessCode.PARAMETERS_ERROR.getCode(),
                        "此用户不存在");
                return JSON.toJSONString(result);
            }

            FishShop fishShop = new FishShop();
            BeanUtils.copyProperties(fishShop,fishShopVO);
            fishShop.setYn(YnEnum.Normal.getKey());
           int res = fishShopService.insertEntry(fishShop);
            if(res <= 0 ){
                result = RemoteResult.failure("0002", "artical publish is failed,server internal error");
            }else{
                result = RemoteResult.success();
                result.setMsg("渔具店上传成功");
            }

            return JSON.toJSONString(result);
        }catch (Exception e) {
            LOGGER.error("失败:" + e.getMessage(), e);
            result = RemoteResult.failure("0001", "操作失败:" + e.getMessage());
        }
        return JSON.toJSONString(result);
    }


    @RequestMapping(value = "/del", method = {RequestMethod.POST})
    @ApiOperation(value = "delete", httpMethod = "POST", response = String.class, notes = "delete commentVO")
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
            FishShop fishShop = fishShopService.selectEntry(id);
            if(null == fishShop){
                result = RemoteResult.failure(BusinessCode.PARAMETERS_ERROR.getCode(),
                        "此渔具店不存在");
                return JSON.toJSONString(result);
            }

            FishShop condition = new FishShop();
            condition.setId(id.intValue());
            condition.setYn(YnEnum.Deleted.getKey());
            int res = fishShopService.updateByKey(condition);
            if(res <= 0 ){
                result = RemoteResult.failure("0002", " fish shop is failed,server internal error");
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
