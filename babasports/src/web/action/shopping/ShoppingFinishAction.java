package web.action.shopping;

import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;

import bean.book.PaymentWay;

import web.action.BaseAction;
@Controller @Scope("prototype")
/**
 * ∂©µ•Ã·Ωªaction
 * @author Administrator
 *
 */
public class ShoppingFinishAction extends BaseAction<Object> {
	private String orderid;
	private PaymentWay Paymentway;
	private Float payablefee;
	private String result;
	

	public String getResult() {
		return result;
	}
	public void setResult(String result) {
		this.result = result;
	}
	public String getOrderid() {
		return orderid;
	}
	public void setOrderid(String orderid) {
		this.orderid = orderid;
	}
	public PaymentWay getPaymentway() {
		return Paymentway;
	}
	public void setPaymentway(PaymentWay paymentway) {
		Paymentway = paymentway;
	}
	public Float getPayablefee() {
		return payablefee;
	}
	public void setPayablefee(Float payablefee) {
		this.payablefee = payablefee;
	}


	@Override
	public String execute() throws Exception {
		result="postofficeremittance";
		if(PaymentWay.COD.equals(Paymentway)){
			result="cod";
		}else if(PaymentWay.NET.equals(Paymentway)){
			result="net";
		}
		else if(PaymentWay.BANKREMITTANCE.equals(Paymentway)){
			result="bankremittance";
		}
	
		
		
		return SUCCESS;
	}

	
}
