package web.action.book;

import javax.annotation.Resource;

import org.apache.struts2.ServletActionContext;
import org.springframework.context.annotation.Scope;

import org.springframework.stereotype.Controller;

import service.book.MessageService;
import service.book.OrderContactInfoService;
import service.book.OrderDeliverInfoService;
import service.book.OrderItemService;
import service.book.OrderService;

import bean.book.DeliverWay;
import bean.book.Message;
import bean.book.Order;
import bean.book.OrderContactInfo;
import bean.book.OrderDeliverInfo;
import bean.book.PaymentWay;
import bean.book.OrderItem;
import bean.privilege.Employee;
import web.action.BaseAction;
/**
 * 订单管理
 * @author Administrator
 *
 */
@Controller @Scope("prototype")
public class OrderManageAction extends BaseAction<Object> {
	@Resource
	private OrderService orderService;
	@Resource
	private OrderContactInfoService orderContactInfoService;
	@Resource
	private OrderDeliverInfoService orderDeliverInfoService;
	@Resource
	private OrderItemService orderItemService;
	@Resource
	private MessageService messageService;
	private String[] orderids;
	private String orderid;
	private OrderContactInfo contactInfo;
	private OrderDeliverInfo deliverInfo;
	private PaymentWay paymentWay;
	private DeliverWay deliverWay;
	private OrderItem orderItem;
	private float fee;
	private String message;
	

	
	public String getMessage() {
		return message;
	}
	public void setMessage(String message) {
		this.message = message;
	}
	public String[] getOrderids() {
		return orderids;
	}
	public void setOrderids(String[] orderids) {
		this.orderids = orderids;
	}
	public float getFee() {
		return fee;
	}
	public void setFee(float fee) {
		this.fee = fee;
	}
	public DeliverWay getDeliverWay() {
		return deliverWay;
	}
	public void setDeliverWay(DeliverWay deliverWay) {
		this.deliverWay = deliverWay;
	}
	public PaymentWay getPaymentWay() {
		return paymentWay;
	}
	public void setPaymentWay(PaymentWay paymentWay) {
		this.paymentWay = paymentWay;
	}
	public OrderDeliverInfo getDeliverInfo() {
		return deliverInfo;
	}
	public void setDeliverInfo(OrderDeliverInfo deliverInfo) {
		this.deliverInfo = deliverInfo;
	}
	public OrderContactInfo getContactInfo() {
		return contactInfo;
	}
	public void setContactInfo(OrderContactInfo contactInfo) {
		this.contactInfo = contactInfo;
	}
	public String getOrderid() {
		return orderid;
	}
	public void setOrderid(String orderid) {
		this.orderid = orderid;
	}
	public OrderItem getOrderItem() {
		return orderItem;
	}
	public void setOrderItem(OrderItem orderItem) {
		this.orderItem = orderItem;
	}
	
	
	/**
	 * 客服留言添加界面
	 */
	public String addMessageUI(){
		return "addmessage";
	}
	
	/**
	 * 添加客服留言
	 * @return
	 */
	public String addMessage(){
		
		Message msg=new Message();
		msg.setContent(message);
		String username=((Employee)ServletActionContext.getRequest().getSession().getAttribute("employee")).getUsername();
		msg.setUsername(username);
		msg.setOrder(new Order(orderid));
		messageService.save(msg);
		request.put("message", "添加留言成功");
		request.put("urladdress", "/control/order/view?orderid="+orderid);
		return "message";
	}
	
	/**
	 * 强行解锁订单
	 * @return
	 */
	public String allUnLock(){
		orderService.unlock(orderids);
		request.put("message", "解锁订单成功");
		request.put("urladdress", "/control/lockorder/list");
		return "message";
	}
	/**
	 * 解锁订单
	 * @return
	 */
	
	public String employeeUnlockOrder(){
		orderService.unlock(orderid);
		
		return "list";
	}
	/**
	 * 修改订购者信息界面
	 * @return
	 */
	public String modifyContactInfoUI(){
		request.put("contactinfo", orderContactInfoService.get(contactInfo.getContactid()));
		return "modifyContactInfoUI";
	}
	
	/**
	 * 修改订购者信息
	 * @return
	 */
	public String modifyContactInfo(){
		OrderContactInfo info=orderContactInfoService.get(contactInfo.getContactid());
		info.setAddress(contactInfo.getAddress());
		info.setBuyerName(contactInfo.getBuyerName());
		info.setGender(contactInfo.getGender());
		info.setPostalcode(contactInfo.getPostalcode());
		info.setTel(contactInfo.getTel());
		info.setMobile(contactInfo.getMobile());
		orderContactInfoService.update(info);
		request.put("message", "订购者信息修改成功");
		request.put("urladdress", "/control/order/view.action?orderid="+orderid);
		return "message";
		
	}
	/**
	 * 修改收货人信息界面
	 * @return
	 */
	public String modifyDeliverInfoUI(){
		request.put("deliverInfo", orderDeliverInfoService.get(deliverInfo.getDeliverid()));
		return "modifyDeliverInfoUI";
	}
	
	/**
	 * 修改收货人信息
	 * @return
	 */

