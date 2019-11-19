package com.frame.service.impl;

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
		throw  new RuntimeException("发不了短信了");
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
