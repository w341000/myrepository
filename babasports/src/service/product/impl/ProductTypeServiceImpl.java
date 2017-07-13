package service.product.impl;

import java.io.Serializable;
import java.util.List;

import org.hibernate.Query;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import dao.impl.BaseDaoImpl;

import service.product.ProductTypeService;

import bean.QueryResult;
import bean.product.ProductType;


@Service("productTypeService") @Transactional
public class ProductTypeServiceImpl extends BaseDaoImpl<ProductType> implements ProductTypeService {

	/**
	 * ��дdelete����,ɾ������ʱ�����Ὣ���ݿ��ж�Ӧ��¼ɾ��,
	 * ���ǽ���ɼ��̶��޸�Ϊfalse
	 */
	@Override
	public void delete(ProductType... objs) {
		for (int i = 0;objs!=null && i < objs.length; i++) {
			getSession().createQuery("update ProductType as o set o.visible=:visible where o.id=:id").setBoolean("visible", false)
			.setInteger("id", objs[i].getId()).executeUpdate();
            if (i % 100 == 0) { // ÿһ����ˢ�²�д�����ݿ�
            	getSession().flush();
            	getSession().clear();
            }
        }
	}
	@Override
	public void delete(Serializable... ids) {
		if(ids!=null && ids.length>0){
			Query query=getSession().createQuery("update ProductType set visible=:visible where id in(:ids)");
			query.setBoolean("visible", false);
			query.setParameterList("ids", ids);
			query.executeUpdate();
		}
	}
	@Override
	public QueryResult<ProductType> findByPage(int pageNo,
			int pageSize,String orderBy,String where) {
		String hql="from ProductType  where visible=? "+where +orderBy;
		String totalHql="select count(*) from ProductType where visible=true"+where;
		return super.findByPage(hql,totalHql, pageNo, pageSize,true);
	}
	@Override
	@Transactional(readOnly=true,propagation=Propagation.NOT_SUPPORTED)
	public List<Integer> getSubTypeid(Integer... ids){
		if(ids.length<=0)
			return null;
		//����ָ��id���������и���Ϊid����֮һ����������id
		Query query=getSession().createQuery("select p.id from ProductType p   where p.parent.id in(:typeids)");
		query.setParameterList("typeids", ids);
		return query.list();
	}
}
