package com.frame.web.controller;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.frame.domain.User;
import com.frame.domain.UserAuths;
import com.frame.domain.UserValid;
import com.frame.domain.base.YnEnum;
import com.frame.domain.common.RemoteResult;
import com.frame.domain.cusAnnotion.RequestLimit;
import com.frame.domain.enums.BusinessCode;
import com.frame.domain.enums.SendSMSTypeEnum;
import com.frame.domain.vo.UserAuthsParam;
import com.frame.service.*;
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
import org.springframework.web.bind.annotation.*;

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

	@Resource
	private UserAuthsService userAuthsService;
	

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
	 * 忘记密码
	 * @param tel
	 * @param password
	 * @param validCode
	 * @return
	 */
	@RequestLimit
	@RequestMapping(value = "/forgetPwd", method = {RequestMethod.POST})
	@ApiOperation(value = "忘记密码", httpMethod = "POST", response = String.class, notes = "忘记密码")
	@ApiImplicitParams({
			@ApiImplicitParam(paramType="query", name = "tel", value = "用户电话", required = true, dataType = "String"),
			@ApiImplicitParam(paramType="query", name = "password", value = "用户密码", required = true, dataType = "String"),
			@ApiImplicitParam(paramType="query", name = "validCode", value = "验证码", required = true, dataType = "String")
	})
	public @ResponseBody String forgetPwd(HttpServletRequest request , @RequestParam(value = "tel", required = true) String tel,
										   @RequestParam(value = "password", required = true) String password,
										   @RequestParam(value = "validCode", required = true) String validCode) {
		RemoteResult result = null;
		try {
			if (StringUtils.isEmpty(tel) || StringUtils.isEmpty(password) || StringUtils.isEmpty(validCode)) {
				LOGGER.error("调用registUser 传入的参数错误 tel【{}】,密码[{}],验证码[{}]", tel, password, validCode);
				result = RemoteResult.failure(BusinessCode.PARAMETERS_ERROR.getCode(), BusinessCode.PARAMETERS_ERROR.getValue());
				return JSON.toJSONString(result);
				// 判断用户是否已经注册
			}

			if(tel.length() != 11){
				result = RemoteResult.failure(BusinessCode.PARAMETERS_ERROR.getCode(), BusinessCode.PARAMETERS_ERROR.getValue());
				return JSON.toJSONString(result);
			}

			boolean existTel = IsExistTel(tel);
			if (!existTel) {
				LOGGER.warn("未找到此电话号码，手机号为[{}]", tel);
				result = RemoteResult.failure(BusinessCode.FAILED.getCode(), "未找到此电话号码" + tel);
				return JSON.toJSONString(result);
			}

			// 判断出入的validCode 是否是发送时的code
			boolean res = isCorrectValidCode(tel, validCode, SendSMSTypeEnum.FORGET_PWD.getKey());
			if(res){
				if (res) {
					UserAuths con = new UserAuths();
					con.setIdentityType(UserAuths.IDENTITY_RYPE_TEL);
					con.setIdentifier(tel);
					con.setYn(YnEnum.Normal.getKey());
					List<UserAuths> authses = userAuthsService.selectEntryList(con);
					if(CollectionUtils.isEmpty(authses)){
						result = RemoteResult.failure(BusinessCode.FAILED.getCode(), "该用户已经注册");
						return JSON.toJSONString(result);
					}

					// 验证成功向数据库写入一条默认数据
					User defaultUser = new User();
					defaultUser.setId(authses.get(0).getUserId());

					UserAuths userAuths = new UserAuths();
					userAuths.setUserId(authses.get(0).getUserId());
					userAuths.setIdentityType(UserAuths.IDENTITY_RYPE_TEL);
					userAuths.setIdentifier(tel);
					userAuths.setCredential(password);
					userAuths.setVerified(1);// 已验证
					userAuths.setYn(YnEnum.Normal.getKey());

					result = userService.registOrUpdateUser(defaultUser, userAuths);
				} else {
					result = RemoteResult.failure("0002", "验证失败,验证码失效，请重新获取验证码");
				}
			}

		}catch (Exception e){
			LOGGER.error("失败:" + e.getMessage(), e);
			result = RemoteResult.failure("0001", "操作失败:" + e.getMessage());
		}
		return JSON.toJSONString(result);
	}


	/**
	 * 
	 * 用户注册接口
	 * 
	 * @param tel
	 * @param password
	 * @param validCode
	 * @return
	 */
	@RequestLimit
	@RequestMapping(value = "/regist", method = {RequestMethod.POST})
	@ApiOperation(value = "注册用户", httpMethod = "POST", response = String.class, notes = "注册用户")
	@ApiImplicitParams({
			@ApiImplicitParam(paramType="query", name = "tel", value = "用户电话", required = true, dataType = "String"),
			@ApiImplicitParam(paramType="query", name = "password", value = "用户密码", required = true, dataType = "String"),
			@ApiImplicitParam(paramType="query", name = "validCode", value = "验证码", required = true, dataType = "String"),
			@ApiImplicitParam(paramType="query", name = "inviteCode", value = "邀请码", required = false, dataType = "String"),


	})
	public @ResponseBody String registUser(HttpServletRequest request , @RequestParam(value = "tel", required = true) String tel,
										   @RequestParam(value = "password", required = true) String password,
										   @RequestParam(value = "validCode", required = true) String validCode,
										   @RequestParam(value = "inviteCode", required = false) String inviteCode) {
		RemoteResult result = null;
		try {

			if (StringUtils.isEmpty(tel) || StringUtils.isEmpty(password) || StringUtils.isEmpty(validCode)) {
				LOGGER.error("调用registUser 传入的参数错误 tel【{}】,密码[{}],验证码[{}]", tel, password, validCode);
				result = RemoteResult.failure(BusinessCode.PARAMETERS_ERROR.getCode(), BusinessCode.PARAMETERS_ERROR.getValue());
				return JSON.toJSONString(result);
				// 判断用户是否已经注册
			}

			if(tel.length() != 11){
				result = RemoteResult.failure(BusinessCode.PARAMETERS_ERROR.getCode(), BusinessCode.PARAMETERS_ERROR.getValue());
				return JSON.toJSONString(result);
			}

			boolean existTel = IsExistTel(tel);
			if (existTel) {
				LOGGER.info("该用户已经注册，手机号为【{}】", tel);
				result = RemoteResult.failure(BusinessCode.FAILED.getCode(), "该用户已经注册");
				return JSON.toJSONString(result);
			}

			// 判断出入的validCode 是否是发送时的code
			boolean res = isCorrectValidCode(tel,validCode,SendSMSTypeEnum.REGIST_USER.getKey());
			if (res) {
				// 验证成功向数据库写入一条默认数据
				User defaultUser = new User();
				defaultUser.setTel(tel);
				defaultUser.setYn(YnEnum.Normal.getKey());

				UserAuths userAuths = new UserAuths();
				userAuths.setIdentityType(UserAuths.IDENTITY_RYPE_TEL);
				userAuths.setIdentifier(tel);
				userAuths.setCredential(password);
				userAuths.setVerified(1);// 已验证
				userAuths.setYn(YnEnum.Normal.getKey());

				result = userService.registOrUpdateUser(defaultUser, userAuths);
			} else {
				result = RemoteResult.failure("0002", "验证失败,验证码失效，请重新获取验证码");
			}
		} catch (Exception e) {
			LOGGER.error("失败:" + e.getMessage(), e);
			result = RemoteResult.failure("0001", "操作失败:" + e.getMessage());
		}
		return JSON.toJSONString(result);
	}

	private boolean isCorrectValidCode(String tel, String validCode, Integer validType) {
		boolean isValid = false;

		UserValid condtion = new UserValid();
		condtion.setTel(tel);
		condtion.setValidType(validType);
		List<UserValid> valids = userValidService.selectEntryList(condtion);
		if (CollectionUtils.isNotEmpty(valids)) {
			for (UserValid userValid : valids) {
				isValid = validUserRegist(userValid, validCode, System.currentTimeMillis());
				if (isValid) {
					break;
				}
			}
		}else{
			LOGGER.warn("未找到tel={},validCode={}，注册的验证码", tel, validCode);
		}

		return isValid;
	}

	private boolean IsExistTel(String tel) {
		boolean result = false;

		User query = new User();
		query.setTel(tel);
		query.setYn(YnEnum.Normal.getKey());
		List<User> users = userService.selectEntryList(query);
		if (CollectionUtils.isNotEmpty(users)) {
			LOGGER.info("该用户已经注册，手机号为【{}】", tel);
			result = true;
		}
		return result;
	}

	/**
	 *
	 * 获取验证码
	 *
	 * @param tel
	 * @return
	 */
	@RequestLimit
	@RequestMapping(value = "/getValidCode", method = {RequestMethod.POST})
	@ApiOperation(value = "注册获取验证		码", httpMethod = "POST", response = String.class, notes = "注册获取验证码")
	@ApiImplicitParams({
			@ApiImplicitParam(paramType="query", name = "tel", value = "用户电话", required = true, dataType = "String"),

	})
	public @ResponseBody String getValidCode(HttpServletRequest request ,@RequestParam(value = "tel", required = true) String tel) {
		RemoteResult result = null;
		try {
			if (StringUtils.isEmpty(tel) ) {
				result = RemoteResult.failure("0001", "传入参数错误");
				return JSON.toJSONString(result);
			}

			if(tel.length() != 11){
				result = RemoteResult.failure("0001", "请传入正确的电话号码");
				return JSON.toJSONString(result);
			}

			result = taoBaoSmsService.sendValidSMS(tel, System.currentTimeMillis(), SendSMSTypeEnum.REGIST_USER.getKey());
		} catch (Exception e) {
			LOGGER.error("失败:" + e.getMessage(), e);
			result = RemoteResult.failure("0001", "操作失败:" + e.getMessage());
		}
		return JSON.toJSONString(result);
	}


	/**
	 *
	 * 获取忘记密码验证码
	 *
	 * @param tel
	 * @return
	 */
	@RequestLimit
	@RequestMapping(value = "/getValidCodeforget", method = {RequestMethod.POST})
	@ApiOperation(value = "获取忘记密码验证码", httpMethod = "POST", response = String.class, notes = "获取忘记密码验证码")
	@ApiImplicitParams({
			@ApiImplicitParam(paramType="query", name = "tel", value = "用户电话", required = true, dataType = "String"),
	})
	public @ResponseBody String getValidCodeforgetPassword(HttpServletRequest request ,@RequestParam(value = "tel", required = true) String tel) {
		RemoteResult result = null;
		try {
			if (StringUtils.isEmpty(tel) ) {
				result = RemoteResult.failure("0001", "传入参数错误");
				return JSON.toJSONString(result);
			}

			if(tel.length() != 11){
				result = RemoteResult.failure("0001", "请传入正确的电话号码");
				return JSON.toJSONString(result);
			}

			result = taoBaoSmsService.sendValidSMS(tel, System.currentTimeMillis(), SendSMSTypeEnum.FORGET_PWD.getKey());
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

			JSONObject object = new JSONObject();
			if(CollectionUtils.isEmpty(users)){
				object.put("registered",BusinessCode.IS_EXIST_NO.getKey());
				result = RemoteResult.failure(BusinessCode.SUCCESS.getCode(), "此电话号码未注册",object);
				return JSON.toJSONString(result);
			}else{
				object.put("registered",BusinessCode.IS_EXIST_YES.getKey());
				result = RemoteResult.failure(BusinessCode.SUCCESS.getCode(), "此电话号码已注册", object);
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
	@RequestMapping(value = "/login", method = {RequestMethod.POST})
	@ApiOperation(value = "用户的登录借口", httpMethod = "POST", response = String.class, notes = "用户的登录借口")
	public @ResponseBody String login(@RequestBody UserAuthsParam userAuths, String nickName) {
		RemoteResult result = null;
		try {
			if (null == userAuths || userAuths.getIdentityType() == null) {
				LOGGER.error("调用login 传入的参数错误 登陆类型【{}】", userAuths.getIdentityType());
				result = RemoteResult.failure(BusinessCode.PARAMETERS_ERROR.getCode(),
						BusinessCode.PARAMETERS_ERROR.getValue());
				return JSON.toJSONString(result);
			}

			UserAuths auths = new UserAuths();
			auths.setIdentifier(userAuths.getIdentifier());
			auths.setIdentityType(userAuths.getIdentityType());
			auths.setCredential(userAuths.getCredential());

			result = userService.login(auths, nickName);
		} catch (Exception e) {
			LOGGER.error("失败:" + e.getMessage(), e);
			result = RemoteResult.failure("0001", "操作失败:" + e.getMessage());
		}
		return JSON.toJSONString(result);
	}



	/**
	 *
	 * change pwd
	 *
	 * @param id
	 * @return
	 */
	@RequestMapping(value = "/changePwd", method = {RequestMethod.POST})
	@ApiOperation(value = "修改密码", httpMethod = "POST", response = String.class, notes = "修改密码")
	@ApiImplicitParams({
			@ApiImplicitParam(paramType="query", name = "id", value = "user ID", required = true, dataType = "Integer"),
			@ApiImplicitParam(paramType="query", name = "oldPwd", value = "旧密码", required = true, dataType = "String"),
			@ApiImplicitParam(paramType="query", name = "newPwd", value = "新密码", required = true, dataType = "String"),
	})
	public @ResponseBody String changePwd(HttpServletRequest request ,@RequestParam(value = "id", required = true) Integer id,
										  @RequestParam(value = "oldPwd", required = true) String oldPwd,
										  @RequestParam(value = "newPwd", required = true) String newPwd) {
		RemoteResult result = null;
		try {
			if (null == id || StringUtils.isEmpty(oldPwd) || StringUtils.isEmpty(newPwd)){
				LOGGER.error("changePwd 传入的参数错误 id【{}】,oldPwd【{}】,newPwd【{}】", id, oldPwd, newPwd);
				result = RemoteResult.failure(BusinessCode.PARAMETERS_ERROR.getCode(),
						BusinessCode.PARAMETERS_ERROR.getValue());
				return JSON.toJSONString(result);
			}
			UserAuths condition = new UserAuths();
			condition.setUserId(id);
			condition.setIdentityType(UserAuths.IDENTITY_RYPE_TEL);
			condition.setCredential(oldPwd);
			List<UserAuths> authses = userAuthsService.selectEntryList(condition);
			if(CollectionUtils.isEmpty(authses)){
				result = RemoteResult.failure(BusinessCode.FAILED.getCode(), "输入的老密码不正确");
				return JSON.toJSONString(result);
			}


			UserAuths auths = new UserAuths();
			auths.setId(authses.get(0).getId());
			auths.setCredential(newPwd);
			int res = userAuthsService.updateByKey(auths);
			if(res > 0){
				result = RemoteResult.success("修改密码成功");
				return JSON.toJSONString(result);
			}else{
				result = RemoteResult.failure("修改密码失败");
				return JSON.toJSONString(result);
			}

		} catch (Exception e) {
			LOGGER.error("失败:" + e.getMessage(), e);
			result = RemoteResult.failure("0001", "操作失败:" + e.getMessage());
		}
		return JSON.toJSONString(result);
	}

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