	public String modifyDeliverInfo(){
		OrderDeliverInfo orderDeliverInfo=orderDeliverInfoService.get(deliverInfo.getDeliverid());
		orderDeliverInfo.setAddress(deliverInfo.getAddress());
		orderDeliverInfo.setEmail(deliverInfo.getEmail());
		orderDeliverInfo.setTel(deliverInfo.getTel());
		orderDeliverInfo.setGender(deliverInfo.getGender());
		orderDeliverInfo.setMobile(deliverInfo.getMobile());
		orderDeliverInfo.setRecipients(deliverInfo.getRecipients());
		orderDeliverInfo.setPostalcode(deliverInfo.getPostalcode());
		orderDeliverInfoService.update(orderDeliverInfo);
		request.put("message", "收货人信息修改成功");
		request.put("urladdress", "/control/order/view.action?orderid="+orderid);
		return "message";
		
	}
	
	/**
	 * 支付方式修改界面
	 * @return
	 */
	public String modifyPaymentWayUI(){
		Order order=orderService.get(orderid);
		request.put("order", order);
		return "modifyPaymentWayUI";
	}
	/**
	 * 支付方式修改
	 * @return
	 */
	public String modifyPaymentWay(){
		orderService.updatePaymentWay(orderid, paymentWay);
		request.put("message", "支付方式修改成功");
		request.put("urladdress", "/control/order/view.action?orderid="+orderid);
		return "message";
	}
	
	
	/**
	 * 配送方式修改界面
	 * @return
	 */
	public String modifyDeliverWayUI(){
		Order order=orderService.get(orderid);
		request.put("order", order);
		return "modifyDeliverWayUI";
		
	}
	/**
	 * 配送方式修改
	 * @return
	 */
	public String modifyDeliverWay(){
		orderService.updateDeliverWay(orderid, deliverWay);
		request.put("message", "支付方式修改成功");
		request.put("urladdress", "/control/order/view.action?orderid="+orderid);
		return "message";
	}
	
	/**
	 * 修改产品数量界面
	 * @return
	 */
	public String modifyProductAmountUI(){
		orderItem=orderItemService.get(orderItem.getItemid());

		return "modifyProductAmountUI";
	}
	
	/**
	 * 修改产品数量
	 * @return
	 */
	public String modifyProductAmount(){
		this.orderItemService.updateAmount(orderItem.getItemid(),orderItem.getAmount());
		request.put("message", "商品数量修改成功");
		request.put("urladdress", "/control/order/view.action?orderid="+orderid);
		return "message";
	}
	/**
	 * 删除订单项
	 * @return
	 */
	public String deleteOrderItem(){
		this.orderItemService.delete(orderItem.getItemid());
		request.put("message", "订单项删除成功");
		request.put("urladdress", "/control/order/view.action?orderid="+orderid);
		return "message";
	}
	/**
	 * 配送费修改界面
	 * @return
	 */
	public String modifyDeliverFeeUI(){
		
		request.put("fee", this.orderService.get(orderid).getDeliverFee());
		return "modifyDeliverFeeUI";
	}
	/**
	 * 配送费修改
	 * @return
	 */
	public String modifyDeliverFee(){
		this.orderService.updateDeliverFee(orderid, fee);
		request.put("message", "更新配送费成功");
		request.put("urladdress", "/control/order/view.action?orderid="+orderid);
		return "message";
	}
	/**
	 * 打印订单
	 * @return
	 */
	public String printOrder(){
		request.put("order", this.orderService.get(orderid));
		return "printOrder";
	}
	
	
	/**
	 * 取消订单
	 * @return
	 */
	public String cancelOrder(){
		this.orderService.cancelOrder(orderid);
		orderService.unlock(orderid);
		request.put("message", "订单取消成功");
		request.put("urladdress", "/control/order/list.action");
		return "message";
	}/**
	 * 审核通过订单
	 * @return
	 */
	public String confirmOrder(){
		this.orderService.confirmOrder(orderid);
		orderService.unlock(orderid);
		request.put("message", "订单审核成功");
		request.put("urladdress", "/control/order/list.action");
		return "message";
	}
	/**
	 * 财务确认已付款
	 * @return
	 */
	public String confirmPayment(){
		this.orderService.confirmPayment(orderid);
		
		request.put("message", "订单已设置支付状态");
		request.put("urladdress", "/control/order/list.action?state=WAITPAYMENT");
		return "message";
	}/**
	 * 将订单设置为等待发货状态
	 * @return
	 */
	public String turnWaitdeliver(){
		this.orderService.turnWaitdeliver(orderid);
		
		request.put("message", "订单已设置等待发货状态");
		request.put("urladdress", "/control/order/list.action?state=ADMEASUREPRODUCT");
		return "message";
	}
	
	/**
	 * 将订单设置为已经发货状态
	 * @return
	 */
	public String turnDelivered(){
		this.orderService.turnDelivered(orderid);
		
		request.put("message", "订单已设置已经发货状态");
		request.put("urladdress", "/control/order/list.action?state=WAITDELIVER");
		return "message";
	}
	
	
	/**
	 * 将订单设置为已经收货状态
	 * @return
	 */
	public String turnReceived(){
		this.orderService.turnReceived(orderid);
		
		request.put("message", "订单已设置已经收货状态");
		request.put("urladdress", "/control/order/list.action?state=DELIVERED");
		return "message";
	}
}
