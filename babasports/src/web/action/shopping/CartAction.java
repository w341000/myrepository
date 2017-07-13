package web.action.shopping;

import java.util.Date;

import javax.annotation.Resource;
import javax.servlet.http.HttpSession;

import org.apache.struts2.ServletActionContext;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;

import bean.BuyCart;
import bean.BuyItem;
import bean.product.ProductInfo;
import bean.product.ProductStyle;

import service.product.ProductInfoService;
import utils.WebUtil;
import web.action.BaseAction;
@Controller @Scope("prototype")
public class CartAction extends BaseAction<Object> {
	private Integer productid;
	private Integer  styleid;
	@Resource
	private ProductInfoService productInfoService;
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


	@Override
	public String execute() throws Exception {
		//从用户当前会话获取购物车
		BuyCart buycart=(BuyCart) ServletActionContext.getRequest().getSession().getAttribute("buyCart");
		if(buycart==null){//如果当前会话无法获取到,则从用户以前的会话获取购物车
			String sid=WebUtil.getCookieByName(ServletActionContext.getRequest(), "buyCartID");
			if(sid!=null){
				HttpSession  session=SiteSessionListener.getSession(sid);
				if(session!=null){//用户以前的session存在
					buycart=(BuyCart) session.getAttribute("buyCart");
					if(buycart!=null){//如果从用户以前的session中获取到了购物车
						SiteSessionListener.removeSession(sid);//移除对session的引用,并将当前购物车放入当前session,对cookie也重新设置
						ServletActionContext.getRequest().getSession().setAttribute("buyCart", buycart);
						WebUtil.addCookie(ServletActionContext.getResponse(), "buyCartID", 
								ServletActionContext.getRequest().getSession().getId(), ServletActionContext.getRequest().getSession().getMaxInactiveInterval());
					}
				}
			}
		}
		if(buycart==null){//如果从用户以前的会话也无法获取购物车,则将一个购物车放入用户现在的session
			buycart=new BuyCart();
			ServletActionContext.getRequest().getSession().setAttribute("buyCart", buycart);
			//将当前有购物车的session的id写入cookie中,最大存活时间为session有效时间
			WebUtil.addCookie(ServletActionContext.getResponse(), "buyCartID", 
					ServletActionContext.getRequest().getSession().getId(), ServletActionContext.getRequest().getSession().getMaxInactiveInterval());
		}
		
		if(productid!=null && productid>0){
			ProductInfo product=productInfoService.get(productid);
			if(product!=null){
				ProductStyle currnetstyle = null;
				for(ProductStyle style:product.getStyles()){//找到对应样式id
					if(style.isVisible() && style.getId().equals(styleid)){
						currnetstyle=style;
						break;
					}
				}
				product.getStyles().clear();	//清除所有样式,然后放入被选样式
				if(currnetstyle!=null) product.addProductStyle(currnetstyle);//只放入被选中的样式
				buycart.addItem(new BuyItem(product,1));//把商品放入购物车
			}
		}
		
		request.put("buyCart", buycart);
		return SUCCESS;
	}

}
