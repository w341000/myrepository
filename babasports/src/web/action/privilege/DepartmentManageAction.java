package web.action.privilege;

import javax.annotation.Resource;

import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;

import service.privilege.DepartmentService;

import bean.privilege.Department;
import web.action.BaseAction;
/**
 * ���Ź���
 */
@Controller @Scope("prototype")
public class DepartmentManageAction extends BaseAction<Department> {
	@Resource
	private DepartmentService departmentService;
	
	
	/**
	 * ������ӽ���
	 * @return
	 */
	@Permission(module = "department", privilege = "insert")
	public String addDepartmentUI(){
	
		return "add";
	}
	
	
	/**
	 * �������
	 * @return
	 */
	@Permission(module = "department", privilege = "insert")
	public String addDepartment(){
		
		departmentService.save(model);
		request.put("message", "������ӳɹ�");
		request.put("urladdress", "/control/department/list.action");
		return "message";
	}
	/**
	 * �����޸Ľ���
	 * @return
	 */
	 @Permission(module = "department", privilege = "update")
	public String editDepartmentUI(){
		request.put("department", departmentService.get(model.getDepartmentid()));
		return "edit";
	}
	
	/**
	 * �����޸�
	 * @return
	 */
	 @Permission(module = "department", privilege = "update")
	public String editDepartment(){
		 Department department= departmentService.get(model.getDepartmentid());
		 department.setName(model.getName());
		departmentService.update(department);
		request.put("message", "�����޸ĳɹ�");
		request.put("urladdress", "/control/department/list.action");
		return "message";
	}
	
	/**
	 * ����ɾ��
	 * @return
	 */
	 @Permission(module = "department", privilege = "delete")
	public String delete(){
		departmentService.delete(model.getDepartmentid());
		request.put("message", "����ɾ���ɹ�");
		request.put("urladdress", "/control/department/list.action");
		return "message";
	}
}
