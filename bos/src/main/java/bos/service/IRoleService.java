package bos.service;

import java.util.List;

import bos.domain.Role;
import bos.utils.PageBean;

public interface IRoleService {

	public void save(Role role, String ids);

	public void pageQuery(PageBean pageBean);

	public List<Role> findAll();

}
