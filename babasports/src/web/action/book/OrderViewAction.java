package web.action.book;

import javax.annotation.Resource;

import org.apache.struts2.ServletActionContext;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;

import service.book.OrderService;

import bean.BuyCart;
import bean.book.Order;
import bean.privilege.Employee;
import web.action.BaseAction;

/**
 * 订单信息查看
 */
@Controller @Scope("prototype")
public class OrderViewAction extends BaseAction<Order> {
	@Resource
	private OrderService orderService;
	@Override
	public String execute() throws Exception {
		Employee employee=(Employee) ServletActionContext.getRequest().getSession().getAttribute("employee");
		String username=employee.getUsername();
		Order order=orderService.addLock(model.getOrderid(), username);
		//锁定用户与当前登录用户不相同
		if(order.getLockuser()!=null && !order.getLockuser().equals(username)){
			request.put("message", "订单已经被"+username+"加锁了");
			request.put("urladdress", "/control/order/list.action");
			return "message";
		}
		request.put("order", order);
		return SUCCESS;
	}
}
