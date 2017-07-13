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
 * ��������
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
	 * �ͷ�������ӽ���
	 */
	public String addMessageUI(){
		return "addmessage";
	}
	
	/**
	 * ��ӿͷ�����
	 * @return
	 */
	public String addMessage(){
		
		Message msg=new Message();
		msg.setContent(message);
		String username=((Employee)ServletActionContext.getRequest().getSession().getAttribute("employee")).getUsername();
		msg.setUsername(username);
		msg.setOrder(new Order(orderid));
		messageService.save(msg);
		request.put("message", "������Գɹ�");
		request.put("urladdress", "/control/order/view?orderid="+orderid);
		return "message";
	}
	
	/**
	 * ǿ�н�������
	 * @return
	 */
	public String allUnLock(){
		orderService.unlock(orderids);
		request.put("message", "���������ɹ�");
		request.put("urladdress", "/control/lockorder/list");
		return "message";
	}
	/**
	 * ��������
	 * @return
	 */
	
	public String employeeUnlockOrder(){
		orderService.unlock(orderid);
		
		return "list";
	}
	/**
	 * �޸Ķ�������Ϣ����
	 * @return
	 */
	public String modifyContactInfoUI(){
		request.put("contactinfo", orderContactInfoService.get(contactInfo.getContactid()));
		return "modifyContactInfoUI";
	}
	
	/**
	 * �޸Ķ�������Ϣ
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
		request.put("message", "��������Ϣ�޸ĳɹ�");
		request.put("urladdress", "/control/order/view.action?orderid="+orderid);
		return "message";
		
	}
	/**
	 * �޸��ջ�����Ϣ����
	 * @return
	 */
	public String modifyDeliverInfoUI(){
		request.put("deliverInfo", orderDeliverInfoService.get(deliverInfo.getDeliverid()));
		return "modifyDeliverInfoUI";
	}
	
	/**
	 * �޸��ջ�����Ϣ
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
		request.put("message", "�ջ�����Ϣ�޸ĳɹ�");
		request.put("urladdress", "/control/order/view.action?orderid="+orderid);
		return "message";
		
	}
	
	/**
	 * ֧����ʽ�޸Ľ���
	 * @return
	 */
	public String modifyPaymentWayUI(){
		Order order=orderService.get(orderid);
		request.put("order", order);
		return "modifyPaymentWayUI";
	}
	/**
	 * ֧����ʽ�޸�
	 * @return
	 */
	public String modifyPaymentWay(){
		orderService.updatePaymentWay(orderid, paymentWay);
		request.put("message", "֧����ʽ�޸ĳɹ�");
		request.put("urladdress", "/control/order/view.action?orderid="+orderid);
		return "message";
	}
	
	
	/**
	 * ���ͷ�ʽ�޸Ľ���
	 * @return
	 */
	public String modifyDeliverWayUI(){
		Order order=orderService.get(orderid);
		request.put("order", order);
		return "modifyDeliverWayUI";
		
	}
	/**
	 * ���ͷ�ʽ�޸�
	 * @return
	 */
	public String modifyDeliverWay(){
		orderService.updateDeliverWay(orderid, deliverWay);
		request.put("message", "֧����ʽ�޸ĳɹ�");
		request.put("urladdress", "/control/order/view.action?orderid="+orderid);
		return "message";
	}
	
	/**
	 * �޸Ĳ�Ʒ��������
	 * @return
	 */
	public String modifyProductAmountUI(){
		orderItem=orderItemService.get(orderItem.getItemid());

		return "modifyProductAmountUI";
	}
	
	/**
	 * �޸Ĳ�Ʒ����
	 * @return
	 */
	public String modifyProductAmount(){
		this.orderItemService.updateAmount(orderItem.getItemid(),orderItem.getAmount());
		request.put("message", "��Ʒ�����޸ĳɹ�");
		request.put("urladdress", "/control/order/view.action?orderid="+orderid);
		return "message";
	}
	/**
	 * ɾ��������
	 * @return
	 */
	public String deleteOrderItem(){
		this.orderItemService.delete(orderItem.getItemid());
		request.put("message", "������ɾ���ɹ�");
		request.put("urladdress", "/control/order/view.action?orderid="+orderid);
		return "message";
	}
	/**
	 * ���ͷ��޸Ľ���
	 * @return
	 */
	public String modifyDeliverFeeUI(){
		
		request.put("fee", this.orderService.get(orderid).getDeliverFee());
		return "modifyDeliverFeeUI";
	}
	/**
	 * ���ͷ��޸�
	 * @return
	 */
	public String modifyDeliverFee(){
		this.orderService.updateDeliverFee(orderid, fee);
		request.put("message", "�������ͷѳɹ�");
		request.put("urladdress", "/control/order/view.action?orderid="+orderid);
		return "message";
	}
	/**
	 * ��ӡ����
	 * @return
	 */
	public String printOrder(){
		request.put("order", this.orderService.get(orderid));
		return "printOrder";
	}
	
	
	/**
	 * ȡ������
	 * @return
	 */
	public String cancelOrder(){
		this.orderService.cancelOrder(orderid);
		orderService.unlock(orderid);
		request.put("message", "����ȡ���ɹ�");
		request.put("urladdress", "/control/order/list.action");
		return "message";
	}/**
	 * ���ͨ������
	 * @return
	 */
	public String confirmOrder(){
		this.orderService.confirmOrder(orderid);
		orderService.unlock(orderid);
		request.put("message", "������˳ɹ�");
		request.put("urladdress", "/control/order/list.action");
		return "message";
	}
	/**
	 * ����ȷ���Ѹ���
	 * @return
	 */
	public String confirmPayment(){
		this.orderService.confirmPayment(orderid);
		
		request.put("message", "����������֧��״̬");
		request.put("urladdress", "/control/order/list.action?state=WAITPAYMENT");
		return "message";
	}/**
	 * ����������Ϊ�ȴ�����״̬
	 * @return
	 */
	public String turnWaitdeliver(){
		this.orderService.turnWaitdeliver(orderid);
		
		request.put("message", "���������õȴ�����״̬");
		request.put("urladdress", "/control/order/list.action?state=ADMEASUREPRODUCT");
		return "message";
	}
	
	/**
	 * ����������Ϊ�Ѿ�����״̬
	 * @return
	 */
	public String turnDelivered(){
		this.orderService.turnDelivered(orderid);
		
		request.put("message", "�����������Ѿ�����״̬");
		request.put("urladdress", "/control/order/list.action?state=WAITDELIVER");
		return "message";
	}
	
	
	/**
	 * ����������Ϊ�Ѿ��ջ�״̬
	 * @return
	 */
	public String turnReceived(){
		this.orderService.turnReceived(orderid);
		
		request.put("message", "�����������Ѿ��ջ�״̬");
		request.put("urladdress", "/control/order/list.action?state=DELIVERED");
		return "message";
	}
}
