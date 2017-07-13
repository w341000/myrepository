package web.action.shopping;

import javax.annotation.Resource;

import org.apache.struts2.ServletActionContext;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;

import bean.BuyCart;
import bean.user.Buyer;
import bean.user.Gender;

import service.user.BuyerService;
import utils.WebUtil;

import web.action.BaseAction;
@Controller @Scope("prototype")
public class DeliverInfoAction extends BaseAction<Object> {
	@Resource
	private BuyerService buyerService;
	private String recipients;
	private Gender gender;
	private String address;
	private String email;
	private String postalcode;
	private String tel;
	private String mobile;
	private boolean buyerIsrecipients;
	private String buyer;
	private Gender buyer_gender;
	private String buyer_address;
	private String buyer_postalcode;
	private String buyer_mobile;
	private String buyer_tel;
	
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

	@Override
	public String execute() throws Exception {
		BuyCart cart=WebUtil.getBuyCart(ServletActionContext.getRequest());
		//为属性添加默认值
		gender=Gender.MAN;
		buyer_gender=Gender.MAN;
		email=WebUtil.getBuyer(ServletActionContext.getRequest()).getEmail();
		buyerIsrecipients=true;
		Buyer user=WebUtil.getBuyer(ServletActionContext.getRequest());
		if(cart.getDeliverInfo()!=null){
			postalcode=cart.getDeliverInfo().getPostalcode();
			recipients=cart.getDeliverInfo().getRecipients();
			mobile=cart.getDeliverInfo().getMobile();
			address=cart.getDeliverInfo().getAddress();
			gender=cart.getDeliverInfo().getGender();
			email=cart.getDeliverInfo().getEmail();
			tel=cart.getDeliverInfo().getTel();
		}else{
			if(user.getContactInfo()!=null){
				postalcode=user.getContactInfo().getPostalcode();
				recipients=user.getRealname();
				mobile=user.getContactInfo().getMobile();;
				address=user.getContactInfo().getAddress();
				gender=user.getGender();
				tel=user.getContactInfo().getPhone();
				email=user.getEmail();
			}
		}
		if(cart.isBuyerIsrecipients()!=null) buyerIsrecipients=cart.isBuyerIsrecipients();
		if(cart.getContactInfo()!=null){
			buyer=cart.getContactInfo().getBuyerName();
			buyer_address=cart.getContactInfo().getAddress();
			buyer_mobile=cart.getContactInfo().getMobile();
			buyer_postalcode=cart.getContactInfo().getPostalcode();
			buyer_gender=cart.getContactInfo().getGender();
			buyer_tel=cart.getContactInfo().getTel();
		}else{
			if(user.getContactInfo()!=null){
				buyer=user.getRealname();
				buyer_address=user.getContactInfo().getAddress();
				buyer_mobile=user.getContactInfo().getMobile();;
				buyer_postalcode=user.getContactInfo().getPostalcode();
				buyer_gender=user.getGender();
				buyer_tel=user.getContactInfo().getPhone();
			}
			
		}
		return SUCCESS;
	}
	
}
