package bos.service.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.activiti.engine.RuntimeService;
import org.activiti.engine.TaskService;
import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.Restrictions;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import bos.dao.IWorkordermanageDao;
import bos.domain.Workordermanage;
import bos.service.IWorkordermanageService;
@Service@Transactional
public class WorkordermanageServiceImpl implements IWorkordermanageService {
	@Resource
	private IWorkordermanageDao workordermanageDao;
	@Resource
	private RuntimeService runtimeService;
	@Resource
	private TaskService taskService;
	@Override
	public void save(Workordermanage model) {
		workordermanageDao.save(model);
	}

	@Override
	public List<Workordermanage> findListNotStart() {
		DetachedCriteria detachedCriteria=DetachedCriteria.forClass(Workordermanage.class);
		detachedCriteria.add(Restrictions.eq("start", "0"));
		return workordermanageDao.findByCriteria(detachedCriteria);
	}
	/**
	 * 启动流程实例并设置流程变量,修改工作单中的start为1
	 */
	@Override
	public void start(String id) {
		Workordermanage workordermanage = workordermanageDao.findById(id);
		workordermanage.setStart("1");//设置已启动
		String processDefinitionKey="transfer";//流程定义key
		String businessKey = id;//业务主键--等于业务表的(工作单)主键,让工作流框架找到业务数据
		Map<String, Object> variables=new HashMap<String,Object>();
		variables.put("业务数据", workordermanage);
		runtimeService.startProcessInstanceByKey(processDefinitionKey, businessKey, variables);
	}

	@Override
	public Workordermanage findById(String workordermanageId) {
		return workordermanageDao.findById(workordermanageId);
	}

	@Override//处理审核工作单任务
	public void checkWorkordermanage(String taskId, Integer check,
			String workordermanageId) {
		Map<String, Object> variables=new HashMap<String,Object>();
		variables.put("check", check);
		//办理审核任务
		taskService.complete(taskId, variables);
		//如果审核不通过,需要修改工作单start为0
		if(check==0){
			Workordermanage workordermanage = workordermanageDao.findById(workordermanageId);
			workordermanage.setStart("0");
		}
	}

}
