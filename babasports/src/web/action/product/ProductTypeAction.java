package web.action.product;


import javax.annotation.Resource;

import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;

import service.product.ProductTypeService;

import web.action.BaseAction;

import bean.PageBean;
import bean.QueryResult;
import bean.product.ProductType;
@Controller @Scope("prototype")
public class ProductTypeAction extends BaseAction<PageBean> {
	@Resource
	private ProductTypeService productTypeService;
	private Integer parentid;
	private String name;
	private String query;
	
	public String getQuery() {
		return query;
	}

	public void setQuery(String query) {
		this.query = query;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public int getParentid() {
		return parentid;
	}

	public void setParentid(Integer parentid) {
		this.parentid = parentid;
	}


	@Override
	public String execute() throws Exception {
		String orderby=" order by id desc";
		StringBuffer where=new StringBuffer();
		//从查询页面而来,则只模糊查询
		if("true".equals(query)){
			//姓名不为空或空字符串,则条件中加入模糊查询
			if(name!=null && !"".equals(name.trim()))
				where.append(" and name like '%"+name+"%'");
		}else{
			//有父类的数据
			if(parentid!=null && parentid>0)
				where.append(" and parent.id ="+parentid);
			else //没有父类的数据,即顶级类型
				where.append(" and parent =null ");
		}
		QueryResult<ProductType> qr=productTypeService.findByPage(model.getCurrentpage(),model.getPagesize(),orderby,where.toString());
		model.setList(qr.getList());
		model.setTotalrecord(qr.getTotalrecord());
		request.put("pagebean", model);
		return SUCCESS;
	}
}
