package dao;

import java.io.Serializable;
import java.util.List;

import bean.QueryResult;

public interface BaseDao<T> {
	/**
	 * ����id��ȡ����
	 * @param id Ψһ��ʾ���������
	 * @return
	 */
	public abstract T get(Serializable id);

	
	/**
	 * ɾ����������
	 * @param objs
	 */
	void delete(T... objs);
	public abstract void save(T t);
	/**
	 * ���¶���
	 * @param t
	 */
	public abstract void update(T t);

	public abstract List<T> query();

	/**
	 * ����ָ��where������ѯ�����ж����list����
	 * @param where �������,��" where name=?"
	 * @param params where�����ʹ�õ�?����,����λ�ô�0��ʼ
	 * @return ��ѯ��list����
	 */
	List<T> query(String where, Object... params);


	/**
	 * ��ȡ��ҳ����,��Ҫע������в�������,��ôÿһ����������Ӧ�ö�ӦObject[]��һ�����͵�����,���õĲ��������ǻ����������ͻ�����ͨ�Ķ���,������򼯺�
	 * ���� name =:name 
	 * ����new Object[]{"name"};
	 * @param where ��ȡ���ݵ�where����,ע����������Ϊ��,ֻ��Ϊ"",��������Ӧ���� and xxx���� &��ͷ������Ҫwhere
	 * @param orderBy �����־�
	 * @param pageNo ��ȡ���ݵ�ҳ��,��1��ʼ
	 * @param pageSize ÿһҳ��ʾ������
	 * @param paramsName ������
	 * @param params ��Ӧ�����Ͳ�������
	 * @return
	 */
	QueryResult<T> findByPage(String where, String orderBy, int pageNo,
			int pageSize, String[] paramsName, Object[] params);

	/**
	 * ɾ��Session�еĻ���,ʹ���и�session�����Ķ����Ϊ����״̬
	 */
	void clear();

	/**
	 * ����idɾ������
	 * @param id Ψһ��ʾ���������
	 */
	void delete(Serializable... ids);

	/**
	 * ����ʵ��Class,��id��ȡʵ�����
	 * @param entityclazz
	 * @param id
	 * @return
	 */
	public <U> U get(Class<U> entityclazz,Serializable  id);


	/**
	 * ��ñ��е��ܼ�¼��
	 * @return
	 */
	int getCount();
	
}