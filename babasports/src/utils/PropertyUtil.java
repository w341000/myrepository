package utils;

import java.io.IOException;
import java.util.Properties;

public class PropertyUtil {
	private static Properties prop=new Properties();
	private static Properties solrprop=new Properties();
	static{
		try {
			prop.load(PropertyUtil.class.getClassLoader().getResourceAsStream("utils/siteurl.properties"));
			solrprop.load(PropertyUtil.class.getClassLoader().getResourceAsStream("utils/dataimport.properties"));
		} catch (IOException e) {
			e.printStackTrace();
			throw new RuntimeException(e);
		}
	}
	/**
	 * 获取类路径下siteurl.properties中指定key的内容
	 * @param key 获取内容的key
	 * @return 需要的内容
	 */
	public static String  readUrl(String key){
		return (String) prop.get(key);
	}
	
	
	/**
	 * 获取类路径下dataimport.properties中指定key的内容
	 * @param key 获取内容的key
	 * @return 需要的内容
	 */
	public static String  readSolrConf(String key){
		return (String) solrprop.get(key);
	}
}
