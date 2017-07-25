package bos.web.action;

import java.io.IOException;

import javax.annotation.Resource;

import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;

import cn.itcast.crm.domain.Customer;

import bos.crm.CustomerService;
import bos.domain.Noticebill;
import bos.domain.User;
import bos.service.INoticebillService;
import bos.utils.BOSContext;
import bos.web.action.base.BaseAction;
@Controller @Scope("prototype")
public class NoticebillAction extends BaseAction<Noticebill> {
	@Resource
	private CustomerService customerService;
	@Resource
	private INoticebillService noticebillService;
	
	/**
	 * 调用代理对象,根据电话查找客户信息
	 * @return
	 * @throws IOException 
	 */
	public String findCustomerByTelephone() throws IOException{
		Customer customer = customerService.findCustomerByPhonenumber(model.getTelephone());
		String[] excludes=new String[]{};
		this.WriteObject2Json(customer, excludes);
		return NONE;
	}
	/**
	 * 保存业务通知单,尝试自动分单
	 * @return
	 */
	public String add(){
		User user=BOSContext.getLoginUser();
		model.setUser(user);
		noticebillService.save(model);
		
		return "addUI";
	}

}
