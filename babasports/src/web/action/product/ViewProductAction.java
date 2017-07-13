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
 * �����Ʒ
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
			 	request.put("message", "��ȡ��������Ҫ����Ĳ�Ʒ!");
				request.put("urladdress", "/");
				return "message";
		 }//��������ʷ��Ϣ��cookie
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
	 * ��Ӳ�Ʒ��ʷ��¼��cookie��
	 * @return
	 */
	public String addCookie(){
		WebUtil.addCookie(ServletActionContext.getResponse(), "productViewHistory", 
				buildViewHistory(ServletActionContext.getRequest(), productid), 30*24*60*60);
		return null;
	}
	
	private String buildViewHistory(HttpServletRequest request,Integer productid){
		//����cookie����ʷ��Ϣ
		//������������id�Ѿ��������ʷ��,������ƶ�����ǰ��
		//��������ʷ�Ѿ��ﵽ��10����Ʒ,��Ҫ�����Ƚ����Ԫ��ɾ��
		String cookieValue=WebUtil.getCookieByName(request, "productViewHistory");
		LinkedList<Integer> productids=new LinkedList<Integer>();
		//���cookieֵ��Ϊ��,��ȡ��ֵ�����б���
		if(cookieValue!=null && !"".equals(cookieValue)){
			String[] ids=cookieValue.split("-");
			for(String id:ids){
				productids.offer(Integer.valueOf(id.trim()));
			}//cookie���Ѿ��е�������Ĳ�Ʒid,��ɾ��cookie�еĲ�Ʒid
			if(productids.contains(productid)) productids.remove(productid);
			//cookie�ڵ�����Ĳ�Ʒ�������ڻ����10,���Ƴ���һ��Ԫ��
			if(productids.size()>=10) productids.poll();
		}//����ǰ����Ĳ�Ʒid��ӵ��б���
		productids.offerFirst(productid);
		StringBuffer sb=new StringBuffer();
		for(Integer id:productids){
			sb.append(id).append("-");
		}
		sb.deleteCharAt(sb.length()-1);
		return sb.toString();
	}
}
