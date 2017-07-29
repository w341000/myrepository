package bos.web.action;

import java.io.IOException;
import java.util.List;

import javax.annotation.Resource;

import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;

import bos.domain.Function;
import bos.service.IFunctionService;
import bos.web.action.base.BaseAction;
@Controller@Scope("prototype")
public class FunctionAction extends BaseAction<Function> {
	
	@Resource 
	private IFunctionService functionService;
	
	/**
	 * 分页查询
	 * @throws IOException 
	 */
	public String pageQuery() throws IOException{
			String page = model.getPage();
			pageBean.setCurrentPage(Integer.parseInt(page));
			functionService.pageQuery(pageBean);
			//过滤需要传输的json内容
			String[]  excludes=new String[]{"pageSize","currentPage","detachedCriteria","functions","roles","function"};
			this.WritePageBean2Json(excludes);
			return NONE;
	}
	/**
	 * 查询所有function
	 */
	public String listajax() throws IOException{
		List<Function> list=functionService.findAll();
		String[]  excludes=new String[]{"function","functions","roles"};
		this.WriteList2Json(list, excludes);
		return NONE;
	}

	public String add(){
		functionService.save(model);
		return "list";
	}
}
