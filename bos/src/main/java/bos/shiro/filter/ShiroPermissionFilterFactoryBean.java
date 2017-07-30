package bos.shiro.filter;

import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.apache.shiro.config.Ini;
import org.apache.shiro.spring.web.ShiroFilterFactoryBean;
import org.apache.shiro.util.CollectionUtils;
import org.apache.shiro.web.config.IniFilterChainResolverFactory;

import bos.dao.IFunctionDao;
import bos.domain.Function;
/**
 * 自定义的shiroFilterFactoryBean,用于从数据库中获取权限相关url
 * @author Administrator
 *
 */
public class ShiroPermissionFilterFactoryBean extends ShiroFilterFactoryBean {
	@Resource
	private IFunctionDao dao;

	@Override
	public void setFilterChainDefinitions(String definitions) {
			Ini ini = new Ini();
	        ini.load(definitions);//先加载配置文件中的url
	        Ini.Section section = ini.getSection(IniFilterChainResolverFactory.URLS);
	        if (CollectionUtils.isEmpty(section)) {
	            section = ini.getSection(Ini.DEFAULT_SECTION_NAME);
	        }
	        System.out.println(section);
	        List<Function> list = dao.findAllPage();
	        for (Function function : list) {
	        	section.put("/"+function.getPage(), "perms["+function.getCode()+"]");
			}
	        if(section.containsKey("/*")){
	        	section.remove("/*");//将其他url需要登录的逻辑放在最后
	        }
	        section.put("/*", "authc");
	        System.out.println(section);
	        setFilterChainDefinitionMap(section);
	}

	
	

}
