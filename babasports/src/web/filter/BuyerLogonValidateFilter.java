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

import org.apache.struts2.ServletActionContext;

import bean.user.Buyer;

import sun.misc.BASE64Decoder;
import sun.misc.BASE64Encoder;
import utils.WebUtil;

public class BuyerLogonValidateFilter implements Filter {

	@Override
	public void init(FilterConfig filterConfig) throws ServletException {
		

	}

	@Override
	public void doFilter(ServletRequest request, ServletResponse response,
			FilterChain chain) throws IOException, ServletException {
		Buyer buyer=WebUtil.getBuyer((HttpServletRequest) request);
		if(buyer==null){
			//Ã»ÓÐµÇÂ¼
			HttpServletResponse res=(HttpServletResponse) response;
			String directUrl=WebUtil.getRequestURIWithParam((HttpServletRequest) request);
			if(directUrl!=null && !"".equals(directUrl.trim())){
				BASE64Encoder encoder=new BASE64Encoder();
				String url=encoder.encode(directUrl.trim().getBytes());
				res.sendRedirect("/user/logon.action?directUrl="+url);
			}else{	
				res.sendRedirect("/user/logon.action");
			}
		}else{
			chain.doFilter(request, response);
		}

	}

	@Override
	public void destroy() {
		

	}

}
