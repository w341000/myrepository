package service.product;

import bean.QueryResult;
import bean.product.ProductInfo;

public interface ProductSearchService {

	/**
	 * 从solr的默认域中搜索商品
	 * @param key 搜索关键字
	 * @param pageNo 搜索的页码,从1开始
	 * @param pageSize 每页获取的记录数
	 * @return
	 */
	public abstract QueryResult<ProductInfo> query(String key, int pageNo,
			int pageSize);

}