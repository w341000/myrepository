package bos.service.impl;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

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

	@Override
	public void save(User user, String[] roleIds) {
		String password = user.getPassword();
		password=MD5Utils.md5(password);
		user.setPassword(password);
		userDao.save(user);//持久对象
		//用户关联角色
		for (String rid : roleIds) {
			user.getRoles().add(new Role(rid));
		}
		
	}

}
