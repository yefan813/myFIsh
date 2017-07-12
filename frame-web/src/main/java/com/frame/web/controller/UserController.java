package com.frame.web.controller;

import com.alibaba.fastjson.JSON;
import com.frame.domain.User;
import com.frame.domain.UserAuths;
import com.frame.domain.UserValid;
import com.frame.domain.base.YnEnum;
import com.frame.domain.common.RemoteResult;
import com.frame.domain.cusAnnotion.RequestLimit;
import com.frame.domain.enums.SendSMSTypeEnum;
import com.frame.service.*;
import com.frame.service.utils.RandomStrUtils;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.util.List;

@Controller
@RequestMapping(value = "/user")
@Api(value="user",description="用户相关接口")
public class UserController extends BaseController {
	private static final Logger LOGGER = LoggerFactory.getLogger(UserController.class);

	@Resource
	private UserService userService;

	@Resource
	private UserLoginService userLoginService;

	@Resource
	private AppSecretService appSecretService;

	@Resource
	private TaoBaoSmsService taoBaoSmsService;

	@Resource
	private UserValidService userValidService;

	@Resource
	private ImgSysService imgSysService;
	
	@Resource
	private UserFriendsService userFriendsService;
	

	@Value("${img.prefix}")
	private String IMAGEPREFIX;

	/**
	 * 编辑用户信息接口
	 * 
	 * @param user
	 * @param imgFile
	 * @return
	 */
	/*@RequestMapping(value = "/editUserInfo", method = {RequestMethod.POST}, produces = "application/json;charset=UTF-8")
	public @ResponseBody String editUserInfo(User user,
			@RequestParam(value = "imgFile", required = false) MultipartFile imgFile) {
		RemoteResult result = null;
		try{
		if (null == user || user.getId() == null) {
			LOGGER.info("调用editUserInfo 传入的参数错误");
			result = RemoteResult.failure("0001", "传入参数错误");
			return JSON.toJSONString(result);
		}
		if (imgFile != null && imgFile.getSize() > 0) {
			try {
				if (imgFile.getBytes() != null && imgFile.getBytes().length > 0) {
					Result r = ImageValidate.validate4Upload(imgFile);
					if (r.isSuccess()) {
						ImgDealMsg re = imgSysService.uploadByteImg(imgFile.getBytes(), "fish");
						if (re != null && re.isSuccess()) {
							// 上传成功
							String imgUrl = (String) re.getMsg();
							// 上传成功设置template 图片路径
							user.setAvatarUrl(imgUrl);
						} else {
							// 上传文件失败，在页面提示
							result = RemoteResult.failure("0001", "头像上传失败！");
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
		
		result = userService.editUserInfo(user);
		
		} catch (Exception e) {
			LOGGER.error("失败:" + e.getMessage(), e);
			result = RemoteResult.failure("0001", "操作失败:" + e.getMessage());
		}
		return JSON.toJSONString(result);
	}*/

