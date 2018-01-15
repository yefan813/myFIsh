package com.frame.web.controller;

import com.alibaba.fastjson.JSON;
import com.frame.domain.ArticalFish;
import com.frame.domain.User;
import com.frame.domain.base.YnEnum;
import com.frame.domain.common.Page;
import com.frame.domain.common.RemoteResult;
import com.frame.domain.enums.BusinessCode;
import com.frame.domain.img.ImageValidate;
import com.frame.domain.img.ImgDealMsg;
import com.frame.domain.img.Result;
import com.frame.domain.vo.Response.ArticalFishListResponse;
import com.frame.service.ArticalFishService;
import com.frame.service.ImgSysService;
import com.frame.service.UserService;
import com.frame.web.entity.request.ArticalFishSaveParam;
import com.frame.web.entity.request.ArtivalFishListParam;
import com.frame.web.entity.request.ActivityIdParam;
import com.frame.web.entity.request.IdParam;
import io.swagger.annotations.*;
import org.apache.commons.beanutils.BeanUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

/**
 * Created by yefan on 2017/7/18.
 */


@Controller
@RequestMapping(value = "/articalFish")
@Api(value = "articalFish", description = "渔获文章相关接口")
public class ArticalFishController extends BaseController {
    private static final Logger LOGGER = LoggerFactory.getLogger(ArticalFishController.class);

    @Resource
    private ImgSysService imgSysService;

    @Resource
    private ArticalFishService articalFishService;

    @Resource
    private UserService userService;

    @Value("${img.prefix}")
    private String IMAGEPREFIX;


    @RequestMapping(value = "/articalFishList", method = {RequestMethod.POST}, produces = "application/json;charset=UTF-8")
    @ApiOperation(value = "渔获列表", httpMethod = "POST", response = String.class, notes = "渔获列表")
    @ResponseBody
    public RemoteResult getArticalFishList(HttpServletRequest request, @RequestBody ArtivalFishListParam artivalFishListParam) {
        RemoteResult result = null;
        try {
            if (null == artivalFishListParam) {
                LOGGER.error("getArticalFishDetail artical 传入的参数错误 articalId【{}】", JSON.toJSON(artivalFishListParam));
                result = RemoteResult.failure(BusinessCode.PARAMETERS_ERROR.getCode(),
                        BusinessCode.PARAMETERS_ERROR.getValue());
                return result;
            }
            Page<ArticalFishListResponse> page = new Page<ArticalFishListResponse>();
            page.setCurrentPage(artivalFishListParam.getCurrentPage());


            ArticalFish articalFish = new ArticalFish();
            BeanUtils.copyProperties(articalFish, artivalFishListParam);
            articalFish.setYn(YnEnum.Normal.getKey());
            articalFish.setOrderField("modified");
            articalFish.setOrderFieldType("DESC");

            Page<ArticalFishListResponse> res = articalFishService.selectBaseEntryList(articalFish, page);

            result = RemoteResult.success(res);
            return result;
        } catch (Exception e) {
            LOGGER.error("失败:" + e.getMessage(), e);
            result = RemoteResult.failure("0001", "操作失败:" + e.getMessage());
        }
        return result;
    }


    @RequestMapping(value = "/articalFishDetail", method = {RequestMethod.POST}, produces = "application/json;charset=UTF-8")
    @ApiOperation(value = "渔获文章详细", httpMethod = "POST", response = String.class, notes = "渔获文章详细")
    @ResponseBody
    public String getArticalFishDetail(HttpServletRequest request, @RequestBody ActivityIdParam param) {

        RemoteResult result = null;
        if (null == param) {
            LOGGER.error("getArticalFishDetail artical 传入的参数错误 articalId【{}】", JSON.toJSONString(param));
            result = RemoteResult.failure(BusinessCode.PARAMETERS_ERROR.getCode(),
                    BusinessCode.PARAMETERS_ERROR.getValue());
            return JSON.toJSONString(result);
        }

        ArticalFishListResponse articalFish = articalFishService.selectEntryDetail(param.getArticalId(),param.getUserId());
        if (null == articalFish) {
            LOGGER.error("getArticalFishDetail artical 传入的参数错误 articalId【{}】", param.getArticalId());
            result = RemoteResult.failure(BusinessCode.FAILED.getCode(),
                    BusinessCode.FAILED.getValue());
            return JSON.toJSONString(result);
        }
        result = RemoteResult.success(articalFish);
        return JSON.toJSONString(result);
    }


