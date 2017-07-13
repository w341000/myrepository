package web.action.user;

import java.io.Serializable;

import javax.annotation.Resource;

import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;

import service.user.BuyerService;
import utils.PropertyUtil;

import bean.user.Buyer;

import web.action.BaseAction;
@Controller("buyerManageAction") @Scope("prototype")
public class BuyerManageAction extends BaseAction<Buyer> {
	@Resource
	private BuyerService buyerService;
	
	private String[] usernames;
	public String[] getUsernames() {
		return usernames;
	}
	public void setUsernames(String[] usernames) {
		this.usernames = usernames;
	}

	/**
	 * �����û��˺�
	 * @return
	 */
	public String delete(){
		buyerService.delete((Serializable[])usernames);
		request.put("message", "�˺Ž��óɹ�");
		request.put("urladdress", PropertyUtil.readUrl("control.user.list"));
		return "message";
	}

	/**
	 * �����û��˺�
	 * @return
	 */
	public String enable(){
		buyerService.enable((Serializable[])usernames);
		request.put("message", "�˺����óɹ�");
		request.put("urladdress", PropertyUtil.readUrl("control.user.list"));
		return "message";
		
	}
}
