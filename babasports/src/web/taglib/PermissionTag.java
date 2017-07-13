package web.taglib;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.jsp.JspException;
import javax.servlet.jsp.tagext.TagSupport;

import bean.privilege.Employee;
import bean.privilege.PrivilegeGroup;
import bean.privilege.SystemPrivilege;
import bean.privilege.SystemPrivilegePK;
/**
 * 权限校验标签
 * @author Administrator
 *
 */
public class PermissionTag extends TagSupport {
	private String module;
	private String privilege;
	public String getModule() {
		return module;
	}
	public void setModule(String module) {
		this.module = module;
	}
	public String getPrivilege() {
		return privilege;
	}
	public void setPrivilege(String privilege) {
		this.privilege = privilege;
	}
	@Override
	public int doStartTag() throws JspException {
		HttpServletRequest request=(HttpServletRequest) pageContext.getRequest();
		Employee employee=(Employee) request.getSession().getAttribute("employee");
		//得到标签中的的权限
		SystemPrivilege methodPrivilege=new SystemPrivilege(new SystemPrivilegePK(module, privilege));
		boolean result=false;
		for(PrivilegeGroup group:employee.getGroups()){
			if(group.getPrivileges().contains(methodPrivilege)){
				//如果用户所拥有的权限组中含有此权限
				result=true;
				break;
			}
		}
			//用户含有权限则执行标签体,否则跳过标签体
		return result?EVAL_BODY_INCLUDE:SKIP_BODY;
	}
}
