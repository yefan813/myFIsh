package com.frame.service.impl;

import com.aliyuncs.DefaultAcsClient;
import com.aliyuncs.IAcsClient;
import com.aliyuncs.dysmsapi.model.v20170525.SendSmsRequest;
import com.aliyuncs.dysmsapi.model.v20170525.SendSmsResponse;
import com.aliyuncs.exceptions.ClientException;
import com.aliyuncs.profile.DefaultProfile;
import com.aliyuncs.profile.IClientProfile;
import com.frame.domain.UserValid;
import com.frame.domain.base.YnEnum;
import com.frame.domain.common.RemoteResult;
import com.frame.domain.enums.BusinessCode;
import com.frame.domain.enums.SendSMSTypeEnum;
import com.frame.service.TaoBaoSmsService;
import com.frame.service.UserValidService;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.Date;
import java.util.Random;

@Service("taobaoSmsService")
public class TaoBaoSmsServiceImpl implements TaoBaoSmsService {
	private static final Logger LOGGER = LoggerFactory.getLogger(TaoBaoSmsServiceImpl.class);

	@Value("${taobaosms_url}")
	private String TAOBAOSMS_URL;

	@Value("${taobaosms_appKey}")
	private String TAOBAOSMS_APPID;

	@Value("${taobaosms_secretKey}")
	private String TAOBAOSMS_SECRET;
	
	@Value("${taobaosms_freeSignName}")
	private String TAOBAOSMS_SIGNFREENAME;
	
	@Resource
	private UserValidService userValidService;

	final String product = "Dysmsapi";//短信API产品名称

	@Override
	@Transactional(rollbackForClassName = "AppException")
	public RemoteResult sendValidSMS(String phoneNum,Long validDate, Integer validType) {
		RemoteResult msg = null;
		try {
			if (StringUtils.isEmpty(phoneNum) || (null == validDate || validDate < 0)
					|| (null == validType || validType < 0)) {
				LOGGER.error("调用发送消息方法传入参数错误");
				return msg = RemoteResult.failure("0001", "调用发送消息方法传入参数错误");
			}

			String validTypeStr = validType == 1 ? "注册验证" : "找回密码";

			//初始化ascClient,暂时不支持多region
			IClientProfile profile = DefaultProfile.getProfile("cn-hangzhou", TAOBAOSMS_APPID, TAOBAOSMS_SECRET);
			DefaultProfile.addEndpoint("cn-hangzhou", "cn-hangzhou", product, TAOBAOSMS_URL);
			IAcsClient acsClient = new DefaultAcsClient(profile);

			String validCode = getRandomPwd();

			//将手机号以及生成的验证码存入数据库 后续验证时使用
			UserValid valid = new UserValid();
			valid.setTel(phoneNum);
			valid.setValidCode(validCode);
			valid.setValidType(validType);
			valid.setValidDate(new Date(validDate));
			valid.setExpireTime(60l);
			valid.setYn(YnEnum.Normal.getKey());
			int res = userValidService.save(valid);
			if (res > 0) {
				LOGGER.info("生成验证码 存入数据库~~~");

				//组装请求对象
				SendSmsRequest request = new SendSmsRequest();
				//必填:待发送手机号。支持以逗号分隔的形式进行批量调用，批量上限为20个手机号码,批量调用相对于单条调用及时性稍有延迟,验证码类型的短信推荐使用单条调用的方式
				request.setPhoneNumbers(phoneNum);
				//必填:短信签名-可在短信控制台中找到
				request.setSignName("钓鱼大仙");
				//必填:短信模板-可在短信控制台中找到
				if(SendSMSTypeEnum.REGIST_USER.getKey().equals(validType)){
					request.setTemplateCode("SMS_77085005");
				}else{
					request.setTemplateCode("SMS_76990005");
				}

				//可选:模板中的变量替换JSON串,如模板内容为"亲爱的${name},您的验证码为${code}"时,此处的值为
				request.setTemplateParam("{\"code\":\""+ validCode +"\", \"phone\":\"" + phoneNum.substring(phoneNum.length() - 4) + "\"}");
				//可选:outId为提供给业务方扩展字段,最终在短信回执消息中将此值带回给调用者
				request.setOutId("yourOutId");

				//请求失败这里会抛ClientException异常
				SendSmsResponse sendSmsResponse = acsClient.getAcsResponse(request);
				if(sendSmsResponse.getCode() != null && sendSmsResponse.getCode().equals("OK")) {
					LOGGER.info("调用sendValidSMS 发送短信成功 发送的手机号码为[{}],验证码为[{}]", phoneNum, validCode);
					msg = RemoteResult.result(BusinessCode.SUCCESS);
				}else{
					LOGGER.info("调用阿里大鱼发送短息失败:", sendSmsResponse.getMessage());
					msg = RemoteResult.failure("0001","触发业务级流控限制");
				}
			} else {
				LOGGER.info("生成验证码 存入数据库 失败");
				msg = RemoteResult.failure("0001", "验证码存入数据库失败");
			}
		}catch (ClientException e){
			LOGGER.error("调用阿里大鱼发送短息失败,失败的信息为:", e);
			msg = RemoteResult.failure("0001", "调用阿里大鱼发送短息失败,未找到服务");
		}
		return msg;
	}

	public static String getRandomPwd() {
		Random rd = new Random();
		String n = "";
		int getNum;
		do {
			getNum = Math.abs(rd.nextInt()) % 10 + 48;// 产生数字0-9的随机数
			// getNum = Math.abs(rd.nextInt())%26 + 97;//产生字母a-z的随机数
			char num1 = (char) getNum;
			String dn = Character.toString(num1);
			n += dn;
		} while (n.length() < 6);
		System.out.println("随机的6位密码是：" + n);
		return n;
	}

}
