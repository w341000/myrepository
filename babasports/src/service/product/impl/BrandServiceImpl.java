package service.product.impl;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import bean.QueryResult;
import bean.product.Brand;
import bean.product.ProductType;
import service.product.BrandService;
import dao.impl.BaseDaoImpl;
@Service("brandService") @Transactional
public class BrandServiceImpl extends BaseDaoImpl<Brand> implements BrandService {
	@Override
	public QueryResult<Brand> findByPage(int pageNo,
			int pageSize,String orderBy,String where) {
		String hql="from Brand  where visible=? "+where +orderBy;
		String totalHql="select count(*) from Brand where visible=true"+where;
		return super.findByPage(hql,totalHql, pageNo, pageSize,true);
	}

}
