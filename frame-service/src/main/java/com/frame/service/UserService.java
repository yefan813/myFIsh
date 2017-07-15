package com.frame.service;

import com.frame.domain.User;
import com.frame.domain.UserAuths;
import com.frame.domain.UserLogin;
import com.frame.domain.common.Page;
import com.frame.domain.common.RemoteResult;
import com.frame.service.base.BaseService;

import java.util.List;

public interface UserService extends BaseService<User, Long> {
	
	/**
	 * 更新用户信息 通过电话号码
	 * @param user
	 * @return
	 */
	public int updateByTel(User user);
	
	
	/**
	 * 注册用户
	 * @param user
	 * @param userAuths
	 * @return
	 * @throws Exception
	 */
	public RemoteResult registOrUpdateUser(User user, UserAuths userAuths) throws Exception;

	/**
	 * 查找附近用户
	 * @param page
	 * @param userLogin
	 * @return
	 */
	public RemoteResult getNearByUser(Page<User> page, UserLogin userLogin);
	


	/**
	 * @param user
	 * @return
	 */
	public RemoteResult bindTel(User user);
	
	
	public RemoteResult login(UserAuths userAuths, String nickName) throws Exception;
	
	public List<User> queryFriendsByTelOrNickName(List<Long> userIds, String query );


	public RemoteResult editUserInfo(User user) throws Exception;
	
}
