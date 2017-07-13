package service.book.impl;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import bean.book.OrderContactInfo;
import service.book.OrderContactInfoService;
import dao.impl.BaseDaoImpl;
@Service("orderContactInfoService") @Transactional
public class OrderContactInfoServiceImpl extends BaseDaoImpl<OrderContactInfo> implements OrderContactInfoService {

}
