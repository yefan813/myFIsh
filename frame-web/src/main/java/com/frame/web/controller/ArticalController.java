package com.frame.web.controller;

import com.alibaba.fastjson.JSON;
import com.frame.domain.common.RemoteResult;
import com.frame.domain.img.ImageValidate;
import com.frame.domain.img.ImgDealMsg;
import com.frame.domain.img.Result;
import com.frame.service.ImgSysService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

/**
 * Created by yefan on 2017/7/18.
 */


@Controller
@RequestMapping(value = "/artical")
@Api(value="artical",description="发文章相关接口")
public class ArticalController extends BaseController {
    private static final Logger LOGGER = LoggerFactory.getLogger(UserController.class);

    @Resource
    private ImgSysService imgSysService;


    /**
     * upload image
     *
     * @param imgFile
     * @return
     */
    @RequestMapping(value = "/uploadImgFile", method = {RequestMethod.POST}, produces = "application/json;charset=UTF-8")
    @ApiOperation(value = "图片上传", httpMethod = "POST", response = String.class, notes = "图片上传")
    public @ResponseBody String uploadImgFile(@ApiParam(value = "imgFile", required = false) MultipartFile imgFile) {
        RemoteResult result = null;
        try{
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
                                result = RemoteResult.success(imagePath);
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
    String publish(HttpServletRequest request ){

        return null;
    }
}
