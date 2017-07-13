package web.action.privilege;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.Resource;

import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;

import service.privilege.EmployeeService;

import bean.PageBean;
import bean.QueryResult;
import bean.privilege.Employee;
import web.action.BaseAction;
@Controller @Scope("prototype")
public class EmployeeListAction extends BaseAction<PageBean> {
	
	@Resource
	private  EmployeeService employeeService;
	private String username;
	private String query;
	private String realname;
	private String departmentid;
	
	
	public String getUsername() {
		return username;
	}
	public void setUsername(String username) {
		this.username = username;
	}
	public String getQuery() {
		return query;
	}
	public void setQuery(String query) {
		this.query = query;
	}
	public String getRealname() {
		return realname;
	}
	public void setRealname(String realname) {
		this.realname = realname;
	}
	public String getDepartmentid() {
		return departmentid;
	}
	public void setDepartmentid(String departmentid) {
		this.departmentid = departmentid;
	}


	@Override
	public String execute() throws Exception {
		StringBuffer where=new StringBuffer();
		String orderby=" order by realname desc";
		List<String> paramNames=new ArrayList<String>();
		List<Object> params=new ArrayList<Object>();
		if("true".equals(query)){
			//来自查询页面
			if(username!=null && !"".equals(username)){//根据用户名查询
				where.append(" and username like :username");
				paramNames.add("username");
				params.add("%"+username+"%");
			}
			if(realname!=null && !"".equals(realname)){//根据真实姓名查询
				where.append(" and realname like :realname");
				paramNames.add("realname");
				params.add("%"+realname+"%");
			}
			if(departmentid!=null && !"".equals(departmentid)){//根据部门id查询
				where.append(" and department.departmentid=:departmentid");
				paramNames.add("departmentid");
				params.add(departmentid);
			}
		}
		QueryResult<Employee> qr=employeeService.findByPage(where.toString(), orderby, model.getCurrentpage(), model.getPagesize(), paramNames.toArray(new String[]{}),params.toArray());
		model.setList(qr.getList());
		model.setTotalrecord(qr.getTotalrecord());
		request.put("pagebean", model);
		return SUCCESS;
	}
}
