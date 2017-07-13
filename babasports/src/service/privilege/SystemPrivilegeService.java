package service.privilege;

import java.util.List;

import bean.privilege.SystemPrivilege;
import dao.BaseDao;

public interface SystemPrivilegeService extends BaseDao<SystemPrivilege> {

	/**
	 * 批量保存权限
	 * @param privileges 权限
	 */
	void saves(List<SystemPrivilege> privileges);

	

}
