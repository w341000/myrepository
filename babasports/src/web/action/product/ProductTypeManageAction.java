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
	 * �����ӽ���
	 *
	 */
	public String addUI() throws Exception {
		return "addUI";
	}
	/**
	 * ������
	 */
	public String add()  {
		ProductType parent=model.getParent();
		if(parent==null || parent.getId()==null)
			model.setParent(null);
		productTypeService.save(model);
		request.put("message", "��ӳɹ�");
		request.put("urladdress", PropertyUtil.readUrl("control.product.type.list"));
		return "message";
	}
	/**
	 * ����޸Ľ���
	 */
	public String editUI() throws Exception {
		model=productTypeService.get(model.getId());
		request.put("producttype", model);
		return "editUI";
	}
	
	/**
	 * ����޸�
	 */
	public String edit() throws Exception {
		ProductType type=productTypeService.get(model.getId());
		type.setName(model.getName());
		type.setNote(model.getNote());
		productTypeService.update(type);
		request.put("message", "�޸����ɹ�!");
		request.put("urladdress", PropertyUtil.readUrl("control.product.type.list"));
		return "message";
	}
	
	/**
	 * ��ѯ����
	 */
	public String queryUI() throws Exception {
		return "queryUI";
	}
	
}
