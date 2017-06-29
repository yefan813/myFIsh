package com.frame.service;

import com.frame.domain.UserLogin;
import com.frame.service.base.BaseService;

import java.util.List;

public interface UserLoginService extends BaseService<UserLogin, Integer> {
	
	

	/**
	 * @param userLogin
	 * @return
	 */
	public int registDeviceToken(UserLogin userLogin);
	
	/**
	 * @param userIds
	 * @return
	 */
	public List<String> getDeviceTokenByIds(List<Long> userIds);
}
