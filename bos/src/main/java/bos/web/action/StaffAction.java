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
	private int page;
	private int rows;
	private String ids;
	@Resource
	private IStaffService staffService;
	
	
	public void setIds(String ids) {
		this.ids = ids;
	}
	public void setPage(int page) {
		this.page = page;
	}
	public void setRows(int rows) {
		this.rows = rows;
	}
	
	//取派员添加
	public String add(){
		staffService.save(model);
		return "list";
	}

	
	//分页查询
	public String pageQuery() throws IOException{
		PageBean pageBean=new PageBean();
		pageBean.setCurrentPage(page);
		pageBean.setPageSize(rows);
		//设置离线查询
		DetachedCriteria detachedCriteria=DetachedCriteria.forClass(Staff.class);
		pageBean.setDetachedCriteria(detachedCriteria);
		
		staffService.pageQuery(pageBean);
		//过滤需要传输的json内容
		JsonConfig jsonConfig=new JsonConfig();
		jsonConfig.setExcludes(new String[]{"pageSize","currentPage","detachedCriteria","decidedzones"});
		JSONObject jsonObject = JSONObject.fromObject(pageBean, jsonConfig);
		String json=jsonObject.toString();
		this.writeJson(json);
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
