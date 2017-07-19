package bos.service;

import bos.domain.User;

public interface IUserService {

	public User login(User user);

	public void editPassword(String password, String id);

}
