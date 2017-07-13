package web.action.shopping;

import java.io.IOException;

import javax.annotation.Resource;

import org.apache.struts2.ServletActionContext;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;

import bean.BuyCart;
import bean.book.DeliverWay;
import bean.book.Order;
import bean.book.OrderContactInfo;
import bean.book.OrderDeliverInfo;
import bean.book.PaymentWay;
import bean.user.Buyer;
import bean.user.Gender;
import service.book.OrderService;
import sun.misc.BASE64Decoder;
import utils.WebUtil;
import web.action.BaseAction;
@Controller @Scope("prototype")
public class ShoppingManageAction extends BaseAction<Object> {
	//收货人姓名
	private String recipients;
	private Gender gender;
	private String address;
	private String email;
	private String postalcode;
	private String tel;
	private String mobile;
	private boolean buyerIsrecipients;
	//购买人姓名
	private String buyer;
	private Gender buyer_gender;
	private String buyer_address;
	private String buyer_postalcode;
	private String buyer_mobile;
	private String buyer_tel;
	
	private String directUrl;
	
	/**时间要求**/
	private String requirement;
	private DeliverWay deliverway;
	private PaymentWay paymentway;
	
	private String note;
	/**特殊说明**/
	private String delivernote;
	
	@Resource
	private OrderService orderService;
	public String getNote() {
		return note;
	}
	public void setNote(String note) {
		this.note = note;
	}


	
	public String getDirectUrl() {
		return directUrl;
	}
	public void setDirectUrl(String directUrl) {
		this.directUrl = directUrl;
	}
	public String getRequirement() {
		return requirement;
	}
	public void setRequirement(String requirement) {
		this.requirement = requirement;
	}
	public DeliverWay getDeliverway() {
		return deliverway;
	}
	public void setDeliverway(DeliverWay deliverway) {
		this.deliverway = deliverway;
	}
	public PaymentWay getPaymentway() {
		return paymentway;
	}
	public void setPaymentway(PaymentWay paymentway) {
		this.paymentway = paymentway;
	}
	public String getDelivernote() {
		return delivernote;
	}
	public void setDelivernote(String delivernote) {
		this.delivernote = delivernote;
	}
	public String getRecipients() {
		return recipients;
	}
	public void setRecipients(String recipients) {
		this.recipients = recipients;
	}
	public Gender getGender() {
		return gender;
	}
	public void setGender(Gender gender) {
		this.gender = gender;
	}
	public String getAddress() {
		return address;
	}
	public void setAddress(String address) {
		this.address = address;
	}
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	public String getPostalcode() {
		return postalcode;
	}
	public void setPostalcode(String postalcode) {
		this.postalcode = postalcode;
	}
	public String getTel() {
		return tel;
	}
	public void setTel(String tel) {
		this.tel = tel;
	}
	public String getMobile() {
		return mobile;
	}
	public void setMobile(String mobile) {
		this.mobile = mobile;
	}
	public boolean isBuyerIsrecipients() {
		return buyerIsrecipients;
	}
	public void setBuyerIsrecipients(boolean buyerIsrecipients) {
		this.buyerIsrecipients = buyerIsrecipients;
	}
	public String getBuyer() {
		return buyer;
	}
	public void setBuyer(String buyer) {
		this.buyer = buyer;
	}
	public Gender getBuyer_gender() {
		return buyer_gender;
	}
	public void setBuyer_gender(Gender buyer_gender) {
		this.buyer_gender = buyer_gender;
	}
	public String getBuyer_address() {
		return buyer_address;
	}
	public void setBuyer_address(String buyer_address) {
		this.buyer_address = buyer_address;
	}
	public String getBuyer_postalcode() {
		return buyer_postalcode;
	}
	public void setBuyer_postalcode(String buyer_postalcode) {
		this.buyer_postalcode = buyer_postalcode;
	}
	public String getBuyer_mobile() {
		return buyer_mobile;
	}
	public void setBuyer_mobile(String buyer_mobile) {
		this.buyer_mobile = buyer_mobile;
	}
	public String getBuyer_tel() {
		return buyer_tel;
	}
	public void setBuyer_tel(String buyer_tel) {
		this.buyer_tel = buyer_tel;
	}
	
