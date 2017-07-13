package service.product;

import bean.QueryResult;
import bean.product.Brand;
import dao.BaseDao;

public interface BrandService extends BaseDao<Brand> {
	/**
	 * ����pageNo��pageSize��÷�ҳ����
	 * @param pageNo ��ȡ������ҳ��,��1��ʼ
	 * @param pageSize ÿһҳ��ʾ������ 
	 * @param orderBy �����־�,��������null,ֻ������""���ַ���
	 * @param where where�Ӿ�,��������null,ֻ������""���ַ���,ע�ⲻ��Ҫ����where ����,ֻ��Ҫ����& ����and Ȼ�������
	 * @return QueryResult<ProductType> 
	 */
	QueryResult<Brand> findByPage(int pageNo, int pageSize, String orderBy,
			String where);
	

}
