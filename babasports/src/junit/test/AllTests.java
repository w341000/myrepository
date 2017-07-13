package junit.test;

import java.io.File;
import java.io.IOException;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.sql.DataSource;

import org.hibernate.SQLQuery;
import org.hibernate.SessionFactory;
import org.junit.BeforeClass;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Suite;
import org.junit.runners.Suite.SuiteClasses;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.jdbc.core.JdbcTemplate;

import bean.book.DeliverWay;
import bean.book.Order;
import bean.book.PaymentWay;
import bean.product.Sex;

import service.book.GeneratedOrderidService;
import service.book.OrderService;
import service.privilege.EmployeeService;
import service.product.ProductInfoService;
import service.user.BuyerService;
import sun.misc.BASE64Decoder;
import sun.misc.BASE64Encoder;
import utils.ImageSizer;
import web.listener.SolrListener;


public class AllTests {
	static ApplicationContext app;
	protected static SessionFactory sessionFactory;
	//@BeforeClass
	public static void setUpBeforeClass() throws Exception {
		try{
			app=new  ClassPathXmlApplicationContext("applicationContext-public.xml","applicationContext-bean.xml");
			sessionFactory=(SessionFactory) app.getBean("sessionFactory");
		}catch(Exception e){
			e.printStackTrace();
			throw e;
		}
	}
	@SuppressWarnings("unused")
	private void initTable() throws IOException {
		createField("brand","updateTime");
		createField("productinfo","updateTime");
		createField("producttype","updateTime");
		createField("productstyle","updateTime");
	}
	private String selectsql(String table,String column){
		return "SELECT * FROM information_schema.columns WHERE table_schema = DATABASE()  AND table_name ='"+table+"' AND column_name ='"+column+"'";
				
	}
	private String editsql(String table,String column){
		return "ALTER TABLE "+table+" ADD COLUMN "+column+" timestamp NULL ON UPDATE CURRENT_TIMESTAMP DEFAULT CURRENT_TIMESTAMP ;";
				
	}
	private void createField(String table,String column) throws IOException{
		String sql=selectsql(table,column);
		SQLQuery query=sessionFactory.openSession().createSQLQuery(sql);
		if(query.list().isEmpty()){
			sql=editsql(table,column);
			query=sessionFactory.openSession().createSQLQuery(sql);
			query.executeUpdate();
		}
	 }
	
	@Test
	 public void test() throws Exception{
		new SolrListener().fullImport();
		Thread.sleep(1000*200);
	 }

	
	//@Test
	 public void test2() throws IOException, ParseException{
		Date date=new Date();
		SimpleDateFormat f=new SimpleDateFormat("yy-MM-dd");
		
		System.out.println(fillZero(8,"123456789"));
	 }
	private String fillZero(int length, String source) {
		if(source.length()>length){
			return source.substring(0, length);
		}
		StringBuffer sb=new StringBuffer();
		for(int i=source.length();i<length;i++){
			sb.append('0');
		}
		return sb.toString()+source;
	}
	@Test
	public void test3(){
		char[] str={'a','\0'};
		System.out.println(new String(str));
		
	}
}
