package bos.service.impl;

import javax.annotation.Resource;

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

}
