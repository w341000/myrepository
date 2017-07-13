package web.action.privilege;

import javax.annotation.Resource;


import org.apache.struts2.ServletActionContext;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;

import service.privilege.EmployeeService;

import bean.privilege.Employee;
import web.action.BaseAction;
/**
 * Ա����¼
 * @author Administrator
 *
 */
@Controller @Scope("prototype")
public class EmployeeLogonAction extends BaseAction<Employee> {
	@Resource
	private  EmployeeService employeeService;
	
	@Override
	public String execute() throws Exception {
		if(model.getUsername()!=null && !"".equals(model.getUsername().trim()) 
				&& model.getPassword()!=null && !"".equals(model.getPassword().trim())){
			if(employeeService.validate(model.getUsername(), model.getPassword())){
				ServletActionContext.getRequest().getSession().setAttribute("employee", employeeService.get(model.getUsername()));
				return "control";
			}
			request.put("message", "�û������������!");
		}
		return SUCCESS;
	}

	
	
}
