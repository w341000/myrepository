package service.privilege;

import bean.privilege.Employee;
import dao.BaseDao;

public interface EmployeeService extends BaseDao<Employee> {

	/**
	 * У���û����Ƿ����
	 * @param username �û���
	 * @return
	 */
	boolean exist(String username);

	/**
	 * У���û����������Ƿ���ȷ
	 * @param username �û���
	 * @param password ����
	 * @return
	 */
	boolean validate(String username, String password);

}
