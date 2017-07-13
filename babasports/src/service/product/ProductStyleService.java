package service.product;

import java.util.List;

import bean.product.ProductStyle;
import dao.BaseDao;

public interface ProductStyleService  extends BaseDao<ProductStyle> {

	/**
	 * 
	 * @param orderBy 排序字句,不能输入null,只能输入""空字符串
	 * @param where where子句,不能输入null,只能输入""空字符串,注意不需要输入where 部分,只需要输入& 或者and 然后跟内容
	 * @return 获取的数据
	 */
	List<ProductStyle> findByWhere(String orderBy, String where);

	/**
	 * 设置是否上架
	 * @param status true为上架,false为下架
	 * @param ids 产品样式的id,可输入多个id或id数组
	 */
	void setVisibleStatus(boolean status, Integer... ids);

}
