package bos.web.action;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.annotation.Resource;

import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Row;
import org.apache.struts2.ServletActionContext;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;

import bos.domain.Region;
import bos.service.IRegionService;
import bos.web.action.base.BaseAction;
@Controller @Scope("prototype")
public class RegionAction extends BaseAction<Region> {
	private File myFile;
	@Resource
	private IRegionService regionService;
	
	
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
				list.add(region);
			}
			regionService.saveBatch(list);
		}catch (Exception e) {
			flag="0";
		}
		this.writeHtml(flag);
		return NONE;
	}
}
