package service.book.impl;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import bean.book.OrderDeliverInfo;
import service.book.OrderDeliverInfoService;
import dao.impl.BaseDaoImpl;
@Service("orderDeliverInfoService") @Transactional
public class OrderDeliverInfoServiceImpl extends BaseDaoImpl<OrderDeliverInfo> implements
		OrderDeliverInfoService {

}
