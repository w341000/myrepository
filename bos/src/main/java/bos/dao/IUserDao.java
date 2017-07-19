package bos.dao;

import bos.dao.base.IBaseDao;
import bos.domain.User;

public interface IUserDao extends IBaseDao<User> {

	/**
	 * 根据用户名和密码查询用户
	 * @param username
	 * @param password
	 * @return
	 */
	public User findByUsernameAndPassword(String username, String password);

}
