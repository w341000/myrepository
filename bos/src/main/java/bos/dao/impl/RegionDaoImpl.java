package bos.dao.impl;

import java.io.Serializable;
import java.util.List;

import org.springframework.stereotype.Repository;

import bos.dao.IRegionDao;
import bos.dao.base.impl.BaseDaoImpl;
import bos.domain.Region;
import bos.utils.PageBean;
@Repository
public class RegionDaoImpl extends BaseDaoImpl<Region> implements IRegionDao {

	@Override
	public List<Region> findByQ(String q) {
		String hql="FROM Region WHERE province LIKE ? OR city LIKE ? OR district LIKE ?";
		return this.find(hql, "%"+q+"%", "%"+q+"%", "%"+q+"%");
	}

	
}
