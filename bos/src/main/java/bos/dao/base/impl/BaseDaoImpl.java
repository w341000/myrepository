package bos.dao.base.impl;

import java.io.Serializable;
import java.lang.reflect.ParameterizedType;
import java.util.List;

import javax.annotation.Resource;

import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;

import bos.dao.base.IBaseDao;

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
	public void findById(Serializable id) {
		getSession().get(clazz, id);
	}

	@Override
	public List<T> findAll() {
		String hql="FROM "+clazz.getSimpleName();
		Query query=getSession().createQuery(hql);
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

}
