package service.privilege.impl;

import java.io.Serializable;

import org.hibernate.Query;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import bean.privilege.Employee;
import service.privilege.EmployeeService;
import dao.impl.BaseDaoImpl;
@Service("employeeService") @Transactional
public class EmployeeServiceImpl extends BaseDaoImpl<Employee> implements EmployeeService {
	@Override
	public boolean validate(String username,String password){
		Query query=this.getSession().createQuery("select count(*) from Employee where username=:username and password=:password and visible=:visible");
		query.setString("username", username).setString("password", password).setBoolean("visible", true);
		long count=(Long)query.uniqueResult();
		return count>0;
	}
	
	@Override
	public boolean exist(String username){
		Query query=this.getSession().createQuery("select count(*) from Employee where username=:username");
		query.setString("username", username);
		long count=(Long)query.uniqueResult();
		return count>0;
	}

	@Override
	public void delete(Serializable... ids) {
		if(ids==null || ids.length <=0) return;
		Query query=this.getSession().createQuery("update Employee set visible=:visible where username in (:usernames)");
		query.setBoolean("visible", false).setParameterList("usernames", ids);
		query.executeUpdate();
	}

	@Override
	public void delete(Employee... objs) {
		for(Employee employee:objs){
			Query query=this.getSession().createQuery("update Employee set visible=:visible where username in (:username)");
			query.setBoolean("visible", false).setString("username", employee.getUsername());
			query.executeUpdate();
		}
		
	}

	
}
