package web.action.privilege;

import java.lang.annotation.Annotation;
import java.lang.reflect.Method;

import javax.servlet.http.HttpServletRequest;

import org.apache.struts2.ServletActionContext;

import utils.WebUtil;

import bean.privilege.Employee;
import bean.privilege.PrivilegeGroup;
import bean.privilege.SystemPrivilege;
import bean.privilege.SystemPrivilegePK;

import com.opensymphony.xwork2.ActionInvocation;
import com.opensymphony.xwork2.interceptor.Interceptor;
/**
 * Ȩ�޼��������
 */
public class PrivilegeInterceptor implements Interceptor {

	@Override
	public void destroy() {
		

	}

	@Override
	public void init() {
		

	}

	@Override
	public String intercept(ActionInvocation ai) throws Exception {
		if(!validate(ai)){//У�鲻ͨ��,û��Ȩ��,ִ�����´���
			HttpServletRequest request = ServletActionContext.getRequest();
			request.setAttribute("message", "��û��ִ�иò�����Ȩ��!");
			request.setAttribute("urladdress", "/control/center/right.action");
			return "message";
		}
		return ai.invoke();
	}

	/**
	 * Ȩ�޼�����
	 * @return
	 * @throws Exception  
	 */
	private boolean validate(ActionInvocation ai) throws Exception {
		String methodName=ai.getProxy().getMethod();
		Method method=ai.getAction().getClass().getMethod(methodName);
		//���ڱ����ʵķ������ҷ����Ϻ���Ȩ��ע��
		if(method!=null && method.isAnnotationPresent(Permission.class)){
			Permission permission=method.getAnnotation(Permission.class);
			//�õ������ϵ�Ȩ��
			SystemPrivilege methodPrivilege=new SystemPrivilege(new SystemPrivilegePK(permission.module(), permission.privilege()));
			Employee employee=(Employee) ServletActionContext.getRequest().getSession().getAttribute("employee");
			for(PrivilegeGroup group:employee.getGroups()){
				if(group.getPrivileges().contains(methodPrivilege)){
					//����û���ӵ�е�Ȩ�����к��д�Ȩ��
					return true;
				}
			}
			return false;
		}
		return true;
	}
}
