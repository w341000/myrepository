package service.privilege;

import java.util.List;

import bean.privilege.SystemPrivilege;
import dao.BaseDao;

public interface SystemPrivilegeService extends BaseDao<SystemPrivilege> {

	/**
	 * ��������Ȩ��
	 * @param privileges Ȩ��
	 */
	void saves(List<SystemPrivilege> privileges);

	

}
