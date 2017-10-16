package bos.service.impl;

import java.util.List;

import javax.annotation.Resource;

import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.Restrictions;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import bos.dao.IStaffDao;
import bos.domain.Staff;
import bos.service.IStaffService;
import bos.utils.PageBean;
@Service @Transactional
public class StaffServiceImpl implements IStaffService {
	@Resource
	private IStaffDao staffDao;

	@Override
	public void save(Staff model) {
		staffDao.save(model);
	}

	@Override
	public void pageQuery(PageBean pageBean) {
		staffDao.pageQuery(pageBean);
	}

	@Override
	public void deleteBatch(String ids) {
		String[] staffIds=ids.split(",");
		for(String id:staffIds){
			staffDao.executeUpdate("staff.delete", id);
		}
	}

	@Override
	public Staff findById(String id) {
		return staffDao.findById(id);
	}

	@Override
	public void update(Staff staff) {
		staffDao.update(staff);
	}
	//查询没有作废的取派员,deltag为0
	@Override
	public List<Staff> findListNotDelete() {
		DetachedCriteria detachedCriteria=DetachedCriteria.forClass(Staff.class);
		detachedCriteria.add(Restrictions.eq("deltag", "0"));
		return staffDao.findByCriteria(detachedCriteria) ;
	}

}
