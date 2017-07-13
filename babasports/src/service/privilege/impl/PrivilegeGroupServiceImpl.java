package service.privilege.impl;

import java.io.Serializable;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import bean.privilege.Employee;
import bean.privilege.PrivilegeGroup;
import service.privilege.PrivilegeGroupService;
import dao.impl.BaseDaoImpl;
@Service("privilegeGroupService") @Transactional
public class PrivilegeGroupServiceImpl extends BaseDaoImpl<PrivilegeGroup> implements PrivilegeGroupService {

	@Override
	public void delete(Serializable... ids) {
		for(Serializable id:ids){
			PrivilegeGroup group=this.get(id);
			for(Employee employee:group.getEmployees()){
				employee.getGroups().remove(group);
			}
			super.delete(group);
		}
	}

	@Override
	public void delete(PrivilegeGroup... objs) {
		for(PrivilegeGroup group:objs){
			group=this.get(group.getGroupid());
			for(Employee employee:group.getEmployees()){
				employee.getGroups().remove(group);
			}
			super.delete(group);
		}
	}
	
	

}
