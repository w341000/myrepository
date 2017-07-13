package junit.test;


import java.lang.reflect.Field;

import org.junit.BeforeClass;
import org.junit.Test;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import dao.impl.BaseDaoImpl;

import service.product.BrandService;
import service.product.ProductInfoService;
import service.product.ProductSearchService;
import service.product.ProductStyleService;
import service.product.ProductTypeService;
import utils.WebUtil;
import bean.product.Brand;
import bean.product.ProductInfo;
import bean.product.ProductStyle;
import bean.product.ProductType;
import bean.product.Sex;

public class ProductTest {
	static ApplicationContext app;
	public String name;
	protected String name1;
	private String name2;
	@BeforeClass
	public static void setUpBeforeClass() throws Exception {
		try{
			app=new  ClassPathXmlApplicationContext("applicationContext-public.xml","applicationContext-bean.xml");
		}catch(Exception e){
			e.printStackTrace();
			throw e;
		}
	}
	
	//@Test
	public void test() {
		try{
			ProductInfo pp=new ProductInfo();
			ProductInfoService service=(ProductInfoService) app.getBean("productInfoService");
			pp.setName("足球");
			Brand brand=new Brand();
			brand.setCode("402881ef5bd70a65015bd718f2aa0000");
			pp.setBrand(brand);
			pp.setCode("123");
			pp.setDescription("好产品");
			pp.setModel("760");
			pp.setMarketprice(120f);
			pp.setWeight(100);
			pp.setSexrequest(Sex.NONE);
			pp.setSellprice(100f);
			pp.setBaseprice(200f);
			ProductStyle style=new ProductStyle();
			style.setName("红色");
			style.setImagename("xxx.gif");
			pp.addProductStyle(style);
			ProductType type1=new ProductType();
			type1.setId(14);
			pp.setType(type1);
			//service.save(pp);
			for(ProductInfo brand1:service.findByPage(1, 10, " order by sellprice desc", " and type.id=14").getList()){
				System.out.println(brand1.getName());
				
			}
			System.out.println("成功");
		}catch(Exception e){
			e.printStackTrace();
		}
		
	}
	@Test
	public void test2() {
		try{
			ProductInfoService productInfoService=(ProductInfoService) app.getBean("productInfoService");
			ProductStyleService styleService=(ProductStyleService) app.getBean("productStyleService");
			ProductTypeService typeService=(ProductTypeService) app.getBean("productTypeService");
			BrandService brandService=(BrandService) app.getBean("brandService");
			productInfoService.save(null);
			productInfoService.update(null);
			
			styleService.setVisibleStatus(false);
			styleService.update(null);
			typeService.update(null);
			typeService.save(null);
			
			brandService.save(null);
			brandService.update(null);
			
			
			productInfoService.setCommend(false);
			System.out.println("成功");
		}catch(Exception e){
			e.printStackTrace();
		}
		
	}
	
	@Test
	public void test3() {
		try{ 
//			ProductSearchService service=(ProductSearchService) app.getBean("productSearchServiceImpl");
//			service.query("运动", 1, 10);
//			System.out.println("成功");
		}catch(Exception e){
			e.printStackTrace();
		}
		
	}
	
	
	
}
