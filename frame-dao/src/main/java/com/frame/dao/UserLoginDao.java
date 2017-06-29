package com.frame.dao;

import com.frame.dao.base.BaseDao;
import com.frame.domain.UserLogin;

import java.util.List;

public interface UserLoginDao extends BaseDao<UserLogin, Integer> {
	
	public int registDeviceToken(UserLogin userLogin);
	
	public List<String> getDeviceTokenByIds(List<Long> userIds);
}
