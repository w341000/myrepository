package web.action.product;

import javax.annotation.Resource;

import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;

import service.product.BrandService;

import bean.PageBean;
import bean.QueryResult;
import bean.product.Brand;
import bean.product.ProductType;

import web.action.BaseAction;
@Controller @Scope("prototype")
public class BrandAction extends BaseAction<PageBean> {
	@Resource
	private BrandService brandService;
	private String query;
	private String name;

	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}

	public String getQuery() {
		return query;
	}

	public void setQuery(String query) {
		this.query = query;
	}

	@Override
	/****
	 * ��Ʒ����ϸ�г���action
	 */
	public String execute() throws Exception {
		String orderBy=" order by code desc";
		StringBuffer where=new StringBuffer();
		//�Ӳ�ѯҳ�����,��ֻģ����ѯ
		if("true".equals(query)){
			//������Ϊ�ջ���ַ���,�������м���ģ����ѯ
			if(name!=null && !"".equals(name.trim()))
				where.append(" and name like '%"+name+"%'");
		}
		QueryResult<Brand> qr=brandService.findByPage(model.getCurrentpage(), model.getPagesize(), orderBy, where.toString());
		model.setList(qr.getList());
		model.setTotalrecord(qr.getTotalrecord());
		request.put("pagebean", model);
		return SUCCESS;
	}
	
	
}