	/**保存配送信息
	 */
	public String saveDeliverInfo() throws Exception {
		BuyCart cart=WebUtil.getBuyCart(ServletActionContext.getRequest());
		cart.setDeliverInfo(new OrderDeliverInfo());
		cart.getDeliverInfo().setRecipients(recipients);
		cart.getDeliverInfo().setGender(gender);
		cart.getDeliverInfo().setEmail(email);
		cart.getDeliverInfo().setTel(tel);
		cart.getDeliverInfo().setMobile(mobile);
		cart.getDeliverInfo().setAddress(address);
		cart.getDeliverInfo().setPostalcode(postalcode);
		cart.setBuyerIsrecipients(buyerIsrecipients);
		
		//判断购买人与收货人是否相同
		cart.setContactInfo(new OrderContactInfo());
		if(buyerIsrecipients){//相同
			cart.getContactInfo().setBuyerName(recipients);
			cart.getContactInfo().setGender(gender);
			cart.getContactInfo().setAddress(address);
			cart.getContactInfo().setMobile(mobile);
			cart.getContactInfo().setPostalcode(postalcode);
			cart.getContactInfo().setTel(tel);
			cart.getContactInfo().setMobile(mobile);
			cart.getContactInfo().setEmail(email);
		}else{
			cart.getContactInfo().setBuyerName(this.buyer);
			cart.getContactInfo().setGender(this.buyer_gender);
			cart.getContactInfo().setAddress(this.buyer_address);
			cart.getContactInfo().setMobile(this.buyer_mobile);
			cart.getContactInfo().setPostalcode(this.buyer_postalcode);
			cart.getContactInfo().setTel(this.buyer_tel);
			cart.getContactInfo().setMobile(this.buyer_mobile);
			cart.getContactInfo().setEmail(WebUtil.getBuyer(ServletActionContext.getRequest()).getEmail());
		}
		//如果用户从其他页面而来则返回那个页面
		if(directUrl!=null && !"".equals(directUrl.trim())){
					BASE64Decoder decoder=new BASE64Decoder();
					String url=new String(decoder.decodeBuffer(directUrl.trim()));
					ServletActionContext.getResponse().sendRedirect(url);
					return null;
		}
		return SUCCESS;
	}
	
	/**保存用户选择的送货方式与支付方式**/
	public String savePaymentway(){
		BuyCart cart=WebUtil.getBuyCart(ServletActionContext.getRequest());
		cart.getDeliverInfo().setDeliverWay(this.deliverway);
		cart.setPaymentWay(this.paymentway);
		if(DeliverWay.EXPRESSDELIVERY.equals(deliverway) || DeliverWay.EXIGENCEEXPRESSDELIVERY.equals(deliverway)){
			if("other".equals(requirement)){//特殊说明,保存文本框的数据
				cart.getDeliverInfo().setRequirement(this.delivernote);
			}else{
				cart.getDeliverInfo().setRequirement(requirement);
			}
		}else{
			cart.getDeliverInfo().setRequirement(null);
		}
		return "confirm";
	}
	
	
	/**提交和保存订单
	 * @throws IOException **/
	public String saveorder() throws IOException{
		BuyCart cart=WebUtil.getBuyCart(ServletActionContext.getRequest());
		cart.setNote(note);
		Order order=orderService.createOrder(cart, WebUtil.getBuyer(ServletActionContext.getRequest()).getUsername());
		WebUtil.deleteBuyCart(ServletActionContext.getRequest());
		
		ServletActionContext.getResponse().sendRedirect("/shopping/finish.action?orderid="+order.getOrderid()+"&paymentway="+order.getPaymentWay()+"&payablefee="+order.getPayablefee());
		return null;
	}
	
}
