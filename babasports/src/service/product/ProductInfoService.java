package service.product;

import java.io.Serializable;
import java.util.List;

import bean.QueryResult;
import bean.product.Brand;
import bean.product.ProductInfo;
import dao.BaseDao;

public interface ProductInfoService extends BaseDao<ProductInfo> {

	/**
	 * 根据pageNo和pageSize获得分页数据
	 * @param pageNo 获取的数据页码,从1开始
	 * @param pageSize 每一页显示的数据 
	 * @param orderBy 排序字句,不能输入null,只能输入""空字符串
	 * @param where where子句,不能输入null,只能输入""空字符串,注意不需要输入where 部分,只需要输入& 或者and 然后跟内容
	 * @return QueryResult<ProductType> 
	 */
	QueryResult<ProductInfo> findByPage(int pageNo, int pageSize,
			String orderBy, String where);
	
	/**
	 * 设置是否上架
	 * @param status true为上架,false为下架
	 * @param ids 产品的id,可输入多个id或id数组
	 */
	public void setVisibleStatus(boolean status,Integer... ids);
	
	/**
	 * 设置产品的推荐状态
	 * @param status true为推荐,false为不推荐
	 * @param ids 产品的id,可输入多个id或id数组
	 */
	public void setCommend(boolean status,Integer... ids);

	/**
	 * 根据产品类别id获取类别下的所有品牌
	 * @param typeid 类别id
	 * @return 类别下的所有品牌
	 */
	List<Brand> getBrandsByProductTypeid(Integer... typeid);

	/**
	 * 根据类别id获取销量最多的并且被推荐的产品
	 * @param typeid 类别id
	 * @param maxResult 获取的产品数量
	 * @return 产品
	 */
	List<ProductInfo> getTopSell(Integer typeid, int maxResult);

	/**
	 * 获取指定id的产品
	 * @param productids 产品id数组
	 * @param maxResults 获取多少个产品
	 * @return
	 */
	List<ProductInfo> getViewHistory(Integer[] productids,int maxResults);




}
