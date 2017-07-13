package service.book;

import bean.book.GeneratedOrderid;
import dao.BaseDao;

public interface GeneratedOrderidService extends BaseDao<GeneratedOrderid> {
	/**
	 * 生成订单流水号
	 * @return
	 */
	int buildOrderid();

	/**
	 * 初始化
	 */
	void init();

}
