package service.product.impl;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.Resource;

import org.apache.solr.client.solrj.SolrClient;
import org.apache.solr.client.solrj.SolrQuery;
import org.apache.solr.client.solrj.response.QueryResponse;
import org.apache.solr.common.SolrDocument;
import org.apache.solr.common.SolrDocumentList;
import org.springframework.stereotype.Service;

import service.product.ProductSearchService;

import bean.QueryResult;
import bean.product.Brand;
import bean.product.ProductInfo;
import bean.product.ProductStyle;
import bean.product.ProductType;

@Service("productSearchService")
public class ProductSearchServiceImpl implements ProductSearchService {
	@Resource
	private SolrClient solrClient;

	
	@Override
	public QueryResult<ProductInfo> query(String key, int pageNo, int pageSize) {
		try {
			SolrQuery query = new SolrQuery(key);
			query.setStart((pageNo - 1) * pageSize).setRows(pageSize);// ���÷�ҳ
			query.addHighlightField("productName,productDescription")
					.setHighlightFragsize(200).setHighlight(true)
					.setHighlightSimplePre("<font color='red'>")
					.setHighlightSimplePost("</font>");// ��������

			QueryResponse resp = solrClient.query(query);
			QueryResult<ProductInfo> qr = new QueryResult<ProductInfo>();
			SolrDocumentList docsList = resp.getResults();
			qr.setTotalrecord((int) docsList.getNumFound());// ��ȡƥ�䵽�ļ�¼����
			qr.setList(add2List(docsList,resp));
			return qr;
		} catch (Exception e) {
			e.printStackTrace();
			throw new RuntimeException(e);
		}
	}

	private List<ProductInfo> add2List(SolrDocumentList docsList,QueryResponse resp) {
		List<ProductInfo> list = new ArrayList<ProductInfo>();
		for (SolrDocument doc : docsList) {
			String id=(String) doc.getFieldValue("id");
			Integer productId = (Integer) doc.getFieldValue("productId");
			String productName = (String) doc.getFieldValue("productName");
			String productModel = (String) doc.getFieldValue("productModel");
			Float productMarketprice = (Float) doc
					.getFieldValue("productMarketprice");
			Float productSellprice = (Float) doc
					.getFieldValue("productSellprice");
			Integer productWeight = (Integer) doc
					.getFieldValue("productWeight");
			String productDescription = (String) doc
					.getFieldValue("productDescription");
			String productBuyexplain = (String) doc
					.getFieldValue("productBuyexplain");
			String brandName = (String) doc.getFieldValue("brandName");
			String brandCode = (String) doc.getFieldValue("brandCode");
			Integer typeId = (Integer) doc.getFieldValue("typeId");
			String typeName = (String) doc.getFieldValue("typeName");
			Object[] styleIds = doc.getFieldValues("styleId").toArray();
			Object[] styleNames = doc.getFieldValues("styleName").toArray();
			Object[] styleImagenames = doc.getFieldValues("styleImagename")
					.toArray();
			//�����ֶ�
			if(resp.getHighlighting().get(id)!=null &&resp.getHighlighting().get(id).get("productName")!=null){
				productName=resp.getHighlighting().get(id).get("productName").get(0);
			}
			//�����ֶ�
			if(resp.getHighlighting().get(id)!=null &&resp.getHighlighting().get(id).get("productDescription")!=null){
				productDescription=resp.getHighlighting().get(id).get("productDescription").get(0);
			}
			ProductInfo product = new ProductInfo(productId, productName,
					productModel, productMarketprice, productSellprice,
					productWeight, productDescription, productBuyexplain); // ���캯������product������
			Brand brand = new Brand();
			brand.setName(brandName);//���ò�ƷƷ���������
			brand.setCode(brandCode);
			product.setBrand(brand);
			product.setType(new ProductType(typeId, typeName));//���ò�Ʒ����������
			for (int i = 0; i < styleIds.length && i < styleNames.length
					&& i < styleImagenames.length; i++) {
				Integer styleId = (Integer) styleIds[i];
				String styleName = (String) styleNames[i];
				String styleImagename = (String) styleImagenames[i];
				ProductStyle style = new ProductStyle(styleId, styleName,
						styleImagename);
				product.addProductStyle(style);
			}
			
			
			list.add(product);
		}
		return list;
	}

}
