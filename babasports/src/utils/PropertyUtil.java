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
	 * ��ȡ��·����siteurl.properties��ָ��key������
	 * @param key ��ȡ���ݵ�key
	 * @return ��Ҫ������
	 */
	public static String  readUrl(String key){
		return (String) prop.get(key);
	}
	
	
	/**
	 * ��ȡ��·����dataimport.properties��ָ��key������
	 * @param key ��ȡ���ݵ�key
	 * @return ��Ҫ������
	 */
	public static String  readSolrConf(String key){
		return (String) solrprop.get(key);
	}
}
