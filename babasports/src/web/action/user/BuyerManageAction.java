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
	 * 禁用用户账号
	 * @return
	 */
	public String delete(){
		buyerService.delete((Serializable[])usernames);
		request.put("message", "账号禁用成功");
		request.put("urladdress", PropertyUtil.readUrl("control.user.list"));
		return "message";
	}

	/**
	 * 启用用户账号
	 * @return
	 */
	public String enable(){
		buyerService.enable((Serializable[])usernames);
		request.put("message", "账号启用成功");
		request.put("urladdress", PropertyUtil.readUrl("control.user.list"));
		return "message";
		
	}
}
