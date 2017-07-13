package service.privilege;

import bean.privilege.Employee;
import dao.BaseDao;

public interface EmployeeService extends BaseDao<Employee> {

	/**
	 * 校验用户名是否存在
	 * @param username 用户名
	 * @return
	 */
	boolean exist(String username);

	/**
	 * 校验用户名及密码是否正确
	 * @param username 用户名
	 * @param password 密码
	 * @return
	 */
	boolean validate(String username, String password);

}
