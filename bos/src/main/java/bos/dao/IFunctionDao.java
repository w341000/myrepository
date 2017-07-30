package bos.dao;

import java.util.List;

import bos.dao.base.IBaseDao;
import bos.domain.Function;

public interface IFunctionDao extends IBaseDao<Function> {

	/**
	 * 根据用户id查询对应权限
	 * @param id
	 * @return
	 */
	public List<Function> findListByUserid(String id);

	/**
	 * 查询所有菜单
	 * @return
	 */
	public List<Function> findAllMenu();

	/**
	 * 根据用户id查询对应菜单
	 * @param id 用户id
	 * @return
	 */
	public List<Function> findMenuByUserid(String id);

	/**
	 * 查询所有需要权限的url
	 * @return
	 */
	public List<Function> findAllPage();

}
