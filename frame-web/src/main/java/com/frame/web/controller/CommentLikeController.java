package com.frame.web.controller;

import com.alibaba.fastjson.JSON;
import com.frame.domain.*;
import com.frame.domain.base.YnEnum;
import com.frame.domain.common.RemoteResult;
import com.frame.domain.enums.BusinessCode;
import com.frame.domain.vo.ArticalLikeVO;
import com.frame.domain.vo.CommentLikeVO;
import com.frame.service.*;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.apache.commons.beanutils.BeanUtils;
import org.slf4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import java.util.Date;

import static org.slf4j.LoggerFactory.getLogger;

@Controller
@RequestMapping(value = "/commentLike")
@Api(value = "commentLike", description = "评论点赞接口")
public class CommentLikeController {

    private static final Logger LOGGER = getLogger(ArticalLikeController.class);


    @Resource
    private UserService userService;

    @Autowired
    private CommentLikeService commentLikeService;

    @Autowired
    private CommentService commentService;



    @RequestMapping(value = "/like", method = {RequestMethod.POST})
    @ApiOperation(value = "评论点赞", httpMethod = "POST", response = String.class, notes = "评论点赞")
    public @ResponseBody
    String like(HttpServletRequest request, @ModelAttribute CommentLikeVO commentLikeVO) {
        RemoteResult result = null;
        try {
            if (null == commentLikeVO) {
                LOGGER.error("like comment 传入的参数错");
                result = RemoteResult.failure(BusinessCode.PARAMETERS_ERROR.getCode(),
                        BusinessCode.PARAMETERS_ERROR.getValue());
                return JSON.toJSONString(result);
            }


            //valid artical is valid

            Comment comment = commentService.selectEntry(commentLikeVO.getCommentId());
            if (null == comment) {
                result = RemoteResult.failure(BusinessCode.PARAMETERS_ERROR.getCode(),
                        "此文章不存在");
                return JSON.toJSONString(result);
            }

            CommentLike commentLike = new CommentLike();
            BeanUtils.copyProperties(commentLike, commentLikeVO);

            commentLike.setYn(YnEnum.Normal.getKey());
            commentLike.setCreated(new Date());
            commentLike.setModified(new Date());


            int res = commentLikeService.insertEntry(commentLike);
            if (res <= 0) {
                result = RemoteResult.failure("0002", "comment like is failed,server internal error");
            } else {
                result = RemoteResult.success();
                result.setMsg("点赞评论成功");
            }
            return JSON.toJSONString(result);
        } catch (Exception e) {
            LOGGER.error("失败:" + e.getMessage(), e);
            result = RemoteResult.failure("0001", "操作失败:" + e.getMessage());
        }
        return JSON.toJSONString(result);
    }

    @RequestMapping(value = "/cancel", method = {RequestMethod.POST})
    @ApiOperation(value = "delete commentLikeVO", httpMethod = "POST", response = String.class, notes = "delete commentVO")
    public  @ResponseBody String cancel(HttpServletRequest request, @RequestParam Long id) {
        RemoteResult result = null;
        try{
            if(null == id){
                LOGGER.error("delete commentLikeVO  传入的参数错误 id【{}】", id);
                result =  RemoteResult.failure(BusinessCode.PARAMETERS_ERROR.getCode(),BusinessCode.PARAMETERS_ERROR.getValue());
                return JSON.toJSONString(result);
            }

            int res =  commentLikeService.deleteByKey(id);
            if(res > 0){
                result = RemoteResult.success("删除评论点赞成功");
            }
        }catch (Exception e){

        }

        return JSON.toJSONString(result);
    }

}
