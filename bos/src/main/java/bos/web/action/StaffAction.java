package bos.web.action;

import java.io.IOException;

import javax.annotation.Resource;

import net.sf.json.JSONObject;
import net.sf.json.JsonConfig;

import org.hibernate.criterion.DetachedCriteria;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;

import bos.domain.Staff;
import bos.service.IStaffService;
import bos.utils.PageBean;
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
	public String delete(){
		staffService.deleteBatch(ids);
		return "list";
	}
	/**
	 * 修改取派员信息
	 */
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
}
