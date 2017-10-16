package bos.service;

import java.util.List;

import bos.domain.Noticebill;
import bos.domain.User;
import bos.utils.PageBean;

public interface INoticebillService {

	public void save(Noticebill model);
	/**
	 * 查询出未关联的通知单
	 * @param pageBean
	 * @return
	 */
	public void findnoassociations(PageBean pageBean);

}
