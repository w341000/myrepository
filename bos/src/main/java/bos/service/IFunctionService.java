package bos.service;

import java.util.List;

import bos.domain.Function;
import bos.utils.PageBean;

public interface IFunctionService {

	public void pageQuery(PageBean pageBean);

	public List<Function> findAll();

	public void save(Function model);

	public List<Function> findMenu();

}
