package bos.service.impl;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import bos.dao.IRoleDao;
import bos.domain.Function;
import bos.domain.Role;
import bos.service.IRoleService;
import bos.utils.PageBean;
@Service @Transactional
public class RoleServiceImpl implements IRoleService {
	@Resource
	private IRoleDao roleDao;

	@SuppressWarnings("unchecked")
	@Override
	public void save(Role role, String ids) {
		roleDao.save(role);
		//角色关联权限
		String[] functionIds = ids.split(",");
		for (String fid : functionIds) {
			role.getFunctions().add(new Function(fid));
		}
	}

	@Override
	public void pageQuery(PageBean pageBean) {
		roleDao.pageQuery(pageBean);
		
	}

	@Override
	public List<Role> findAll() {
		return roleDao.findAll();
	}

	

}
