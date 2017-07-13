package web.action.product;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;

import service.product.ProductStyleService;

import bean.product.ProductStyle;

import web.action.BaseAction;
@Controller @Scope("prototype")
public class ProductStyleAction extends BaseAction<ProductStyle> {
	@Resource
	private ProductStyleService productStyleService;
	private Integer productid;
	public Integer getProductid() {
		return productid;
	}
	public void setProductid(Integer productid) {
		this.productid = productid;
	}
	@Override
	public String execute() throws Exception {
		String orderBy=" order by visible desc,id asc";
		StringBuffer where=new StringBuffer();
		if(productid!=null && productid>0)
			where.append(" and product.id="+productid);
		List styles=productStyleService.findByWhere(orderBy, where.toString());
		request.put("styles", styles);
		return SUCCESS;
	}
	
	
}
