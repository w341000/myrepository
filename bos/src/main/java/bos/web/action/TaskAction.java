package bos.web.action;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.activiti.engine.RuntimeService;
import org.activiti.engine.TaskService;
import org.activiti.engine.runtime.ProcessInstance;
import org.activiti.engine.task.Task;
import org.activiti.engine.task.TaskQuery;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;

import bos.domain.Workordermanage;
import bos.service.IWorkordermanageService;
import bos.utils.BOSContext;
import bos.web.action.base.BaseAction;

import com.opensymphony.xwork2.ActionContext;

@Controller
@Scope("prototype")
public class TaskAction extends BaseAction<Object> {
	@Resource
	private TaskService taskService;
	@Resource
	private RuntimeService runtimeService;
	@Resource
	private IWorkordermanageService workordermanageService;
	private String taskId;// 任务id
	private Integer check;//审核结果:0:审核不通过,1:审核通过
	
	public void setCheck(Integer check) {
		this.check = check;
	}
	public void setTaskId(String taskId) {
		this.taskId = taskId;
	}
	public String getTaskId() {
		return taskId;
	}


	/**
	 * 查询组任务
	 */
	public String findGroupTask() {
		TaskQuery query = taskService.createTaskQuery();
		query.taskCandidateUser(BOSContext.getLoginUser().getId());
		List<Task> list = query.list();
		ActionContext.getContext().getValueStack().set("list", list);
		return "grouptasklist";
	}

	/**
	 * 根据任务id查询对应流程变量数据
	 * 
	 * @throws IOException
	 */
	public String showData() throws IOException {
		Map<String, Object> variables = taskService.getVariables(taskId);
		this.writeHtml(variables.toString());
		return NONE;
	}

	/**
	 * 拾取组任务
	 */
	public String takeTask() {
		taskService.claim(taskId, BOSContext.getLoginUser().getId());
		return "togrouptasklist";
	}

	/**
	 * 查看个人任务
	 */
	public String findPersonalTask() {
		TaskQuery query = taskService.createTaskQuery();
		query.taskAssignee(BOSContext.getLoginUser().getId());
		List<Task> list = query.list();
		ActionContext.getContext().getValueStack().set("list", list);
		return "personaltasklist";
	}

	/**
	 * 办理审核工作单任务
	 */
	public String checkWorkOrderManage() {
		// 跳转到审核工作单页面,展示当前对应工作单信息
		// 根据任务id查询任务对象
		Task task = taskService.createTaskQuery().taskId(taskId).singleResult();
		// 根据任务对象查询流程实例id
		String processInstanceId = task.getProcessInstanceId();
		ProcessInstance processInstance = runtimeService
				.createProcessInstanceQuery()
				.processInstanceId(processInstanceId).singleResult();
		String workordermanageId = processInstance.getBusinessKey();
		Workordermanage workordermanage=workordermanageService.findById(workordermanageId);
		if(check==null){
		//跳转审核页面
			ActionContext.getContext().getValueStack().set("workordermanage", workordermanage);
			return "check";
		}else{
			workordermanageService.checkWorkordermanage(taskId,check,workordermanageId);
			return "topersonaltasklist";
		}
	}
	
	/**
	 * 办理出库任务
	 */
	public String outStore(){
		taskService.complete(taskId);
		return "topersonaltasklist";
	}
	/**
	 * 办理配送任务
	 */
	public String transferGoods(){
		taskService.complete(taskId);
		return "topersonaltasklist";
	}
	/**
	 * 办理签收任务
	 */
	public String receive(){
		taskService.complete(taskId);
		return "topersonaltasklist";
	}
}
