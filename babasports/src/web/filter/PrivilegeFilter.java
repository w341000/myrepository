package web.filter;

import java.io.IOException;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import bean.privilege.Employee;
/**
 * Ȩ�����ع�����
 * @author Administrator
 *
 */
public class PrivilegeFilter implements Filter {

	@Override
	public void init(FilterConfig filterConfig) throws ServletException {
		// TODO Auto-generated method stub

	}

	@Override
	public void doFilter(ServletRequest req, ServletResponse response,
			FilterChain chain) throws IOException, ServletException {
		HttpServletRequest request=(HttpServletRequest)req;
		Employee employee=(Employee) request.getSession().getAttribute("employee");
		if(employee==null){//���Ա��δ��¼,�ض��򵽵�¼����
			HttpServletResponse res=(HttpServletResponse) response;
			res.sendRedirect("/employee/logon.action");
		}else
			chain.doFilter(request, response);

	}

	@Override
	public void destroy() {
		// TODO Auto-generated method stub

	}

}
