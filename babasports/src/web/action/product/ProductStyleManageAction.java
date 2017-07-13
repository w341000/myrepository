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
	private File imagefile;//�õ��ϴ����ļ�
	private String imagefileContentType;//�õ��ļ�������
	private String imagefileFileName;//�õ��ļ�������
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
	 * ��Ӳ�Ʒ��ʽ����
	 * @return
	 */
	public String addUI(){
		
		return "addUI";
	}
	/**
	 * ��Ӳ�Ʒ��ʽ
	 * @return
	 */
	public String add(){
		try {//���ϴ���ͼƬ����У��,�ļ���Ϊ����������ȷ������½��б���
			if(WebUtil.validateImageType(imagefile, imagefileContentType, imagefileFileName)){
				//����uuid�ļ���
				String uuidfilename=WebUtil.generateUUIDFileName(imagefileFileName);
				//��ò�Ʒ
				ProductInfo product=this.productInfoService.get(productid);
				//ԭʼͼƬ�ı���·��
				String pathdir="/images/product/"+product.getType().getId()+"/"+product.getId()+"/prototype";
				//140���ͼƬ����ʵ����·��
				String pathdir140="/images/product/"+product.getType().getId()+"/"+product.getId()+"/140x";
				//���������ļ���
				model.setImagename(uuidfilename);
				this.productStyleService.save(model);
				//����ԭʼͼƬ��ѹ��ͼƬ
				WebUtil.saveAndReduceImage(uuidfilename, pathdir, pathdir140, imagefile, 140);
			}//���ʹ����������ʾ
		} catch (NoSupportFileException e) {
			 request.put("message", "����ʧ��,�ϴ����ļ����ʹ���");
			 request.put("urladdress", PropertyUtil.readUrl("control.product.style.list")+"?productid="+productid);
			 return "message";
		}//������ȷ,���б������
        request.put("message", "��ӳɹ�");
        request.put("urladdress", PropertyUtil.readUrl("control.product.style.list")+"?productid="+productid);
        return "message";
	}
	
	/**
	 * �޸Ĳ�Ʒ��ʽ����
	 * @return
	 */
	public String editUI(){
		model=this.productStyleService.get(model.getId());
		request.put("style", model);
		return "editUI";
	}
	
	
	/**
	 * �޸Ĳ�Ʒ��ʽ
	 * @return
	 */
	public String edit(){
		ProductStyle style=this.productStyleService.get(model.getId());
		ProductInfo product=style.getProduct();
		try {//���ϴ���ͼƬ����У��,�ļ���Ϊ����������ȷ������½��б���
			if(WebUtil.validateImageType(imagefile, imagefileContentType, imagefileFileName)){
				//����uuid�ļ���
				String uuidfilename=WebUtil.generateUUIDFileName(imagefileFileName);
				//ԭʼͼƬ����Ա���·��
				String pathdir="/images/product/"+product.getType().getId()+"/"+product.getId()+"/prototype";
				//140���ͼƬ����ʵ����·��
				String pathdir140="/images/product/"+product.getType().getId()+"/"+product.getId()+"/140x";
				//���������ļ���
				style.setImagename(uuidfilename);
				//����ԭʼͼƬ��ѹ��ͼƬ
				WebUtil.saveAndReduceImage(uuidfilename, pathdir, pathdir140, imagefile, 140);
			}//���ʹ����������ʾ
		} catch (NoSupportFileException e) {
			 request.put("message", "�޸�ʧ��,�ϴ����ļ����ʹ���");
			 request.put("urladdress", PropertyUtil.readUrl("control.product.style.list")+"?productid="+product.getId());
			 return "message";
		}
		//������ȷ,���б������
		style.setName(model.getName());
		this.productStyleService.update(style);
        request.put("message", "�޸ĳɹ�");
        request.put("urladdress", PropertyUtil.readUrl("control.product.style.list")+"?productid="+product.getId());
        return "message";
	}
	
	
	
	/**
	 * �ϼܲ�Ʒ��ʽ
	 * @return
	 */
	public String visible(){
		productStyleService.setVisibleStatus(true, this.stylesids);
		request.put("message", "�޸ĳɹ�");
        request.put("urladdress", PropertyUtil.readUrl("control.product.style.list")+"?productid="+productid);
		return "message";
	}
	
	/**
	 * �ϼܲ�Ʒ��ʽ
	 * @return
	 */
	public String disable(){
		productStyleService.setVisibleStatus(false, this.stylesids);
		request.put("message", "�޸ĳɹ�");
        request.put("urladdress", PropertyUtil.readUrl("control.product.style.list")+"?productid="+productid);
		return "message";
	}
}
