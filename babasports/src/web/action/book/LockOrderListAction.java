package web.action.book;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.Resource;

import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;

import service.book.OrderService;
import bean.PageBean;
import bean.QueryResult;
import bean.book.Order;
import bean.book.OrderState;
import web.action.BaseAction;

/*
 * 查看被锁定的订单
 */
@SuppressWarnings("serial")
@Controller @Scope("prototype")
public class LockOrderListAction extends BaseAction<PageBean> {
	
	@Resource
	private OrderService orderService;

	@Override
	public String execute() throws Exception {
		StringBuffer where=new StringBuffer();
		where.append(" and lockuser is not null");
		String orderby=" order by createDate asc ";
		QueryResult<Order> qr=orderService.findByPage(where.toString(), orderby, model.getCurrentpage(), model.getPagesize(), null, null);
		model.setList(qr.getList());
		model.setTotalrecord(qr.getTotalrecord());
		request.put("pagebean", model);
		request.put("showButton", true);
		return SUCCESS;
	}
	
	
	
	

}
