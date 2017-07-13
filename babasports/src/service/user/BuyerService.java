package service.user;

import java.io.Serializable;

import bean.user.Buyer;
import dao.BaseDao;

public interface BuyerService extends BaseDao<Buyer> {
	/**
	 * �ж��û��Ƿ����
	 * @param username �û���
	 * @return
	 */
	public boolean exist(String username);

	/**
	 * У���û��������Ƿ���ȷ
	 * @param username �û���
	 * @param password ����
	 * @return
	 */
	public boolean validate(String username,String password);

	/**
	 * �����û��˻�
	 * @param ids
	 */
	void enable(Serializable... ids);

	/**
	 * �����û�������,�˷����������MD5����
	 * @param username �û���
	 * @param password ����
	 */
	void updatePassword(String username, String password);
}
