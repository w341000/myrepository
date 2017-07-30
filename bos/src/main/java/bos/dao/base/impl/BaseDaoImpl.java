package bos.dao.base.impl;

import java.io.Serializable;
import java.lang.reflect.ParameterizedType;
import java.util.List;
import javax.annotation.Resource;
import org.hibernate.Criteria;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.Projections;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import bos.dao.base.IBaseDao;
import bos.utils.PageBean;
public class BaseDaoImpl<T> implements IBaseDao<T> {
	
	private Class<T> clazz;
	@SuppressWarnings("unchecked")
	public BaseDaoImpl(){
		ParameterizedType type=  (ParameterizedType) this.getClass().getGenericSuperclass();
		clazz=(Class<T>) type.getActualTypeArguments()[0];
	}
	@Resource
	private SessionFactory sessionFactory;
	/**
	 * 获取session对象
	 * @return
	 */
	protected Session getSession(){
		return sessionFactory.getCurrentSession();
	}

	@Override
	public void save(T entity) {
		getSession().save(entity);	
	}

	@Override
	public void delete(T entity) {
		getSession().delete(entity);
	}

	@Override
	public void update(T entity) {
		getSession().update(entity);
	}

	@Override
	public T findById(Serializable id) {
		return (T) getSession().get(clazz, id);
	}

	@Override  
	public List<T> findAll() {
		String hql="FROM "+clazz.getSimpleName();
		Query query=this.getSession().createQuery(hql);
		return query.list();
	}
	@Override
	public List<T> find(String hql,Object... params){
		Query query=this.getSession().createQuery(hql);
		for(int i=0;i<params.length;i++){
			query.setParameter(i, params[i]);
		}
		return query.list();
	}

	@Override
	public void executeUpdate(String queryName, Object... params) {
		Session session = this.getSession();
		Query query=session.getNamedQuery(queryName);//获得命名查询的query对象
		for(int i=0;i<params.length;i++){
			query.setParameter(i, params[i]);
		}
		query.executeUpdate();
	}

	@Override 
	public void pageQuery(PageBean pageBean) {
		int currentPage = pageBean.getCurrentPage();
		int pageSize = pageBean.getPageSize();
		DetachedCriteria detachedCriteria = pageBean.getDetachedCriteria();
		//总数据库
		//改变hibernate发出的sql  --select count(*) fomr xxx
		detachedCriteria.setProjection(Projections.rowCount());
		//获取到总数据量
		int total=((Long)detachedCriteria.getExecutableCriteria(getSession()).uniqueResult()).intValue();
		pageBean.setTotal(total);
		detachedCriteria.setProjection(null);	//修改回sql的形式
		//重置表和类的映射关系
		detachedCriteria.setResultTransformer(DetachedCriteria.ROOT_ENTITY);
		//当前页展示的数据
		int firstResult=(currentPage-1)*pageSize;
		int maxResults=pageSize;
		List list = detachedCriteria.getExecutableCriteria(getSession()).setFirstResult(firstResult).setMaxResults(maxResults).list();
		pageBean.setRows(list);
	}

	@Override
	public void saveOrUpdate(T entity) {
		this.getSession().saveOrUpdate(entity);
	}

	@Override 
	public List<T> findByCriteria(DetachedCriteria detachedCriteria) {
		Criteria criteria = detachedCriteria.getExecutableCriteria(getSession());
		return criteria.list();
	}


	
	

}
