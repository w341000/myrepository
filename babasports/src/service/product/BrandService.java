package service.product;

import bean.QueryResult;
import bean.product.Brand;
import dao.BaseDao;

public interface BrandService extends BaseDao<Brand> {
	/**
	 * 根据pageNo和pageSize获得分页数据
	 * @param pageNo 获取的数据页码,从1开始
	 * @param pageSize 每一页显示的数据 
	 * @param orderBy 排序字句,不能输入null,只能输入""空字符串
	 * @param where where子句,不能输入null,只能输入""空字符串,注意不需要输入where 部分,只需要输入& 或者and 然后跟内容
	 * @return QueryResult<ProductType> 
	 */
	QueryResult<Brand> findByPage(int pageNo, int pageSize, String orderBy,
			String where);
	

}
