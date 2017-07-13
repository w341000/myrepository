package web.action;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.annotation.Resource;

import org.hibernate.SQLQuery;
import org.hibernate.SessionFactory;
import org.junit.Test;
import org.springframework.context.annotation.Scope;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Controller;

import bean.privilege.Employee;
import bean.privilege.IDCard;
import bean.privilege.PrivilegeGroup;
import bean.privilege.SystemPrivilege;
import bean.user.Gender;

import service.book.GeneratedOrderidService;
import service.privilege.EmployeeService;
import service.privilege.PrivilegeGroupService;
import service.privilege.SystemPrivilegeService;

@Controller @Scope("prototype")
public class SystemInitAction extends BaseAction<Object> {

	@Resource 
	private GeneratedOrderidService generatedOrderidService;
	@Resource 
	private  SystemPrivilegeService systemPrivilegeService;
	@Resource 
	private  PrivilegeGroupService privilegeGroupService;
	@Resource 
	private EmployeeService employeeService;
	@Resource
	protected SessionFactory sessionFactory;
	
	/**
	 * 执行系统初始化工作的action
	 */
	@Override
	public String execute() throws Exception {
		
		this.generatedOrderidService.init();//初始化
		
		initPrivileges();//权限初始化
		initPrivilegeGroup();//权限组
		initAdmin();//初始化系统管理员
		initTable();//需要增量索引的表中添加updateTime时间戳字段
		request.put("message", "初始化完成");
		request.put("urladdress", "/");
		return "message";
	}
	private void initTable() throws IOException {
		createField("brand","updateTime");
		createField("productinfo","updateTime");
		createField("producttype","updateTime");
		createField("productstyle","updateTime");
	}
	private String selectsql(String table,String column){
		return "SELECT * FROM information_schema.columns WHERE table_schema = DATABASE()  AND table_name ='"+table+"' AND column_name ='"+column+"'";
				
	}
	private String editsql(String table,String column){
		return "ALTER TABLE "+table+" ADD COLUMN "+column+" timestamp NULL ON UPDATE CURRENT_TIMESTAMP DEFAULT CURRENT_TIMESTAMP ;";
				
	}
	private void createField(String table,String column) throws IOException{
		String sql=selectsql(table,column);
		SQLQuery query=sessionFactory.openSession().createSQLQuery(sql);
		if(query.list().isEmpty()){
			sql=editsql(table,column);
			query=sessionFactory.openSession().createSQLQuery(sql);
			query.executeUpdate();
		}
	 }
	/**
	 * 初始化管理员
	 */
	private void initAdmin() {
		if(employeeService.getCount()==0){
			Employee employee=new Employee();
			employee.setUsername("admin");
			employee.setPassword("341000");
			employee.setRealname("系统管理员");
			employee.setGender(Gender.MAN);
			employee.setIdCard(new IDCard("2333", "北京", new Date()));
			employee.getGroups().addAll(privilegeGroupService.query());//赋予权限
			employeeService.save(employee);
		}
		
	}
	/**
	 * 初始化系统权限组
	 */
	private void initPrivilegeGroup() {
		if(privilegeGroupService.getCount()==0){
			PrivilegeGroup group=new PrivilegeGroup();
			group.setName("系统权限组");
			group.getPrivileges().addAll(systemPrivilegeService.query());
			privilegeGroupService.save(group);
		}
		
		
	}
	/**
	 * 初始化权限
	 */
	private void initPrivileges() {
		if(systemPrivilegeService.getCount()==0){
			List<SystemPrivilege> privileges= new ArrayList<SystemPrivilege>();
			privileges.add(new SystemPrivilege("department", "view", "部门查看"));
			privileges.add(new SystemPrivilege("department", "insert", "部门添加"));
			privileges.add(new SystemPrivilege("department", "update", "部门修改"));
			privileges.add(new SystemPrivilege("department", "delete", "部门删除"));
			
			
			privileges.add(new SystemPrivilege("employee", "view", "员工查看"));
			privileges.add(new SystemPrivilege("employee", "insert", "员工添加"));
			privileges.add(new SystemPrivilege("employee", "update", "员工信息修改"));
			privileges.add(new SystemPrivilege("employee", "leave", "员工离职设置"));
			privileges.add(new SystemPrivilege("employee", "privilegeSet", "员工权限分配"));
			privileges.add(new SystemPrivilege("employee", "query", "员工查询"));
			systemPrivilegeService.saves(privileges);
		}
	}
	

}
