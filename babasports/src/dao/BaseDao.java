package dao;

import java.io.Serializable;
import java.util.List;

import bean.QueryResult;

public interface BaseDao<T> {
	/**
	 * 根据id获取对象
	 * @param id 唯一标示对象的主键
	 * @return
	 */
	public abstract T get(Serializable id);

	
	/**
	 * 删除对象数组
	 * @param objs
	 */
	void delete(T... objs);
	public abstract void save(T t);
	/**
	 * 更新对象
	 * @param t
	 */
	public abstract void update(T t);

	public abstract List<T> query();

	/**
	 * 根据指定where条件查询出所有对象的list集合
	 * @param where 条件语句,如" where name=?"
	 * @param params where语句中使用的?参数,参数位置从0开始
	 * @return 查询的list集合
	 */
	List<T> query(String where, Object... params);


	/**
	 * 获取分页数据,需要注意如果有参数条件,那么每一个参数条件应该对应Object[]中一个类型的数据,设置的参数可以是基本数据类型或者普通的对象,或数组或集合
	 * 例如 name =:name 
	 * 传递new Object[]{"name"};
	 * @param where 获取数据的where条件,注意条件不能为空,只能为"",并且条件应该以 and xxx或者 &开头而不需要where
	 * @param orderBy 排序字句
	 * @param pageNo 获取数据的页码,从1开始
	 * @param pageSize 每一页显示的数据
	 * @param paramsName 参数名
	 * @param params 对应的类型参数数组
	 * @return
	 */
	QueryResult<T> findByPage(String where, String orderBy, int pageNo,
			int pageSize, String[] paramsName, Object[] params);

	/**
	 * 删除Session中的缓存,使所有跟session关联的对象变为游离状态
	 */
	void clear();

	/**
	 * 根据id删除对象
	 * @param id 唯一标示对象的主键
	 */
	void delete(Serializable... ids);

	/**
	 * 根据实体Class,和id获取实体对象
	 * @param entityclazz
	 * @param id
	 * @return
	 */
	public <U> U get(Class<U> entityclazz,Serializable  id);


	/**
	 * 获得表中的总记录数
	 * @return
	 */
	int getCount();
	
}