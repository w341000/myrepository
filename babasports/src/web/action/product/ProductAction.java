package web.action.product;

import javax.annotation.Resource;

import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;

import service.product.ProductInfoService;

import bean.PageBean;
import bean.QueryResult;
import bean.product.ProductInfo;
import web.action.BaseAction;
@Controller("productAction") @Scope("prototype")
public class ProductAction extends BaseAction<PageBean> {
	@Resource
	private ProductInfoService productInfoService;
	private String query;
	private String name;
	private Integer typeid;
	private Float startbaseprice;
	private Float endbaseprice;
	private Float startsellprice;
	private Float endsellprice;
	private String code;
	private String brandcode;
	public void setQuery(String query) {
		this.query = query;
	}
	
	public void setProductInfoService(ProductInfoService productInfoService) {
		this.productInfoService = productInfoService;
	}
	public void setName(String name) {
		this.name = name;
	}
	public void setTypeid(Integer typeid) {
		this.typeid = typeid;
	}
	public void setStartbaseprice(Float startbaseprice) {
		this.startbaseprice = startbaseprice;
	}
	public void setEndbaseprice(Float endbaseprice) {
		this.endbaseprice = endbaseprice;
	}
	public void setStartsellprice(Float startsellprice) {
		this.startsellprice = startsellprice;
	}
	public void setEndsellprice(Float endsellprice) {
		this.endsellprice = endsellprice;
	}
	public void setCode(String code) {
		this.code = code;
	}
	public void setBrandcode(String brandcode) {
		this.brandcode = brandcode;
	}
	@Override
	public String execute() throws Exception {
		String orderby=" order by visible desc, id desc";
		StringBuffer where=new StringBuffer();
		//����Ӳ�ѯҳ�����,��ƴ��hql��where���
		if("true".equals(query)){
			if(name!=null && !"".equals(name.trim()))//�����Ʋ�ѯ
				where.append(" and name like '%"+name+"%'");
			if(typeid!=null && typeid>0)//�����id��ѯ
				where.append(" and type.id="+typeid);
			if(startbaseprice!=null && startbaseprice>0)//���׼������ѯ
				where.append(" and baseprice>="+startbaseprice);
			if(endbaseprice!=null && endbaseprice>0)
				where.append(" and baseprice<="+endbaseprice);
			if(startsellprice!=null && startsellprice>0)//�����������ѯ
				where.append(" and sellprice>="+startsellprice);
			if(endsellprice!=null && endsellprice>0)
				where.append(" and sellprice<="+endsellprice);
			if(code!=null && !"".equals(code.trim()))//�����Ų�ѯ
				where.append(" and code="+"'"+code+"'");
			if(brandcode!=null && !"".equals(brandcode.trim()))//��Ʒ�Ʋ�ѯ
				where.append(" and brand.code="+"'"+brandcode+"'");
		}
		QueryResult<ProductInfo> qr=productInfoService.findByPage(model.getCurrentpage(),model.getPagesize(),orderby,where.toString());
		model.setList(qr.getList());
		model.setTotalrecord(qr.getTotalrecord());
		request.put("pagebean", model);
		return SUCCESS;
	}
	
	
}
