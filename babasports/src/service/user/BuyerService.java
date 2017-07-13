package service.user;

import java.io.Serializable;

import bean.user.Buyer;
import dao.BaseDao;

public interface BuyerService extends BaseDao<Buyer> {
	/**
	 * 判断用户是否存在
	 * @param username 用户名
	 * @return
	 */
	public boolean exist(String username);

	/**
	 * 校验用户名密码是否正确
	 * @param username 用户名
	 * @param password 密码
	 * @return
	 */
	public boolean validate(String username,String password);

	/**
	 * 启用用户账户
	 * @param ids
	 */
	void enable(Serializable... ids);

	/**
	 * 更新用户的密码,此方法会对密码MD5加密
	 * @param username 用户名
	 * @param password 密码
	 */
	void updatePassword(String username, String password);
}
