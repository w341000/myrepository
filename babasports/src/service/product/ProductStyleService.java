package service.product;

import java.util.List;

import bean.product.ProductStyle;
import dao.BaseDao;

public interface ProductStyleService  extends BaseDao<ProductStyle> {

	/**
	 * 
	 * @param orderBy �����־�,��������null,ֻ������""���ַ���
	 * @param where where�Ӿ�,��������null,ֻ������""���ַ���,ע�ⲻ��Ҫ����where ����,ֻ��Ҫ����& ����and Ȼ�������
	 * @return ��ȡ������
	 */
	List<ProductStyle> findByWhere(String orderBy, String where);

	/**
	 * �����Ƿ��ϼ�
	 * @param status trueΪ�ϼ�,falseΪ�¼�
	 * @param ids ��Ʒ��ʽ��id,��������id��id����
	 */
	void setVisibleStatus(boolean status, Integer... ids);

}
