package bos.dao.impl;

import java.util.List;

import org.springframework.stereotype.Repository;

import bos.dao.IUserDao;
import bos.dao.base.impl.BaseDaoImpl;
import bos.domain.User;
@Repository
public class UserDaoImpl extends BaseDaoImpl<User> implements IUserDao {

	@Override
	public User findByUsernameAndPassword(String username, String password) {
		String hql="FROM User u where u.username=? and u.password=?";
		List<User> list=super.find(hql, username,password);
		if(list!=null && list.size()>0){
			return list.get(0);
		}
		return null;
	}

}
