package web.taglib;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.jsp.JspException;
import javax.servlet.jsp.tagext.TagSupport;

import bean.privilege.Employee;
import bean.privilege.PrivilegeGroup;
import bean.privilege.SystemPrivilege;
import bean.privilege.SystemPrivilegePK;
/**
 * Ȩ��У���ǩ
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
		//�õ���ǩ�еĵ�Ȩ��
		SystemPrivilege methodPrivilege=new SystemPrivilege(new SystemPrivilegePK(module, privilege));
		boolean result=false;
		for(PrivilegeGroup group:employee.getGroups()){
			if(group.getPrivileges().contains(methodPrivilege)){
				//����û���ӵ�е�Ȩ�����к��д�Ȩ��
				result=true;
				break;
			}
		}
			//�û�����Ȩ����ִ�б�ǩ��,����������ǩ��
		return result?EVAL_BODY_INCLUDE:SKIP_BODY;
	}
}
