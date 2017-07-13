package web.action.product;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import org.apache.struts2.ServletActionContext;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;

import com.opensymphony.xwork2.ActionContext;

import service.product.ProductInfoService;
import utils.PropertyUtil;
import utils.WebUtil;

import bean.product.ProductInfo;
import bean.product.ProductType;
import web.action.BaseAction;
/**
 * 浏览商品
 */
@Controller @Scope("prototype")
public class ViewProductAction extends BaseAction<ProductInfo> {
	@Resource
	private ProductInfoService productInfoService;
	private Integer productid;
	public Integer getProductid() {
		return productid;
	}
	public void setProductid(Integer productid) {
		this.productid = productid;
	}

	@Override
	public String execute() throws Exception {
		 ProductInfo product=productInfoService.get(productid);
		 if(product==null){
			 	request.put("message", "获取不到你需要浏览的产品!");
				request.put("urladdress", "/");
				return "message";
		 }//添加浏览历史信息的cookie
		WebUtil.addCookie(ServletActionContext.getResponse(), "productViewHistory", 
				buildViewHistory(ServletActionContext.getRequest(), productid), 30*24*60*60);
		List<ProductType> stypes=new ArrayList<ProductType>();
		ProductType parent=product.getType();
		while(parent!=null){
			stypes.add(0,parent);
			parent=parent.getParent();
		}
		request.put("stypes", stypes);
		request.put("product", product);
		return SUCCESS;
	}
	
	/**
	 * 添加产品历史记录到cookie中
	 * @return
	 */
	public String addCookie(){
		WebUtil.addCookie(ServletActionContext.getResponse(), "productViewHistory", 
				buildViewHistory(ServletActionContext.getRequest(), productid), 30*24*60*60);
		return null;
	}
	
	private String buildViewHistory(HttpServletRequest request,Integer productid){
		//构建cookie的历史信息
		//如果当期浏览的id已经在浏览历史中,则把它移动到最前面
		//如果浏览历史已经达到了10个产品,需要把最先进入的元素删除
		String cookieValue=WebUtil.getCookieByName(request, "productViewHistory");
		LinkedList<Integer> productids=new LinkedList<Integer>();
		//如果cookie值不为空,则取出值放入列表中
		if(cookieValue!=null && !"".equals(cookieValue)){
			String[] ids=cookieValue.split("-");
			for(String id:ids){
				productids.offer(Integer.valueOf(id.trim()));
			}//cookie中已经有当期浏览的产品id,则删除cookie中的产品id
			if(productids.contains(productid)) productids.remove(productid);
			//cookie内的浏览的产品数量大于或等于10,则移除第一个元素
			if(productids.size()>=10) productids.poll();
		}//将当前浏览的产品id添加到列表中
		productids.offerFirst(productid);
		StringBuffer sb=new StringBuffer();
		for(Integer id:productids){
			sb.append(id).append("-");
		}
		sb.deleteCharAt(sb.length()-1);
		return sb.toString();
	}
}
