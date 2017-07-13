package web.action.privilege;

import javax.annotation.Resource;

import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;

import service.privilege.DepartmentService;

import bean.privilege.Department;
import web.action.BaseAction;
/**
 * 部门管理
 */
@Controller @Scope("prototype")
public class DepartmentManageAction extends BaseAction<Department> {
	@Resource
	private DepartmentService departmentService;
	
	
	/**
	 * 部门添加界面
	 * @return
	 */
	@Permission(module = "department", privilege = "insert")
	public String addDepartmentUI(){
	
		return "add";
	}
	
	
	/**
	 * 部门添加
	 * @return
	 */
	@Permission(module = "department", privilege = "insert")
	public String addDepartment(){
		
		departmentService.save(model);
		request.put("message", "部门添加成功");
		request.put("urladdress", "/control/department/list.action");
		return "message";
	}
	/**
	 * 部门修改界面
	 * @return
	 */
	 @Permission(module = "department", privilege = "update")
	public String editDepartmentUI(){
		request.put("department", departmentService.get(model.getDepartmentid()));
		return "edit";
	}
	
	/**
	 * 部门修改
	 * @return
	 */
	 @Permission(module = "department", privilege = "update")
	public String editDepartment(){
		 Department department= departmentService.get(model.getDepartmentid());
		 department.setName(model.getName());
		departmentService.update(department);
		request.put("message", "部门修改成功");
		request.put("urladdress", "/control/department/list.action");
		return "message";
	}
	
	/**
	 * 部门删除
	 * @return
	 */
	 @Permission(module = "department", privilege = "delete")
	public String delete(){
		departmentService.delete(model.getDepartmentid());
		request.put("message", "部门删除成功");
		request.put("urladdress", "/control/department/list.action");
		return "message";
	}
}
