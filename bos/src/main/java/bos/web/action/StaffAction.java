package bos.web.action;

import java.io.IOException;
import java.util.List;

import javax.annotation.Resource;

import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.apache.shiro.authz.annotation.RequiresRoles;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;

import bos.domain.Staff;
import bos.service.IStaffService;
import bos.web.action.base.BaseAction;
@Controller@Scope("prototype")
public class StaffAction extends BaseAction<Staff> {

	private String ids;
	@Resource
	private IStaffService staffService;
	
	
	public void setIds(String ids) {
		this.ids = ids;
	}
	
	//取派员添加
	@RequiresPermissions("staff:add")
	public String add(){
		staffService.save(model);
		return "list";
	}

	
	//分页查询
	public String pageQuery() throws IOException{
		//设置离线查询
		staffService.pageQuery(pageBean);
		//过滤需要传输的json内容
		String[]  excludes=new String[]{"pageSize","currentPage","detachedCriteria","decidedzones"};
		this.WritePageBean2Json(excludes);
		return NONE;
	}
	
	/**
	 * 批量逻辑删除Staff
	 * @return
	 */
	@RequiresPermissions("staff:delete")
	public String delete(){
		staffService.deleteBatch(ids);
		return "list";
	}
	/**
	 * 修改取派员信息
	 */
	@RequiresPermissions("staff:edit")
	public String edit(){
		Staff staff=staffService.findById(model.getId());
		staff.setName(model.getName());
		staff.setTelephone(model.getTelephone());
		staff.setStation(model.getStation());
		staff.setHaspda(model.getHaspda());
		staff.setStandard(model.getStandard());
		staffService.update(staff);
		return "list";
	}
	/**
	 * 查询没有作废的取派员,返回json
	 * @throws IOException 
	 */
	public String listajax() throws IOException{
		List<Staff> list=staffService.findListNotDelete();
		String[] excludes=new String[]{"telephone","haspda","deltag","station","standard","decidedzones"};
		this.WriteList2Json(list, excludes);
		return NONE;
	}
}
