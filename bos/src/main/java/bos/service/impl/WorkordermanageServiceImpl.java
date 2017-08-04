package bos.service.impl;

import java.util.List;

import javax.annotation.Resource;

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

}
