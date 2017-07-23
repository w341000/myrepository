package bos.service;

import bos.domain.Decidedzone;
import bos.utils.PageBean;

public interface IDecidedzoneService {

	/**
	 * 添加定区
	 * @param model
	 * @param subareaid
	 */
	public void save(Decidedzone model, String[] subareaid);

	public void pageQuery(PageBean pageBean);

	public Decidedzone findById(String id);

	public void update(Decidedzone decidedzone, String[] subareaid);

}
