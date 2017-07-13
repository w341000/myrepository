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
 * 权限检查拦截器
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
		if(!validate(ai)){//校验不通过,没有权限,执行以下代码
			HttpServletRequest request = ServletActionContext.getRequest();
			request.setAttribute("message", "你没有执行该操作的权限!");
			request.setAttribute("urladdress", "/control/center/right.action");
			return "message";
		}
		return ai.invoke();
	}

	/**
	 * 权限检查操作
	 * @return
	 * @throws Exception  
	 */
	private boolean validate(ActionInvocation ai) throws Exception {
		String methodName=ai.getProxy().getMethod();
		Method method=ai.getAction().getClass().getMethod(methodName);
		//存在被访问的方法并且方法上含有权限注解
		if(method!=null && method.isAnnotationPresent(Permission.class)){
			Permission permission=method.getAnnotation(Permission.class);
			//得到方法上的权限
			SystemPrivilege methodPrivilege=new SystemPrivilege(new SystemPrivilegePK(permission.module(), permission.privilege()));
			Employee employee=(Employee) ServletActionContext.getRequest().getSession().getAttribute("employee");
			for(PrivilegeGroup group:employee.getGroups()){
				if(group.getPrivileges().contains(methodPrivilege)){
					//如果用户所拥有的权限组中含有此权限
					return true;
				}
			}
			return false;
		}
		return true;
	}
}
