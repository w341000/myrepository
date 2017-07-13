package web.action.privilege;

import java.io.File;

import javax.annotation.Resource;

import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;

import service.privilege.DepartmentService;
import service.privilege.EmployeeService;
import service.privilege.PrivilegeGroupService;
import utils.WebUtil;

import bean.privilege.Employee;
import bean.privilege.PrivilegeGroup;
import web.action.BaseAction;
@Controller @Scope("prototype")
public class EmployeeManageAction extends BaseAction<Employee> {
	@Resource
	private  EmployeeService employeeService;
	@Resource
	private DepartmentService departmentService;
	@Resource
	private PrivilegeGroupService privilegeGroupService;
	
	private File picture;
	private String pictureContentType;
	private String pictureFileName;
	
	private String[] groupids;
	


	public String[] getGroupids() {
		return groupids;
	}
	public void setGroupids(String[] groupids) {
		this.groupids = groupids;
	}
	public File getPicture() {
		return picture;
	}

	public void setPicture(File picture) {
		this.picture = picture;
	}

	public String getPictureContentType() {
		return pictureContentType;
	}

	public void setPictureContentType(String pictureContentType) {
		this.pictureContentType = pictureContentType;
	}

	public String getPictureFileName() {
		return pictureFileName;
	}

	public void setPictureFileName(String pictureFileName) {
		this.pictureFileName = pictureFileName;
	}

	/**
	 * 员工添加界面
	 * @return
	 */
	@Permission(module = "employee", privilege = "insert")
	public String regEmployeeUI(){
		request.put("departments",departmentService.query());
		return "add";
	}
	/**
	 * 员工添加
	 * @return
	 */@Permission(module = "employee", privilege = "insert")
	public String regEmployee(){
		if(picture!=null && picture.length()>0 && pictureFileName!=null && !"".equals(pictureFileName)){
			String uuidfilename=WebUtil.generateUUIDFileName(pictureFileName);
			String path="/images/employee/"+model.getUsername()+"/";//路径/images/employee/[username]/文件名
			WebUtil.saveUUIDFile(uuidfilename, path, this.picture);
			model.setImageName(uuidfilename);
		}
		this.employeeService.save(model);
		request.put("message", "员工添加成功");
		request.put("urladdress", "/control/employee/list.action");
		return "message";
		
	}
	
	/**
	 * 配合ajax校验用户名是否存在
	 * @return
	 */
	public String exist(){
		request.put("exist", employeeService.exist(model.getUsername().trim()));
		return "usernameExist";
	}
	
	/**
	 * 修改员工信息界面
	 * @return
	 */@Permission(module = "employee", privilege = "update")
	public String editEmployeeUI(){
		request.put("employee", this.employeeService.get(model.getUsername()));
		request.put("departments",departmentService.query());
		return "edit";
	}
	
	/**
	 * 修改员工信息
	 * @return
	 */@Permission(module = "employee", privilege = "update")
	public String editEmployee(){
		Employee employee=employeeService.get(model.getUsername());
		employee.setRealname(model.getRealname());
		employee.setGender(model.getGender());
		employee.getIdCard().setCardno(model.getIdCard().getCardno());
		employee.getIdCard().setBirthday(model.getIdCard().getBirthday());
		employee.getIdCard().setAddress(model.getIdCard().getAddress());
		employee.setPhone(model.getPhone());
		employee.setEmail(model.getEmail());
		employee.setDegree(model.getDegree());
		employee.setSchool(model.getSchool());
		employee.setDepartment(model.getDepartment());
		if(picture!=null && picture.length()>0 && pictureFileName!=null && !"".equals(pictureFileName)){
			String uuidfilename=WebUtil.generateUUIDFileName(pictureFileName);
			String path="/images/employee/"+model.getUsername()+"/";//路径/images/employee/[username]/文件名
			WebUtil.saveUUIDFile(uuidfilename, path, this.picture);
			model.setImageName(uuidfilename);
			employee.setImageName(model.getImageName());
		}
		this.employeeService.update(employee);
		request.put("message", "员工修改成功");
		request.put("urladdress", "/control/employee/list.action");
		return "message";
		
	}
	
	/**
	 * 员工离职设置
	 * @return
	 */@Permission(module = "employee", privilege = "leave")
	public String leave(){
		this.employeeService.delete(model.getUsername());
		request.put("message", "员工离职成功");
		request.put("urladdress", "/control/employee/list.action");
		return "message";
	}
	/**
	 * 员工查询界面
	 * @return
	 */@Permission(module = "employee", privilege = "query")
	public String query(){
		request.put("departments",departmentService.query());
		return "query";
	}
	
	/**
	 * 员工权限组设置界面
	 * @return
	 */@Permission(module = "employee", privilege = "privilegeSet")
	public String privilegeGroupSetUI(){
		request.put("employee",employeeService.get(model.getUsername()));
		request.put("groups",privilegeGroupService.query());
		return "privilegeSet";
	}
	
	/**
	 * 员工权限组设置
	 * @return
	 */@Permission(module = "employee", privilege = "privilegeSet")
	public String privilegeGroupSet(){
		Employee employee=employeeService.get(model.getUsername());
		employee.getGroups().clear();
		for(String groupid:groupids)
			employee.addPrivilegeGroup(new PrivilegeGroup(groupid));
		
		employeeService.update(employee);
		request.put("message", "设置成功");
		request.put("urladdress", "/control/employee/list.action");
		return "message";
	}
}
