package com.frame.web.controller;


import com.alibaba.fastjson.JSON;
import com.frame.domain.*;
import com.frame.domain.base.YnEnum;
import com.frame.domain.common.Page;
import com.frame.domain.common.RemoteResult;
import com.frame.domain.enums.BusinessCode;
import com.frame.domain.vo.ArticalCollectionVO;
import com.frame.domain.vo.SiteCollectionVO;
import com.frame.service.FishSiteService;
import com.frame.service.SiteCollectionService;
import com.frame.service.UserService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.apache.commons.beanutils.BeanUtils;
import org.slf4j.Logger;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.util.Date;

import static org.slf4j.LoggerFactory.getLogger;

@Controller
@RequestMapping(value = "/siteCollection")
@Api(value = "siteCollection", description = "站点收藏接口")
public class SiteCollectionController {
    private static final Logger LOGGER = getLogger(SiteCollectionController.class);

    @Resource
    private FishSiteService fishSiteService;

    @Resource
    private SiteCollectionService siteCollectionService;

    @Resource
    private UserService userService;


    private boolean validUser(Long userId){
        User user = userService.selectEntry(userId);

        if (null == user) {
            LOGGER.warn("用户不存在");
            return false;
        }else{
            return true;
        }
    }

    @RequestMapping(value = "/siteCollectionList", method = {RequestMethod.GET}, produces = "application/json;charset=UTF-8")
    @ApiOperation(value = "站点搜藏列表", httpMethod = "GET", response = String.class, notes = "站点搜藏列表")
    @ResponseBody
    public String getSiteCollectionList(HttpServletRequest request
            , @ApiParam(value = "currrentPage", required = true) @RequestParam(value = "currrentPage", required = true) Integer currrentPage,
                                        @ModelAttribute SiteCollectionVO siteCollectionVO) {
        RemoteResult result = null;

        if (null == siteCollectionVO) {
            LOGGER.error("getSiteCollectionList 传入参数错误，传入的参数为:[{}]", JSON.toJSON(siteCollectionVO));
            result = RemoteResult.failure(BusinessCode.PARAMETERS_ERROR.getCode(),
                    BusinessCode.PARAMETERS_ERROR.getValue());
            return JSON.toJSONString(result);
        }
        try {

            //检查用户是否存在
            if (null != siteCollectionVO.getUserId() || !validUser(siteCollectionVO.getUserId())) {
                LOGGER.warn("用户不存在", siteCollectionVO);
                result = RemoteResult.failure(BusinessCode.IS_EXIST_NO.getCode(),
                        "用户不存在");
                return JSON.toJSONString(result);
            }


            Page<SiteCollection> page = new Page<SiteCollection>();
            page.setCurrentPage(currrentPage);


            Page<FishSite> res = siteCollectionService.getSiteCollectionById(siteCollectionVO, page);
            result = RemoteResult.success(res);
            return JSON.toJSONString(result);
        } catch (Exception e) {
            LOGGER.error("失败:" + e.getMessage(), e);
            result = RemoteResult.failure("0001", "操作失败:" + e.getMessage());
        }
        return JSON.toJSONString(result);
    }


    @RequestMapping(value = "/collection", method = {RequestMethod.POST})
    @ApiOperation(value = "收藏钓点", httpMethod = "POST", response = String.class, notes = "收藏钓点")
    public @ResponseBody
    String collection(HttpServletRequest request, @ModelAttribute  SiteCollectionVO siteCollectionVO) {
        RemoteResult result = null;
        try {
            if (null == siteCollectionVO) {
                LOGGER.error("collection site 传入的参数错");
                result = RemoteResult.failure(BusinessCode.PARAMETERS_ERROR.getCode(),
                        BusinessCode.PARAMETERS_ERROR.getValue());
                return JSON.toJSONString(result);
            }

            //valid user is valid
            User user = userService.selectEntry(siteCollectionVO.getUserId());
            if (null == user) {
                result = RemoteResult.failure(BusinessCode.PARAMETERS_ERROR.getCode(),
                        "此用户不存在");
                return JSON.toJSONString(result);
            }

            SiteCollection siteCollection = new SiteCollection();
            BeanUtils.copyProperties(siteCollection, siteCollectionVO);

            siteCollection.setYn(YnEnum.Normal.getKey());
            siteCollection.setCreated(new Date());
            siteCollection.setModified(new Date());
            int res = siteCollectionService.insertEntry(siteCollection);
            if (res <= 0) {
                result = RemoteResult.failure("0002", "site collection is failed,server internal error");
            } else {
                result = RemoteResult.success();
                result.setMsg("收藏钓点成功");
            }
            return JSON.toJSONString(result);
        } catch (Exception e) {
            LOGGER.error("失败:" + e.getMessage(), e);
            result = RemoteResult.failure("0001", "操作失败:" + e.getMessage());
        }
        return JSON.toJSONString(result);
    }

    @RequestMapping(value = "/del", method = {RequestMethod.GET})
    @ApiOperation(value = "delete site collection", httpMethod = "POST", response = String.class, notes = "delete commentVO")
    public @ResponseBody
    String del(HttpServletRequest request, @RequestParam Long id) {
        RemoteResult result = null;
        try {
            if (null == id) {
                LOGGER.error("delete site collection  传入的参数错误 id【{}】", id);
                result = RemoteResult.failure(BusinessCode.PARAMETERS_ERROR.getCode(), BusinessCode.PARAMETERS_ERROR.getValue());
                return JSON.toJSONString(result);
            }

            int res = siteCollectionService.deleteByKey(id);
            if (res > 0) {
                result = RemoteResult.success("删除钓点收藏成功");
            }
        } catch (Exception e) {

        }

        return JSON.toJSONString(result);
    }


}
