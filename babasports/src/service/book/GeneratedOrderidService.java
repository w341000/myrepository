package service.book;

import bean.book.GeneratedOrderid;
import dao.BaseDao;

public interface GeneratedOrderidService extends BaseDao<GeneratedOrderid> {
	/**
	 * ���ɶ�����ˮ��
	 * @return
	 */
	int buildOrderid();

	/**
	 * ��ʼ��
	 */
	void init();

}
