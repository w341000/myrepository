package bos.dao.base;

import java.io.Serializable;
import java.util.List;

public interface IBaseDao<T> {
	public void save(T entity);
	public void delete(T entity);
	public void update(T entity);
	public void findById(Serializable id);
	public List<T> findAll();
	public List<T> find(String hql, Object... params);
	/**
	 * 提供通用更新方法
	 * @param queryName 查询语句名字
	 * @param params 参数
	 */
	public void executeUpdate(String queryName,Object... params);
}
