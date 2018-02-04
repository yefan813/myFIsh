package com.frame.web.controller;


import com.alibaba.fastjson.JSON;
import com.frame.domain.ArticalFish;
import com.frame.domain.Collection;
import com.frame.domain.User;
import com.frame.domain.base.YnEnum;
import com.frame.domain.common.Page;
import com.frame.domain.common.RemoteResult;
import com.frame.domain.enums.BusinessCode;
import com.frame.domain.vo.CollectionVO;
import com.frame.service.ArticalFishService;
import com.frame.service.CollectionService;
import com.frame.service.ImgSysService;
import com.frame.service.UserService;
import com.frame.web.entity.request.CollectionListParam;
import com.frame.web.entity.request.CollecttionId;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.apache.commons.beanutils.BeanUtils;
import org.apache.commons.collections.CollectionUtils;
import org.slf4j.Logger;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.util.Date;
import java.util.List;

import static org.slf4j.LoggerFactory.getLogger;

@Controller
@RequestMapping(value = "/collection")
@Api(value = "collection", description = "收藏接口")
public class CollectionController extends  BaseController{
    private static final Logger LOGGER = getLogger(CollectionController.class);

    @Resource
    private ImgSysService imgSysService;

    @Resource
    private ArticalFishService articalFishService;

    @Resource
    private CollectionService collectionService;

    @Resource
    private UserService userService;

    @Value("${img.prefix}")
    private String IMAGEPREFIX;

    /**
     * list collection artical
     *
     * @param request
     * @param listParam
     * @return
     */
    @RequestMapping(value = "/list", method = {RequestMethod.POST}, produces = "application/json;charset=UTF-8")
    @ApiOperation(value = "搜藏列表", httpMethod = "POST", response = String.class, notes = "搜藏列表")
    @ResponseBody
    public String getArticalFishCollectionList(HttpServletRequest request
            , @RequestBody CollectionListParam listParam) {
        RemoteResult result = null;

        if (null == listParam) {
            LOGGER.error("articalCollectionList 传入参数错误，传入的参数为:[{}]", JSON.toJSON(listParam));
            result = RemoteResult.failure(BusinessCode.PARAMETERS_ERROR.getCode(),
                    BusinessCode.PARAMETERS_ERROR.getValue());
            return JSON.toJSONString(result);
        }

        Long userId = getLoginId();
        if(userId == null){
            LOGGER.error(BusinessCode.NO_LOGIN.getValue());
            result = RemoteResult.failure(BusinessCode.NO_LOGIN.getCode(),BusinessCode.NO_LOGIN.getValue());
            return JSON.toJSONString(result);
        }
        try {

            Page<Collection> page = new Page<Collection>();
            page.setCurrentPage(listParam.getCurrentPage());

            CollectionVO articalCollectionVO = new CollectionVO();
            BeanUtils.copyProperties(articalCollectionVO, listParam);

            Page<ArticalFish> res = collectionService.getArticalCollectionById(userId,articalCollectionVO, page);
            result = RemoteResult.success(res);
            return JSON.toJSONString(result);
        } catch (Exception e) {
            LOGGER.error("失败:" + e.getMessage(), e);
            result = RemoteResult.failure("0001", "操作失败:" + e.getMessage());
        }
        return JSON.toJSONString(result);
    }


    /**
     * collect artical
     *
     * @param request
     * @param collectionVO
     * @return
     */
    @RequestMapping(value = "/collection", method = {RequestMethod.POST})
    @ApiOperation(value = "收藏文章", httpMethod = "POST", response = String.class, notes = "收藏文章")
    public @ResponseBody
    String collection(HttpServletRequest request, @RequestBody CollectionVO collectionVO) {
        RemoteResult result = null;
        try {
            if (null == collectionVO) {
                LOGGER.error("collection artical 传入的参数错");
                result = RemoteResult.failure(BusinessCode.PARAMETERS_ERROR.getCode(),
                        BusinessCode.PARAMETERS_ERROR.getValue());
                return JSON.toJSONString(result);
            }

            Long userId = getLoginId();
            if(userId == null){
                LOGGER.error(BusinessCode.NO_LOGIN.getValue());
                result = RemoteResult.failure(BusinessCode.NO_LOGIN.getCode(),BusinessCode.NO_LOGIN.getValue());
                return JSON.toJSONString(result);
            }



            int res = collectionService.saveOrUpdate(collectionVO,userId);
            if (res <= 0) {
                result = RemoteResult.failure("0002", "artical collection is failed,server internal error");
            } else {
                result = RemoteResult.success();
                result.setMsg("收藏文章成功");
            }
            return JSON.toJSONString(result);
        } catch (Exception e) {
            LOGGER.error("collection 失败:" + e.getMessage(), e);
            result = RemoteResult.failure("0001", "操作失败:" + e.getMessage());
        }
        return JSON.toJSONString(result);
    }


    @RequestMapping(value = "/cancelCollection", method = {RequestMethod.POST})
    @ApiOperation(value = "cancel artical collection", httpMethod = "POST", response = String.class, notes = "cancle artical collection")
    public @ResponseBody
    String cancelArticalCollection(HttpServletRequest request, @RequestBody CollectionVO param) {
        RemoteResult result = null;

        if (null == param || param.getSourceId() == null || param.getSourceType() == null) {
            LOGGER.error("cancleArticlCollection 传入参数错误，传入的参数为:collectionId:[{}]", JSON.toJSONString(param));
            result = RemoteResult.failure(BusinessCode.PARAMETERS_ERROR.getCode(),
                    BusinessCode.PARAMETERS_ERROR.getValue());
            return JSON.toJSONString(result);
        }

        Long userId = getLoginId();
        if(userId == null){
            LOGGER.error(BusinessCode.NO_LOGIN.getValue());
            result = RemoteResult.failure(BusinessCode.NO_LOGIN.getCode(),BusinessCode.NO_LOGIN.getValue());
            return JSON.toJSONString(result);
        }
        try {
            Collection query = new Collection();
            BeanUtils.copyProperties(query,param);
            query.setYn(YnEnum.Normal.getKey());
            //valid user is valid
            query.setUserId(userId);
            List<Collection> resList = collectionService.selectEntryList(query);
            if (CollectionUtils.isEmpty(resList)) {
                result = RemoteResult.failure(BusinessCode.PARAMETERS_ERROR.getCode(),
                        "此收藏不存在");
                return JSON.toJSONString(result);
            }

            int res = collectionService.deleteByKey(resList.get(0).getId().longValue());
            if (res <= 0) {
                result = RemoteResult.failure("0002", "artical collection cancel is failed,server internal error");
            } else {
                result = RemoteResult.success();
            }
        } catch (Exception e) {
            LOGGER.error("cancleArticlCollection exception", e);
            result = RemoteResult.failure("0001", "操作失败:" + e.getMessage());
        }
        return JSON.toJSONString(result);
    }


}
