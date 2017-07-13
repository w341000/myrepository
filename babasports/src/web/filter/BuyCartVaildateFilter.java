package web.filter;

import java.io.IOException;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;

import bean.BuyCart;

import utils.WebUtil;

public class BuyCartVaildateFilter implements Filter {

	@Override
	public void init(FilterConfig filterConfig) throws ServletException {
		

	}

	@Override
	public void doFilter(ServletRequest req, ServletResponse response,
			FilterChain chain) throws IOException, ServletException {
		HttpServletRequest request=(HttpServletRequest) req;
		BuyCart cart=WebUtil.getBuyCart(request);
		if(cart==null || cart.getItems()==null ||cart.getItems().isEmpty()){
			request.setAttribute("message", "目前您的购物车中没有商品,请购买商品后再执行操作");
			request.setAttribute("urladdress", "/");
			request.getRequestDispatcher("/WEB-INF/page/share/message.jsp").forward(request, response);
		}else{
			chain.doFilter(request, response);
		}

	}

	@Override
	public void destroy() {
		

	}

}
