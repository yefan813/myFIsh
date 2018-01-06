package com.frame.web.controller;


import com.alibaba.fastjson.JSON;
import com.frame.domain.ArticalCollection;
import com.frame.domain.ArticalFish;
import com.frame.domain.FishSite;
import com.frame.domain.User;
import com.frame.domain.base.YnEnum;
import com.frame.domain.common.Page;
import com.frame.domain.common.RemoteResult;
import com.frame.domain.enums.BusinessCode;
import com.frame.domain.ro.ArticalCollectionRO;
import com.frame.domain.vo.ArticalCollectionVO;
import com.frame.domain.vo.ArticalFishVO;
import com.frame.domain.vo.FishSiteVO;
import com.frame.service.ArticalCollectionService;
import com.frame.service.ArticalFishService;
import com.frame.service.ImgSysService;
import com.frame.service.UserService;
import com.frame.web.entity.request.ArticalCollectionListParam;
import com.frame.web.entity.request.CollecttionId;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.apache.commons.beanutils.BeanUtils;
import org.slf4j.Logger;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import java.util.Date;

import static org.slf4j.LoggerFactory.getLogger;

@Controller
@RequestMapping(value = "/articalCollection")
@Api(value = "articalCollection", description = "文章收藏接口")
public class ArticalCollectionController {
    private static final Logger LOGGER = getLogger(ArticalCollectionController.class);

    @Resource
    private ImgSysService imgSysService;

    @Resource
    private ArticalFishService articalFishService;

    @Resource
    private ArticalCollectionService articalCollectionService;

    @Resource
    private UserService userService;

    @Value("${img.prefix}")
    private String IMAGEPREFIX;

    /**
     * list collection artical
     * @param request
     * @param listParam
     * @return
     */
    @RequestMapping(value = "/list", method = {RequestMethod.POST}, produces = "application/json;charset=UTF-8")
    @ApiOperation(value = "文章搜藏列表", httpMethod = "POST", response = String.class, notes = "文章搜藏列表")
    @ResponseBody
    public String getArticalFishCollectionList(HttpServletRequest request
            , @RequestBody ArticalCollectionListParam listParam){
        RemoteResult result = null;

        if(null == listParam){
            LOGGER.error("articalCollectionList 传入参数错误，传入的参数为:[{}]", JSON.toJSON(listParam));
            result = RemoteResult.failure(BusinessCode.PARAMETERS_ERROR.getCode(),
                    BusinessCode.PARAMETERS_ERROR.getValue());
            return JSON.toJSONString(result);
        }
        try {

            //检查用户是否存在
            if(null != listParam.getUserId()){
                User user = userService.selectEntry(listParam.getUserId());
                if(null == user){
                    LOGGER.warn("用户不存在", listParam);
                    result = RemoteResult.failure(BusinessCode.IS_EXIST_NO.getCode(),
                            "用户不存在");
                    return JSON.toJSONString(result);
                }
            }


            Page<ArticalCollection> page = new Page<ArticalCollection>();
            page.setCurrentPage(listParam.getCurrrentPage());

            ArticalCollectionVO articalCollectionVO = new ArticalCollectionVO();
            BeanUtils.copyProperties(articalCollectionVO,listParam);

            Page<ArticalFish> res = articalCollectionService.getArticalCollectionById(articalCollectionVO, page);
            result = RemoteResult.success(res);
            return JSON.toJSONString(result);
        }catch (Exception e) {
            LOGGER.error("失败:" + e.getMessage(), e);
            result = RemoteResult.failure("0001", "操作失败:" + e.getMessage());
        }
            return JSON.toJSONString(result);
    }


    /**
     * collect artical
     * @param request
     * @param articalCollectionVO
     * @return
     */
    @RequestMapping(value = "/collection", method = {RequestMethod.POST})
    @ApiOperation(value = "收藏文章", httpMethod = "POST", response = String.class, notes = "收藏文章")
    public  @ResponseBody String collection(HttpServletRequest request, @RequestBody ArticalCollectionVO articalCollectionVO) {
        RemoteResult result = null;
        try{
            if (null == articalCollectionVO) {
                LOGGER.error("collection artical 传入的参数错");
                result = RemoteResult.failure(BusinessCode.PARAMETERS_ERROR.getCode(),
                        BusinessCode.PARAMETERS_ERROR.getValue());
                return JSON.toJSONString(result);
            }

            //valid user is valid
            User user = userService.selectEntry(articalCollectionVO.getUserId());
            if(null == user){
                result = RemoteResult.failure(BusinessCode.PARAMETERS_ERROR.getCode(),
                        "此用户不存在");
                return JSON.toJSONString(result);
            }

            ArticalCollection articalCollection = new ArticalCollection();
            BeanUtils.copyProperties(articalCollection,articalCollectionVO);

            articalCollection.setYn(YnEnum.Normal.getKey());
            articalCollection.setCreated(new Date());
            articalCollection.setModified(new Date());
            int res = articalCollectionService.insertEntry(articalCollection);
            if(res <= 0 ){
                result = RemoteResult.failure("0002", "artical collection is failed,server internal error");
            }else{
                result = RemoteResult.success();
                result.setMsg("收藏文章成功");
            }
            return JSON.toJSONString(result);
        }catch (Exception e) {
            LOGGER.error("collection 失败:" + e.getMessage(), e);
            result = RemoteResult.failure("0001", "操作失败:" + e.getMessage());
        }
        return JSON.toJSONString(result);
    }


    @RequestMapping(value = "/cancelArticalCollection", method = {RequestMethod.POST})
    @ApiOperation(value = "cancel artical collection", httpMethod = "POST", response = String.class, notes = "cancle artical collection")
    public @ResponseBody String cancelArticalCollection(HttpServletRequest request, @RequestBody CollecttionId id) {
        RemoteResult result = null;

        if(null == id || id.getCollectionId() ==null ){
            LOGGER.error("cancleArticlCollection 传入参数错误，传入的参数为:collectionId:[{}]", JSON.toJSONString(id) );
            result = RemoteResult.failure(BusinessCode.PARAMETERS_ERROR.getCode(),
                    BusinessCode.PARAMETERS_ERROR.getValue());
            return JSON.toJSONString(result);
        }
        try {

            //valid user is valid
            ArticalCollection collection = articalCollectionService.selectEntry(id.getCollectionId());
            if(null == collection){
                result = RemoteResult.failure(BusinessCode.PARAMETERS_ERROR.getCode(),
                        "此收藏不存在");
                return JSON.toJSONString(result);
            }

            ArticalCollection condition = new ArticalCollection();
            condition.setId(id.getCollectionId().intValue());
            condition.setYn(YnEnum.Deleted.getKey());
            int res = articalCollectionService.updateByKey(condition);
            if(res <= 0 ){
                result = RemoteResult.failure("0002", "artical collection cancel is failed,server internal error");
            }else{
                result = RemoteResult.success();
            }
        } catch (Exception e) {
            LOGGER.error("cancleArticlCollection exception" , e);
            result = RemoteResult.failure("0001", "操作失败:" + e.getMessage());
        }
        return JSON.toJSONString(result);
    }





}
