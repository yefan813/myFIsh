package com.frame.web.controller;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.frame.domain.User;
import com.frame.domain.UserAuths;
import com.frame.domain.UserFriends;
import com.frame.domain.UserValid;
import com.frame.domain.base.YnEnum;
import com.frame.domain.common.Page;
import com.frame.domain.common.RemoteResult;
import com.frame.domain.enums.BusinessCode;
import com.frame.domain.enums.CookieEnum;
import com.frame.domain.enums.SendSMSTypeEnum;
import com.frame.domain.vo.UserVO;
import com.frame.service.*;
import com.frame.service.utils.AESUtils;
import com.frame.web.entity.request.*;
import com.frame.web.utils.CookieUtils;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.apache.commons.beanutils.BeanUtils;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.Resource;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Date;
import java.util.List;

@Controller
@RequestMapping(value = "/user")
@Api(value = "user", description = "用户相关接口")
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

    @Value("${COOKIE_KEY}")
    private String COOKIE_KEY;


    /**
     * 编辑用户信息接口
     *
     * @return
     */
    @RequestMapping(value = "/editUserInfo", method = {RequestMethod.POST})
    @ApiOperation(value = "编辑用户信息接口", httpMethod = "POST", response = String.class, notes = "编辑用户信息接口")

    public @ResponseBody
    String editUserInfo(HttpServletRequest request, @RequestBody UserInfoParam userInfoParam) {
        RemoteResult result = null;
        try {
            if (null == userInfoParam) {
                LOGGER.info("调用editUserInfo 传入的参数错误,参数[{}]", JSON.toJSONString(userInfoParam));
                result = RemoteResult.failure("0001", "传入参数错误");
                return JSON.toJSONString(result);
            }
            Long userId = getLoginId();
            if(userId == null){
                LOGGER.error(BusinessCode.NO_LOGIN.getValue());
                result = RemoteResult.failure(BusinessCode.NO_LOGIN.getCode(),BusinessCode.NO_LOGIN.getValue());
                return JSON.toJSONString(result);
            }


            User user = new User();
            user.setId(userId.intValue());
            user.setAvatarUrl(userInfoParam.getAvatarUrl());
            user.setNickName(userInfoParam.getNikeName());
            user.setSex(userInfoParam.getSex());
            if (null != userInfoParam.getBirthday()) {
                user.setBirthday(new Date(userInfoParam.getBirthday()));
            }
            user.setAddress(userInfoParam.getAddress());

            result = userService.editUserInfo(user);
        } catch (Exception e) {
            LOGGER.error("失败:" + e.getMessage(), e);
            result = RemoteResult.failure("0001", "操作失败:" + e.getMessage());
        }
        return JSON.toJSONString(result);
    }

    /**
     * 忘记密码
     *
     * @return
     */
