package service.book;

import bean.BuyCart;
import bean.book.DeliverWay;
import bean.book.Order;
import bean.book.PaymentWay;
import dao.BaseDao;
/**
 * 订单管理service
 */
public interface OrderService extends BaseDao<Order> {
	/**
	 * 根据用户的购物车和用户名生成订单
	 * @param cart
	 * @param username
	 * @return
	 */
	public Order createOrder(BuyCart cart,String username);
	/**
	 * 更新支付方式
	 * @param orderid 订单id
	 * @param paymentWay 支付方式
	 */
	void updatePaymentWay(String orderid, PaymentWay paymentWay);
	/**
	 * 更新配送方式
	 * @param orderid 订单id
	 * @param deliverWay 配送方式
	 */
	void updateDeliverWay(String orderid, DeliverWay deliverWay);
	/**
	 * 更新配送费
	 * @param orderid 订单id
	 * @param fee 配送费
	 */
	void updateDeliverFee(String orderid, float fee);
	/**
	 * 取消订单
	 * @param orderid 订单id
	 */
	void cancelOrder(String orderid);
	/**
	 * 审核订单
	 * @param orderid 订单id
	 */
	void confirmOrder(String orderid);
	/**
	 * 财务确认已付款
	 * @param orderid 订单id
	 */
	void confirmPayment(String orderid);
	/**
	 * 订单转为等待发货状态
	 * @param orderid 订单id
	 */
	void turnWaitdeliver(String orderid);
	/**
	 * 订单转为已经发货状态
	 * @param orderid 订单id
	 */
	void turnDelivered(String orderid);
	/**
	 * 订单转为已经收货状态
	 * @param orderid 订单id
	 */
	void turnReceived(String orderid);
	/**
	 * 加锁订单
	 * @param orderid 订单号
	 * @param username 锁定用户
	 * @return
	 */
	Order addLock(String orderid, String username);
	/**
	 * 订单解锁 
	 * @param orderids 订单号
	 */
	void unlock(String... orderids);

}
