package bos.service.impl;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import bos.dao.IFunctionDao;
import bos.domain.Function;
import bos.service.IFunctionService;
import bos.utils.PageBean;
@Service @Transactional
public class FunctionServiceImpl implements IFunctionService {
	@Resource
	private IFunctionDao functionDao;

	@Override
	public void pageQuery(PageBean pageBean) {
		functionDao.pageQuery(pageBean);
	}

	@Override
	public List<Function> findAll() {
		return functionDao.findAll();
	}

	@Override
	public void save(Function model) {
		Function function = model.getFunction();
		//对父权限为空时进行处理
		if(function!=null && "".equals(function.getId())){
			model.setFunction(null);
		}
		functionDao.save(model);
		
	}
}
