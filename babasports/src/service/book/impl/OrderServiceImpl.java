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
		// lockuser is null解决了多个事物并发访问时的问题,保证了获取到的都是同一个order,即锁定订单的时同一个username
		Query query=getSession().createQuery("update Order set lockuser=:lockuser where orderid=:orderid and lockuser is null");
		query.setString("lockuser", username).setString("orderid", orderid);
		query.executeUpdate();
		getSession().flush();//立刻提交到数据库
		return get(orderid);	
	}
	@Override
	public  void turnReceived(String orderid){
		Order order=this.get(orderid);
		if(OrderState.DELIVERED.equals(order.getState())){
			//订单是已经发货状态时,则进入已经收货状态
				order.setState(OrderState.RECEIVED);	
		}
	}
	@Override
	public  void turnDelivered(String orderid){
		Order order=this.get(orderid);
		if(OrderState.WAITDELIVER.equals(order.getState())){
			//订单是等待发货状态时,则进入已经发货状态
				order.setState(OrderState.DELIVERED);	
		}
	}
	
	@Override
	public  void turnWaitdeliver(String orderid){
		Order order=this.get(orderid);
		if(OrderState.ADMEASUREPRODUCT.equals(order.getState())){
			//订单是正在配送付款状态时,则进入等待发货
				order.setState(OrderState.WAITDELIVER);	
		}
	}
	
	@Override
	public  void confirmPayment(String orderid){
		Order order=this.get(orderid);
		order.setPaymentstate(true);
		if(OrderState.WAITPAYMENT.equals(order.getState())){
			//订单以支付,并且不是是等待付款状态时,则进入正在配送状态
				order.setState(OrderState.ADMEASUREPRODUCT);	
		//如果订单是已发货状态并且是货到付款的支付方式,则进入已收货状态
		}else if(OrderState.DELIVERED.equals(order.getState()) && PaymentWay.COD.equals(order.getPaymentWay())){
			order.setState(OrderState.RECEIVED);
		}
	}
	@Override
	public  void confirmOrder(String orderid){
		Order order=this.get(orderid);
		if(OrderState.WAITCONFIRM.equals(order.getState())){
			//订单未支付,并且不是货到付款时,进入待支付状态
			if(!PaymentWay.COD.equals(order.getPaymentWay()) && !order.getPaymentstate()){
				order.setState(OrderState.WAITPAYMENT);
			}else{//否则进入正在配送状态
				order.setState(OrderState.ADMEASUREPRODUCT);
			}
			
		}
	}
	@Override
	public  void cancelOrder(String orderid){
		Order order=this.get(orderid);
		if(!OrderState.RECEIVED.equals(order.getState())){//对于已收货状态以外的订单都可以进入取消状态
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
		 * 下面的语法在使用hibernate时执行出错,是hibernate的bug所导致的,如果用在topLink能够正常运行
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
	
	
	
	
	
	
	
	@Override//为防止同时有两个用户下订单造成订单编号冲突问题,给方法加上同步锁
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
	 * 生成订单号,组成方式:2位年份,2位月份,2位日期,+当天订单总数+1,如果订单总数不够8位,前面补0,
	 * 如09120200000001
	 * @param date 
	 * @return 订单号
	 */
	private String buildOrderid(Date date) {
		SimpleDateFormat format=new SimpleDateFormat("yyMMdd");
		//生成当前年月日字符串
		String newdate=format.format(date);
		format=new SimpleDateFormat("yyyy-MM-dd");
		try {
			Date now=format.parse(format.format(date));
			Query query=getSession().createQuery("select count(*) from Order where createDate > :createDate");
			query.setDate("createDate", now);
			long count=(Long)query.uniqueResult();
			//生成订单8位数字符串
			String strCount=fillZero(8,String.valueOf(count+1));
			return newdate+strCount;
		} catch (ParseException e) {
			throw new RuntimeException("生成订单错误",e);
		}
	}

	/**
	 * 生成订单号,组成方式:2位年份,2位月份,2位日期,+流水号
	 * 如09120200000001
	 * @param date 
	 * @return 订单号
	 */
	private String buildOrderid2(Date date) {
		SimpleDateFormat format=new SimpleDateFormat("yyMMdd");
		//生成当前年月日字符串
		String newdate=format.format(date);
		//8位订单流水号
		String orderid=fillZero(8,String.valueOf(this.generatedOrderidService.buildOrderid()));
		return newdate+orderid;
	}

	/**
	 * 补0
	 * @param length 补零后的长度
	 * @param source  需要补零的字符串
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
