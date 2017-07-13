package web.action.product;

import javax.annotation.Resource;

import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;

import bean.product.ProductType;

import service.product.ProductTypeService;
import utils.PropertyUtil;

import web.action.BaseAction;
@Controller @Scope("prototype")
public class ProductTypeManageAction extends BaseAction<ProductType> {
	@Resource
	private ProductTypeService productTypeService;


	/**
	 * 类别添加界面
	 *
	 */
	public String addUI() throws Exception {
		return "addUI";
	}
	/**
	 * 类别添加
	 */
	public String add()  {
		ProductType parent=model.getParent();
		if(parent==null || parent.getId()==null)
			model.setParent(null);
		productTypeService.save(model);
		request.put("message", "添加成功");
		request.put("urladdress", PropertyUtil.readUrl("control.product.type.list"));
		return "message";
	}
	/**
	 * 类别修改界面
	 */
	public String editUI() throws Exception {
		model=productTypeService.get(model.getId());
		request.put("producttype", model);
		return "editUI";
	}
	
	/**
	 * 类别修改
	 */
	public String edit() throws Exception {
		ProductType type=productTypeService.get(model.getId());
		type.setName(model.getName());
		type.setNote(model.getNote());
		productTypeService.update(type);
		request.put("message", "修改类别成功!");
		request.put("urladdress", PropertyUtil.readUrl("control.product.type.list"));
		return "message";
	}
	
	/**
	 * 查询界面
	 */
	public String queryUI() throws Exception {
		return "queryUI";
	}
	
}
