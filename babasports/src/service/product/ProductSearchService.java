package service.product;

import bean.QueryResult;
import bean.product.ProductInfo;

public interface ProductSearchService {

	/**
	 * ��solr��Ĭ������������Ʒ
	 * @param key �����ؼ���
	 * @param pageNo ������ҳ��,��1��ʼ
	 * @param pageSize ÿҳ��ȡ�ļ�¼��
	 * @return
	 */
	public abstract QueryResult<ProductInfo> query(String key, int pageNo,
			int pageSize);

}