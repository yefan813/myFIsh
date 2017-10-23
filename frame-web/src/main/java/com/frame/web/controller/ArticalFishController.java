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
import com.frame.domain.vo.ArticalFishVO;
import com.frame.service.ArticalFishService;
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
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.util.Date;

/**
 * Created by yefan on 2017/7/18.
 *
 *
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
    public String getArticalFishList(HttpServletRequest request, @ApiParam(value="currrentPage",required = true)@RequestParam(value = "currrentPage", required = true)Integer currrentPage ,@ModelAttribute ArticalFishVO articalFishVO){
        RemoteResult result = null;
        try {
            if (null == articalFishVO) {
                LOGGER.error("getArticalFishDetail artical 传入的参数错误 articalId【{}】");
                result = RemoteResult.failure(BusinessCode.PARAMETERS_ERROR.getCode(),
                        BusinessCode.PARAMETERS_ERROR.getValue());
                return JSON.toJSONString(result);
            }


            Page<ArticalFish> page = new Page<ArticalFish>();
            page.setCurrentPage(currrentPage);


            ArticalFish articalFish = new ArticalFish();
            BeanUtils.copyProperties(articalFish, articalFishVO);
            articalFish.setYn(YnEnum.Normal.getKey());
            articalFish.setOrderField("modified");
            articalFish.setOrderFieldType("DESC");

            Page<ArticalFish> res = articalFishService.selectPage(articalFish, page);
            result = RemoteResult.success(res);
            return JSON.toJSONString(result);
        }catch (Exception e) {
            LOGGER.error("失败:" + e.getMessage(), e);
            result = RemoteResult.failure("0001", "操作失败:" + e.getMessage());
        }
        return JSON.toJSONString(result);
    }

    @RequestMapping(value = "/articalFishDetail", method = {RequestMethod.POST}, produces = "application/json;charset=UTF-8")
    @ApiOperation(value = "渔获文章详细", httpMethod = "POST", response = String.class, notes = "渔获文章详细")
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

        ArticalFish articalFish = articalFishService.selectEntry(articalId);
        if(null == articalFish){
            LOGGER.error("getArticalFishDetail artical 传入的参数错误 articalId【{}】", articalId);
            result = RemoteResult.failure(BusinessCode.SUCCESS.getCode(),
                    BusinessCode.SUCCESS.getValue());
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
    @ApiImplicitParams({
            @ApiImplicitParam(paramType="query", name = "userId", value = "userId", required = true, dataType = "Long"),
            @ApiImplicitParam(paramType="query", name = "title", value = "title", required = true, dataType = "String"),
            @ApiImplicitParam(paramType="query", name = "fishTime", value = "fishTime", required = true, dataType = "Long"),
            @ApiImplicitParam(paramType="query", name = "waterType", value = "waterType", required = false, dataType = "Integer"),
            @ApiImplicitParam(paramType="query", name = "bait", value = "bait", required = true, dataType = "Integer"),
            @ApiImplicitParam(paramType="query", name = "fishType", value = "fishType", required = true, dataType = "Integer"),
            @ApiImplicitParam(paramType="query", name = "fishingFunc", value = "fishingFunc", required = true, dataType = "Integer"),
            @ApiImplicitParam(paramType="query", name = "fishLines", value = "fishLines", required = false, dataType = "Double"),
            @ApiImplicitParam(paramType="query", name = "fishPoleLength", value = "fishPoleLength", required = true, dataType = "Double"),
            @ApiImplicitParam(paramType="query", name = "fishPoleBrand", value = "fishPoleBrand", required = true, dataType = "String"),
            @ApiImplicitParam(paramType="query", name = "lat", value = "lat", required = true, dataType = "String"),
            @ApiImplicitParam(paramType="query", name = "lng", value = "lng", required = false, dataType = "String"),
            @ApiImplicitParam(paramType="query", name = "locationAddress", value = "locationAddress", required = true, dataType = "String"),
            @ApiImplicitParam(paramType="query", name = "articleType", value = "articleType", required = true, dataType = "Integer"),
            @ApiImplicitParam(paramType="query", name = "isPublish", value = "isPublish", required = true, dataType = "Integer"),
            @ApiImplicitParam(paramType="query", name = "content", value = "content", required = false, dataType = "String"),
    })
    public  @ResponseBody String publish(HttpServletRequest request,
                                         @RequestParam(value = "userId", required = true) Long userId,
                                         @RequestParam(value = "title", required = true)String title,
                                         @RequestParam(value = "fishTime", required = true)Long fishTime,
                                         @RequestParam(value = "waterType", required = false)Integer waterType,
                                         @RequestParam(value = "bait", required = true) Integer bait,
                                         @RequestParam(value = "fishType", required = true)Integer fishType,
                                         @RequestParam(value = "fishingFunc", required = true) Integer fishingFunc,
                                         @RequestParam(value = "fishLines", required = false) Double fishLines,
                                         @RequestParam(value = "fishPoleLength", required = true) Double fishPoleLength,
                                         @RequestParam(value = "fishPoleBrand", required = true)String fishPoleBrand,
                                         @RequestParam(value = "lat", required = true)String lat,
                                         @RequestParam(value = "lng", required = true) String lng,
                                         @RequestParam(value = "locationAddress", required = true) String locationAddress,
                                         @RequestParam(value = "articleType", required = true)Integer articleType,
                                         @RequestParam(value = "isPublish", required = true)Integer isPublish,
                                         @RequestParam(value = "content", required = true)String content) {
        RemoteResult result = null;
        if (null == userId || StringUtils.isEmpty(title) ||
                null == waterType ||
                bait == null || null == fishType ||
                fishingFunc == null || null == fishingFunc ||
                fishLines == null || fishPoleLength == null ||
                StringUtils.isEmpty(fishPoleBrand) || StringUtils.isEmpty(lat) ||
                StringUtils.isEmpty(lng) || StringUtils.isEmpty(locationAddress) ||
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


        ArticalFish articalFish = new ArticalFish();
        articalFish.setUserId(userId);
        articalFish.setTitle(title);
        articalFish.setWaterType(waterType);
        articalFish.setBait(bait);
        articalFish.setFishType(fishType);
        articalFish.setFishingFunc(fishingFunc);
        articalFish.setFishLines(fishLines);
        articalFish.setFishTime(new Date(fishTime));
        articalFish.setFishPoleBrand(fishPoleBrand);
        articalFish.setFishPoleLength(fishPoleLength);
        articalFish.setLat(lat);
        articalFish.setLng(lng);
        articalFish.setContent(content);
        articalFish.setIsPublish(isPublish);
        articalFish.setLocationAddress(locationAddress);
        articalFish.setArticleType(articleType);
        articalFish.setYn(YnEnum.Normal.getKey());
       int res = articalFishService.insertEntry(articalFish);
        if(res <= 0 ){
            result = RemoteResult.failure("0002", "artical publish is failed,server internal error");
        }else{
            result = RemoteResult.success();
        }
        return JSON.toJSONString(result);
    }
}
