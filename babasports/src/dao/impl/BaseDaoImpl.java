package dao.impl;

import java.io.Serializable;
import java.lang.reflect.ParameterizedType;
import java.util.Collection;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import bean.QueryResult;

import dao.BaseDao;

/**
 * 对增删改查操作进行抽取的基本basedao,具体业务dao应该继承此对象
 * @author Administrator
 *
 * @param <T>
 */
@Transactional
public abstract class BaseDaoImpl<T> implements BaseDao<T> {
	
	private Class<T> clazz;
	@SuppressWarnings("unchecked")
	public BaseDaoImpl(){
		ParameterizedType type=  (ParameterizedType) this.getClass().getGenericSuperclass();
		clazz=(Class<T>) type.getActualTypeArguments()[0];
	}
	protected SessionFactory sessionFactory;
	
	protected Session getSession(){
		return sessionFactory.getCurrentSession();
	}
	protected SessionFactory getSessionFactory() {
		return sessionFactory;
	}

	@Override 
	public void clear(){
		getSession().clear();
	}
	@Resource
	public void setSessionFactory(SessionFactory sessionFactory) {
		this.sessionFactory = sessionFactory;
	}
	
	@SuppressWarnings("unchecked")
	@Override @Transactional(readOnly=true,propagation=Propagation.NOT_SUPPORTED)
	public T get(Serializable  id){
		return (T) sessionFactory.getCurrentSession().get(clazz, id);
	}
	@SuppressWarnings("unchecked")
	@Override @Transactional(readOnly=true,propagation=Propagation.NOT_SUPPORTED)
	public <U> U get(Class<U> entityclazz,Serializable  id){
		return (U) sessionFactory.getCurrentSession().get(entityclazz, id);
	}
	
	@Override
	public void delete(Serializable... ids){
		for(Serializable id:ids)
			sessionFactory.getCurrentSession().delete(get(id));
	}
	
	
	@Override
	public void delete(T... objs){
		for(Object obj:objs){
			sessionFactory.getCurrentSession().delete(obj);
		}
	}
	
	@Override
	public void save(T t){
		sessionFactory.getCurrentSession().save(t);
	}

	@Override
	public void update(T t){
		sessionFactory.getCurrentSession().update(t);
	}
	@SuppressWarnings("unchecked")
	@Override @Transactional(readOnly=true,propagation=Propagation.NOT_SUPPORTED)
	public List<T> query() {
		String hql="from "+clazz.getSimpleName();
		return sessionFactory.getCurrentSession().createQuery(hql).list();
	}
	
	
	@SuppressWarnings("unchecked")
	@Override @Transactional(readOnly=true,propagation=Propagation.NOT_SUPPORTED)
	public List<T> query(String where,Object... params ) {
		String hql="from "+clazz.getSimpleName()+where;
		Query query=sessionFactory.getCurrentSession().createQuery(hql);
		for(int i=0;i<params.length;i++){
			query.setParameter(i, params[i]);
		}
		return query.list();
	}
	
	@SuppressWarnings("unchecked")
	public List<T> query(String where,int size,String[] paramsName,Object[]... params ) {
		String hql="from "+clazz.getSimpleName()+where;
		Query query=sessionFactory.getCurrentSession().createQuery(hql);
		for(int i=0;i<params.length;i++){
			query.setParameterList(paramsName[i],params[i]);
		}
		query.setFirstResult(0).setMaxResults(size);
		return query.list();
	}
	

	/**
	 * 
	 * @param hql 需要执行的hql语句
	 * @param toralHql 获取总记录数的hql语句
	 * @param pageNo 当前页码
	 * @param pageSize 每一页显示的记录数
	 * @param params hql语句中的参数
	 * @return
	 */ 
	@SuppressWarnings("unchecked") @Transactional(readOnly=true,propagation=Propagation.NOT_SUPPORTED)
	protected QueryResult<T> findByPage(String hql,String toralHql,int pageNo,int pageSize,Object... params){
		QueryResult<T> qr=new QueryResult<T>();
		Query query=sessionFactory.getCurrentSession().createQuery(hql);
		for(int i=0;i<params.length;i++){
			query.setParameter(i, params[i]);
		}
		//执行一次分页查询.获取分页的数据
		List<T> list=query.setFirstResult((pageNo-1)*pageSize)
		.setMaxResults(pageSize).list();
		query=sessionFactory.getCurrentSession().createQuery(toralHql);
		//执行一次查询,获取hql所查的所有记录数
		int totalrecord=((Long)query.uniqueResult()).intValue();
		qr.setList(list);
		qr.setTotalrecord(totalrecord);
		return qr;
	}
	//设置普通参数的分页查询
	@SuppressWarnings("unchecked") @Override @Transactional(readOnly=true,propagation=Propagation.NOT_SUPPORTED)
	public QueryResult<T> findByPage(String where,String orderBy,int pageNo,int pageSize,String[] paramsName,Object[] params){
		String hql="from "+clazz.getSimpleName()+" where 1=1 "+where+orderBy;
		String totalHql="select count(*) from "+clazz.getSimpleName()+" where 1=1 "+where;
		QueryResult<T> qr=new QueryResult<T>();
		Query query=sessionFactory.getCurrentSession().createQuery(hql);
		Query totalquery=sessionFactory.getCurrentSession().createQuery(totalHql);
		for(int i=0;paramsName!=null && params!=null && i<params.length;i++){
			//反射判断参数类型如果是数组或集合数据类型,则调用setParameterList方法
			if(params[i].getClass().isArray()){
				query.setParameterList(paramsName[i],  (Object[])params[i]);
				totalquery.setParameterList(paramsName[i], (Object[])params[i]);
				continue;
			}
			if(params[i] instanceof Collection){
				query.setParameterList(paramsName[i],  (Collection) params[i]);
				totalquery.setParameterList(paramsName[i], (Collection) params[i]);
				continue;
			}//不是集合也不是数组则调用普通参数方法
			query.setParameter(paramsName[i], params[i]);
			totalquery.setParameter(paramsName[i], params[i]);
		}
		//执行一次分页查询.获取分页的数据
		List<T> list=query.setFirstResult((pageNo-1)*pageSize).setMaxResults(pageSize).list();
		//执行一次查询,获取hql所查的所有记录数
		int totalrecord=((Long)totalquery.uniqueResult()).intValue();
		qr.setList(list);
		qr.setTotalrecord(totalrecord);
		return qr;
	}

	@Override
	public int getCount(){
		String hql="select count(*) from "+clazz.getSimpleName();
		Query query=sessionFactory.getCurrentSession().createQuery(hql);
		int count=((Long)query.uniqueResult()).intValue();
		return count;
	}
	
	
	
	/**
	 * 构造orderBy语句
	 * @param orderBy key为需要被排序的属性,value为查询顺序ASC或 desc
	 * @return
	 */
	protected String buildOrderBy(LinkedHashMap<String, String> orderBy){
		StringBuffer orderByhql=new StringBuffer("");
		if(orderBy!=null&&orderBy.size()>0){
			 orderByhql.append(" order by ");
			 for(Map.Entry<String,String> entry:orderBy.entrySet()){
				 orderByhql.append("o.").append(entry.getKey()).append(" ").append(entry.getValue()).append(",");
			 }
			 orderByhql.deleteCharAt(orderByhql.length()-1);
		}
		return orderByhql.toString();
	}
}



