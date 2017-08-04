package bos.service.impl;

import javax.annotation.Resource;

import org.activiti.engine.IdentityService;
import org.activiti.engine.impl.persistence.entity.UserEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import bos.dao.IRoleDao;
import bos.dao.IUserDao;
import bos.domain.Role;
import bos.domain.User;
import bos.service.IUserService;
import bos.utils.MD5Utils;
import bos.utils.PageBean;
@Service @Transactional
public class UserServiceImpl implements IUserService {
	@Resource
	private IUserDao userDao;
	@Resource
	private IRoleDao roleDao;
	@Resource
	private IdentityService identityService;

	@Override
	public User login(User user) {
		String username=user.getUsername();
		String password=user.getPassword();
		password=MD5Utils.md5(password);//md5加密
		return userDao.findByUsernameAndPassword(username,password);
	}

	@Override
	public void editPassword(String password, String id) {
		userDao.executeUpdate("editPassword", password,id);
		
	}

	@Override
	public void pageQuery(PageBean pageBean) {
		userDao.pageQuery(pageBean);
	}

	/**
	 * 保存一个用户,同步到activiti的act_id_user和act_id_membership
	 */
	@Override
	public void save(User user, String[] roleIds) {
		String password = user.getPassword();
		password=MD5Utils.md5(password);
		user.setPassword(password);
		userDao.save(user);//持久对象
		
		org.activiti.engine.identity.User actUser=new UserEntity(user.getId());
		identityService.saveUser(actUser);//同步用户
		
		
		//用户关联角色
		for (String rid : roleIds) {
			Role role=roleDao.findById(rid);
			user.getRoles().add(role);
			identityService.createMembership(actUser.getId(), role.getName());//同步关系
		}
		
	}

}
