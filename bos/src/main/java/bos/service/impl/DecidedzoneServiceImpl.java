package bos.service.impl;

import java.util.Set;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import bos.dao.IDecidedzoneDao;
import bos.dao.ISubareaDao;
import bos.domain.Decidedzone;
import bos.domain.Subarea;
import bos.service.IDecidedzoneService;
import bos.utils.PageBean;
@Service @Transactional
public class DecidedzoneServiceImpl implements IDecidedzoneService {
	@Resource
	private IDecidedzoneDao decidedzoneDao;
	@Resource
	private ISubareaDao subareaDao;
	@Override
	public void save(Decidedzone model, String[] subareaid) {
		decidedzoneDao.save(model);
		for (String sid : subareaid) {
			subareaDao.executeUpdate("updateSubareas", model.getId(),sid);//此处事物未提交,所以hibernate会在事物结束后自动关联数据库
		}
		
	}
	@Override
	public void pageQuery(PageBean pageBean) {
		decidedzoneDao.pageQuery(pageBean);
		
	}
	@Override
	public Decidedzone findById(String id) {
		return decidedzoneDao.findById(id);
	}
	@Override
	public void update(Decidedzone decidedzone, String[] subareaid) {
		decidedzoneDao.update(decidedzone);
		@SuppressWarnings("unchecked")
		Set<Subarea> subareas = decidedzone.getSubareas();
		//先清空原来的关系
		for (Subarea subarea : subareas) {
			subarea.setDecidedzone(null);
		}
		//再重新建立关系
		for (String sid : subareaid) {
			Subarea subarea = subareaDao.findById(sid);
			subarea.setDecidedzone(decidedzone);
		}
	}

}
