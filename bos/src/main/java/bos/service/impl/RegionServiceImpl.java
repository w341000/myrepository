package bos.service.impl;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import bos.dao.IRegionDao;
import bos.domain.Region;
import bos.service.IRegionService;
@Service @Transactional
public class RegionServiceImpl implements IRegionService {
	@Resource
	private IRegionDao regionDao;

	@Override
	public void saveBatch(List<Region> list) {
		for(Region region:list){
			regionDao.saveOrUpdate(region);
		}
	}

}
