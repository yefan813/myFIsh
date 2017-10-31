package com.frame.web.controller;

import com.alibaba.fastjson.JSON;
import com.frame.domain.Comment;
import com.frame.domain.FishShop;
import com.frame.domain.User;
import com.frame.domain.base.YnEnum;
import com.frame.domain.common.RemoteResult;
import com.frame.domain.enums.BusinessCode;
import com.frame.domain.vo.CommentVO;
import com.frame.service.CommentService;
import com.frame.service.UserService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.apache.commons.beanutils.BeanUtils;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

@Controller
@RequestMapping(value = "comment")
@Api(value = "commentVO", description = "评论相关接口")
public class CommentController extends  BaseController {
    private static final Logger LOGGER = LoggerFactory.getLogger(CommentController.class);

    @Resource
    private CommentService commentService;

    @Resource
    private UserService userService;



    @Value("${img.prefix}")
    private String IMAGEPREFIX;

    @RequestMapping(value = "/getComment", method = {RequestMethod.POST})
    @ApiOperation(value = "获取comment", httpMethod = "POST", response = String.class, notes = "获取comment")
    public @ResponseBody String getCommentByArticalId(HttpServletRequest request,@ModelAttribute Comment comment){
        RemoteResult result = null;
        try{

        }catch (Exception e){

        }
        return JSON.toJSONString(result);
    }

    @RequestMapping(value = "/publish", method = {RequestMethod.POST})
    @ApiOperation(value = "发布comment", httpMethod = "POST", response = String.class, notes = "发布comment")
    public  @ResponseBody String publish(HttpServletRequest request, @ModelAttribute CommentVO commentVO) {
        RemoteResult result = null;
        try{
            if(null == commentVO || StringUtils.isBlank(commentVO.getContent())|| commentVO.getFromUserId() == null ||
                    commentVO.getTopicId() == null){
                LOGGER.error("publish commentVO is error , param is error[{}]",JSON.toJSONString(commentVO));
                result = RemoteResult.failure(BusinessCode.PARAMETERS_ERROR.getCode(),BusinessCode.PARAMETERS_ERROR.getValue());
                return JSON.toJSONString(result);
            }

            Comment comment = new Comment();
            BeanUtils.copyProperties(comment,commentVO);

            User user = userService.selectEntry(commentVO.getFromUserId());
            comment.setFromUserName(user.getNickName());
            comment.setFromUserAvtor(user.getAvatarUrl());
            comment.setYn(YnEnum.Normal.getKey());
            comment.setReView(false);                            //default is false;

            int res = commentService.insertEntry(comment);
            if(res > 0){
                result = RemoteResult.success("publish commentVO is success");
            }

        }catch (Exception e){
            LOGGER.error("失败:" + e.getMessage(), e);
            result = RemoteResult.failure("0001", "操作失败:" + e.getMessage());
        }

        return JSON.toJSONString(result);
    }

    @RequestMapping(value = "/del", method = {RequestMethod.POST})
    @ApiOperation(value = "delete commentVO", httpMethod = "POST", response = String.class, notes = "delete commentVO")
    public  @ResponseBody String del(HttpServletRequest request, @RequestParam Long id) {
        RemoteResult result = null;
        try{
            if(null == id){
                LOGGER.error("delete commentVO  传入的参数错误 id【{}】", id);
                result =  RemoteResult.failure(BusinessCode.PARAMETERS_ERROR.getCode(),BusinessCode.PARAMETERS_ERROR.getValue());
                return JSON.toJSONString(result);
            }

           int res =  commentService.deleteByKey(id);
            if(res > 0){
                result = RemoteResult.success("删除评论成功");
            }
        }catch (Exception e){

        }

        return JSON.toJSONString(result);
    }







}
