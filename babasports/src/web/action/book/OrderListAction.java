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
/**
 * 订单分页列表
 * @author Administrator
 *
 */
@Controller @Scope("prototype")
public class OrderListAction extends BaseAction<PageBean> {
	@Resource
	private OrderService orderService;
	private OrderState state;
	private String query;
	private String orderid;
	private String username;
	private String buyer;
	private String recipients;
	

	public String getQuery() {
		return query;
	}
	public void setQuery(String query) {
		this.query = query;
	}
	public String getOrderid() {
		return orderid;
	}
	public void setOrderid(String orderid) {
		this.orderid = orderid;
	}
	public String getUsername() {
		return username;
	}
	public void setUsername(String username) {
		this.username = username;
	}
	public String getBuyer() {
		return buyer;
	}
	public void setBuyer(String buyer) {
		this.buyer = buyer;
	}
	public String getRecipients() {
		return recipients;
	}
	public void setRecipients(String recipients) {
		this.recipients = recipients;
	}
	public OrderState getState() {
		return state;
	}
	public void setState(OrderState state) {
		this.state = state;
	}

	@Override
	public String execute() throws Exception {
		//如果传递了state参数,查询出该状态下的订单,否则查询待审核订单
		StringBuffer where=new StringBuffer();
		List<String> paramNames=new ArrayList<String>();
		List<Object> params=new ArrayList<Object>();
		//不管是不是来自查询页面,都对订单状态state进行判断
		state=state!=null?state:OrderState.WAITCONFIRM;
		where.append(" and state =:state");
		String orderby=" order by createDate asc ";
		paramNames.add("state");
		params.add(state);
		if("true".equals(query)){
			//来自查询界面
			if(orderid!=null && !"".equals(orderid)){//根据订单id查询
				where.append(" and orderid like :orderid");
				paramNames.add("orderid");
				params.add("%"+orderid+"%");
			}
			if(username!=null && !"".equals(username)){//根据用户名查询
				where.append(" and buyer.username like :username");
				paramNames.add("username");
				params.add("%"+username+"%");
			}
			if(recipients!=null && !"".equals(recipients)){//根据收货人姓名查询
				where.append(" and orderDeliverInfo.recipients like :recipients");
				paramNames.add("recipients");
				params.add("%"+recipients+"%");
			}
			if(buyer!=null && !"".equals(buyer)){//根据购买者姓名查询
				where.append(" and orderContactInfo.buyerName like :buyerName");
				paramNames.add("buyerName");
				params.add("%"+buyer+"%");
			}
		}
		QueryResult<Order> qr =orderService.findByPage(where.toString(), orderby, model.getCurrentpage(), model.getPagesize(), paramNames.toArray(new String[]{}),params.toArray());
		model.setList(qr.getList());
		model.setTotalrecord(qr.getTotalrecord());
		request.put("pagebean", model);
		return SUCCESS;
	}
	

}
