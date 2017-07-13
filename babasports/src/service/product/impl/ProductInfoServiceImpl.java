package service.product.impl;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.annotation.Resource;

import org.apache.solr.client.solrj.SolrClient;
import org.apache.solr.client.solrj.SolrServerException;
import org.apache.solr.common.SolrInputDocument;
import org.hibernate.Query;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import bean.QueryResult;
import bean.product.Brand;
import bean.product.ProductInfo;
import bean.product.ProductStyle;
import service.product.ProductInfoService;
import service.product.ProductTypeService;
import dao.impl.BaseDaoImpl;

@Service("productInfoService")
@Transactional
public class ProductInfoServiceImpl extends BaseDaoImpl<ProductInfo> implements
		ProductInfoService {
	@Resource
	private ProductTypeService productTypeService;
	@Resource
	private SolrClient solrClient;

	@Override
	@Transactional(propagation = Propagation.REQUIRED)
	public void save(ProductInfo t) {
		super.save(t);
		//saveIndex(t);
	}

	private void saveIndex(ProductInfo t) {
		try {
			SolrInputDocument document = new SolrInputDocument();
			document.addField("id", t.getId());
			document.addField("productName", t.getName());
			document.addField("productModel", t.getModel());
			document.addField("productMarketprice", t.getMarketprice());
			document.addField("productSellprice", t.getSellprice());
			document.addField("productWeight", t.getWeight());
			document.addField("productDescription", t.getDescription());
			document.addField("productBuyexplain", t.getBuyexplain());
			document.addField("brandName", t.getBrand().getName());
			document.addField("brandCode", t.getBrand().getCode());
			document.addField("typeId", t.getType().getId());
			document.addField("typeName", t.getType().getName());
			if (t.getStyles() != null) {
				for (ProductStyle style : t.getStyles()) {
					document.addField("styleId", style.getId());
					document.addField("styleName", style.getName());
					document.addField("styleImagename", style.getImagename());
					document.addField("styleProductid", style.getProduct()
							.getId());
				}
			}
			solrClient.add(document);
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
	}

	@Override
	@Transactional(readOnly = true, propagation = Propagation.NOT_SUPPORTED)
	public List<ProductInfo> getViewHistory(Integer[] productids, int maxResults) {
		List<ProductInfo> list = new ArrayList<ProductInfo>();
		for (int i = 0; i < productids.length; i++) {
			String hql = "from ProductInfo  where  id =:id";
			Query query = getSession().createQuery(hql);
			query.setInteger("id", productids[i]);
			ProductInfo product = (ProductInfo) query.uniqueResult();
			list.add(product);
		}
		return list;
	}

	@Override
	@Transactional(readOnly = true, propagation = Propagation.NOT_SUPPORTED)
	public List<ProductInfo> getTopSell(Integer typeid, int maxResult) {
		List<Integer> typeids = new ArrayList<Integer>();
		typeids.add(typeid);
		// 获取到指定类别下的所有子类id(包括子类的子类)
		getTypeids(typeids, typeid);
		String hql = "from ProductInfo  where  visible=true and commend=:commend  and type.id in(:ids) order by sellcount desc";
		Query query = getSession().createQuery(hql);
		query.setBoolean("commend", true);
		query.setParameterList("ids", typeids);
		// 设置获取的数量
		query.setFirstResult(0).setMaxResults(maxResult);
		return query.list();
	}

	/**
	 * 根据指定类型id,获取所有子类别的id(注:子类及其子类的id都会获取)
	 * 
	 * @param typeids
	 * @param integers
	 */
	private void getTypeids(List<Integer> typeids, Integer... integers) {
		List<Integer> subtypeids = this.productTypeService
				.getSubTypeid(integers);
		if (subtypeids != null) {
			typeids.addAll(subtypeids);
			getTypeids(typeids, subtypeids.toArray(new Integer[] {}));
		}
	}

	@Override
	public QueryResult<ProductInfo> findByPage(int pageNo, int pageSize,
			String orderBy, String where) {
		String hql = "from ProductInfo  where 1=1 " + where + orderBy;
		String totalHql = "select count(*) from ProductInfo where 1=1" + where;
		return super.findByPage(hql, totalHql, pageNo, pageSize);
	}

	@Override
	public void setVisibleStatus(boolean status, Integer... ids) {
		if (ids == null || ids.length <= 0)
			throw new RuntimeException("传递的产品id不能为空");
		Query query = getSession().createQuery(
				"update ProductInfo set visible=:visible where id in(:ids)");
		query.setParameter("visible", status);
		query.setParameterList("ids", ids);
		query.executeUpdate();

	}

	@Override
	public void setCommend(boolean status, Integer... ids) {
		if (ids == null || ids.length <= 0)
			throw new RuntimeException("传递的产品id不能为空");
		Query query = getSession().createQuery(
				"update ProductInfo set commend=:commend where id in(:ids)");
		query.setParameter("commend", status);
		query.setParameterList("ids", ids);
		query.executeUpdate();
	}

	@Override
	@Transactional(readOnly = true, propagation = Propagation.NOT_SUPPORTED)
	public List<Brand> getBrandsByProductTypeid(Integer... typeids) {
		if (typeids == null || typeids.length <= 0)
			return null;
		// 查询某一类别下的所有品牌,从产品表中查询符合类别要求的产品.并按品牌code分组获得所有品牌
		String hql = " from Brand where code in(select p.brand.code from ProductInfo p where p.type.id in(:typeids) group by p.brand.code)";
		Query query = getSession().createQuery(hql);
		query.setParameterList("typeids", typeids);
		return query.list();

	}
}
