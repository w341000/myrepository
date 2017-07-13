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
		//根据传递的条件进行排序
		String orderby=buildOrder(sort);
		//只显示在售商品
		StringBuffer where=new StringBuffer(" and visible=true");
		List<Integer> typeids=new ArrayList<Integer>();
		if(typeid!=null && typeid>0) {//根据产品类别
			typeids.add(typeid);
			//获取到指定类别下的所有子类id(包括子类的子类)
			getTypeids(typeids,typeid);
			where.append(" and type.id in(");
			for(Integer id:typeids)
				where.append(id+",");
			where.setCharAt(where.length()-1, ')');
			request.put("type", this.productTypeService.get(this.typeid));
		}
		if(brandid!=null && !"".equals(brandid.trim()))//根据产品品牌
			where.append(" and brand.code='"+brandid+"'");
		if(sex!=null && !"".equals(sex.trim()) )//根据性别
			where.append(" and sexrequest='"+Sex.valueOf(sex)+"'");
		QueryResult<ProductInfo> qr=productInfoService.findByPage(model.getCurrentpage(),model.getPagesize(),orderby,where.toString());
		//对商品进行预处理,保证每一个商品中只有一个在售样式
		for(ProductInfo product:qr.getList()){
			Set<ProductStyle> styles=new HashSet<ProductStyle>();
			for(ProductStyle style:product.getStyles()){
				if(style.isVisible()){
					styles.add(style);
					break;
				}
			}
			product.setStyles(styles);
			//取出产品描述中的所有html标签
			product.setDescription(WebUtil.HtmltoText(product.getDescription()));
		}
	
		model.setList(qr.getList());
		model.setTotalrecord(qr.getTotalrecord());
		request.put("pagebean", model);
		
		if(typeid!=null && typeid>0){
			List<ProductType> types=new ArrayList<ProductType>();
			//获取到当前点击的类型信息
			ProductType type=productTypeService.get(typeid);
			//将点击的类型信息存放进入集合中
			types.add(type);
			ProductType parent=type.getParent();
			while(parent!=null){
				//类型还有父类型则继续遍历,并且按堆栈顺序放入集合中,
				types.add(0,parent);
				parent=parent.getParent();
			}
			//types存放所有导航栏中显示的类别,即当前类别信息,父类,直到父类型为空为止
			request.put("types", types);
			//subtypes记住当前类别下所有子类类型的数据(不包括子类的子类)
			request.put("subtypes", productTypeService.query(" where parent.id=?", this.typeid));
		}else{//当用户直接浏览首页时,则subtypes存放所有顶级类别的数据
			request.put("subtypes", productTypeService.query(" where parent=null"));
		}
		
		request.put("brands", productInfoService.getBrandsByProductTypeid(typeids.toArray(new Integer[]{})));
		return getView(style);
	}
	/**
	 * 获取需要显示的视图
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
	 * 根据指定类型id,获取所有子类别的id(注:子类及其子类的id都会获取)
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