	/**
	 * 
	 * 用户注册接口
	 * 
	 * @param tel
	 * @param password
	 * @param validCode
	 * @param validDate
	 * @return
	 */
	@RequestLimit
	@RequestMapping(value = "/regist", method = {RequestMethod.POST})
	@ApiOperation(value = "注册用户", httpMethod = "POST", response = String.class, notes = "注册用户")
	@ApiImplicitParams({
			@ApiImplicitParam(paramType="query", name = "tel", value = "用户电话", required = true, dataType = "String"),
			@ApiImplicitParam(paramType="query", name = "password", value = "用户密码", required = true, dataType = "String"),
			@ApiImplicitParam(paramType="query", name = "validCode", value = "验证码", required = true, dataType = "String"),
			@ApiImplicitParam(paramType="query", name = "validDate", value = "验证日期", required = true, dataType = "Long"),
			@ApiImplicitParam(paramType="query", name = "inviteCode", value = "邀请码", required = false, dataType = "String"),


	})
	public @ResponseBody String registUser(HttpServletRequest request , @RequestParam(value = "tel", required = true) String tel,
										   @RequestParam(value = "password", required = true) String password,
										   @RequestParam(value = "validCode", required = true) String validCode,
										   @RequestParam(value = "validDate", required = true) Long validDate,
										   @RequestParam(value = "inviteCode", required = false) String inviteCode) {
		RemoteResult result = null;
		try {

			if (StringUtils.isEmpty(tel) || StringUtils.isEmpty(password) || StringUtils.isEmpty(validCode)
					|| (null == validDate || validDate.longValue() <= 0)) {
				LOGGER.error("调用registUser 传入的参数错误 tel【{}】,密码[{}],验证码[{}],验证时间【{}】", tel, password, validCode,
						validDate);
				result = RemoteResult.failure("0001", "传入参数错误");
				return JSON.toJSONString(result);
				// 判断用户是否已经注册
			}

			if(tel.length() != 11){
				result = RemoteResult.failure("0001", "请传入正确的电话号码");
				return JSON.toJSONString(result);
			}

			User query = new User();
			query.setTel(tel);
			query.setYn(YnEnum.Normal.getKey());
			List<User> users = userService.selectEntryList(query);
			if (CollectionUtils.isNotEmpty(users)) {
				LOGGER.info("该用户已经注册，手机号为【{}】", tel);
				result = RemoteResult.failure("0001", "该手机号已经注册");
				return JSON.toJSONString(result);
			}

			// 判断出入的validCode 是否是发送时的code
			UserValid condtion = new UserValid();
			condtion.setTel(tel);
			condtion.setValidType(SendSMSTypeEnum.REGIST_USER.getKey());
			condtion.setYn(YnEnum.Normal.getKey());
			List<UserValid> valids = userValidService.selectEntryList(condtion);

			if (CollectionUtils.isNotEmpty(valids)) {
				boolean res = false;
				for (UserValid userValid : valids) {
					res = validUserRegist(userValid, validCode, validDate);
					if (res) {
						break;
					}
				}
				if (res) {
					// 验证成功向数据库写入一条默认数据
					User defaultUser = new User();
					defaultUser.setTel(tel);
					defaultUser.setPassword(password);
					defaultUser.setNickName(RandomStrUtils.getUniqueString(6));
					defaultUser.setYn(YnEnum.Normal.getKey());

					UserAuths userAuths = new UserAuths();
					userAuths.setIdentityType(UserAuths.IDENTITY_RYPE_TEL);
					userAuths.setIdentifier(tel);
					userAuths.setCredential(password);
					userAuths.setVerified(1);// 已验证
					userAuths.setYn(YnEnum.Normal.getKey());

					result = userService.registUser(defaultUser, userAuths);
					
					
				} else {
					result = RemoteResult.failure("0002", "验证失败,验证码失效，请重新获取验证码");
				}
			} else {
				result = RemoteResult.failure("0001", "验证失败,验证码正确，或验证码已失效");
			}
		} catch (Exception e) {
			LOGGER.error("失败:" + e.getMessage(), e);
			result = RemoteResult.failure("0001", "操作失败:" + e.getMessage());
		}
		return JSON.toJSONString(result);
	}

	/**
	 * 验证调用接口的时间是否超时
	 * 
	 * @param userValid
	 * @param validCode
	 * @param validDate
	 * @return
	 */
	private boolean validUserRegist(UserValid userValid, String validCode, Long validDate) {
		boolean result = false;
		long from = userValid.getValidDate().getTime() + 60 * 1000;
		long to = validDate;
		if (from > 0 && from > to && validCode.equals(userValid.getValidCode())) {
			result = true;

			UserValid valid = new UserValid();
			valid.setId(userValid.getId());
			valid.setYn(YnEnum.Deleted.getKey());
			userValidService.updateByKey(valid);
		}
		return result;
	}

