package bos.web.action;

import java.io.IOException;
import java.util.List;

import javax.annotation.Resource;

import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;

import com.opensymphony.xwork2.ActionContext;

import bos.domain.Workordermanage;
import bos.service.IWorkordermanageService;
import bos.web.action.base.BaseAction;
@Controller @Scope("prototype")
public class WorkordermanageAction extends BaseAction<Workordermanage> {
	@Resource
	private IWorkordermanageService workordermanageService;
	
	/**
	 *添加工作单
	 * @return
	 * @throws IOException
	 */
	public String add() throws IOException{
		workordermanageService.save(model);
		this.writeHtml("1");
		return NONE;
	}
	/**
	 * 查询没有启动流程的工作单(start为0)
	 * @return
	 * @throws IOException
	 */
	public String list() throws IOException{
		List<Workordermanage> list= workordermanageService.findListNotStart();
		ActionContext.getContext().getValueStack().set("list", list);
		return "list";
	}
	
	/**
	 * 启动物流配送流程
	 * @return
	 * @throws IOException
	 */
	public String start() throws IOException{
		//启动流程实例并设置流程变量,修改工作单中的start为1
		return "tolist";
	}

}
