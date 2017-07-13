package service.privilege.impl;

import java.io.Serializable;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import bean.privilege.Department;
import bean.privilege.Employee;
import service.privilege.DepartmentService;
import dao.impl.BaseDaoImpl;
@Service("departmentService") @Transactional
public class DepartmentServiceImpl extends BaseDaoImpl<Department> implements DepartmentService {

	@Override
	public void delete(Serializable... ids) {
		for(Serializable id:ids){
			Department department=this.get(id);
			for(Employee employee:department.getEmployees()){
				employee.setDepartment(null);
			}
			super.delete(id);
		}
	}

	@Override
	public void delete(Department... objs) {
		for(Department department:objs){
			department=this.get(department.getDepartmentid());
			for(Employee employee:department.getEmployees()){
				employee.setDepartment(null);
			}
			super.delete(department);
		}
	}
	
	
}
