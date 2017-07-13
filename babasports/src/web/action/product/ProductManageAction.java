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
	private File imagefile;//�õ��ϴ����ļ�
	private String imagefileContentType;//�õ��ļ�������
	private String imagefileFileName;//�õ��ļ�������
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
	 * ��Ʒ��ӽ���
	 */
	public String addUI(){
		request.put("brands", brandService.query());
		return "addUI";
		
	}
	
	/**
	 * ��Ʒ���
	 */
	public String add(){
		try {//���ϴ���ͼƬ����У��,�ļ���Ϊ����������ȷ������½��б���
			if(WebUtil.validateImageType(imagefile, imagefileContentType, imagefileFileName)){
				String uuidfilename=WebUtil.generateUUIDFileName(imagefileFileName);
				model.addProductStyle(new ProductStyle(this.stylename,uuidfilename));
				//�������Ʒ��ʱ���ܲ����Ʒ��,��ʱ���ݵ��ǿ��ַ���,�ڴ����ж�
				if("".equals(model.getBrand().getCode())|| "".equals(model.getBrand().getCode().trim()))
					model.setBrand(null);
				this.productInfoService.save(model);
				//ԭʼͼƬ�ı���·��
				String pathdir="/images/product/"+typeid+"/"+model.getId()+"/prototype";
				//140���ͼƬ����ʵ����·��
				String pathdir140="/images/product/"+typeid+"/"+model.getId()+"/140x";
				//����ԭʼͼƬ��ѹ��ͼƬ
				WebUtil.saveAndReduceImage(uuidfilename, pathdir, pathdir140, imagefile, 140);
			}//���ʹ����������ʾ
		} catch (NoSupportFileException e) {
			 request.put("message", "����ʧ��,�ϴ����ļ����ʹ���");
			 request.put("urladdress", PropertyUtil.readUrl("control.product.list"));
			 return "message";
		}//������ȷ,���б������
		//���ɾ�̬�ļ�
		File saveDir=new File(ServletActionContext.getRequest().getServletContext().getRealPath("/html/product/"+model.getType().getId()));
		BuildHtmlFile.createProductHtml(model,saveDir);
        request.put("message", "��ӳɹ�");
        request.put("urladdress", PropertyUtil.readUrl("control.product.list"));
        return "message";
		
	}
	/**
	 * ���ѡ�� ����
	 */
	public String selectUI(){
		List<ProductType> types;
		if(typeid!=null && typeid>0){
			types=productTypeService.query(" where visible=true and parent.id=?", typeid);
			//��ȡ����ǰ�����������Ϣ
			ProductType type=productTypeService.get(typeid);
			ProductType parent=type.getParent();
			List<ProductType> menutypes=new ArrayList<ProductType>();
			//�������������Ϣ��Ž��뼯����
			menutypes.add(type);
			while(parent!=null){
				//���ͻ��и��������������,���Ұ���ջ˳����뼯����,
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
	 * �޸Ľ���
	 */
	public String editUI(){
		model=productInfoService.get(model.getId());
		request.put("brands", brandService.query());
		request.put("product", model);
		return "editUI";
	}	
	/**
	 * ��Ʒ�޸�
	 */
	public String edit(){
		//�������Ʒ��ʱ����Ʒ��Ϊ��,��ʱ���ݵ��ǿ��ַ���,�ڴ����ж�
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
		//�������ɾ�̬�ļ�
		File saveDir=new File(ServletActionContext.getRequest().getServletContext().getRealPath("/html/product/"+product.getType().getId()));
		BuildHtmlFile.createProductHtml(product,saveDir);
        request.put("message", "�޸ĳɹ�");
        request.put("urladdress", PropertyUtil.readUrl("control.product.list"));
        return "message";
	}	
	
	/**
	 * ��Ʒ��ѯ����
	 */
	public String queryUI(){
		request.put("brands", brandService.query());
		return "queryUI";
	}
	
	
	
	/**
	 * ��Ʒ�ϼ�
	 */
	public String visible(){
		productInfoService.setVisibleStatus(true, productids);
		request.put("message", "���óɹ�");
        request.put("urladdress", PropertyUtil.readUrl("control.product.list"));
		return "message";
	}
	
	/**
	 * ��Ʒ�¼�
	 */
	public String disable(){
		productInfoService.setVisibleStatus(false, productids);
		request.put("message", "���óɹ�");
        request.put("urladdress", PropertyUtil.readUrl("control.product.list"));
		return "message";
	}
	
	
	/**
	 * ��Ʒ�Ƽ�
	 */
	public String commend(){
		productInfoService.setCommend(true, productids);
		request.put("message", "���óɹ�");
        request.put("urladdress", PropertyUtil.readUrl("control.product.list"));
		return "message";
	}
	
	/**
	 * ��Ʒ���Ƽ�
	 */
	public String uncommend(){
		productInfoService.setCommend(false, productids);
		request.put("message", "���óɹ�");
        request.put("urladdress", PropertyUtil.readUrl("control.product.list"));
		return "message";
	}
}
