package service.book.impl;

import java.io.Serializable;

import org.hibernate.Query;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import bean.book.Order;
import bean.book.OrderItem;
import dao.impl.BaseDaoImpl;
@Service("orderItemService") @Transactional
public class OrderItemServiceImpl extends BaseDaoImpl<OrderItem> implements
		service.book.OrderItemService {
	
	@Override
	public void updateAmount(Integer itemid,Integer amount){
		Query query=this.getSession().createQuery("update OrderItem set amount=:amount where itemid=:itemid");
		query.setInteger("amount", amount).setInteger("itemid", itemid).executeUpdate();
	}

	@Override
	public void delete(Serializable... ids) {
		for(Serializable id:ids){
			OrderItem item=this.get(id);
			Order order=item.getOrder();
			//先将订单的集合中删除订单项
			order.getItems().remove(item);
			super.delete(id);
		}
	}

	/**
	 * 重新delete方法
	 */
	@Override
	public void delete(OrderItem... objs) {
		for(OrderItem item:objs){
			Order order=item.getOrder();
			//先将订单的集合中删除订单项
			order.getItems().remove(item);
			super.delete(item);
		}
	}
	
	
}
