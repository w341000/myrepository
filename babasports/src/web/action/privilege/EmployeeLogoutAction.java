package web.action.privilege;

import org.apache.struts2.ServletActionContext;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;

import bean.privilege.Employee;
import web.action.BaseAction;
/**
 * Ô±¹¤ÍË³öµÇÂ¼
 * @author Administrator
 *
 */
@Controller @Scope("prototype")
public class EmployeeLogoutAction extends BaseAction<Employee> {

	@Override
	public String execute() throws Exception {
		ServletActionContext.getRequest().getSession().removeAttribute("employee");
		return SUCCESS;
	}
	
}
