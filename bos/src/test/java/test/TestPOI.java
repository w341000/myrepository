package test;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;

import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Row;
import org.junit.Test;

public class TestPOI {
	
	@Test
	public void testPOI() throws FileNotFoundException, IOException{
		HSSFWorkbook workbook=new HSSFWorkbook(new FileInputStream("D:\\abc.xls"));
		HSSFSheet sheet = workbook.getSheetAt(0);
		for(Row row:sheet){
			int rowNum = row.getRowNum();
			if(rowNum ==0){
				continue;
			}
			String v1 = row.getCell(0).getStringCellValue();
			String v2 = row.getCell(1).getStringCellValue();
			String v3 = row.getCell(2).getStringCellValue();
			String v4 = row.getCell(3).getStringCellValue();
			String v5 = row.getCell(4).getStringCellValue();
			System.out.println(v1+"  "+v2+"  "+v3+"  "+v4+"  "+v5);
		}
		
	}
}
