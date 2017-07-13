package web.action.product;

import java.io.File;
import java.io.IOException;

import javax.annotation.Resource;

import org.apache.struts2.ServletActionContext;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;

import exception.NoSupportFileException;

import service.product.ProductInfoService;
import service.product.ProductStyleService;
import utils.ImageSizer;
import utils.PropertyUtil;
import utils.WebUtil;
import bean.product.ProductInfo;
import bean.product.ProductStyle;
import web.action.BaseAction;
@Controller @Scope("prototype")
public class ProductStyleManageAction extends BaseAction<ProductStyle> {
	@Resource
	private ProductStyleService productStyleService;
	@Resource
	private ProductInfoService productInfoService;
	private Integer productid;
	private File imagefile;//得到上传的文件
	private String imagefileContentType;//得到文件的类型
	private String imagefileFileName;//得到文件的名称
	private Integer[] stylesids;
	public Integer[] getStylesids() {
		return stylesids;
	}
	public void setStylesids(Integer[] stylesids) {
		this.stylesids = stylesids;
	}
	public Integer getProductid() {
		return productid;
	}
	public void setProductid(Integer productid) {
		this.productid = productid;
		ProductInfo product=new ProductInfo();
		product.setId(productid);
		model.setProduct(product);
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
	/**
	 * 添加产品样式界面
	 * @return
	 */
	public String addUI(){
		
		return "addUI";
	}
	/**
	 * 添加产品样式
	 * @return
	 */
	public String add(){
		try {//对上传的图片进行校验,文件不为空且类型正确的情况下进行保存
			if(WebUtil.validateImageType(imagefile, imagefileContentType, imagefileFileName)){
				//生成uuid文件名
				String uuidfilename=WebUtil.generateUUIDFileName(imagefileFileName);
				//获得产品
				ProductInfo product=this.productInfoService.get(productid);
				//原始图片的保存路径
				String pathdir="/images/product/"+product.getType().getId()+"/"+product.getId()+"/prototype";
				//140宽度图片的真实保存路径
				String pathdir140="/images/product/"+product.getType().getId()+"/"+product.getId()+"/140x";
				//重新设置文件名
				model.setImagename(uuidfilename);
				this.productStyleService.save(model);
				//保存原始图片并压缩图片
				WebUtil.saveAndReduceImage(uuidfilename, pathdir, pathdir140, imagefile, 140);
			}//类型错误则输出提示
		} catch (NoSupportFileException e) {
			 request.put("message", "保存失败,上传的文件类型错误");
			 request.put("urladdress", PropertyUtil.readUrl("control.product.style.list")+"?productid="+productid);
			 return "message";
		}//类型正确,进行保存操作
        request.put("message", "添加成功");
        request.put("urladdress", PropertyUtil.readUrl("control.product.style.list")+"?productid="+productid);
        return "message";
	}
	
	/**
	 * 修改产品样式界面
	 * @return
	 */
	public String editUI(){
		model=this.productStyleService.get(model.getId());
		request.put("style", model);
		return "editUI";
	}
	
	
	/**
	 * 修改产品样式
	 * @return
	 */
	public String edit(){
		ProductStyle style=this.productStyleService.get(model.getId());
		ProductInfo product=style.getProduct();
		try {//对上传的图片进行校验,文件不为空且类型正确的情况下进行保存
			if(WebUtil.validateImageType(imagefile, imagefileContentType, imagefileFileName)){
				//生成uuid文件名
				String uuidfilename=WebUtil.generateUUIDFileName(imagefileFileName);
				//原始图片的相对保存路径
				String pathdir="/images/product/"+product.getType().getId()+"/"+product.getId()+"/prototype";
				//140宽度图片的真实保存路径
				String pathdir140="/images/product/"+product.getType().getId()+"/"+product.getId()+"/140x";
				//重新设置文件名
				style.setImagename(uuidfilename);
				//保存原始图片并压缩图片
				WebUtil.saveAndReduceImage(uuidfilename, pathdir, pathdir140, imagefile, 140);
			}//类型错误则输出提示
		} catch (NoSupportFileException e) {
			 request.put("message", "修改失败,上传的文件类型错误");
			 request.put("urladdress", PropertyUtil.readUrl("control.product.style.list")+"?productid="+product.getId());
			 return "message";
		}
		//类型正确,进行保存操作
		style.setName(model.getName());
		this.productStyleService.update(style);
        request.put("message", "修改成功");
        request.put("urladdress", PropertyUtil.readUrl("control.product.style.list")+"?productid="+product.getId());
        return "message";
	}
	
	
	
	/**
	 * 上架产品样式
	 * @return
	 */
	public String visible(){
		productStyleService.setVisibleStatus(true, this.stylesids);
		request.put("message", "修改成功");
        request.put("urladdress", PropertyUtil.readUrl("control.product.style.list")+"?productid="+productid);
		return "message";
	}
	
	/**
	 * 上架产品样式
	 * @return
	 */
	public String disable(){
		productStyleService.setVisibleStatus(false, this.stylesids);
		request.put("message", "修改成功");
        request.put("urladdress", PropertyUtil.readUrl("control.product.style.list")+"?productid="+productid);
		return "message";
	}
}
