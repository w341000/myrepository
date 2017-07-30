package bos.dao.impl;

import java.util.List;

import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import bos.dao.IFunctionDao;
import bos.dao.base.impl.BaseDaoImpl;
import bos.domain.Function;
@Repository
public class FunctionDaoImpl extends BaseDaoImpl<Function> implements
		IFunctionDao {

	//根据用户id查询对应权限
	@Override
	public List<Function> findListByUserid(String id) {
		String hql="SELECT DISTINCT f FROM Function f LEFT OUTER JOIN f.roles r LEFT OUTER JOIN r.users u WHERE u.id=? ";
		return super.find(hql, id);
	}

	@Override
	public List<Function> findAllMenu() {
		String hql="SELECT  f FROM Function f  WHERE f.generatemenu='1' ORDER BY f.zindex asc";
		return super.find(hql);
	}

	@Override
	public List<Function> findMenuByUserid(String id) {
		String hql="SELECT DISTINCT f FROM Function f LEFT OUTER JOIN f.roles r LEFT OUTER JOIN r.users u WHERE u.id=? AND f.generatemenu='1' ORDER BY f.zindex desc";
		return super.find(hql, id);
	}

	@Override @Transactional(readOnly=true)
	public List<Function> findAllPage() {
		String hql="SELECT  f FROM Function f  WHERE f.page IS NOT NULL ";
		return super.find(hql);
	}

	

}
