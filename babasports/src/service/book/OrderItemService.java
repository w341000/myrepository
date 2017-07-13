package service.book;

import bean.book.OrderItem;
import dao.BaseDao;

public interface OrderItemService extends BaseDao<OrderItem> {

	/**
	 * 修改订单项的产品数量
	 * @param itemid 订单项id
	 * @param amount 产品数量
	 */
	void updateAmount(Integer itemid, Integer amount);

}
