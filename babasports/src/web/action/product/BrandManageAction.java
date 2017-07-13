package web.action.product;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.lang.reflect.Field;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.UUID;

import javax.annotation.Resource;

import org.apache.struts2.ServletActionContext;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;

import exception.NoSupportFileException;

import service.product.BrandService;
import utils.PropertyUtil;
import utils.WebUtil;
import bean.product.Brand;
import web.action.BaseAction;
@Controller @Scope("prototype")
public class BrandManageAction extends BaseAction<Brand> {
	
	@Resource
	private BrandService brandService;
	private File logofile;//�õ��ϴ����ļ�
	private String logofileContentType;//�õ��ļ�������
	private String logofileFileName;//�õ��ļ�������

	public File getLogofile() {
		return logofile;
	}

	public void setLogofile(File logofile) {
		this.logofile = logofile;
	}

	public String getLogofileContentType() {
		return logofileContentType;
	}

	public void setLogofileContentType(String logofileContentType) {
		this.logofileContentType = logofileContentType;
	}

	public String getLogofileFileName() {
		return logofileFileName;
	}

	public void setLogofileFileName(String logofileFileName) {
		this.logofileFileName = logofileFileName;
	}
	/**
	 * Ʒ����ӽ���
	 *
	 */
	public String addUI() throws Exception {
		return "addUI";
	}
	
	/**
	 * Ʒ�����
	 */
	public String add() {
		try {//���ϴ���ͼƬ����У��,�ļ���Ϊ����������ȷ������½��б���
			if(WebUtil.validateImageType(logofile, logofileContentType, logofileFileName)){
				String path=WebUtil.saveFile(this.logofileFileName, "/images/brand/", this.logofile);
				model.setLogopath(path);
			}//���ʹ����������ʾ
		} catch (NoSupportFileException e) {
			System.out.println(logofileContentType);
			 request.put("message", "����ʧ��,�ϴ����ļ����ʹ���");
			 request.put("urladdress", PropertyUtil.readUrl("control.brand.list"));
			 return "message";
		}//������ȷ,���б������
        brandService.save(model);
        request.put("message", "��ӳɹ�");
        request.put("urladdress", PropertyUtil.readUrl("control.brand.list"));
        return "message";
	}
	/**
	 * Ʒ�Ʋ�ѯ����
	 */
	public String queryUI() throws Exception {
		return "queryUI";
	}
	
	/**
	 * Ʒ���޸Ľ���
	 */
	public String editUI() throws Exception {
		
		Brand brand=brandService.get(model.getCode());
		request.put("brand", brand);
		return "editUI";
	}
	
	/**
	 * Ʒ���޸�
	 */
	public String edit()  {
		model=brandService.get(model.getCode());
		try {
			if(WebUtil.validateImageType(logofile, logofileContentType, logofileFileName)){
				String path=WebUtil.saveFile(this.logofileFileName, "/images/brand/", this.logofile);
				model.setLogopath(path);
			}
		} catch (NoSupportFileException e) {
			System.out.println(logofileContentType);
			 request.put("message", "�޸�ʧ��,�ϴ����ļ����ʹ���");
			 request.put("urladdress", PropertyUtil.readUrl("control.brand.list"));
			 return "message";
		}
		brandService.update(model);
		request.put("message", "Ʒ���޸ĳɹ�");
		request.put("urladdress", PropertyUtil.readUrl("control.brand.list"));
		return "message";
	}
}
