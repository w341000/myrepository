package service.book;

import bean.book.OrderItem;
import dao.BaseDao;

public interface OrderItemService extends BaseDao<OrderItem> {

	/**
	 * �޸Ķ�����Ĳ�Ʒ����
	 * @param itemid ������id
	 * @param amount ��Ʒ����
	 */
	void updateAmount(Integer itemid, Integer amount);

}
