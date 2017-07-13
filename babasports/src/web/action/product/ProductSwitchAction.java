package web.action.product;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import javax.annotation.Resource;

import org.apache.struts2.ServletActionContext;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;

import service.product.ProductInfoService;
import service.product.ProductTypeService;
import utils.WebUtil;

import bean.product.ProductInfo;
import bean.product.ProductType;
import web.action.BaseAction;
@Controller("productSwitchAction") @Scope("prototype")
public class ProductSwitchAction extends BaseAction<ProductInfo> {
	@Resource
	private ProductInfoService productInfoService;
	@Resource
	private ProductTypeService productTypeService;
	
	private Integer typeid;
	private String productid;
	public Integer getTypeid() {
		return typeid;
	}
	public void setTypeid(Integer typeid) {
		this.typeid = typeid;
	}
	/**
	 * 获取10个最畅销的产品
	 * @return 
	 */
	
	public String topsell(){
		List list=productInfoService.getTopSell(typeid, 10);
		request.put("topsellproducts", list);
		return "topsell";
	}	
	
	
	/**
	 * 获取10个用户浏览过的商品
	 * @return 
	 */
	
	public String getViewHistory(){
		String cookieValue=WebUtil.getCookieByName(ServletActionContext.getRequest(), "productViewHistory");
		//12-44-55
		if(cookieValue!=null && !"".equals(cookieValue.trim())){
			String[] ids=cookieValue.split("-");
			Integer[] productids=new Integer[ids.length];
			for(int i=0;i<ids.length;i++){
				productids[i]=new Integer(ids[i].trim());
			}
			List list=productInfoService.getViewHistory(productids, 10);
			request.put("viewhistory", list);
		}
		return "viewHistory";
	}	
	
	

	
	/**
	 * 显示产品图片
	 * @return 
	 */

	public String showimage(){
		
		return "showimage";
	}	
	
	/**
	 * 获取推荐商品数据
	 * @return
	 */
	public String getCommend(){
		model=this.productInfoService.get(model.getId());
		Set<ProductInfo> set=model.getType().getProducts();
		List<ProductInfo> list=new ArrayList<ProductInfo>();
		for(ProductInfo product:set){
			if(product.getCommend() &&product!=model )
				list.add(product);
		}
		for(ProductType type:model.getType().getChildren()){
			for(ProductInfo product:type.getProducts())
				if(product.getCommend() &&product!=model )
					list.add(product);
		}
		request.put("products", list);
		return "commend";
		
	}
	
	
}
