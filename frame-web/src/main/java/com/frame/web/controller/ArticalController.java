package com.frame.web.controller;

import com.alibaba.fastjson.JSON;
import com.frame.domain.ArticalFish;
import com.frame.domain.base.YnEnum;
import com.frame.domain.common.RemoteResult;
import com.frame.domain.enums.BusinessCode;
import com.frame.domain.img.ImageValidate;
import com.frame.domain.img.ImgDealMsg;
import com.frame.domain.img.Result;
import com.frame.service.ArticalService;
import com.frame.service.ImgSysService;
import io.swagger.annotations.*;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

/**
 * Created by yefan on 2017/7/18.
 */


@Controller
@RequestMapping(value = "/artical")
@Api(value = "artical", description = "发文章相关接口")
public class ArticalController extends BaseController {
    private static final Logger LOGGER = LoggerFactory.getLogger(UserController.class);

    @Resource
    private ImgSysService imgSysService;

    @Resource
    private ArticalService articalService;


    @Value("${img.prefix}")
    private String IMAGEPREFIX;

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
    String uploadImgFile(@ApiParam(value = "imgFile", required = false) MultipartFile imgFile) {
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
            @ApiImplicitParam(paramType="query", name = "is publish", value = "isPublish", required = true, dataType = "Integer"),
            @ApiImplicitParam(paramType="query", name = "content", value = "content", required = false, dataType = "String"),
    })
    public  @ResponseBody String publish(HttpServletRequest request,
                                         @RequestParam(value = "userId", required = true) Long userId,
                                         @RequestParam(value = "title", required = true)String title,
                                         @RequestParam(value = "waterType", required = true)Integer waterType,
                                         @RequestParam(value = "bait", required = true) Integer bait,
                                         @RequestParam(value = "fishType", required = true)Integer fishType,
                                         @RequestParam(value = "fishingFunc", required = true) Integer fishingFunc,
                                         @RequestParam(value = "fishLines", required = true) Double fishLines,
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

        ArticalFish articalFish = new ArticalFish();
        articalFish.setUserId(userId);
        articalFish.setTitle(title);
        articalFish.setWaterType(waterType);
        articalFish.setBait(bait);
        articalFish.setFishType(fishType);
        articalFish.setFishingFunc(fishingFunc);
        articalFish.setFishLines(fishLines);
        articalFish.setFishPoleBrand(fishPoleBrand);
        articalFish.setFishPoleLength(fishPoleLength);
        articalFish.setLat(lat);
        articalFish.setLng(lng);
        articalFish.setContent(content);
        articalFish.setIsPublish(isPublish);
        articalFish.setLocationAddress(locationAddress);
        articalFish.setArticleType(articleType);
        articalFish.setYn(YnEnum.Normal.getKey());
       int res = articalService.insertEntry(articalFish);
        if(res <= 0 ){
            result = RemoteResult.failure("0002", "artical publish is failed,server internal error");
        }

        result = RemoteResult.success();
        return JSON.toJSONString(result);
    }
}
