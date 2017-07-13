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
	 * ִ��ϵͳ��ʼ��������action
	 */
	@Override
	public String execute() throws Exception {
		
		this.generatedOrderidService.init();//��ʼ��
		
		initPrivileges();//Ȩ�޳�ʼ��
		initPrivilegeGroup();//Ȩ����
		initAdmin();//��ʼ��ϵͳ����Ա
		initTable();//��Ҫ���������ı������updateTimeʱ����ֶ�
		request.put("message", "��ʼ�����");
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
	 * ��ʼ������Ա
	 */
	private void initAdmin() {
		if(employeeService.getCount()==0){
			Employee employee=new Employee();
			employee.setUsername("admin");
			employee.setPassword("341000");
			employee.setRealname("ϵͳ����Ա");
			employee.setGender(Gender.MAN);
			employee.setIdCard(new IDCard("2333", "����", new Date()));
			employee.getGroups().addAll(privilegeGroupService.query());//����Ȩ��
			employeeService.save(employee);
		}
		
	}
	/**
	 * ��ʼ��ϵͳȨ����
	 */
	private void initPrivilegeGroup() {
		if(privilegeGroupService.getCount()==0){
			PrivilegeGroup group=new PrivilegeGroup();
			group.setName("ϵͳȨ����");
			group.getPrivileges().addAll(systemPrivilegeService.query());
			privilegeGroupService.save(group);
		}
		
		
	}
	/**
	 * ��ʼ��Ȩ��
	 */
	private void initPrivileges() {
		if(systemPrivilegeService.getCount()==0){
			List<SystemPrivilege> privileges= new ArrayList<SystemPrivilege>();
			privileges.add(new SystemPrivilege("department", "view", "���Ų鿴"));
			privileges.add(new SystemPrivilege("department", "insert", "�������"));
			privileges.add(new SystemPrivilege("department", "update", "�����޸�"));
			privileges.add(new SystemPrivilege("department", "delete", "����ɾ��"));
			
			
			privileges.add(new SystemPrivilege("employee", "view", "Ա���鿴"));
			privileges.add(new SystemPrivilege("employee", "insert", "Ա�����"));
			privileges.add(new SystemPrivilege("employee", "update", "Ա����Ϣ�޸�"));
			privileges.add(new SystemPrivilege("employee", "leave", "Ա����ְ����"));
			privileges.add(new SystemPrivilege("employee", "privilegeSet", "Ա��Ȩ�޷���"));
			privileges.add(new SystemPrivilege("employee", "query", "Ա����ѯ"));
			systemPrivilegeService.saves(privileges);
		}
	}
	

}
