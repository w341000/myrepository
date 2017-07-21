package bos.dao.base;

import java.io.Serializable;
import java.util.List;

import bos.domain.Region;
import bos.utils.PageBean;

public interface IBaseDao<T> {
	public void save(T entity);
	public void delete(T entity);
	public void update(T entity);
	public T findById(Serializable id);
	public List<T> findAll();
	public List<T> find(String hql, Object... params);
	/**
	 * 提供通用更新方法
	 * @param queryName 查询语句名字
	 * @param params 参数
	 */
	public void executeUpdate(String queryName,Object... params);
	
	public void saveOrUpdate(T entity);
	

	/**
	 * 通用分页查询方法
	 * @param pageBean 
	 */
	public void pageQuery(PageBean pageBean);
}
