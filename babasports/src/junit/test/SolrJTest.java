package junit.test;

import java.io.IOException;  
import java.util.ArrayList;
import java.util.HashMap;  
import java.util.List;
import java.util.Map;  
  
import org.apache.solr.client.solrj.SolrClient;  
import org.apache.solr.client.solrj.SolrQuery;
import org.apache.solr.client.solrj.SolrServer;
import org.apache.solr.client.solrj.SolrServerException;  
import org.apache.solr.client.solrj.impl.HttpSolrClient;  
import org.apache.solr.client.solrj.response.QueryResponse;  
import org.apache.solr.client.solrj.response.SolrPingResponse;  
import org.apache.solr.client.solrj.response.UpdateResponse;  
import org.apache.solr.common.SolrDocument;  
import org.apache.solr.common.SolrDocumentList;  
import org.apache.solr.common.SolrInputDocument;  
import org.apache.solr.common.params.MapSolrParams;  
import org.apache.solr.common.params.MultiMapSolrParams;
import org.apache.solr.common.params.SolrParams;  
import org.apache.solr.servlet.SolrRequestParsers;
import org.junit.BeforeClass;
import org.junit.Test;  
import org.springframework.beans.factory.config.PropertyPlaceholderConfigurer;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.orm.hibernate3.annotation.AnnotationSessionFactoryBean;

public class SolrJTest {

	static ApplicationContext app;
	@BeforeClass
	public static void setUpBeforeClass() throws Exception {
		try{
			app=new  ClassPathXmlApplicationContext("applicationContext-public.xml","applicationContext-bean.xml");
		}catch(Exception e){
			e.printStackTrace();
			throw e;
		}
	}
	
	/** 
     * 创建solrClient （4.x的版本使用类是SolrServer,在新版本中已经被弃用了） 
     */  
    @Test  
    public void createSolrClient(){  
        try {  
            SolrClient solr = (SolrClient) app.getBean("solrClient");  
            SolrPingResponse  response = solr.ping();  
            //打印执行时间  
            System.out.println(response.getElapsedTime());  
            solr.close();  
        } catch (Exception e) {  
            e.printStackTrace();  
        }  
    }  
     
    /** 
     * 增加bean索引,该bean中的字段需要匹配schema中的fields,(可以用@Field注解来关联相关字段) 
     * 否者抛出org.apache.solr.client.solrj.beans.BindingException: class: class com.ccy.solr.Blog does not define any fields. 
     * @throws Exception  
     */  
//   @Test  
//   public void addBeanIndex() throws Exception{  
//       SolrClient solr = new HttpSolrClient("http://localhost:8080/solr/test");  
//       Blog blog = new Blog();  
//       blog.setId(123);  
//       blog.setTitle("test");  
//       blog.setContent("test...");  
//       blog.setKeyWord("test");  
//       UpdateResponse response = solr.addBean(blog);  
//       System.out.println(response.getElapsedTime());  
//       solr.commit();  
//       solr.close();  
//   }  
     
     
   /** 
    * 增加索引 
    *  
    * @throws Exception 
    */  
   @Test  
   public void addIndex() throws Exception{  
       SolrClient solr = new HttpSolrClient("http://localhost:8080/solr/test");  
       SolrInputDocument document = new SolrInputDocument();  
       document.addField("id",123, new Float(1.0));  
       //document.addField("", new String[]{});
       //document.addField("title", "my first solrj program");  
       document.addField("msg_title", "我的第一个solrj程序??");  
       document.addField("msg_content", "我的solrj能不能跑起来呢??");  
       document.addField("msg_content", "我的solrjsss能不能跑起来呢??"); 
       UpdateResponse response = solr.add(document);  
       System.out.println(response.getElapsedTime());  
       solr.commit();  
       solr.close();  
   }  
   @Test  
   public void addListIndex() throws Exception{  
       SolrClient solr = new HttpSolrClient("http://localhost:8080/solr/test");  
       List<SolrInputDocument> documents=new ArrayList<SolrInputDocument>();
       SolrInputDocument document = new SolrInputDocument();  
       document.addField("id",2, new Float(1.0));  
       document.addField("msg_title", "我的第一个solrj程序??");  
       document.addField("msg_content", "我的solrj能不能跑起来呢??");  
       documents.add(document);
       document = new SolrInputDocument();  
       document.addField("id",3, new Float(1.0));  
       document.addField("msg_title", "我的第一个solrj程序??");  
       document.addField("msg_content", "我的solrj能不能跑起来呢??");  
       documents.add(document);
       UpdateResponse response = solr.add(documents);  
       System.out.println(response.getElapsedTime());  
       solr.commit();  
       solr.close();  
   }  
     
     
   /** 
    * 删除索引 
    */  
   @Test  
   public void delIndex() throws Exception{  
       SolrClient solr = new HttpSolrClient("http://localhost:8080/solr/test");  
       UpdateResponse response= solr.deleteByQuery("*:*");   //删除所有索引
       //UpdateResponse response = solr.deleteById("123");  //根据id删除索引
       System.out.println(response.getElapsedTime());  
       solr.commit();  
       solr.close();  
   }  
     
   /** 
    * 简单查询 
    * @throws IOException  
    * @throws SolrServerException  
    */  
   @Test  
   public void query() throws Exception{  
       SolrClient solr = new HttpSolrClient("http://localhost:8080/solr/test");  
//       Map<String, String> map = new HashMap<String, String>();  
//       map.put("q", "msg_all:我的");  
//       map.put("start", "0");  //在请求参数中加入此项,进行分页查询
//       map.put("rows", "3"); 
//       SolrParams params = new MapSolrParams(map);  
       SolrQuery query=new SolrQuery("*");
       query.setStart(0).setRows(5);
       QueryResponse resp = solr.query(query);  
//       以下是第二种方法,需要在服务器中运行
//       String queryString="*:*";  
//       MultiMapSolrParams mParams = SolrRequestParsers.parseQueryString(queryString);  
//       QueryResponse resp = solr.query(mParams);  
       SolrDocumentList docsList = resp.getResults();  
       System.out.println(docsList.getNumFound());
       System.out.println(docsList.size());  
       for (SolrDocument doc : docsList) {  
            System.out.println(doc.getFieldValue("msg_content")+"::"+doc.getFieldValue("msg_content"));  
       }  
       solr.close();  
   }  
   
   
   
   /** 
    * 高亮
    */  
   @Test  
   public void highLight() throws Exception{  
	   SolrClient solr = new HttpSolrClient("http://localhost:8080/solr/test");
	   SolrQuery query=new SolrQuery("我的");
	   query.setStart(0).setRows(5).setHighlight(true).setHighlightSimplePre("<span>")
	   		.setHighlightSimplePost("</span>");//启动高亮
	   query.setParam("hl.fl", "msg_all");
       QueryResponse resp = solr.query(query);   
       SolrDocumentList docsList = resp.getResults();  
       for (SolrDocument doc : docsList) {  
    	   String id=(String) doc.getFieldValue("id");
    	   System.out.println(id);
    	   System.out.println(resp.getHighlighting().get(id).get("msg_content"));
       }  
       solr.close();   
   }  
}
