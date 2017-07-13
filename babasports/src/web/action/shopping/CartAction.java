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
		//���û���ǰ�Ự��ȡ���ﳵ
		BuyCart buycart=(BuyCart) ServletActionContext.getRequest().getSession().getAttribute("buyCart");
		if(buycart==null){//�����ǰ�Ự�޷���ȡ��,����û���ǰ�ĻỰ��ȡ���ﳵ
			String sid=WebUtil.getCookieByName(ServletActionContext.getRequest(), "buyCartID");
			if(sid!=null){
				HttpSession  session=SiteSessionListener.getSession(sid);
				if(session!=null){//�û���ǰ��session����
					buycart=(BuyCart) session.getAttribute("buyCart");
					if(buycart!=null){//������û���ǰ��session�л�ȡ���˹��ﳵ
						SiteSessionListener.removeSession(sid);//�Ƴ���session������,������ǰ���ﳵ���뵱ǰsession,��cookieҲ��������
						ServletActionContext.getRequest().getSession().setAttribute("buyCart", buycart);
						WebUtil.addCookie(ServletActionContext.getResponse(), "buyCartID", 
								ServletActionContext.getRequest().getSession().getId(), ServletActionContext.getRequest().getSession().getMaxInactiveInterval());
					}
				}
			}
		}
		if(buycart==null){//������û���ǰ�ĻỰҲ�޷���ȡ���ﳵ,��һ�����ﳵ�����û����ڵ�session
			buycart=new BuyCart();
			ServletActionContext.getRequest().getSession().setAttribute("buyCart", buycart);
			//����ǰ�й��ﳵ��session��idд��cookie��,�����ʱ��Ϊsession��Чʱ��
			WebUtil.addCookie(ServletActionContext.getResponse(), "buyCartID", 
					ServletActionContext.getRequest().getSession().getId(), ServletActionContext.getRequest().getSession().getMaxInactiveInterval());
		}
		
		if(productid!=null && productid>0){
			ProductInfo product=productInfoService.get(productid);
			if(product!=null){
				ProductStyle currnetstyle = null;
				for(ProductStyle style:product.getStyles()){//�ҵ���Ӧ��ʽid
					if(style.isVisible() && style.getId().equals(styleid)){
						currnetstyle=style;
						break;
					}
				}
				product.getStyles().clear();	//���������ʽ,Ȼ����뱻ѡ��ʽ
				if(currnetstyle!=null) product.addProductStyle(currnetstyle);//ֻ���뱻ѡ�е���ʽ
				buycart.addItem(new BuyItem(product,1));//����Ʒ���빺�ﳵ
			}
		}
		
		request.put("buyCart", buycart);
		return SUCCESS;
	}

}
