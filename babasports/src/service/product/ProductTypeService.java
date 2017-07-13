package service.product;

import java.util.List;

import dao.BaseDao;

import bean.QueryResult;
import bean.product.ProductType;

public interface ProductTypeService extends BaseDao<ProductType>{
/**
 * 根据pageNo和pageSize获得分页数据
 * @param pageNo 获取的数据页码,从1开始
 * @param pageSize 每一页显示的数据 
 * @param orderBy 排序字句,不能输入null,只能输入""空字符串
 * @param where where子句,不能输入null,只能输入""空字符串,注意不需要输入where 部分,只需要输入& 或者and 然后跟内容
 * @return QueryResult<ProductType> 
 */
	QueryResult<ProductType> findByPage(int pageNo, int pageSize,
			String orderBy, String where);

	/**
	 * 根据父类ids获取类别下的所有子类别id(注:不会获得子类的子类的id)
	 * @param id 父类id
	 * @return 所有子类别
	 */
List<Integer> getSubTypeid(Integer... ids);

	
	
}