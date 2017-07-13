package web.action.product;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import javax.annotation.Resource;

import org.apache.struts2.ServletActionContext;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;

import exception.NoSupportFileException;

import service.product.BrandService;
import service.product.ProductInfoService;
import service.product.ProductTypeService;
import utils.BuildHtmlFile;
import utils.PropertyUtil;
import utils.WebUtil;

import bean.product.ProductInfo;
import bean.product.ProductStyle;
import bean.product.ProductType;
import bean.product.Sex;
import web.action.BaseAction;
@Controller @Scope("prototype")
public class ProductManageAction extends BaseAction<ProductInfo> {
	@Resource
	private ProductInfoService productInfoService;
	@Resource
	private BrandService brandService;
	@Resource
	private ProductTypeService productTypeService;
	private Integer typeid;
	private File imagefile;//得到上传的文件
	private String imagefileContentType;//得到文件的类型
	private String imagefileFileName;//得到文件的名称
	private String stylename;
	private Integer[] productids;
	public Integer[] getProductids() {
		return productids;
	}
	public void setProductids(Integer[] productids) {
		this.productids = productids;
	}
	public File getImagefile() {
		return imagefile;
	}
	public void setImagefile(File imagefile) {
		this.imagefile = imagefile;
	}
	public String getImagefileContentType() {
		return imagefileContentType;
	}
	public void setImagefileContentType(String imagefileContentType) {
		this.imagefileContentType = imagefileContentType;
	}
	public String getImagefileFileName() {
		return imagefileFileName;
	}
	public void setImagefileFileName(String imagefileFileName) {
		this.imagefileFileName = imagefileFileName;
	}
	public String getStylename() {
		return stylename;
	}
	public void setStylename(String stylename) {
		this.stylename = stylename;
	}
	public void setSex(String sex){
		model.setSexrequest(Sex.valueOf(sex));
	}
	public void setTypeid(Integer typeid) {
		this.typeid=typeid;
		ProductType type=new ProductType();
		type.setId(typeid);
		model.setType(type);
	}
	public Integer getTypeid() {
		return typeid;
	}
	/**
	 * 产品添加界面
	 */
	public String addUI(){
		request.put("brands", brandService.query());
		return "addUI";
		
	}
	
	/**
	 * 产品添加
	 */
	public String add(){
		try {//对上传的图片进行校验,文件不为空且类型正确的情况下进行保存
			if(WebUtil.validateImageType(imagefile, imagefileContentType, imagefileFileName)){
				String uuidfilename=WebUtil.generateUUIDFileName(imagefileFileName);
				model.addProductStyle(new ProductStyle(this.stylename,uuidfilename));
				//由于添加品牌时可能不添加品牌,此时传递的是空字符串,在此做判断
				if("".equals(model.getBrand().getCode())|| "".equals(model.getBrand().getCode().trim()))
					model.setBrand(null);
				this.productInfoService.save(model);
				//原始图片的保存路径
				String pathdir="/images/product/"+typeid+"/"+model.getId()+"/prototype";
				//140宽度图片的真实保存路径
				String pathdir140="/images/product/"+typeid+"/"+model.getId()+"/140x";
				//保存原始图片并压缩图片
				WebUtil.saveAndReduceImage(uuidfilename, pathdir, pathdir140, imagefile, 140);
			}//类型错误则输出提示
		} catch (NoSupportFileException e) {
			 request.put("message", "保存失败,上传的文件类型错误");
			 request.put("urladdress", PropertyUtil.readUrl("control.product.list"));
			 return "message";
		}//类型正确,进行保存操作
		//生成静态文件
		File saveDir=new File(ServletActionContext.getRequest().getServletContext().getRealPath("/html/product/"+model.getType().getId()));
		BuildHtmlFile.createProductHtml(model,saveDir);
        request.put("message", "添加成功");
        request.put("urladdress", PropertyUtil.readUrl("control.product.list"));
        return "message";
		
	}
	/**
	 * 类别选择 界面
	 */
	public String selectUI(){
		List<ProductType> types;
		if(typeid!=null && typeid>0){
			types=productTypeService.query(" where visible=true and parent.id=?", typeid);
			//获取到当前点击的类型信息
			ProductType type=productTypeService.get(typeid);
			ProductType parent=type.getParent();
			List<ProductType> menutypes=new ArrayList<ProductType>();
			//将点击的类型信息存放进入集合中
			menutypes.add(type);
			while(parent!=null){
				//类型还有父类型则继续遍历,并且按堆栈顺序放入集合中,
				menutypes.add(0,parent);
				parent=parent.getParent();
			}
			request.put("menutypes", menutypes);
		}else
			types=productTypeService.query(" where visible=true and parent=null ");
		request.put("types", types);
		return "selectUI";
		
	}
	/**
	 * 修改界面
	 */
	public String editUI(){
		model=productInfoService.get(model.getId());
		request.put("brands", brandService.query());
		request.put("product", model);
		return "editUI";
	}	
	/**
	 * 产品修改
	 */
	public String edit(){
		//由于添加品牌时可能品牌为空,此时传递的是空字符串,在此做判断
		ProductInfo product=productInfoService.get(model.getId());
		product.setName(model.getName());
		product.setType(productTypeService.get(model.getType().getId()));
		product.setBaseprice(model.getBaseprice());
		product.setMarketprice(model.getMarketprice());
		product.setSellprice(model.getSellprice());
		product.setCode(model.getCode());
		if("".equals(model.getBrand().getCode())|| "".equals(model.getBrand().getCode().trim()))
			product.setBrand(null);
		else
			product.setBrand(brandService.get(model.getBrand().getCode()));
		product.setSexrequest(model.getSexrequest());
		product.setModel(model.getModel());
		product.setWeight(model.getWeight());
		product.setDescription(model.getDescription());
		product.setBuyexplain(model.getBuyexplain());
		this.productInfoService.update(product);
		//重新生成静态文件
		File saveDir=new File(ServletActionContext.getRequest().getServletContext().getRealPath("/html/product/"+product.getType().getId()));
		BuildHtmlFile.createProductHtml(product,saveDir);
        request.put("message", "修改成功");
        request.put("urladdress", PropertyUtil.readUrl("control.product.list"));
        return "message";
	}	
	
	/**
	 * 产品查询界面
	 */
	public String queryUI(){
		request.put("brands", brandService.query());
		return "queryUI";
	}
	
	
	
	/**
	 * 产品上架
	 */
	public String visible(){
		productInfoService.setVisibleStatus(true, productids);
		request.put("message", "设置成功");
        request.put("urladdress", PropertyUtil.readUrl("control.product.list"));
		return "message";
	}
	
	/**
	 * 产品下架
	 */
	public String disable(){
		productInfoService.setVisibleStatus(false, productids);
		request.put("message", "设置成功");
        request.put("urladdress", PropertyUtil.readUrl("control.product.list"));
		return "message";
	}
	
	
	/**
	 * 产品推荐
	 */
	public String commend(){
		productInfoService.setCommend(true, productids);
		request.put("message", "设置成功");
        request.put("urladdress", PropertyUtil.readUrl("control.product.list"));
		return "message";
	}
	
	/**
	 * 产品不推荐
	 */
	public String uncommend(){
		productInfoService.setCommend(false, productids);
		request.put("message", "设置成功");
        request.put("urladdress", PropertyUtil.readUrl("control.product.list"));
		return "message";
	}
}
