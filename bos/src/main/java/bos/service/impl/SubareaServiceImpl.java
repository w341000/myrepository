package bos.service.impl;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import bos.dao.ISubareaDao;
import bos.domain.Subarea;
import bos.service.ISubareaService;
import bos.utils.PageBean;
@Service @Transactional
public class SubareaServiceImpl implements ISubareaService {
	@Resource
	private ISubareaDao subareaDao;

	@Override
	public void save(Subarea model) {
		subareaDao.save(model);
	}

	@Override
	public void pageQuery(PageBean pageBean) {
		subareaDao.pageQuery(pageBean);
	}

	@Override
	public Subarea findById(String id) {
		return subareaDao.findById(id);
	}

	@Override
	public void update(Subarea subarea) {
		subareaDao.update(subarea);
	}

	@Override
	public List<Subarea> findAll() {
		return subareaDao.findAll();
	}
}
