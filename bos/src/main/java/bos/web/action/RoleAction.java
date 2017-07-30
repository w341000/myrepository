package bos.web.action;

import java.io.IOException;
import java.util.List;

import javax.annotation.Resource;

import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;

import bos.domain.Function;
import bos.domain.Role;
import bos.service.IRoleService;
import bos.web.action.base.BaseAction;
/**
 * 角色管理
 */
@Controller@Scope("prototype")
public class RoleAction extends BaseAction<Role> {
	private String ids;
	@Resource
	private IRoleService roleService;

	public void setIds(String ids) {
		this.ids = ids;
	}
	
	
	public String add(){
		roleService.save(model,ids);
		return "list";
	}
	
	public String pageQuery() throws IOException{
		roleService.pageQuery(pageBean);
		//过滤需要传输的json内容
		String[]  excludes=new String[]{"pageSize","currentPage","detachedCriteria","users","functions"};
		this.WritePageBean2Json(excludes);
		return NONE;
	}
	/**
	 * 查询所有角色数据,返回json
	 * @return
	 * @throws IOException 
	 */
	public String listajax() throws IOException{
		List<Role> list=roleService.findAll();
		String[]  excludes=new String[]{"functions","users"};
		this.WriteList2Json(list, excludes);
		return NONE;
	}

}
