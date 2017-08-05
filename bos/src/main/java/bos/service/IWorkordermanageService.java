package bos.service;

import java.util.List;

import bos.domain.Workordermanage;

public interface IWorkordermanageService {

	public void save(Workordermanage model);

	public List<Workordermanage> findListNotStart();

	public void start(String id);

	public Workordermanage findById(String workordermanageId);

	/**
	 * 处理审核工作单任务
	 * @param taskId
	 * @param check
	 * @param workordermanageId 
	 */
	public void checkWorkordermanage(String taskId, Integer check, String workordermanageId);

}
