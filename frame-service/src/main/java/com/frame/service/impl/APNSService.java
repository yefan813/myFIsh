package com.frame.service.impl;


import java.util.Date;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.apache.commons.collections.CollectionUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import com.alibaba.druid.util.StringUtils;
import com.frame.domain.common.RemoteResult;
import com.frame.domain.enums.BusinessCode;
import com.frame.service.UserLoginService;
import com.google.common.collect.Lists;
import com.notnoop.apns.APNS;
import com.notnoop.apns.ApnsService;
import com.notnoop.apns.ApnsServiceBuilder;
import com.notnoop.apns.PayloadBuilder;
import com.notnoop.exceptions.NetworkIOException;

@Service("apnsService")
public class APNSService {
	private static final Logger LOGGER = LoggerFactory.getLogger(APNSService.class);
	
	
	@Value("${iosCertPassword}")
	private String iosCertPassword;
	
	@Value("${iosCertPath}")
	private String iosCertPath;
	
	@Resource
	private UserLoginService userLoginService;
	
	
	public RemoteResult senPushNotificationByIds(List<Long> userIds,  String content){
		RemoteResult result = null;
		if(CollectionUtils.isEmpty(userIds)){
			result = RemoteResult.failure(BusinessCode.PARAMETERS_ERROR.getCode(), BusinessCode.PARAMETERS_ERROR.getValue());
			return result;
		}
		List<String> deviceTokens = userLoginService.getDeviceTokenByIds(userIds);
		result = senPushNotification(deviceTokens,content);
		return result;
	}
	
	public RemoteResult senPushNotification(Long userId,  String content){
		RemoteResult result = null;
		if(null == userId || StringUtils.isEmpty(content)){
			result = RemoteResult.failure(BusinessCode.PARAMETERS_ERROR.getCode(), BusinessCode.PARAMETERS_ERROR.getValue());
			return result;
		}
		List<Long> userIds = Lists.newArrayList();
		userIds.add(userId);
		List<String> deviceTokens = userLoginService.getDeviceTokenByIds(userIds);
		result = senPushNotification(deviceTokens,content);
		return result;
	}
	
	@Async
	public RemoteResult senPushNotification(List<String> deviceTokens, String content){
		RemoteResult msg = null;
		if(CollectionUtils.isEmpty(deviceTokens)){
			msg = RemoteResult.failure(BusinessCode.PARAMETERS_ERROR.getCode(), BusinessCode.PARAMETERS_ERROR.getValue());
			return msg;
		}
		 long start = System.currentTimeMillis();
	 
	        // 创建和苹果APNS服务器的连接connection对象
	        ApnsServiceBuilder serviceBuilder = APNS.newService();
	        // 通过类加载器加载ios证书\
	        serviceBuilder.withCert(iosCertPath, iosCertPassword);
	        // 设定为推送正式产品环境
	        serviceBuilder.withSandboxDestination();
	        // 从builder到service对象
	        ApnsService service = serviceBuilder.build();
	        String sound = "default.mp3";// 默认响铃文件名称
	        int badge = 5;// 小红点数字
	        PayloadBuilder payloadBuilder = APNS.newPayload();
	        payloadBuilder.badge(badge);
	        payloadBuilder.sound(sound);
	        try {
	            // 尝试推送10条消息内容不同的消息
                String payload = payloadBuilder.alertBody(content).build();
                LOGGER.info(service.push(deviceTokens, payload).toString());
                msg = RemoteResult.success();
	        } catch (NetworkIOException e) {
	        	LOGGER.error("推送消息到苹果APNS服务器遇到网络异常" + e);
	        	msg = RemoteResult.failure("0001", "推送消息到苹果APNS服务器遇到网络异常");
	        } catch (Exception ex) {
	        	LOGGER.error("推送消息到苹果APNS服务器错误" + ex);
	        	msg = RemoteResult.failure("0001", "推送消息到苹果APNS服务器遇到网络异常");
	        } finally {
	        	LOGGER.info("推送成功，推送列表为:");
	            for (String t : deviceTokens) {
	                System.out.println(t);
	            }
	            LOGGER.info("耗时为：" + (System.currentTimeMillis() - start) + "ms");
	        }
	 
	        Map<String, Date> inactiveDevices = service.getInactiveDevices();
	        for (String deviceToken : inactiveDevices.keySet()) {
	            Date inactiveOf = inactiveDevices.get(deviceToken);
	            LOGGER.info(inactiveOf.toString());
	        }
	 
	        // 推送完毕后关闭service连接
	        service.stop();
		
		
		return msg;
	}
}
