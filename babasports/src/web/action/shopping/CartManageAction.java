package web.action.shopping;

import java.io.IOException;

import org.apache.struts2.ServletActionContext;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;

import sun.misc.BASE64Decoder;

import bean.BuyCart;
import bean.BuyItem;
import bean.product.ProductInfo;
import bean.product.ProductStyle;
import web.action.BaseAction;
@Controller @Scope("prototype")
public class CartManageAction extends BaseAction<BuyCart> {

	private Integer productid;
	private Integer  styleid;
	private Integer  amount;
	private String directUrl;
	public String getDirectUrl() {
		return directUrl;
	}
	public void setDirectUrl(String directUrl) {
		this.directUrl = directUrl;
	}
	public Integer getAmount() {
		return amount;
	}
	public void setAmount(Integer amount) {
		this.amount = amount;
	}
	public Integer getProductid() {
		return productid;
	}
	public void setProductid(Integer productid) {
		this.productid = productid;
	}
	public Integer getStyleid() {
		return styleid;
	}
	public void setStyleid(Integer styleid) {
		this.styleid = styleid;
	}


	private BuyCart getBuyCart(){
		BuyCart buycart=(BuyCart) ServletActionContext.getRequest().getSession().getAttribute("buyCart");
		return buycart;
	}
	/**
	 * 删除指定购物项
	 * @return
	 */
	
	public String delete(){
		BuyCart buycart=getBuyCart();
		if(buycart!=null){
			ProductInfo product=new ProductInfo(productid);
			product.addProductStyle(new ProductStyle(this.getStyleid()));
			buycart.removeBuyItem(new BuyItem(product));
		}
		
		return SUCCESS;
	}
	/**
	 * 删除所有购物项
	 */
	public String deleteAll(){
		BuyCart buycart=getBuyCart();
		if(buycart!=null){
			buycart.removeAll();
		}
		
		return SUCCESS;
	}
	/**
	 *购物项的数量
	 * @return
	 */
	public String updateAmount(){
		BuyCart buycart=getBuyCart();
		ProductInfo product=new ProductInfo(productid);
		product.addProductStyle(new ProductStyle(styleid));
		BuyItem bi=new BuyItem(product, amount);
		if(buycart!=null){
			for(BuyItem item:buycart.getItems()){
				if(bi.equals(item))
					item.setAmount(bi.getAmount());
			}
		}
		return SUCCESS;
	}
	
	
	
	
	/**
	 * 结算中心
	 * @return
	 * @throws IOException 
	 */
	
	public String settleAccounts() throws IOException{
		BuyCart buycart=getBuyCart();
		for(BuyItem item:buycart.getItems()){
			String paramName="amount_"+item.getProduct().getId()+"_"+item.getProduct().getStyles().iterator().next().getId();
			String amount=ServletActionContext.getRequest().getParameter(paramName);
			if(amount!=null && !"".equals(amount.trim()))
				item.setAmount(Integer.parseInt(amount));
		}
		//如果用户从其他页面而来则返回那个页面
		if(directUrl!=null && !"".equals(directUrl.trim())){
			BASE64Decoder decoder=new BASE64Decoder();
			String url=new String(decoder.decodeBuffer(directUrl.trim()));
			ServletActionContext.getResponse().sendRedirect(url);
			return null;
		}
		return "settleAccounts";
	}
}