	/**
	 * 
	 * 获取验证码
	 * 
	 * @param tel
	 * @param validDate
	 * @return
	 */
	@RequestLimit
	@RequestMapping(value = "/getValidCode", method = {RequestMethod.POST})
	@ApiOperation(value = "注册获取验证码", httpMethod = "POST", response = String.class, notes = "注册获取验证码")
	@ApiImplicitParams({
			@ApiImplicitParam(paramType="query", name = "tel", value = "用户电话", required = true, dataType = "String"),
			@ApiImplicitParam(paramType="query", name = "validDate", value = "验证时间", required = true, dataType = "Long"),

	})
	public @ResponseBody String getValidCode(HttpServletRequest request ,@RequestParam(value = "tel", required = true) String tel,@RequestParam(value = "validDate" , required = true) Long validDate) {
		RemoteResult result = null;
		try {
			if (StringUtils.isEmpty(tel) || (validDate == null || validDate <= 0)) {
				result = RemoteResult.failure("0001", "传入参数错误");
				return JSON.toJSONString(result);
			}

			if(tel.length() != 11){
				result = RemoteResult.failure("0001", "请传入正确的电话号码");
				return JSON.toJSONString(result);
			}

			result = taoBaoSmsService.sendValidSMS(tel, validDate, SendSMSTypeEnum.REGIST_USER.getKey());
		} catch (Exception e) {
			LOGGER.error("失败:" + e.getMessage(), e);
			result = RemoteResult.failure("0001", "操作失败:" + e.getMessage());
		}
		return JSON.toJSONString(result);
	}

	@RequestLimit
	@RequestMapping(value = "/validTelIsExist", method = {RequestMethod.POST})
	@ApiOperation(value = "验证手机是否注册", httpMethod = "POST", response = String.class, notes = "验证手机是否注册")
	@ApiImplicitParams({
			@ApiImplicitParam(paramType="query", name = "tel", value = "用户电话", required = true, dataType = "String")
	})
	public @ResponseBody String validTelIsExist(HttpServletRequest request ,@RequestParam(value = "tel", required = true) String tel) {
		RemoteResult result = null;
		try {
			if (StringUtils.isEmpty(tel)) {
				result = RemoteResult.failure("0001", "传入参数错误");
				return JSON.toJSONString(result);
			}
			if(tel.length() != 11){
				result = RemoteResult.failure("0001", "请传入正确的电话号码");
				return JSON.toJSONString(result);
			}

			User user = new User();
			user.setTel(tel);
			user.setYn(YnEnum.Normal.getKey());
			List<User> users = userService.selectEntryList(user);
			if(CollectionUtils.isEmpty(users)){
				result = RemoteResult.failure("1000", "此电话号码未注册");
				return JSON.toJSONString(result);
			}else{
				result = RemoteResult.failure("0001", "此电话号码已注册");
				return JSON.toJSONString(result);
			}
		} catch (Exception e) {
			LOGGER.error("失败:" + e.getMessage(), e);
			result = RemoteResult.failure("0001", "操作失败:" + e.getMessage());
		}
		return JSON.toJSONString(result);
	}


	/**
	 * 
	 * 用户登录接口
	 * 
	 * @param userAuths
	 * @return
	 */
	/*@RequestMapping(value = "/login", method = {RequestMethod.POST})
	public @ResponseBody String login(UserAuths userAuths, String nickName) {
		RemoteResult result = null;
		try {
			if (null == userAuths || userAuths.getIdentityType() == null) {
				LOGGER.error("调用login 传入的参数错误 登陆类型【{}】", userAuths.getIdentityType());
				result = RemoteResult.failure(BusinessCode.PARAMETERS_ERROR.getCode(),
						BusinessCode.PARAMETERS_ERROR.getValue());
				return JSON.toJSONString(result);
			}
			result = userService.login(userAuths, nickName);
		} catch (Exception e) {
			LOGGER.error("失败:" + e.getMessage(), e);
			result = RemoteResult.failure("0001", "操作失败:" + e.getMessage());
		}
		return JSON.toJSONString(result);
	}*/

	/*@RequestMapping(value = "/bindTel", method = {RequestMethod.POST})
	public @ResponseBody String bindTel(User user) {
		RemoteResult result = null;
		if(null == user || user.getId() == null || user.getTel() == null){
			LOGGER.info("调用bindTel 传入的参数错误");
			result = RemoteResult.failure("0001", "传入参数错误");
			return JSON.toJSONString(result);
		}
		result = userService.bindTel(user);
		return JSON.toJSONString(result);
	}*/
	
	/*@RequestMapping(value = "/logout", method = {RequestMethod.POST})
	public @ResponseBody String logout(User user) {
		RemoteResult result = null;

		return null;
	}*/

