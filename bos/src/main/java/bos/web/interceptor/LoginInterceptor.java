package bos.web.interceptor;


import org.apache.struts2.ServletActionContext;

import bos.domain.User;

import com.opensymphony.xwork2.ActionInvocation;
import com.opensymphony.xwork2.interceptor.MethodFilterInterceptor;

/**
 * 自定义struts2拦截器,实现未登录跳转到指定页面
 * @author Administrator
 *
 */
public class LoginInterceptor extends MethodFilterInterceptor {


	@Override
	//拦截方法
	protected String doIntercept(ActionInvocation invocation) throws Exception {
		User user = (User) ServletActionContext.getRequest().getSession().getAttribute("loginUser");
		if(user==null){
			//如果未登录,跳转到登录页面
			return "login";
		}
		return invocation.invoke();
	}

}
