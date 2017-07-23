package bos.service;

import java.util.List;

import bos.domain.Subarea;
import bos.utils.PageBean;

public interface ISubareaService {

	public void save(Subarea model);

	public void pageQuery(PageBean pageBean);

	public Subarea findById(String id);

	public void update(Subarea subarea);

	public List<Subarea> findAll();

	public List<Subarea> findListNotAssociation();

}
