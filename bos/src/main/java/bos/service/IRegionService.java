package bos.service;

import java.util.List;

import bos.domain.Region;
import bos.utils.PageBean;

public interface IRegionService {

	public void saveBatch(List<Region> list);
	public void pageQuery(PageBean pageBean);
	public List<Region> findAll();
	public List<Region> findByQ(String q);
}
