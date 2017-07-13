package service.product.impl;

import java.util.List;

import org.hibernate.Query;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import bean.QueryResult;
import bean.product.ProductStyle;
import bean.product.ProductType;
import service.product.ProductStyleService;
import dao.impl.BaseDaoImpl;
@Service("productStyleService") @Transactional
public class ProductStyleServiceImpl extends BaseDaoImpl<ProductStyle> implements ProductStyleService {
	
	@Override
	public List<ProductStyle> findByWhere(String orderBy,String where) {
		String hql="from ProductStyle  where 1=1 "+where +orderBy;
		return getSession().createQuery(hql).list();
	}
	
	
	@Override
	public void setVisibleStatus(boolean status, Integer... ids) {
		if(ids==null || ids.length<=0)
			throw new RuntimeException("传递的产品id不能为空");
		Query query=getSession().createQuery("update ProductStyle set visible=:visible where id in(:ids)");
		query.setParameter("visible", status);
		query.setParameterList("ids", ids);
		query.executeUpdate();
		
	}
}
