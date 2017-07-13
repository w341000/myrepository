package web.action.privilege;

import javax.annotation.Resource;

import org.springframework.context.annotation.Scope;

import org.springframework.stereotype.Controller;

import service.privilege.PrivilegeGroupService;

import web.action.BaseAction;

import bean.PageBean;
import bean.QueryResult;
import bean.privilege.Department;
import bean.privilege.PrivilegeGroup;
/**
 * 权限组分页列表
 */
@Controller @Scope("prototype")
public class PrivilegeGroupListAction extends BaseAction<PageBean> {
	@Resource
	private PrivilegeGroupService privilegeGroupService;

	@Override
	public String execute() throws Exception {
		
		QueryResult<PrivilegeGroup> qr=privilegeGroupService.findByPage("", "", model.getCurrentpage(), model.getPagesize(), null, null);
		model.setList(qr.getList());
		model.setTotalrecord(qr.getTotalrecord());
		request.put("pagebean", model);
		return SUCCESS;
	}
	
	
	

}
