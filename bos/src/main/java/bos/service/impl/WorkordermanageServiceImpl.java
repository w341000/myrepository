package bos.service.impl;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import bos.dao.IWorkordermanageDao;
import bos.domain.Workordermanage;
import bos.service.IWorkordermanageService;
@Service@Transactional
public class WorkordermanageServiceImpl implements IWorkordermanageService {
	@Resource
	private IWorkordermanageDao workordermanageDao;

	@Override
	public void save(Workordermanage model) {
		workordermanageDao.save(model);
	}

}
