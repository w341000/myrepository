package junit.test;

import javax.annotation.Resource;

import org.apache.struts2.ServletActionContext;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;

import service.product.ProductInfoService;
import service.product.ProductTypeService;

import bean.PageBean;
import bean.QueryResult;
import bean.product.ProductInfo;
import web.action.BaseAction;
@Controller("testAction") @Scope("prototype")
public class TestAction extends BaseAction<PageBean> {
	
	@Resource
	private ProductInfoService productInfoService;
	@Resource
	private ProductTypeService productTypeService;

	@Override
	public String execute() throws Exception {
		ServletActionContext.getRequest().getParameterNames();
		model.setPagesize(5);
		model.setTotalrecord(50);
		request.put("pagebean", model);
		return "success";
	}
	

}
