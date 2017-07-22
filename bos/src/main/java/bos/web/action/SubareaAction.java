package bos.web.action;

import java.io.IOException;
import java.net.URLEncoder;
import java.util.List;

import javax.annotation.Resource;
import javax.servlet.ServletOutputStream;

import org.apache.commons.lang3.StringUtils;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.struts2.ServletActionContext;
import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.Restrictions;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;

import bos.domain.Region;
import bos.domain.Subarea;
import bos.service.ISubareaService;
import bos.utils.FileUtils;
import bos.web.action.base.BaseAction;

@Controller
@Scope("prototype")
public class SubareaAction extends BaseAction<Subarea> {
	@Resource
	private ISubareaService subareaService;

	/**
	 * 保存分区
	 */
	public String add() {
		subareaService.save(model);
		return NONE;
	}

	/**
	 * 修改分区
	 */
	public String edit() {
		Subarea subarea = subareaService.findById(model.getId());
		subarea.setAddresskey(model.getAddresskey());
		subarea.setStartnum(model.getStartnum());
		subarea.setEndnum(model.getEndnum());
		subarea.setSingle(model.getSingle());
		subarea.setRegion(model.getRegion());
		subarea.setPosition(model.getPosition());
		subareaService.update(subarea);
		return NONE;
	}

	/**
	 * 分页查询
	 */
	public String pageQuery() throws IOException {
		// 查询之前封装条件
		DetachedCriteria detachedCriteria = pageBean.getDetachedCriteria();
		String addresskey = model.getAddresskey();
		Region region = model.getRegion();
		if (region != null) {
			String province = region.getProvince();
			String city = region.getCity();
			String district = region.getDistrict();
			// 关联查询时给被关联的表创建别名
			detachedCriteria.createAlias("region", "r");

			if (StringUtils.isNotBlank(province)) {
				detachedCriteria.add(Restrictions.like("r.province", "%"
						+ province + "%"));// 根据省份模糊查询
			}
			if (StringUtils.isNotBlank(city)) {
				detachedCriteria.add(Restrictions.like("r.city", "%" + city
						+ "%"));// 根据城市模糊查询
			}
			if (StringUtils.isNotBlank(district)) {
				detachedCriteria.add(Restrictions.like("r.district", "%"
						+ district + "%"));// 根据地区模糊查询
			}
		}
		if (StringUtils.isNotBlank(addresskey)) {
			// 根据地址模糊查询
			detachedCriteria.add(Restrictions.like("addresskey", "%"
					+ addresskey + "%"));
		}
		subareaService.pageQuery(this.pageBean);
		String[] excludes = new String[] { "pageSize", "currentPage",
				"detachedCriteria", "subareas", "decidedzone" };
		this.WritePageBean2Json(excludes);
		return NONE;
	}

	/**
	 * 使用poi写入excel文件提供下载
	 * 
	 * @return
	 * @throws IOException 
	 */
	public String exportXls() throws IOException {
		List<Subarea> list = subareaService.findAll();

		// 在内存中创建一个excel文件,通过输出流写入客户端提供下载
		HSSFWorkbook workbook = new HSSFWorkbook();
		// 创建一个sheet页
		HSSFSheet sheet = workbook.createSheet("分区数据");
		// 创建标题行
		HSSFRow headRow = sheet.createRow(0);
		headRow.createCell(0).setCellValue("分区编号");
		headRow.createCell(1).setCellValue("区域编号");
		headRow.createCell(2).setCellValue("地址关键字");
		headRow.createCell(3).setCellValue("省市区");
		for (Subarea subarea : list) {
			HSSFRow dataRow = sheet.createRow(sheet.getLastRowNum()+1);
			dataRow.createCell(0).setCellValue(subarea.getId());
			dataRow.createCell(1).setCellValue(subarea.getRegion().getId());
			dataRow.createCell(2).setCellValue(subarea.getAddresskey());
			dataRow.createCell(3).setCellValue(subarea.getRegion().getName());
		}
		//一个流两个头
		String filename="你好.xls";
		//针对中文文件名进行url编码
		String agent=ServletActionContext.getRequest().getHeader("User-Agent");
		filename=FileUtils.encodeDownloadFilename(filename, agent);
		//获得mime类型
		String mimeType = ServletActionContext.getServletContext().getMimeType(filename);
		this.setResponseContentType(mimeType);
		ServletActionContext.getResponse().setHeader("content-disposition", "attachment;filename="+filename);
		 ServletOutputStream out = ServletActionContext.getResponse().getOutputStream();
		 workbook.write(out);
		return NONE;
	}
}
