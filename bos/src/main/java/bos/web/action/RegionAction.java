package bos.web.action;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.annotation.Resource;

import org.apache.commons.lang3.StringUtils;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Row;
import org.apache.struts2.ServletActionContext;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;

import bos.domain.Region;
import bos.service.IRegionService;
import bos.utils.PinYin4jUtils;
import bos.web.action.base.BaseAction;
@Controller @Scope("prototype")
public class RegionAction extends BaseAction<Region> {
	private File myFile;
	@Resource
	private IRegionService regionService;
	//模糊查询条件
	private String q;
	public void setQ(String q) {
		this.q = q;
	}
	public void setMyFile(File myFile) {
		this.myFile = myFile;
	}


	/**
	 * 批量导入Excel文件
	 * @throws IOException 
	 * @throws FileNotFoundException 
	 */
	public String importXls() throws Exception{
		String flag="1";
		try{
			//使用poi解析Excel文件
			HSSFWorkbook workbook=new HSSFWorkbook(new FileInputStream(myFile));
			HSSFSheet sheet = workbook.getSheetAt(0);//获得第一个工作簿
			List <Region> list=new ArrayList<Region>();
			for(Row row :sheet){
				int rowNum = row.getRowNum();
				if(rowNum ==0){
					continue;
				}
				String id = row.getCell(0).getStringCellValue();
				String province = row.getCell(1).getStringCellValue();
				String city = row.getCell(2).getStringCellValue();
				String district = row.getCell(3).getStringCellValue();
				String postcode = row.getCell(4).getStringCellValue();
				
				Region region=new Region(id, province, city, district, postcode, null, null, null);
				//生成城市编码和简码
				city=city.substring(0, city.length()-1);
				province=province.substring(0, province.length()-1);
				district=district.substring(0, district.length()-1);
				String[] stringToPinyin = PinYin4jUtils.stringToPinyin(city);
				//城市编码
				String citycode=StringUtils.join(stringToPinyin);
				String info=province+city+district;
				String[] headByString = PinYin4jUtils.getHeadByString(info);
				//简码
				String shortcode= StringUtils.join(headByString);
				region.setShortcode(shortcode);
				region.setCitycode(citycode);
				
				list.add(region);
			}
			regionService.saveBatch(list);
		}catch (Exception e) {
			flag="0";
		}
		this.writeHtml(flag);
		return NONE;
	}
	
	/**
	 * 分页查询
	 */
	public String pageQuery() throws IOException{
		
		regionService.pageQuery(pageBean);
		//过滤需要传输的json内容
		String[]  excludes=new String[]{"pageSize","currentPage","detachedCriteria","subareas"};
		this.WritePageBean2Json(excludes);
		return NONE;
	}
	/**
	 * 查询所有区域数据,返回json
	 * @throws IOException 
	 */
	
	public String listajax() throws IOException{
		List<Region> list=null;
		if(StringUtils.isNotBlank(q)){
			list=regionService.findByQ(q);
		}else{
			list=regionService.findAll();
		}
		String[] excludes=new String[]{"subareas","postcode","shortcode","citycode","province",
				"city","district"};
		this.WriteList2Json(list,excludes);
		return NONE;
	}


	
	
}
