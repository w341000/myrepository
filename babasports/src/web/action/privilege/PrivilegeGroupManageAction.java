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
 * Ȩ�������
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
	 * Ȩ������ӽ���
	 * @return
	 */
	public String addUI(){
		request.put("privileges",systemPrivilegeService.query());
		return "add";
	}
	
	/**
	 * Ȩ�������
	 * @return
	 */
	public String add(){
		PrivilegeGroup group=new PrivilegeGroup();
		group.setName(name);
		for(SystemPrivilegePK id:privileges)
			group.addSystemPrivilege(new SystemPrivilege(id));
		
		privilegeGroupService.save(group);
		request.put("message", "Ȩ������ӳɹ�");
		request.put("urladdress", "/control/privilegegroup/list.action");
		return "message";
	}
	/**
	 * Ȩ�����޸Ľ���
	 * @return
	 */
	public String editUI(){
		request.put("privileges",systemPrivilegeService.query());
		request.put("group",privilegeGroupService.get(groupid));
		return "edit";
	}
	/**
	 * Ȩ�����޸�
	 * @return
	 */
	public String edit(){
		PrivilegeGroup group=privilegeGroupService.get(groupid);
		group.setName(name);
		group.getPrivileges().clear();
		for(SystemPrivilegePK id:privileges)
			group.addSystemPrivilege(new SystemPrivilege(id));
		privilegeGroupService.update(group);
		request.put("message", "Ȩ�����޸ĳɹ�");
		request.put("urladdress", "/control/privilegegroup/list.action");
		return "message";
	}
	
	/**
	 * Ȩ����ɾ��
	 * @return
	 */
	public String delete(){
		privilegeGroupService.delete(this.groupid);
		request.put("message", "Ȩ����ɾ���ɹ�");
		request.put("urladdress", "/control/privilegegroup/list.action");
		return "message";
	}
}
