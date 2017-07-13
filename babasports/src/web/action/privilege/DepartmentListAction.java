package web.action.privilege;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.Resource;

import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;

import service.privilege.DepartmentService;
import bean.PageBean;
import bean.QueryResult;
import bean.privilege.Department;
import web.action.BaseAction;
/**
 * 部门分页列表
 */
@Controller @Scope("prototype")
public class DepartmentListAction extends BaseAction<PageBean> {
	@Resource
	private DepartmentService departmentService;
	

	@Override @Permission(module = "department", privilege = "view")
	public String execute() throws Exception {
		QueryResult<Department> qr=departmentService.findByPage("", "", model.getCurrentpage(), model.getPagesize(), null, null);
		model.setList(qr.getList());
		model.setTotalrecord(qr.getTotalrecord());
		request.put("pagebean", model);
		return SUCCESS;
	}
	
	
	

}
