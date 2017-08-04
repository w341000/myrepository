package bos.web.action;

import java.io.IOException;
import java.io.InputStream;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.activiti.engine.RepositoryService;
import org.activiti.engine.RuntimeService;
import org.activiti.engine.impl.persistence.entity.ProcessDefinitionEntity;
import org.activiti.engine.impl.pvm.process.ActivityImpl;
import org.activiti.engine.repository.ProcessDefinition;
import org.activiti.engine.repository.ProcessDefinitionQuery;
import org.activiti.engine.runtime.ProcessInstance;
import org.activiti.engine.runtime.ProcessInstanceQuery;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;

import com.opensymphony.xwork2.ActionContext;

import bos.web.action.base.BaseAction;
/**
 * 流程实例管理
 */
@Controller@Scope("prototype")
public class ProcessInstanceAction extends BaseAction<Object> {
	
	@Resource
	private RuntimeService runtimeService;
	@Resource
	private RepositoryService repositoryService;
	private String id;//接收流程实例id
	private String deploymentId;
	private String imageName;
	
	public String getDeploymentId() {
		return deploymentId;
	}
	public void setDeploymentId(String deploymentId) {
		this.deploymentId = deploymentId;
	}
	public String getImageName() {
		return imageName;
	}
	public void setImageName(String imageName) {
		this.imageName = imageName;
	}
	public void setId(String id) {
		this.id = id;
	}
	/**
	 * 查询流程实例列表数据
	 */
	public String list() {
		ProcessInstanceQuery query = runtimeService.createProcessInstanceQuery();
		query.orderByProcessDefinitionId().desc();
		List<ProcessInstance> list = query.list();
		// 压栈
		ActionContext.getContext().getValueStack().set("list", list);
		return "list";
	}
	/**
	 * 根据流程实例id查询对应流程变量数据
	 * @throws IOException 
	 */
	public String findData() throws IOException{
		Map<String, Object> variables = runtimeService.getVariables(id);
		this.writeHtml(variables.toString());
		return NONE;
	}
	/**
	 * 根据流程实例id查询坐标,部署id和图片名称
	 */
	public String showPng(){
		//根据流程实例id查询流程实例对象
		 ProcessInstance processInstance = runtimeService.createProcessInstanceQuery().processInstanceId(id).singleResult();
		//根据流程实例对象查询流程定义id
		 String processDefinitionId = processInstance.getProcessDefinitionId();
		 //根据流程定义id查询流程定义对象
		 ProcessDefinition processDefinition = repositoryService.createProcessDefinitionQuery().processDefinitionId(processDefinitionId).singleResult();
		 //根据流程定义对象查询部署id
		 deploymentId = processDefinition.getDeploymentId();
		 //图片名称
		 imageName = processDefinition.getDiagramResourceName();
		 
		 //查询坐标
		 //1,获得当前流程实例执行的节点
		 String activityId = processInstance.getActivityId();
		 //2,加载(bpmn)xml文件,获得流程定义对象
		 ProcessDefinitionEntity pd = (ProcessDefinitionEntity) repositoryService.getProcessDefinition(processDefinitionId);
		 //3.根据activitiId获得含有坐标信息的对象
		 ActivityImpl activity = pd.findActivity(activityId);
		 int x = activity.getX();
		 int y = activity.getY();
		 int height = activity.getHeight();
		 int width = activity.getWidth();
		 ActionContext.getContext().getValueStack().set("x", x);
		 ActionContext.getContext().getValueStack().set("y", y);
		 ActionContext.getContext().getValueStack().set("height", height);
		 ActionContext.getContext().getValueStack().set("width", width);
		 
		 
		return "showPng";
	}
	
	/**
	 * 获取png输入流
	 */
	public String viewImage(){
		InputStream pngStream = repositoryService.getResourceAsStream(deploymentId, imageName);
		ActionContext.getContext().getValueStack().set("pngStream", pngStream);
		
		return "viewImage";
	}
}
