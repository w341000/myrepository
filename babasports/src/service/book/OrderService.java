package service.book;

import bean.BuyCart;
import bean.book.DeliverWay;
import bean.book.Order;
import bean.book.PaymentWay;
import dao.BaseDao;
/**
 * ��������service
 */
public interface OrderService extends BaseDao<Order> {
	/**
	 * �����û��Ĺ��ﳵ���û������ɶ���
	 * @param cart
	 * @param username
	 * @return
	 */
	public Order createOrder(BuyCart cart,String username);
	/**
	 * ����֧����ʽ
	 * @param orderid ����id
	 * @param paymentWay ֧����ʽ
	 */
	void updatePaymentWay(String orderid, PaymentWay paymentWay);
	/**
	 * �������ͷ�ʽ
	 * @param orderid ����id
	 * @param deliverWay ���ͷ�ʽ
	 */
	void updateDeliverWay(String orderid, DeliverWay deliverWay);
	/**
	 * �������ͷ�
	 * @param orderid ����id
	 * @param fee ���ͷ�
	 */
	void updateDeliverFee(String orderid, float fee);
	/**
	 * ȡ������
	 * @param orderid ����id
	 */
	void cancelOrder(String orderid);
	/**
	 * ��˶���
	 * @param orderid ����id
	 */
	void confirmOrder(String orderid);
	/**
	 * ����ȷ���Ѹ���
	 * @param orderid ����id
	 */
	void confirmPayment(String orderid);
	/**
	 * ����תΪ�ȴ�����״̬
	 * @param orderid ����id
	 */
	void turnWaitdeliver(String orderid);
	/**
	 * ����תΪ�Ѿ�����״̬
	 * @param orderid ����id
	 */
	void turnDelivered(String orderid);
	/**
	 * ����תΪ�Ѿ��ջ�״̬
	 * @param orderid ����id
	 */
	void turnReceived(String orderid);
	/**
	 * ��������
	 * @param orderid ������
	 * @param username �����û�
	 * @return
	 */
	Order addLock(String orderid, String username);
	/**
	 * �������� 
	 * @param orderids ������
	 */
	void unlock(String... orderids);

}