    /**
     * upload image
     *
     * @param imgFile
     * @return
     */
    @RequestMapping(value = "/uploadImgFile", method = {RequestMethod.POST}, produces = "application/json;charset=UTF-8")
    @ApiOperation(value = "图片上传", httpMethod = "POST", response = String.class, notes = "图片上传")
    public
    @ResponseBody
    String uploadImgFile(HttpServletRequest request, @ApiParam(value = "imgFile", required = false) MultipartFile imgFile) {
        RemoteResult result = null;
        try {
            String imagePath = null;
            if (imgFile != null && imgFile.getSize() > 0) {
                try {
                    if (imgFile.getBytes() != null && imgFile.getBytes().length > 0) {
                        Result r = ImageValidate.validate4Upload(imgFile);
                        if (r.isSuccess()) {
                            ImgDealMsg re = imgSysService.uploadByteImg(imgFile.getBytes(), "fish");
                            if (re != null && re.isSuccess()) {
                                // 上传成功
                                imagePath = (String) re.getMsg();
                                // 上传成功设置template 图片路径
                                result = RemoteResult.success(IMAGEPREFIX + imagePath);
                                return dealJosnP("", result);
                            } else {
                                // 上传文件失败，在页面提示
                                result = RemoteResult.failure("0001", "文件上传失败！");
                                return dealJosnP("", result);
                            }
                        } else {
                            result = RemoteResult.failure("0001", r.getResultCode());
                            return dealJosnP("", result);
                        }
                    }
                } catch (Exception e) {
                    LOGGER.error("失败:" + e.getMessage(), e);
                    result = RemoteResult.failure("0001", "操作失败:" + e.getMessage());
                }
            }

        } catch (Exception e) {
            LOGGER.error("失败:" + e.getMessage(), e);
            result = RemoteResult.failure("0001", "操作失败:" + e.getMessage());
        }
        return JSON.toJSONString(result);
    }


    @RequestMapping(value = "/publish", method = {RequestMethod.POST})
    @ApiOperation(value = "发布文章", httpMethod = "POST", response = String.class, notes = "发布文章")
    public @ResponseBody
    String publish(HttpServletRequest request, @RequestBody ArticalFishSaveParam fishSaveParam) {
       try {
           RemoteResult result = null;
           if (null == fishSaveParam) {
               LOGGER.error("publish artical 传入的参数错误【{}】", JSON.toJSONString(fishSaveParam));
               result = RemoteResult.failure(BusinessCode.PARAMETERS_ERROR.getCode(),
                       BusinessCode.PARAMETERS_ERROR.getValue());
               return JSON.toJSONString(result);
           }

           //valid user is valid
           User user = userService.selectEntry(fishSaveParam.getUserId());
           if (null == user) {
               result = RemoteResult.failure(BusinessCode.PARAMETERS_ERROR.getCode(),
                       "此用户不存在");
               return JSON.toJSONString(result);
           }


           ArticalFish articalFish = new ArticalFish();
           BeanUtils.copyProperties(articalFish, fishSaveParam);
           if (null != fishSaveParam.getFishTime()) {
               articalFish.setFishTime(fishSaveParam.getFishTime());
           }
           articalFish.setIsPublish(0);
           articalFish.setYn(YnEnum.Normal.getKey());
           int res = articalFishService.insertEntry(articalFish);
           if (res <= 0) {
               result = RemoteResult.failure("0002", "artical publish is failed,server internal error");
           } else {
               result = RemoteResult.success();
           }
           return JSON.toJSONString(result);
       }catch (Exception e){
           LOGGER.error("调用发布文章异常", e);
           return JSON.toJSONString(RemoteResult.failure("调用发布文章异常"));
       }
    }

    @RequestMapping(value = "/del", method = {RequestMethod.DELETE})
    @ApiOperation(value = "delete", httpMethod = "DELETE", response = String.class, notes = "delete commentVO")
    public @ResponseBody
    String del(HttpServletRequest request, @RequestBody IdParam param) {
        RemoteResult result = null;

        if (null == param || param.getId() == null) {
            LOGGER.error("delete 传入参数错误，传入的参数为:id:[{}]", JSON.toJSONString(param));
            result = RemoteResult.failure(BusinessCode.PARAMETERS_ERROR.getCode(),
                    BusinessCode.PARAMETERS_ERROR.getValue());
            return JSON.toJSONString(result);
        }
        try {

            //valid artical fish is valid
            ArticalFish collection = articalFishService.selectEntry(param.getId());
            if (null == collection) {
                result = RemoteResult.failure(BusinessCode.PARAMETERS_ERROR.getCode(),
                        "此收藏不存在");
                return JSON.toJSONString(result);
            }

            ArticalFish condition = new ArticalFish();
            condition.setId(param.getId().intValue());
            condition.setYn(YnEnum.Deleted.getKey());
            int res = articalFishService.updateByKey(condition);
            if (res <= 0) {
                result = RemoteResult.failure("0002", "artical delete is failed,server internal error");
            } else {
                result = RemoteResult.success();
            }
        } catch (Exception e) {
            LOGGER.error("del exception", e);
            result = RemoteResult.failure("0001", "操作失败:" + e.getMessage());
        }
        return JSON.toJSONString(result);
    }

}