//	@RequestLimit
    @RequestMapping(value = "/forgetPwd", method = {RequestMethod.POST})
    @ApiOperation(value = "忘记密码", httpMethod = "POST", response = String.class, notes = "忘记密码")
    public @ResponseBody
    String forgetPwd(HttpServletRequest request, @RequestBody ForgetPWDParam param) {
        RemoteResult result = null;
        try {
            if (null == param || StringUtils.isEmpty(param.getTel()) || StringUtils.isEmpty(param.getPassword()) || StringUtils.isEmpty(param.getValidCode())) {
                LOGGER.error("调用registUser 传入的参数错误 tel【{}】,密码[{}],验证码[{}]", JSON.toJSONString(param));
                result = RemoteResult.failure(BusinessCode.PARAMETERS_ERROR.getCode(), BusinessCode.PARAMETERS_ERROR.getValue());
                return JSON.toJSONString(result);
                // 判断用户是否已经注册
            }

            if (param.getTel().length() != 11) {
                result = RemoteResult.failure(BusinessCode.PARAMETERS_ERROR.getCode(), BusinessCode.PARAMETERS_ERROR.getValue());
                return JSON.toJSONString(result);
            }

            boolean existTel = IsExistTel(param.getTel());
            if (!existTel) {
                LOGGER.warn("未找到此电话号码，手机号为[{}]", param.getTel());
                result = RemoteResult.failure(BusinessCode.FAILED.getCode(), "未找到此电话号码" + param.getTel());
                return JSON.toJSONString(result);
            }

            // 判断出入的validCode 是否是发送时的code
            boolean res = isCorrectValidCode(param.getTel(), param.getValidCode(), SendSMSTypeEnum.FORGET_PWD.getKey());

            if (!res) {
                LOGGER.warn("验证码失效，请重新获取, tel【{}】,密码[{}],验证码[{}]", JSON.toJSONString(param));
                result = RemoteResult.failure(BusinessCode.FAILED.getCode(), "验证码失效" + param.getTel());
                return JSON.toJSONString(result);
            }
            if (res) {
                UserAuths con = new UserAuths();
                con.setIdentityType(UserAuths.IDENTITY_RYPE_TEL);
                con.setIdentifier(param.getTel());
                con.setYn(YnEnum.Normal.getKey());
                List<UserAuths> authses = userAuthsService.selectEntryList(con);
                if (CollectionUtils.isEmpty(authses)) {
                    result = RemoteResult.failure(BusinessCode.FAILED.getCode(), "该用户已经注册");
                    return JSON.toJSONString(result);
                }

                // 验证成功向数据库写入一条默认数据
                User defaultUser = new User();
                defaultUser.setId(authses.get(0).getUserId());

                UserAuths userAuths = new UserAuths();
                userAuths.setId(authses.get(0).getId());
                userAuths.setUserId(authses.get(0).getUserId());
                userAuths.setIdentityType(UserAuths.IDENTITY_RYPE_TEL);
                userAuths.setIdentifier(param.getTel());
                userAuths.setCredential(param.getPassword());
                userAuths.setVerified(1);// 已验证
                userAuths.setYn(YnEnum.Normal.getKey());

                result = userService.registOrUpdateUser(defaultUser, userAuths);
                result.setMsg("重置密码成功");
            } else {
                result = RemoteResult.failure("0002", "验证失败,验证码失效，请重新获取验证码");
            }

        } catch (Exception e) {
            LOGGER.error("失败:" + e.getMessage(), e);
            result = RemoteResult.failure("0001", "操作失败:" + e.getMessage());
        }
        return JSON.toJSONString(result);
    }


    /**
     * 用户注册接口
     *
     * @return
     */