	/*@RequestMapping(value = "/getNearByUser", method = {RequestMethod.POST})
	public @ResponseBody String getNearByUser(Page<User> page, UserLogin userLogin) {
		RemoteResult result = null;
		try {
			if (null == userLogin || userLogin.getUserId() == null || userLogin.getLatitude() == null
					|| userLogin.getLongitude() == null) {
				LOGGER.info("调用getNearByUser 传入的参数错误");
				result = RemoteResult.failure("0001", "传入参数错误");
				return JSON.toJSONString(result);
			}

			result = userService.getNearByUser(page, userLogin);
		} catch (Exception e) {
			LOGGER.error("失败:" + e.getMessage(), e);
			result = RemoteResult.failure("0001", "操作失败:" + e.getMessage());
		}
		return JSON.toJSONString(result);
	}*/
	
	/*@RequestMapping(value = "/applyFriend", method = {RequestMethod.POST})
	public @ResponseBody String applyFriend(UserFriends userFriends) {
		RemoteResult result = null;
		try {
			if (null == userFriends || userFriends.getFromUserId() == null || userFriends.getToUserId() == null) {
				LOGGER.info("调用applyFriend 传入的参数错误");
				result = RemoteResult.failure("0001", "传入参数错误");
				return JSON.toJSONString(result);
			}
			result = userFriendsService.applyFriend(userFriends);
		} catch (Exception e) {
			LOGGER.error("失败:" + e.getMessage(), e);
			result = RemoteResult.failure("0001", "操作失败:" + e.getMessage());
		}
		return JSON.toJSONString(result);
	}*/
	
	/*@RequestMapping(value = "/getPendingFriends", method = {RequestMethod.POST})
	public @ResponseBody String getPendingFriends(Page<User> page, Long userId) {
		RemoteResult result = null;
		try {
			if (null == userId || userId < 0) {
				LOGGER.info("调用getPendingFriends 传入的参数错误");
				result = RemoteResult.failure("0001", "传入参数错误");
				return JSON.toJSONString(result);
			}
			result = userFriendsService.getPendingFriendList(page, userId);
		} catch (Exception e) {
			LOGGER.error("失败:" + e.getMessage(), e);
			result = RemoteResult.failure("0001", "操作失败:" + e.getMessage());
		}
		return JSON.toJSONString(result);
	}*/
	
	
	/*@RequestMapping(value = "/getFriendsList", method = {RequestMethod.POST})
	public @ResponseBody String getFriendsList(Page<User> page, Long userId) {
		RemoteResult result = null;
		try {
			if (null == userId || userId < 0) {
				LOGGER.info("调用getFriendsList 传入的参数错误");
				result = RemoteResult.failure("0001", "传入参数错误");
				return JSON.toJSONString(result);
			}
			result = userFriendsService.getFriendsList(page, userId);
			
		} catch (Exception e) {
			LOGGER.error("失败:" + e.getMessage(), e);
			result = RemoteResult.failure("0001", "操作失败:" + e.getMessage());
		}
		return JSON.toJSONString(result);
	}*/
	
	/*@RequestMapping(value = "/queryFriends", method = {RequestMethod.POST})
	public @ResponseBody String queryFriends(Page<User> page, Long userId, String query) {
		RemoteResult result = null;
		try {
			if (StringUtils.isEmpty(query)) {
				LOGGER.info("调用queryFriends 传入的参数错误");
				result = RemoteResult.failure("0001", "传入参数错误");
				return JSON.toJSONString(result);
			}
			result = userFriendsService.queryFriends(page, userId, query);
		} catch (Exception e) {
			LOGGER.error("失败:" + e.getMessage(), e);
			result = RemoteResult.failure("0001", "操作失败:" + e.getMessage());
		}
		return JSON.toJSONString(result);
	}*/
	
