package service.product;

import java.io.Serializable;
import java.util.List;

import bean.QueryResult;
import bean.product.Brand;
import bean.product.ProductInfo;
import dao.BaseDao;

public interface ProductInfoService extends BaseDao<ProductInfo> {

	/**
	 * ����pageNo��pageSize��÷�ҳ����
	 * @param pageNo ��ȡ������ҳ��,��1��ʼ
	 * @param pageSize ÿһҳ��ʾ������ 
	 * @param orderBy �����־�,��������null,ֻ������""���ַ���
	 * @param where where�Ӿ�,��������null,ֻ������""���ַ���,ע�ⲻ��Ҫ����where ����,ֻ��Ҫ����& ����and Ȼ�������
	 * @return QueryResult<ProductType> 
	 */
	QueryResult<ProductInfo> findByPage(int pageNo, int pageSize,
			String orderBy, String where);
	
	/**
	 * �����Ƿ��ϼ�
	 * @param status trueΪ�ϼ�,falseΪ�¼�
	 * @param ids ��Ʒ��id,��������id��id����
	 */
	public void setVisibleStatus(boolean status,Integer... ids);
	
	/**
	 * ���ò�Ʒ���Ƽ�״̬
	 * @param status trueΪ�Ƽ�,falseΪ���Ƽ�
	 * @param ids ��Ʒ��id,��������id��id����
	 */
	public void setCommend(boolean status,Integer... ids);

	/**
	 * ���ݲ�Ʒ���id��ȡ����µ�����Ʒ��
	 * @param typeid ���id
	 * @return ����µ�����Ʒ��
	 */
	List<Brand> getBrandsByProductTypeid(Integer... typeid);

	/**
	 * �������id��ȡ�������Ĳ��ұ��Ƽ��Ĳ�Ʒ
	 * @param typeid ���id
	 * @param maxResult ��ȡ�Ĳ�Ʒ����
	 * @return ��Ʒ
	 */
	List<ProductInfo> getTopSell(Integer typeid, int maxResult);

	/**
	 * ��ȡָ��id�Ĳ�Ʒ
	 * @param productids ��Ʒid����
	 * @param maxResults ��ȡ���ٸ���Ʒ
	 * @return
	 */
	List<ProductInfo> getViewHistory(Integer[] productids,int maxResults);




}
