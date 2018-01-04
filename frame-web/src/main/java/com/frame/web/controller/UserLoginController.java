package com.frame.web.controller;

import com.alibaba.fastjson.JSON;
import com.frame.domain.UserLogin;
import com.frame.domain.base.YnEnum;
import com.frame.domain.common.RemoteResult;
import com.frame.service.UserLoginService;
import com.frame.service.impl.APNSService;
import com.frame.web.entity.request.RegistDeviceParam;
import com.frame.web.entity.request.SendNotiParam;
import com.google.common.collect.Lists;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.util.List;

@Controller
@RequestMapping(value = "/userLogin")
@Api(value="userLogin", description = "登录后上传相关数据接口")
public class UserLoginController extends BaseController {
	private static final Logger LOGGER = LoggerFactory.getLogger(UserLoginController.class);

	@Resource
	private UserLoginService userLoginService;

	@Resource
	private APNSService aPNSService;

	/**
	 * 保存用户登录信息接口
	 * 
	 * @param userLogin
	 * @return
	 */
	@RequestMapping(value = "/saveUserLoginInfo", method = {RequestMethod.POST}, produces = "application/json;charset=UTF-8")
	@ApiOperation(value = "用户登录上创登陆信息", httpMethod = "POST", response = String.class, notes = "用户登录上创登陆信息")
	public @ResponseBody
	String saveUserLoginInfo(HttpServletRequest request, @RequestBody UserLogin userLogin) {
		RemoteResult result = null;
		try {
			if (userLogin == null || userLogin.getUserId() == null
					|| (userLogin.getLocation() == null || !userLogin.getLocation().contains(","))
					|| userLogin.getLoginTime() == null) {
				LOGGER.info("调用saveUsetLoginInfo 传入的参数错误");
				result = RemoteResult.failure("0001", "传入参数错误");
				return JSON.toJSONString(result);
			}
			String[] loArr = userLogin.getLocation().split(",");
			if (null != loArr && loArr.length > 0) {
				userLogin.setLatitude(Double.valueOf(loArr[0]));
				userLogin.setLongitude(Double.valueOf(loArr[0]));
			}
			userLogin.setYn(YnEnum.Normal.getKey());
			if (userLoginService.insertEntry(userLogin) > 0) {
				LOGGER.info("用户定位保存成功,传入的参数为：[{}]", JSON.toJSONString(userLogin));
				result = RemoteResult.success();
			} else {
				LOGGER.info("用户定位失败,传入的参数为：[{}]", JSON.toJSONString(userLogin));
				result = RemoteResult.failure("0001", "用户定位失败，服务器异常");
			}
		} catch (Exception e) {
			LOGGER.error("失败:" + e.getMessage(), e);
			result = RemoteResult.failure("0001", "操作失败:" + e.getMessage());
		}
		return JSON.toJSONString(result);
	}

	/**
	 * 注册用户deviceToken接口
	 * 
	 * @param param
	 * @return
	 */
	@RequestMapping(value = "/registDeviceToken", method = {  RequestMethod.POST })
	@ApiOperation(value = "注册设备信息",httpMethod = "POST", response = String.class, notes = "注册设备信息")
	public @ResponseBody String registDeviceToken(@RequestBody RegistDeviceParam param) {
		RemoteResult result = null;
		try {
			if (param == null || StringUtils.isEmpty(param.getDeviceToken())) {
				LOGGER.info("调用registDeviceToken 传入的参数错误");
				result = RemoteResult.failure("0001", "传入参数错误");
				return JSON.toJSONString(result);
			}
			UserLogin login = new UserLogin();
			login.setUserId(param.getUserId());
			login.setDeviceToken(param.getDeviceToken());

			if (userLoginService.registDeviceToken(login) > 0) {
				LOGGER.info("用户注册deviceToken成功,传入的参数为：[{}]", JSON.toJSONString(login));
				result = RemoteResult.success();
			} else {
				LOGGER.info("用户注册deviceToken失败,传入的参数为：[{}]", JSON.toJSONString(login));
				result = RemoteResult.failure("0001", "用户注册deviceToken失败，服务器异常");
			}
		} catch (Exception e) {
			LOGGER.info("调用registDeviceToken异常", e);
		}
		return JSON.toJSONString(result);
	}

	/**
	 * 发送推送消息接口
	 * 
	 * @return
	 */
	@RequestMapping(value = "/sendNotifi", method = {  RequestMethod.POST })
	@ApiOperation(value = "发送推送消息接口",httpMethod = "POST", response = String.class, notes = "注册设备信息")
	public @ResponseBody String sendNotifi(@RequestBody SendNotiParam param) {
		RemoteResult result = null;
		if(param == null  || StringUtils.isBlank(param.getDeviceToken())){
			LOGGER.error("发送推送消息接口传入参数错误，参数为:[{}]", JSON.toJSONString(param));
			result = RemoteResult.failure("0001", "发送推送消息接口传入参数错误:");
			return JSON.toJSONString(result);
		}
		try {
			List<String> list = Lists.newArrayList();
			list.add(param.getDeviceToken());
			aPNSService.senPushNotification(list, param.getMsg());
		} catch (Exception e) {
			LOGGER.error("失败:" + e.getMessage(), e);
			result = RemoteResult.failure("0001", "操作失败:" + e.getMessage());
		}
		return JSON.toJSONString(result);
	}
}
