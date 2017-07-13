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
	private File logofile;//得到上传的文件
	private String logofileContentType;//得到文件的类型
	private String logofileFileName;//得到文件的名称

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
	 * 品牌添加界面
	 *
	 */
	public String addUI() throws Exception {
		return "addUI";
	}
	
	/**
	 * 品牌添加
	 */
	public String add() {
		try {//对上传的图片进行校验,文件不为空且类型正确的情况下进行保存
			if(WebUtil.validateImageType(logofile, logofileContentType, logofileFileName)){
				String path=WebUtil.saveFile(this.logofileFileName, "/images/brand/", this.logofile);
				model.setLogopath(path);
			}//类型错误则输出提示
		} catch (NoSupportFileException e) {
			System.out.println(logofileContentType);
			 request.put("message", "保存失败,上传的文件类型错误");
			 request.put("urladdress", PropertyUtil.readUrl("control.brand.list"));
			 return "message";
		}//类型正确,进行保存操作
        brandService.save(model);
        request.put("message", "添加成功");
        request.put("urladdress", PropertyUtil.readUrl("control.brand.list"));
        return "message";
	}
	/**
	 * 品牌查询界面
	 */
	public String queryUI() throws Exception {
		return "queryUI";
	}
	
	/**
	 * 品牌修改界面
	 */
	public String editUI() throws Exception {
		
		Brand brand=brandService.get(model.getCode());
		request.put("brand", brand);
		return "editUI";
	}
	
	/**
	 * 品牌修改
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
			 request.put("message", "修改失败,上传的文件类型错误");
			 request.put("urladdress", PropertyUtil.readUrl("control.brand.list"));
			 return "message";
		}
		brandService.update(model);
		request.put("message", "品牌修改成功");
		request.put("urladdress", PropertyUtil.readUrl("control.brand.list"));
		return "message";
	}
}
