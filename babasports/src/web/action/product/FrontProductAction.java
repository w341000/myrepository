package web.action.product;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Set;

import javax.annotation.Resource;

import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;

import service.product.ProductInfoService;
import service.product.ProductTypeService;
import utils.WebUtil;

import web.action.BaseAction;
import bean.PageBean;
import bean.QueryResult;
import bean.product.ProductInfo;
import bean.product.ProductStyle;
import bean.product.ProductType;
import bean.product.Sex;
@Controller("frontProductAction") @Scope("prototype")
public class FrontProductAction extends BaseAction<PageBean> {
	
	@Resource
	private ProductInfoService productInfoService;
	@Resource
	private ProductTypeService productTypeService;
	private String sort;
	private Integer typeid;
	private String brandid;
	private String sex;
	private String style;
	public String getStyle() {
		return style;
	}
	public void setStyle(String style) {
		this.style = style;
	}
	public String getSex() {
		return sex;
	}

	public void setSex(String sex) {
		this.sex = sex;
	}

	public String getBrandid() {
		return brandid;
	}

	public void setBrandid(String brandid) {
		this.brandid = brandid;
	}

	public Integer getTypeid() {
		return typeid;
	}

	public void setTypeid(Integer typeid) {
		this.typeid = typeid;
	}

	public String getSort() {
		return sort;
	}

	public void setSort(String sort) {
		this.sort = sort;
	}

	@Override
	public String execute() throws Exception {
		//���ݴ��ݵ�������������
		String orderby=buildOrder(sort);
		//ֻ��ʾ������Ʒ
		StringBuffer where=new StringBuffer(" and visible=true");
		List<Integer> typeids=new ArrayList<Integer>();
		if(typeid!=null && typeid>0) {//���ݲ�Ʒ���
			typeids.add(typeid);
			//��ȡ��ָ������µ���������id(�������������)
			getTypeids(typeids,typeid);
			where.append(" and type.id in(");
			for(Integer id:typeids)
				where.append(id+",");
			where.setCharAt(where.length()-1, ')');
			request.put("type", this.productTypeService.get(this.typeid));
		}
		if(brandid!=null && !"".equals(brandid.trim()))//���ݲ�ƷƷ��
			where.append(" and brand.code='"+brandid+"'");
		if(sex!=null && !"".equals(sex.trim()) )//�����Ա�
			where.append(" and sexrequest='"+Sex.valueOf(sex)+"'");
		QueryResult<ProductInfo> qr=productInfoService.findByPage(model.getCurrentpage(),model.getPagesize(),orderby,where.toString());
		//����Ʒ����Ԥ����,��֤ÿһ����Ʒ��ֻ��һ��������ʽ
		for(ProductInfo product:qr.getList()){
			Set<ProductStyle> styles=new HashSet<ProductStyle>();
			for(ProductStyle style:product.getStyles()){
				if(style.isVisible()){
					styles.add(style);
					break;
				}
			}
			product.setStyles(styles);
			//ȡ����Ʒ�����е�����html��ǩ
			product.setDescription(WebUtil.HtmltoText(product.getDescription()));
		}
	
		model.setList(qr.getList());
		model.setTotalrecord(qr.getTotalrecord());
		request.put("pagebean", model);
		
		if(typeid!=null && typeid>0){
			List<ProductType> types=new ArrayList<ProductType>();
			//��ȡ����ǰ�����������Ϣ
			ProductType type=productTypeService.get(typeid);
			//�������������Ϣ��Ž��뼯����
			types.add(type);
			ProductType parent=type.getParent();
			while(parent!=null){
				//���ͻ��и��������������,���Ұ���ջ˳����뼯����,
				types.add(0,parent);
				parent=parent.getParent();
			}
			//types������е���������ʾ�����,����ǰ�����Ϣ,����,ֱ��������Ϊ��Ϊֹ
			request.put("types", types);
			//subtypes��ס��ǰ����������������͵�����(���������������)
			request.put("subtypes", productTypeService.query(" where parent.id=?", this.typeid));
		}else{//���û�ֱ�������ҳʱ,��subtypes������ж�����������
			request.put("subtypes", productTypeService.query(" where parent=null"));
		}
		
		request.put("brands", productInfoService.getBrandsByProductTypeid(typeids.toArray(new Integer[]{})));
		return getView(style);
	}
	/**
	 * ��ȡ��Ҫ��ʾ����ͼ
	 * @param style
	 * @return
	 */
	private String getView(String style){
		if("imagetext".equalsIgnoreCase(style))
			return "list_imagetext";
		else
			return "list_image";
		
	}

	private String  buildOrder(String order){
		StringBuffer orderby=new StringBuffer(" order by ");
		if("selldesc".equals(order))
			orderby.append(" sellcount desc");
		else if("sellpricedesc".equals(order))
			orderby.append(" sellprice desc");
		else if("sellpriceasc".equals(order))
			orderby.append(" sellprice asc");
		else 
			orderby.append(" createdate desc");
		return orderby.toString();
	}
	/**
	 * ����ָ������id,��ȡ����������id(ע:���༰�������id�����ȡ)
	 * @param typeids
	 * @param integers
	 */
	private void getTypeids(List<Integer> typeids,Integer... integers){
		List<Integer> subtypeids=this.productTypeService.getSubTypeid(integers);
		if(subtypeids!=null){
			typeids.addAll(subtypeids);
			getTypeids(typeids,subtypeids.toArray(new Integer[]{}));
		}
	}
}
