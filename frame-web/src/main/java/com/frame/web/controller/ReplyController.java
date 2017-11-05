package com.frame.web.controller;

import com.alibaba.fastjson.JSON;
import com.frame.domain.Reply;
import com.frame.domain.User;
import com.frame.domain.base.YnEnum;
import com.frame.domain.common.RemoteResult;
import com.frame.domain.enums.BusinessCode;
import com.frame.domain.enums.ReplyTypeEnum;
import com.frame.domain.vo.ReplyVO;
import com.frame.service.ReplyService;
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
@RequestMapping(value = "reply")
@Deprecated
@Api(value = "reply", description = "回复评论相关接口")
public class ReplyController extends  BaseController {
    private static final Logger LOGGER = LoggerFactory.getLogger(ReplyController.class);

    @Resource
    private ReplyService replyService;

    @Resource
    private UserService userService;



    @Value("${img.prefix}")
    private String IMAGEPREFIX;



    @RequestMapping(value = "/publish", method = {RequestMethod.POST})
    @ApiOperation(value = "发布reply", httpMethod = "POST", response = String.class, notes = "发布reply")
    public  @ResponseBody String publish(HttpServletRequest request, @ModelAttribute ReplyVO replyVO) {
        RemoteResult result = null;
        try{
            if(null == replyVO || StringUtils.isBlank(replyVO.getContent())|| replyVO.getFromUserId() == null || null == replyVO.getReplyType()){
                LOGGER.error("publish reply is error , param is error[{}]",JSON.toJSONString(replyVO));
                result = RemoteResult.failure(BusinessCode.PARAMETERS_ERROR.getCode(),BusinessCode.PARAMETERS_ERROR.getValue());
                return JSON.toJSONString(result);
            }

            if(replyVO.getReplyType() != 1 && replyVO.getReplyType() != 2 ){
                LOGGER.error("publish reply type is error , param is error[{}]",JSON.toJSONString(replyVO));
                result = RemoteResult.failure(BusinessCode.PARAMETERS_ERROR.getCode(),"publish reply type is error ");
                return JSON.toJSONString(result);
            }
            if(replyVO.getReplyType().intValue() == ReplyTypeEnum.COMMENT.getKey() && null != replyVO.getCommentId() && replyVO.getCommentId() == replyVO.getReplyId() ){
                LOGGER.error("publish comment reply, the reply id need equal comment id ,param is error[{}]",JSON.toJSONString(replyVO));
                result = RemoteResult.failure(BusinessCode.PARAMETERS_ERROR.getCode(),"publish comment reply, the reply id need equal comment id ");
                return JSON.toJSONString(result);
            }

            if(replyVO.getReplyType().intValue() == ReplyTypeEnum.REPLY.getKey() && null != replyVO.getReplyId()){
                LOGGER.error("publish comment reply, the reply id need equal comment id ,param is error[{}]",JSON.toJSONString(replyVO));
                result = RemoteResult.failure(BusinessCode.PARAMETERS_ERROR.getCode(),"publish comment reply, the reply id need equal comment id ");
                return JSON.toJSONString(result);
            }

            Reply reply = new Reply();
            BeanUtils.copyProperties(reply,replyVO);

            User user = userService.selectEntry(reply.getFromUserId());
            reply.setFromUserName(user.getNickName());
            reply.setFromUserAvtor(user.getAvatarUrl());
            reply.setYn(YnEnum.Normal.getKey());
            reply.setReview(false);                            //default is false;

            int res = replyService.insertEntry(reply);
            if(res > 0){
                result = RemoteResult.success("publish reply is success");
            }

        }catch (Exception e){
            LOGGER.error("失败:" + e.getMessage(), e);
            result = RemoteResult.failure("0001", "操作失败:" + e.getMessage());
        }

        return JSON.toJSONString(result);
    }

    @RequestMapping(value = "/del", method = {RequestMethod.POST})
    @ApiOperation(value = "delete reply", httpMethod = "POST", response = String.class, notes = "delete reply")
    public  @ResponseBody String del(HttpServletRequest request, @RequestParam Long id) {
        RemoteResult result = null;
        try{
            if(null == id){
                LOGGER.error("delete reply  传入的参数错误 id【{}】", id);
                result =  RemoteResult.failure(BusinessCode.PARAMETERS_ERROR.getCode(),BusinessCode.PARAMETERS_ERROR.getValue());
                return JSON.toJSONString(result);
            }

           int res =  replyService.deleteByKey(id);
            if(res > 0){
                result = RemoteResult.success("删除评论成功");
            }
        }catch (Exception e){

        }

        return JSON.toJSONString(result);
    }







}
