package service.book.impl;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import javax.annotation.Resource;

import org.hibernate.Query;
import org.springframework.stereotype.Service;

import org.springframework.transaction.annotation.Transactional;

import bean.BuyCart;
import bean.BuyItem;
import bean.book.DeliverWay;
import bean.book.Order;
import bean.book.OrderItem;
import bean.book.OrderState;
import bean.book.PaymentWay;
import bean.product.ProductStyle;
import bean.user.Buyer;
import bean.user.ContactInfo;
import service.book.GeneratedOrderidService;
import service.book.OrderService;
import dao.impl.BaseDaoImpl;
@Service("orderService") @Transactional
public class OrderServiceImpl extends BaseDaoImpl<Order> implements OrderService {

	@Resource 
	private GeneratedOrderidService generatedOrderidService;
	
	
	@Override
	public void unlock(String... orderids){
		Query query=getSession().createQuery("update Order set lockuser=null where orderid in (:orderids)");
		query.setParameterList("orderids", orderids);
		query.executeUpdate();
	}
	
	@Override
	public Order addLock(String orderid,String username){
		// lockuser is null����˶�����ﲢ������ʱ������,��֤�˻�ȡ���Ķ���ͬһ��order,������������ʱͬһ��username
		Query query=getSession().createQuery("update Order set lockuser=:lockuser where orderid=:orderid and lockuser is null");
		query.setString("lockuser", username).setString("orderid", orderid);
		query.executeUpdate();
		getSession().flush();//�����ύ�����ݿ�
		return get(orderid);	
	}
	@Override
	public  void turnReceived(String orderid){
		Order order=this.get(orderid);
		if(OrderState.DELIVERED.equals(order.getState())){
			//�������Ѿ�����״̬ʱ,������Ѿ��ջ�״̬
				order.setState(OrderState.RECEIVED);	
		}
	}
	@Override
	public  void turnDelivered(String orderid){
		Order order=this.get(orderid);
		if(OrderState.WAITDELIVER.equals(order.getState())){
			//�����ǵȴ�����״̬ʱ,������Ѿ�����״̬
				order.setState(OrderState.DELIVERED);	
		}
	}
	
	@Override
	public  void turnWaitdeliver(String orderid){
		Order order=this.get(orderid);
		if(OrderState.ADMEASUREPRODUCT.equals(order.getState())){
			//�������������͸���״̬ʱ,�����ȴ�����
				order.setState(OrderState.WAITDELIVER);	
		}
	}
	
	@Override
	public  void confirmPayment(String orderid){
		Order order=this.get(orderid);
		order.setPaymentstate(true);
		if(OrderState.WAITPAYMENT.equals(order.getState())){
			//������֧��,���Ҳ����ǵȴ�����״̬ʱ,�������������״̬
				order.setState(OrderState.ADMEASUREPRODUCT);	
		//����������ѷ���״̬�����ǻ��������֧����ʽ,��������ջ�״̬
		}else if(OrderState.DELIVERED.equals(order.getState()) && PaymentWay.COD.equals(order.getPaymentWay())){
			order.setState(OrderState.RECEIVED);
		}
	}
	@Override
	public  void confirmOrder(String orderid){
		Order order=this.get(orderid);
		if(OrderState.WAITCONFIRM.equals(order.getState())){
			//����δ֧��,���Ҳ��ǻ�������ʱ,�����֧��״̬
			if(!PaymentWay.COD.equals(order.getPaymentWay()) && !order.getPaymentstate()){
				order.setState(OrderState.WAITPAYMENT);
			}else{//���������������״̬
				order.setState(OrderState.ADMEASUREPRODUCT);
			}
			
		}
	}
	@Override
	public  void cancelOrder(String orderid){
		Order order=this.get(orderid);
		if(!OrderState.RECEIVED.equals(order.getState())){//�������ջ�״̬����Ķ��������Խ���ȡ��״̬
			order.setState(OrderState.CANCEL);
		}
	}
	@Override
	public  void updateDeliverFee(String orderid, float fee){
		this.get(orderid).setDeliverFee(fee);
	}
	@Override
	public  void updateDeliverWay(String orderid, DeliverWay deliverWay) {
		/**
		 * ������﷨��ʹ��hibernateʱִ�г���,��hibernate��bug�����µ�,�������topLink�ܹ���������
		 */
//	   String hql="update Order set orderDeliverInfo.deliverWay=:deliverWay where orderid=:orderid";
//	   Query query=this.getSession().createQuery(hql);
//	   query.setParameter("deliverWay", deliverWay).setString("orderid", orderid).executeUpdate();
		this.get(orderid).getOrderDeliverInfo().setDeliverWay(deliverWay);
	}
	
	
	@Override
	public  void updatePaymentWay(String orderid, PaymentWay paymentWay) {
	   String hql="update Order set paymentWay=:paymentWay where orderid=:orderid";
	   Query query=this.getSession().createQuery(hql);
	   query.setParameter("paymentWay", paymentWay).setString("orderid", orderid).executeUpdate();
		
	}
	
	
	
	
	
	
	