	/*@RequestMapping(value = "/deleteFriends", method = {RequestMethod.POST})
	public @ResponseBody String deleteFriends(UserFriends userFriends) {
		RemoteResult result = null;
		try {
			if (null == userFriends.getFromUserId() || userFriends.getFromUserId() < 0 || null == userFriends.getToUserId() || userFriends.getToUserId() < 0) {
				LOGGER.info("调用deleteFriends 传入的参数错误");
				result = RemoteResult.failure("0001", "传入参数错误");
				return JSON.toJSONString(result);
			}
			
			result = userFriendsService.deleteFriends(userFriends);
		} catch (Exception e) {
			LOGGER.error("失败:" + e.getMessage(), e);
			result = RemoteResult.failure("0001", "操作失败:" + e.getMessage());
		}
		return JSON.toJSONString(result);
	}*/
	
	
	/*@RequestMapping(value = "/agreeApplyFriends", method = {RequestMethod.POST})
	public @ResponseBody String agreeApplyFriends(UserFriends userFriends) {
		RemoteResult result = null;
		try {
			if (null == userFriends.getFromUserId() || userFriends.getFromUserId() < 0 || null == userFriends.getToUserId() || userFriends.getToUserId() < 0) {
				LOGGER.info("调用agreeApplyFriends 传入的参数错误");
				result = RemoteResult.failure("0001", "传入参数错误");
				return JSON.toJSONString(result);
			}
			
			result = userFriendsService.agreeApplyFriends(userFriends);
		} catch (Exception e) {
			LOGGER.error("失败:" + e.getMessage(), e);
			result = RemoteResult.failure("0001", "操作失败:" + e.getMessage());
		}
		return JSON.toJSONString(result);
	}*/
	
	/*@RequestMapping(value = "/refuseInvitation", method = { RequestMethod.POST })
	public @ResponseBody String refuseInvitation(UserFriends userFriends) {
		RemoteResult result = null;
		try {
			if (null == userFriends.getFromUserId() || userFriends.getFromUserId() < 0 || null == userFriends.getToUserId() || userFriends.getToUserId() < 0) {
				LOGGER.info("调用refuseInvitation 传入的参数错误");
				result = RemoteResult.failure("0001", "传入参数错误");
				return JSON.toJSONString(result);
			}
			
			result = userFriendsService.refuseInvitation(userFriends);
		} catch (Exception e) {
			LOGGER.error("失败:" + e.getMessage(), e);
			result = RemoteResult.failure("0001", "操作失败:" + e.getMessage());
		}
		return JSON.toJSONString(result);
	}
	*/
	/*@RequestMapping(value = "/isFriend", method = {  RequestMethod.POST })
	public @ResponseBody String isFriend(UserFriends userFriends) {
		RemoteResult result = null;
		try {
			if (null == userFriends.getFromUserId() || userFriends.getFromUserId() < 0 || null == userFriends.getToUserId() || userFriends.getToUserId() < 0) {
				LOGGER.info("调用isFriend 传入的参数错误");
				result = RemoteResult.failure("0001", "传入参数错误");
				return JSON.toJSONString(result);
			}
			
			int res = userFriendsService.check2PIsFriend(userFriends);
			if(res > 0){
				result = RemoteResult.result(BusinessCode.IS_FRIEND,null);
			}else {
				result = RemoteResult.result(BusinessCode.NO_FRIEND,null);
			}
		} catch (Exception e) {
			LOGGER.error("失败:" + e.getMessage(), e);
			result = RemoteResult.failure("0001", "操作失败:" + e.getMessage());
		}
		return JSON.toJSONString(result);
	}*/
	
	
	/*@RequestMapping(value = "/getUserInfo", method = {  RequestMethod.POST })
	public @ResponseBody String getUserInfoById(User user) {
		RemoteResult result = null;
		try {
			if (null == user.getId() && StringUtils.isEmpty(user.getTel()) ) {
				LOGGER.info("调用getUserInfo 传入的参数错误");
				result = RemoteResult.failure("0001", "传入参数错误");
				return JSON.toJSONString(result);
			}
			List<User> users = userService.selectEntryList(user);
			if(CollectionUtils.isNotEmpty(users)){
				User us = users.get(0);
				us.setAvatarUrl(IMAGEPREFIX + us.getAvatarUrl());
				result = RemoteResult.success(us);
			}else {
				result = RemoteResult.result(BusinessCode.NO_RESULTS,null);
			}
		} catch (Exception e) {
			LOGGER.error("失败:" + e.getMessage(), e);
			result = RemoteResult.failure("0001", "操作失败:" + e.getMessage());
		}
		return JSON.toJSONString(result);
	}*/
}
