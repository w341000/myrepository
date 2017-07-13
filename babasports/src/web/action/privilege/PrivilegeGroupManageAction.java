package web.action.privilege;

import java.util.ArrayList;

import javax.annotation.Resource;


import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;

import service.privilege.PrivilegeGroupService;
import service.privilege.SystemPrivilegeService;

import bean.privilege.PrivilegeGroup;
import bean.privilege.SystemPrivilege;
import bean.privilege.SystemPrivilegePK;
import web.action.BaseAction;
/**
 * 权限组管理
 * @author Administrator
 *
 */
@Controller @Scope("prototype")
public class PrivilegeGroupManageAction extends BaseAction<Object> {
	@Resource
	private PrivilegeGroupService privilegeGroupService;
	@Resource
	private SystemPrivilegeService systemPrivilegeService;
	private SystemPrivilegePK[] privileges;
	private String name;
	private String groupid;
	

	public String getGroupid() {
		return groupid;
	}
	public void setGroupid(String groupid) {
		this.groupid = groupid;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public SystemPrivilegePK[] getPrivileges() {
		return privileges;
	}
	public void setPrivileges(SystemPrivilegePK[] privileges) {
		this.privileges = privileges;
	}

	/**
	 * 权限组添加界面
	 * @return
	 */
	public String addUI(){
		request.put("privileges",systemPrivilegeService.query());
		return "add";
	}
	
	/**
	 * 权限组添加
	 * @return
	 */
	public String add(){
		PrivilegeGroup group=new PrivilegeGroup();
		group.setName(name);
		for(SystemPrivilegePK id:privileges)
			group.addSystemPrivilege(new SystemPrivilege(id));
		
		privilegeGroupService.save(group);
		request.put("message", "权限组添加成功");
		request.put("urladdress", "/control/privilegegroup/list.action");
		return "message";
	}
	/**
	 * 权限组修改界面
	 * @return
	 */
	public String editUI(){
		request.put("privileges",systemPrivilegeService.query());
		request.put("group",privilegeGroupService.get(groupid));
		return "edit";
	}
	/**
	 * 权限组修改
	 * @return
	 */
	public String edit(){
		PrivilegeGroup group=privilegeGroupService.get(groupid);
		group.setName(name);
		group.getPrivileges().clear();
		for(SystemPrivilegePK id:privileges)
			group.addSystemPrivilege(new SystemPrivilege(id));
		privilegeGroupService.update(group);
		request.put("message", "权限组修改成功");
		request.put("urladdress", "/control/privilegegroup/list.action");
		return "message";
	}
	
	/**
	 * 权限组删除
	 * @return
	 */
	public String delete(){
		privilegeGroupService.delete(this.groupid);
		request.put("message", "权限组删除成功");
		request.put("urladdress", "/control/privilegegroup/list.action");
		return "message";
	}
}
