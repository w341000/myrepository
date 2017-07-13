package web.action.user;

import org.apache.struts2.ServletActionContext;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;

import bean.user.Buyer;

import utils.WebUtil;
import web.action.BaseAction;
@Controller @Scope("prototype")
public class BuyerLogoutAction extends BaseAction<Buyer> {

	@Override
	public String execute() throws Exception {
		Buyer buyer=WebUtil.getBuyer(ServletActionContext.getRequest());
		if(buyer!=null){
			ServletActionContext.getRequest().getSession().setAttribute("user", null);
		}
		request.put("message", "ÍË³öµÇÂ¼³É¹¦");
		request.put("urladdress", "/");
		return "message";
	}
	
	

}
