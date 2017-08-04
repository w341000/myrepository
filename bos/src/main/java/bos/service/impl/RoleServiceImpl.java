package bos.service.impl;

import java.util.List;

import javax.annotation.Resource;

import org.activiti.engine.IdentityService;
import org.activiti.engine.identity.Group;
import org.activiti.engine.impl.persistence.entity.GroupEntity;
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
	@Resource
	private IdentityService identityService;
	/**
	 * 保存角色同步到activiti的主表
	 */
	@SuppressWarnings("unchecked")
	@Override
	public void save(Role role, String ids) {
		roleDao.save(role);//持久化对象
		//使用角色名称作为组id
		Group group=new GroupEntity(role.getName());
		identityService.saveGroup(group);
		
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
