package bos.web.action;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.annotation.Resource;

import org.apache.commons.lang3.StringUtils;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;

import cn.itcast.crm.domain.Customer;

import bos.crm.CustomerService;
import bos.domain.Decidedzone;
import bos.service.IDecidedzoneService;
import bos.web.action.base.BaseAction;
@Controller @Scope("prototype")
public class DecidedzoneAction extends BaseAction<Decidedzone> {
	//接受分区id
	private String[] subareaid;
	//接受客户id
	private Integer[] customerIds;
	@Resource
	private IDecidedzoneService decidedzoneService;
	@Resource
	private CustomerService customerService;
	
	public void setCustomerIds(Integer[] customerIds) {
		this.customerIds = customerIds;
	}
	public void setSubareaid(String[] subareaid) {
		this.subareaid = subareaid;
	}
	
	/**
	 * 添加定区
	 */
	public String add(){
		decidedzoneService.save(model,subareaid);
		return "list";
	}
	/**
	 * 列出定区已经关联的分区数据
	 * @return
	 * @throws IOException 
	 */
	@SuppressWarnings("unchecked")
	public String listAssociationSubareaAjax() throws IOException{
		if(StringUtils.isNotBlank(model.getId())){
			Decidedzone decidedzone=decidedzoneService.findById(model.getId());
			List list =new ArrayList(decidedzone.getSubareas());
			String[] excludes=new String[]{"decidedzone","subareas"};
			this.WriteList2Json(list, excludes);
		}
		return NONE;
	}
	/**
	 * 更新操作
	 * @return
	 */
	public String edit(){
		Decidedzone decidedzone = decidedzoneService.findById(model.getId());
		decidedzone.setName(model.getName());
		decidedzone.setStaff(model.getStaff());
		decidedzoneService.update(decidedzone,subareaid);
		return "list";
	}
	/**
	 * 分页查询
	 */
	public String pageQuery() throws IOException{
		decidedzoneService.pageQuery(pageBean);
		String[] excludes = new String[] { "pageSize", "currentPage",
				"detachedCriteria", "subareas", "decidedzones" };
		this.WritePageBean2Json(excludes);
		return NONE;
	}
	/**
	 * 查找未关联定区的客户
	 * @throws IOException 
	 */
	public String findnoassociationCustomers() throws IOException{
		List<Customer> list = customerService.findnoassociationCustomers();
		String[] excludes=new String[]{"station","address"};
		this.WriteList2Json(list, excludes);
		return NONE;
	}
	/**
	 * 查找已关联到定区的客户
	 * @throws IOException
	 */
	public String findhasassociationCustomers() throws IOException{
		List<Customer> list = customerService.findhasassociationCustomers(model.getId());
		String[] excludes=new String[]{"station","address"};
		this.WriteList2Json(list, excludes);
		return NONE;
	}
	/**
	 * 定区关联客户
	 */
	public String assigncustomerstodecidedzone(){
		customerService.assignCustomersToDecidedZone(customerIds, model.getId());
		return "list";
	}
}