	@Override//Ϊ��ֹͬʱ�������û��¶�����ɶ�����ų�ͻ����,����������ͬ����
	public synchronized Order createOrder(BuyCart cart, String username) {
		Order order=new Order();
		Buyer buyer=this.get(Buyer.class, username);
		order.setBuyer(buyer);
		order.setDeliverFee(cart.getDeliveFee());
		order.setNote(cart.getNote());
		order.setOrderContactInfo(cart.getContactInfo());
		order.setOrderDeliverInfo(cart.getDeliverInfo());
		order.setState(OrderState.WAITCONFIRM);
		order.setPaymentWay(cart.getPaymentWay());
		order.setProductTotalPrice(cart.getTotalSellPrice());
		order.setTotalPrice(cart.getOrderTotalPrice());
		order.setPayablefee(cart.getOrderTotalPrice());
		
		for(BuyItem item:cart.getItems()){
			ProductStyle style=item.getProduct().getStyles().iterator().next();
			OrderItem oitem=new OrderItem(item.getProduct().getName(),item.getProduct().getId(),item.getProduct().getSellprice(),item.getAmount(),style.getName(),style.getId());
			order.addOrderItem(oitem);
		}
		if(buyer.getContactInfo()==null){
			buyer.setContactInfo(new ContactInfo());
			buyer.getContactInfo().setAddress(order.getOrderContactInfo().getAddress());
			buyer.getContactInfo().setPostalcode(order.getOrderContactInfo().getPostalcode());
			buyer.getContactInfo().setMobile(order.getOrderContactInfo().getMobile());
			buyer.getContactInfo().setPhone(order.getOrderContactInfo().getTel());
			if(buyer.getRealname()==null) buyer.setRealname(order.getOrderContactInfo().getBuyerName());
			if(buyer.getGender()==null)buyer.setGender(order.getOrderContactInfo().getGender());
		}
		order.setOrderid(buildOrderid2(order.getCreateDate()));
		this.save(order);
		return order;
	}

	/**
	 * ���ɶ�����,��ɷ�ʽ:2λ���,2λ�·�,2λ����,+���충������+1,���������������8λ,ǰ�油0,
	 * ��09120200000001
	 * @param date 
	 * @return ������
	 */
	private String buildOrderid(Date date) {
		SimpleDateFormat format=new SimpleDateFormat("yyMMdd");
		//���ɵ�ǰ�������ַ���
		String newdate=format.format(date);
		format=new SimpleDateFormat("yyyy-MM-dd");
		try {
			Date now=format.parse(format.format(date));
			Query query=getSession().createQuery("select count(*) from Order where createDate > :createDate");
			query.setDate("createDate", now);
			long count=(Long)query.uniqueResult();
			//���ɶ���8λ���ַ���
			String strCount=fillZero(8,String.valueOf(count+1));
			return newdate+strCount;
		} catch (ParseException e) {
			throw new RuntimeException("���ɶ�������",e);
		}
	}

	/**
	 * ���ɶ�����,��ɷ�ʽ:2λ���,2λ�·�,2λ����,+��ˮ��
	 * ��09120200000001
	 * @param date 
	 * @return ������
	 */
	private String buildOrderid2(Date date) {
		SimpleDateFormat format=new SimpleDateFormat("yyMMdd");
		//���ɵ�ǰ�������ַ���
		String newdate=format.format(date);
		//8λ������ˮ��
		String orderid=fillZero(8,String.valueOf(this.generatedOrderidService.buildOrderid()));
		return newdate+orderid;
	}

	/**
	 * ��0
	 * @param length �����ĳ���
	 * @param source  ��Ҫ������ַ���
	 * @return
	 */
	private String fillZero(int length, String source) {
		if(source.length()>length){
			return source.substring(0, length);
		}
		StringBuffer sb=new StringBuffer();
		for(int i=source.length();i<length;i++){
			sb.append('0');
		}
		return sb.toString()+source;
	}
}
