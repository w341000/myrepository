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
	 * Ա����ӽ���
	 * @return
	 */
	@Permission(module = "employee", privilege = "insert")
	public String regEmployeeUI(){
		request.put("departments",departmentService.query());
		return "add";
	}
	/**
	 * Ա�����
	 * @return
	 */@Permission(module = "employee", privilege = "insert")
	public String regEmployee(){
		if(picture!=null && picture.length()>0 && pictureFileName!=null && !"".equals(pictureFileName)){
			String uuidfilename=WebUtil.generateUUIDFileName(pictureFileName);
			String path="/images/employee/"+model.getUsername()+"/";//·��/images/employee/[username]/�ļ���
			WebUtil.saveUUIDFile(uuidfilename, path, this.picture);
			model.setImageName(uuidfilename);
		}
		this.employeeService.save(model);
		request.put("message", "Ա����ӳɹ�");
		request.put("urladdress", "/control/employee/list.action");
		return "message";
		
	}
	
	/**
	 * ���ajaxУ���û����Ƿ����
	 * @return
	 */
	public String exist(){
		request.put("exist", employeeService.exist(model.getUsername().trim()));
		return "usernameExist";
	}
	
	/**
	 * �޸�Ա����Ϣ����
	 * @return
	 */@Permission(module = "employee", privilege = "update")
	public String editEmployeeUI(){
		request.put("employee", this.employeeService.get(model.getUsername()));
		request.put("departments",departmentService.query());
		return "edit";
	}
	
	/**
	 * �޸�Ա����Ϣ
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
			String path="/images/employee/"+model.getUsername()+"/";//·��/images/employee/[username]/�ļ���
			WebUtil.saveUUIDFile(uuidfilename, path, this.picture);
			model.setImageName(uuidfilename);
			employee.setImageName(model.getImageName());
		}
		this.employeeService.update(employee);
		request.put("message", "Ա���޸ĳɹ�");
		request.put("urladdress", "/control/employee/list.action");
		return "message";
		
	}
	
	/**
	 * Ա����ְ����
	 * @return
	 */@Permission(module = "employee", privilege = "leave")
	public String leave(){
		this.employeeService.delete(model.getUsername());
		request.put("message", "Ա����ְ�ɹ�");
		request.put("urladdress", "/control/employee/list.action");
		return "message";
	}
	/**
	 * Ա����ѯ����
	 * @return
	 */@Permission(module = "employee", privilege = "query")
	public String query(){
		request.put("departments",departmentService.query());
		return "query";
	}
	
	/**
	 * Ա��Ȩ�������ý���
	 * @return
	 */@Permission(module = "employee", privilege = "privilegeSet")
	public String privilegeGroupSetUI(){
		request.put("employee",employeeService.get(model.getUsername()));
		request.put("groups",privilegeGroupService.query());
		return "privilegeSet";
	}
	
	/**
	 * Ա��Ȩ��������
	 * @return
	 */@Permission(module = "employee", privilege = "privilegeSet")
	public String privilegeGroupSet(){
		Employee employee=employeeService.get(model.getUsername());
		employee.getGroups().clear();
		for(String groupid:groupids)
			employee.addPrivilegeGroup(new PrivilegeGroup(groupid));
		
		employeeService.update(employee);
		request.put("message", "���óɹ�");
		request.put("urladdress", "/control/employee/list.action");
		return "message";
	}
}
