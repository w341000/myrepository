package bos.dao;

import java.util.List;

import bos.dao.base.IBaseDao;
import bos.domain.Region;

public interface IRegionDao extends IBaseDao<Region> {

	public List<Region> findByQ(String q);

	

}
