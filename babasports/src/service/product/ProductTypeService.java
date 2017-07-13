package service.product;

import java.util.List;

import dao.BaseDao;

import bean.QueryResult;
import bean.product.ProductType;

public interface ProductTypeService extends BaseDao<ProductType>{
/**
 * ����pageNo��pageSize��÷�ҳ����
 * @param pageNo ��ȡ������ҳ��,��1��ʼ
 * @param pageSize ÿһҳ��ʾ������ 
 * @param orderBy �����־�,��������null,ֻ������""���ַ���
 * @param where where�Ӿ�,��������null,ֻ������""���ַ���,ע�ⲻ��Ҫ����where ����,ֻ��Ҫ����& ����and Ȼ�������
 * @return QueryResult<ProductType> 
 */
	QueryResult<ProductType> findByPage(int pageNo, int pageSize,
			String orderBy, String where);

	/**
	 * ���ݸ���ids��ȡ����µ����������id(ע:����������������id)
	 * @param id ����id
	 * @return ���������
	 */
List<Integer> getSubTypeid(Integer... ids);

	
	
}