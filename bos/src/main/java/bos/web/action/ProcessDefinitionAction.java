package bos.web.action;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.List;
import java.util.zip.ZipInputStream;

import javax.annotation.Resource;

import org.activiti.engine.RepositoryService;
import org.activiti.engine.repository.DeploymentBuilder;
import org.activiti.engine.repository.ProcessDefinition;
import org.activiti.engine.repository.ProcessDefinitionQuery;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;

import bos.web.action.base.BaseAction;

import com.opensymphony.xwork2.ActionContext;

/**
 * 流程定义action
 */
@Controller
@Scope("prototype")
public class ProcessDefinitionAction extends BaseAction<Object> {
	private static final long serialVersionUID = 9036941436866135107L;
	// 注入service
	@Resource
	private RepositoryService repositoryService;
	private String id; //接受流程定义id
	private File zipFile;//接受上传的流程压缩文件
	
	public void setId(String id) {
		this.id = id;
	}

	public void setZipFile(File zipFile) {
		this.zipFile = zipFile;
	}

	/**
	 * 查询最新版本流程定义列表
	 */
	public String list() {
		ProcessDefinitionQuery query = repositoryService
				.createProcessDefinitionQuery();
		query.latestVersion();// 过滤最新版本
		query.orderByProcessDefinitionName().desc();// 排序
		List<ProcessDefinition> list = query.list();
		// 压栈
		ActionContext.getContext().getValueStack().set("list", list);
		return "list";
	}

	/**
	 * 发布流程定义
	 * 
	 * @throws FileNotFoundException
	 */
	public String deploy() throws FileNotFoundException {
		DeploymentBuilder deploymentBuilder = repositoryService
				.createDeployment();
		deploymentBuilder.addZipInputStream(new ZipInputStream(
				new FileInputStream(zipFile)));
		deploymentBuilder.deploy();
		return "tolist";
	}
	/**
	 * 展示png图片
	 */
	public String showpng(){
		//获取png图片的输入流
		InputStream pngStream = repositoryService.getProcessDiagram(id);
		ActionContext.getContext().getValueStack().set("pngStream", pngStream);
		return "showpng";
	}
	/**
	 * 删除流程定义
	 * @throws IOException 
	 */
	public String delete() throws IOException{
		String dleTag="0";
		//根据流程定义id查询流程部署id
		ProcessDefinitionQuery query = repositoryService.createProcessDefinitionQuery();
		query.processDefinitionId(id);
		ProcessDefinition processDefinition = query.singleResult();
		try {
			repositoryService.deleteDeployment(processDefinition.getDeploymentId());
		} catch (Exception e) {
			//当前要删除的流程定义正在被使用
			dleTag="1";//删除失败
		}
		this.writeHtml(dleTag);
		return NONE;
	}
}