//	@RequestLimit
    @RequestMapping(value = "/regist", method = {RequestMethod.POST})
    @ApiOperation(value = "注册用户", httpMethod = "POST", response = String.class, notes = "注册用户")
    public @ResponseBody
    String registUser(HttpServletRequest request, @RequestBody UserRegistParam param) {
        RemoteResult result = null;
        try {

            if (null == param || StringUtils.isEmpty(param.getTel()) || StringUtils.isEmpty(param.getPassword()) || StringUtils.isEmpty(param.getPassword())) {
                LOGGER.error("调用registUser 传入的参数错误 tel【{}】,密码[{}],验证码[{}]", JSON.toJSONString(param));
                result = RemoteResult.failure(BusinessCode.PARAMETERS_ERROR.getCode(), BusinessCode.PARAMETERS_ERROR.getValue());
                return JSON.toJSONString(result);
                // 判断用户是否已经注册
            }

            if (param.getTel().length() != 11) {
                result = RemoteResult.failure(BusinessCode.PARAMETERS_ERROR.getCode(), BusinessCode.PARAMETERS_ERROR.getValue());
                return JSON.toJSONString(result);
            }

            boolean existTel = IsExistTel(param.getTel());
            if (existTel) {
                LOGGER.info("该用户已经注册，手机号为【{}】", param.getTel());
                result = RemoteResult.failure(BusinessCode.FAILED.getCode(), "该用户已经注册");
                return JSON.toJSONString(result);
            }

            // 判断出入的validCode 是否是发送时的code
            boolean res = isCorrectValidCode(param.getTel(), param.getValidCode(), SendSMSTypeEnum.REGIST_USER.getKey());
            if (res) {
                // 验证成功向数据库写入一条默认数据
                User defaultUser = new User();
                defaultUser.setTel(param.getTel());
                defaultUser.setLevel(0);
                defaultUser.setPoint(0l);
                defaultUser.setYn(YnEnum.Normal.getKey());

                UserAuths userAuths = new UserAuths();
                userAuths.setIdentityType(UserAuths.IDENTITY_RYPE_TEL);
                userAuths.setIdentifier(param.getTel());
                userAuths.setCredential(param.getPassword());
                userAuths.setVerified(1);// 已验证
                userAuths.setYn(YnEnum.Normal.getKey());

                result = userService.registOrUpdateUser(defaultUser, userAuths);
                result.setMsg("注册成功");
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
        } else {
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
     * 获取验证码
     *
     * @param param
     * @return
     */
//	@RequestLimit
    @RequestMapping(value = "/getValidCode", method = {RequestMethod.POST})
    @ApiOperation(value = "注册获取验证		码", httpMethod = "POST", response = String.class, notes = "注册获取验证码")
    public @ResponseBody
    String getValidCode(HttpServletRequest request, @RequestBody ValidCodeParam param) {
        RemoteResult result = null;
        try {
            if (param == null || StringUtils.isEmpty(param.getTel())) {
                result = RemoteResult.failure("0001", "传入参数错误");
                return JSON.toJSONString(result);
            }

            if (param.getTel().length() != 11) {
                result = RemoteResult.failure("0001", "请传入正确的电话号码");
                return JSON.toJSONString(result);
            }

            boolean existTel = IsExistTel(param.getTel());
            if (existTel) {
                LOGGER.info("该用户已经注册，手机号为【{}】", param.getTel());
                result = RemoteResult.failure(BusinessCode.FAILED.getCode(), "该用户已经注册");
                return JSON.toJSONString(result);
            }

            result = taoBaoSmsService.sendValidSMS(param.getTel(), System.currentTimeMillis(), SendSMSTypeEnum.REGIST_USER.getKey());
        } catch (Exception e) {
            LOGGER.error("失败:" + e.getMessage(), e);
            result = RemoteResult.failure("0001", "操作失败:" + e.getMessage());
        }
        return JSON.toJSONString(result);
    }


    /**
     * 获取忘记密码验证码
     *
     * @param param
     * @return
     */
//	@RequestLimit
    @RequestMapping(value = "/getValidCodeforget", method = {RequestMethod.POST})
    @ApiOperation(value = "获取忘记密码验证码", httpMethod = "POST", response = String.class, notes = "获取忘记密码验证码")
    public @ResponseBody
    String getValidCodeforgetPassword(HttpServletRequest request, @RequestBody ValidCodeParam param) {
        RemoteResult result = null;
        try {
            if (param == null || StringUtils.isEmpty(param.getTel())) {
                result = RemoteResult.failure("0001", "传入参数错误");
                return JSON.toJSONString(result);
            }

            if (param.getTel().length() != 11) {
                result = RemoteResult.failure("0001", "请传入正确的电话号码");
                return JSON.toJSONString(result);
            }

            boolean existTel = IsExistTel(param.getTel());
            if (!existTel) {
                LOGGER.info("该用户未注册，手机号为【{}】", param.getTel());
                result = RemoteResult.failure(BusinessCode.FAILED.getCode(), "该用户未注册");
                return JSON.toJSONString(result);
            }

            result = taoBaoSmsService.sendValidSMS(param.getTel(), System.currentTimeMillis(), SendSMSTypeEnum.FORGET_PWD.getKey());
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

    //	@RequestLimit
    @RequestMapping(value = "/validTelIsExist", method = {RequestMethod.POST})
    @ApiOperation(value = "验证手机是否注册", httpMethod = "POST", response = String.class, notes = "验证手机是否注册")
    public @ResponseBody
    String validTelIsExist(HttpServletRequest request, @RequestBody ValidCodeParam param) {
        RemoteResult result = null;
        try {
            if (param == null || StringUtils.isEmpty(param.getTel())) {
                result = RemoteResult.failure("0001", "传入参数错误");
                return JSON.toJSONString(result);
            }
            if (param.getTel().length() != 11) {
                result = RemoteResult.failure("0001", "请传入正确的电话号码");
                return JSON.toJSONString(result);
            }

            User user = new User();
            user.setTel(param.getTel());
            user.setYn(YnEnum.Normal.getKey());
            List<User> users = userService.selectEntryList(user);

            JSONObject object = new JSONObject();
            if (CollectionUtils.isEmpty(users)) {
                object.put("registered", BusinessCode.IS_EXIST_NO.getKey());
                result = RemoteResult.failure(BusinessCode.SUCCESS.getCode(), "此电话号码未注册", object);
                return JSON.toJSONString(result);
            } else {
                object.put("registered", BusinessCode.IS_EXIST_YES.getKey());
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
     * 用户的电话号码登录借口
     *
     * @return
     */
    @RequestMapping(value = "/login4Tel", method = {RequestMethod.POST})
    @ApiOperation(value = "用户的电话号码登录借口", httpMethod = "POST", response = String.class, notes = "用户的电话号码登录借口")
    public @ResponseBody
    String login4Tel(HttpServletRequest request, HttpServletResponse response, @RequestBody UserLoginParam loginParam) {
        RemoteResult result = null;
        try {
            if (StringUtils.isEmpty(loginParam.getTel()) || StringUtils.isEmpty(loginParam.getPassword())) {
                LOGGER.error("调用login 传入的参数错误 tel【{}】, password=[{}]", JSON.toJSONString(loginParam));
                result = RemoteResult.failure(BusinessCode.PARAMETERS_ERROR.getCode(),
                        BusinessCode.PARAMETERS_ERROR.getValue());
                return JSON.toJSONString(result);
            }

            UserAuths auths = new UserAuths();
            auths.setIdentifier(loginParam.getTel());
            auths.setIdentityType(UserAuths.IDENTITY_RYPE_TEL);
            auths.setCredential(loginParam.getPassword());

            result = userService.login4Tel(auths);
            if (null != request && RemoteResult.SUCCESS_CODE.equals(result.getCode())) {
                UserVO userVO = (UserVO) result.getData();
                setLoginTicket(request, response, userVO);
            }
        } catch (Exception e) {
            LOGGER.error("失败:" + e.getMessage(), e);
            result = RemoteResult.failure("0001", "操作失败:" + e.getMessage());
        }
        return JSON.toJSONString(result);
    }

    private void setLoginTicket(HttpServletRequest request, HttpServletResponse response, UserVO userVO) {
        String value = userVO.getId() + "##" + "account" + "##" + userVO.getNickName() + "##" + System.currentTimeMillis();
        String data =null;
        try {
            data = String.valueOf(AESUtils.encrypt(value, COOKIE_KEY));
        } catch (Exception e) {
            e.printStackTrace();
        }
        if(StringUtils.isBlank(data)){
            return;
        }
        Cookie cookie = new Cookie(CookieEnum.TICKET.getKey(), data);
        cookie.setDomain(request.getServerName());
        cookie.setMaxAge(259200000);
        response.addCookie(cookie);
    }


    /**
     * 第三方登录借口
     *
     * @return
     */
    @RequestMapping(value = "/login4ThirdPart", method = {RequestMethod.POST}, produces = "application/json;charset=UTF-8")
    @ApiOperation(value = "第三方登录借口", httpMethod = "POST", response = String.class, notes = "第三方登录借口")
    public @ResponseBody
    String login4ThirdPart(HttpServletRequest request, @RequestBody UserThirdPartLoginParam param) {
        RemoteResult result = null;
        try {
            if (null == param || null == param.getIdentityType()) {
                LOGGER.error("调用login 传入的参数错误 tel[{}]", param.getIdentityType());
                result = RemoteResult.failure(BusinessCode.PARAMETERS_ERROR.getCode(),
                        BusinessCode.PARAMETERS_ERROR.getValue());
                return JSON.toJSONString(result);
            }

            UserAuths auths = new UserAuths();
            auths.setIdentifier(param.getIdentifier());
            auths.setIdentityType(param.getIdentityType());
            auths.setCredential(param.getCredential());

            User user = new User();
            user.setAvatarUrl(param.getAvartar());
            user.setNickName(param.getNickName());
            user.setSex(param.getSex());
            if (null != param.getBirthday()) {
                user.setBirthday(new Date(param.getBirthday()));
            }

            result = userService.login4ThirdPart(auths, user);
        } catch (Exception e) {
            LOGGER.error("失败:" + e.getMessage(), e);
            result = RemoteResult.failure("0001", "操作失败:" + e.getMessage());
        }
        return JSON.toJSONString(result);
    }


    /**
     * change pwd
     *
     * @return
     */
    @RequestMapping(value = "/changePwd", method = {RequestMethod.POST})
    @ApiOperation(value = "修改密码", httpMethod = "POST", response = String.class, notes = "修改密码")
    public @ResponseBody
    String changePwd(HttpServletRequest request, @RequestBody ChangePWDParam param) {
        RemoteResult result = null;
        try {
            if (null == param || StringUtils.isEmpty(param.getOldPwd()) || StringUtils.isEmpty(param.getNewPwd())) {
                LOGGER.error("changePwd 传入的参数错误 id【{}】,oldPwd【{}】,newPwd【{}】", JSON.toJSONString(param));
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

            UserAuths condition = new UserAuths();
            condition.setUserId(userId.intValue());
            condition.setIdentityType(UserAuths.IDENTITY_RYPE_TEL);

            List<UserAuths> authses = userAuthsService.selectEntryList(condition);
            if (CollectionUtils.isEmpty(authses)) {
                result = RemoteResult.failure(BusinessCode.FAILED.getCode(), "第三方登录账号不能修改密码");
                return JSON.toJSONString(result);
            }

            condition.setCredential(param.getOldPwd());
            authses = userAuthsService.selectEntryList(condition);
            if (CollectionUtils.isEmpty(authses)) {
                result = RemoteResult.failure(BusinessCode.FAILED.getCode(), "输入的老密码不正确");
                return JSON.toJSONString(result);
            }


            UserAuths auths = new UserAuths();
            auths.setId(authses.get(0).getId());
            auths.setCredential(param.getNewPwd());
            int res = userAuthsService.updateByKey(auths);
            if (res > 0) {
                result = RemoteResult.success(null, "修改密码成功");
                return JSON.toJSONString(result);
            } else {
                result = RemoteResult.failure("修改密码失败");
                return JSON.toJSONString(result);
            }

        } catch (Exception e) {
            LOGGER.error("失败:" + e.getMessage(), e);
            result = RemoteResult.failure("0001", "操作失败:" + e.getMessage());
        }
        return JSON.toJSONString(result);
    }

    @RequestMapping(value = "/bindTel", method = {RequestMethod.POST})
    public @ResponseBody
    String bindTel(User user) {
        RemoteResult result = null;
        if (null == user || user.getId() == null || user.getTel() == null) {
            LOGGER.info("调用bindTel 传入的参数错误");
            result = RemoteResult.failure("0001", "传入参数错误");
            return JSON.toJSONString(result);
        }
        result = userService.bindTel(user);
        return JSON.toJSONString(result);
    }

    @RequestMapping(value = "/applyFriend", method = {RequestMethod.POST})
    public @ResponseBody
    String applyFriend(@RequestBody UserFriendParam param) {
        RemoteResult result = null;
        try {
            if (null == param || param.getFromUserId() == null || param.getToUserId() == null) {
                LOGGER.info("调用applyFriend 传入的参数错误");
                result = RemoteResult.failure("0001", "传入参数错误");
                return JSON.toJSONString(result);
            }
            UserFriends userFriends = new UserFriends();
            BeanUtils.copyProperties(userFriends, param);

            result = userFriendsService.applyFriend(userFriends);
        } catch (Exception e) {
            LOGGER.error("失败:" + e.getMessage(), e);
            result = RemoteResult.failure("0001", "操作失败:" + e.getMessage());
        }
        return JSON.toJSONString(result);
    }

    @RequestMapping(value = "/getFriendsList", method = {RequestMethod.POST})
    public @ResponseBody
    String getFriendsList(@RequestBody UserFriendsListParam param) {
        RemoteResult result = null;
        try {
            if (null == param || param.getUserId() < 0) {
                LOGGER.info("调用getFriendsList 传入的参数错误");
                result = RemoteResult.failure("0001", "传入参数错误");
                return JSON.toJSONString(result);
            }
            Page<User> page = new Page<>();
            page.setCurrentPage(param.getCurrentPage());
            page.setPageSize(param.getPageSize());

            result = userFriendsService.getFriendsList(page, param.getUserId());

        } catch (Exception e) {
            LOGGER.error("失败:" + e.getMessage(), e);
            result = RemoteResult.failure("0001", "操作失败:" + e.getMessage());
        }
        return JSON.toJSONString(result);
    }

    @RequestMapping(value = "/queryFriends", method = {RequestMethod.POST})
    public @ResponseBody
    String queryFriends(@RequestBody QueryFriendsParam param) {
        RemoteResult result = null;
        try {
            if (param == null || StringUtils.isEmpty(param.getNickName())) {
                LOGGER.info("调用queryFriends 传入的参数错误");
                result = RemoteResult.failure("0001", "传入参数错误");
                return JSON.toJSONString(result);
            }
            Page<User> page = new Page<>();
            page.setCurrentPage(param.getCurrentPage());
            page.setPageSize(param.getPageSize());

            result = userFriendsService.queryFriends(page, param.getUserId(), param.getNickName());
        } catch (Exception e) {
            LOGGER.error("失败:" + e.getMessage(), e);
            result = RemoteResult.failure("0001", "操作失败:" + e.getMessage());
        }
        return JSON.toJSONString(result);
    }

    @RequestMapping(value = "/deleteFriends", method = {RequestMethod.POST})
    public @ResponseBody
    String deleteFriends(@RequestBody UserFriendParam param) {
        RemoteResult result = null;
        try {
            if (null == param.getFromUserId() || param.getFromUserId() < 0 || null == param.getToUserId() || param.getToUserId() < 0) {
                LOGGER.info("调用deleteFriends 传入的参数错误");
                result = RemoteResult.failure("0001", "传入参数错误");
                return JSON.toJSONString(result);
            }
            UserFriends userFriends = new UserFriends();
            BeanUtils.copyProperties(userFriends, param);
            result = userFriendsService.deleteFriends(userFriends);
        } catch (Exception e) {
            LOGGER.error("失败:" + e.getMessage(), e);
            result = RemoteResult.failure("0001", "操作失败:" + e.getMessage());
        }
        return JSON.toJSONString(result);
    }

    @RequestMapping(value = "/agreeApplyFriends", method = {RequestMethod.POST})
    public @ResponseBody
    String agreeApplyFriends(@RequestBody UserFriendParam param) {
        RemoteResult result = null;
        try {
            if (null == param.getFromUserId() || param.getFromUserId() < 0 || null == param.getToUserId() || param.getToUserId() < 0) {
                LOGGER.info("调用agreeApplyFriends 传入的参数错误");
                result = RemoteResult.failure("0001", "传入参数错误");
                return JSON.toJSONString(result);
            }
            UserFriends userFriends = new UserFriends();
            BeanUtils.copyProperties(userFriends, param);
            result = userFriendsService.agreeApplyFriends(userFriends);
        } catch (Exception e) {
            LOGGER.error("失败:" + e.getMessage(), e);
            result = RemoteResult.failure("0001", "操作失败:" + e.getMessage());
        }
        return JSON.toJSONString(result);
    }

    @RequestMapping(value = "/refuseInvitation", method = {RequestMethod.POST})
    public @ResponseBody
    String refuseInvitation(@RequestBody UserFriendParam param) {
        RemoteResult result = null;
        try {
            if (null == param.getFromUserId() || param.getFromUserId() < 0 || null == param.getToUserId() || param.getToUserId() < 0) {
                LOGGER.info("调用refuseInvitation 传入的参数错误");
                result = RemoteResult.failure("0001", "传入参数错误");
                return JSON.toJSONString(result);
            }
            UserFriends userFriends = new UserFriends();
            BeanUtils.copyProperties(userFriends, param);
            result = userFriendsService.refuseInvitation(userFriends);
        } catch (Exception e) {
            LOGGER.error("失败:" + e.getMessage(), e);
            result = RemoteResult.failure("0001", "操作失败:" + e.getMessage());
        }
        return JSON.toJSONString(result);
    }

    @RequestMapping(value = "/isFriend", method = {RequestMethod.POST})
    public @ResponseBody
    String isFriend(@RequestBody UserFriendParam param) {
        RemoteResult result = null;
        try {
            if (null == param.getFromUserId() || param.getFromUserId() < 0 || null == param.getToUserId() || param.getToUserId() < 0) {
                LOGGER.info("调用isFriend 传入的参数错误");
                result = RemoteResult.failure("0001", "传入参数错误");
                return JSON.toJSONString(result);
            }
            UserFriends userFriends = new UserFriends();
            BeanUtils.copyProperties(userFriends, param);


            int res = userFriendsService.check2PIsFriend(userFriends);
            if (res > 0) {
                result = RemoteResult.result(BusinessCode.IS_FRIEND, null);
            } else {
                result = RemoteResult.result(BusinessCode.NO_FRIEND, null);
            }
        } catch (Exception e) {
            LOGGER.error("失败:" + e.getMessage(), e);
            result = RemoteResult.failure("0001", "操作失败:" + e.getMessage());
        }
        return JSON.toJSONString(result);
    }

    @RequestMapping(value = "/getUserInfo", method = {RequestMethod.POST})
    public @ResponseBody
    String getUserInfoById(@RequestBody User user) {
        RemoteResult result = null;
        try {
            if (null == user.getId() && StringUtils.isEmpty(user.getTel())) {
                LOGGER.info("调用getUserInfo 传入的参数错误");
                result = RemoteResult.failure("0001", "传入参数错误");
                return JSON.toJSONString(result);
            }
            List<User> users = userService.selectEntryList(user);
            if (CollectionUtils.isNotEmpty(users)) {
                User us = users.get(0);
                us.setAvatarUrl(IMAGEPREFIX + us.getAvatarUrl());
                result = RemoteResult.success(us);
            } else {
                result = RemoteResult.result(BusinessCode.IS_EXIST_NO, null);
            }
        } catch (Exception e) {
            LOGGER.error("失败:" + e.getMessage(), e);
            result = RemoteResult.failure("0001", "操作失败:" + e.getMessage());
        }
        return JSON.toJSONString(result);
    }

    @RequestMapping(value = "/getPendingFriends", method = {RequestMethod.POST})
    public @ResponseBody
    String getPendingFriends(Page<User> page, Long userId) {
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
    }


	@RequestMapping(value = "/logout", method = {RequestMethod.POST})
    public @ResponseBody String logout(HttpServletRequest request, HttpServletResponse response) {
		RemoteResult result = null;
        Cookie ck = new Cookie(CookieEnum.TICKET.getKey(), "");
        ck.setMaxAge(0);
        response.addCookie(ck);
		return JSON.toJSONString(RemoteResult.success());
	}

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
