package bos.service;

import bos.domain.User;
import bos.utils.PageBean;

public interface IUserService {

	public User login(User user);

	public void editPassword(String password, String id);

	public void pageQuery(PageBean pageBean);

}
