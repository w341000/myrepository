package bos.web.action.base;

import java.io.IOException;
import java.io.Writer;
import java.lang.reflect.ParameterizedType;
import java.util.Map;

import net.sf.json.JSONObject;
import net.sf.json.JsonConfig;

import org.apache.struts2.ServletActionContext;
import org.apache.struts2.interceptor.ApplicationAware;
import org.apache.struts2.interceptor.RequestAware;
import org.apache.struts2.interceptor.SessionAware;
import org.hibernate.criterion.DetachedCriteria;

import bos.domain.Staff;
import bos.utils.PageBean;

import com.opensymphony.xwork2.ActionSupport;
import com.opensymphony.xwork2.ModelDriven;

public class BaseAction<T> extends ActionSupport implements RequestAware,
SessionAware, ApplicationAware, ModelDriven<T> {
	/**
	 * 
	 */
	private static final long serialVersionUID = 504702853045416636L;
	protected T model; 
	protected Map<String, Object> application;
	protected Map<String, Object> session;
	protected Map<String, Object> request;
	DetachedCriteria detachedCriteria=null;
	protected PageBean pageBean=new PageBean();
	public void setPage(int page) {
		pageBean.setCurrentPage(page);
	}
	public void setRows(int rows) {
		pageBean.setPageSize(rows);
	}
	
	/**
	 * 完成初始化工作
	 */
		@SuppressWarnings("unchecked")
		public BaseAction() {
			//获取子类实际泛型类型信息
			ParameterizedType type = (ParameterizedType) this.getClass()
					.getGenericSuperclass();
			Class<T>  clazz = (Class<T>) type.getActualTypeArguments()[0];
			//给pagebean设置查询信息
			detachedCriteria=DetachedCriteria.forClass(clazz);
			pageBean.setDetachedCriteria(detachedCriteria);
			try {
				//通过类型信息构建一个model
				model = (T) clazz.newInstance();
			} catch (Exception e) {
				throw new RuntimeException(e);
			}
		}
	protected void setResponseContentType(String type){
		ServletActionContext.getResponse().setContentType(type);
	}

	protected Writer getWriter(){
		try {
			return ServletActionContext.getResponse().getWriter();
		} catch (IOException e) {
			e.printStackTrace();
			throw new RuntimeException(e);
		}
	}
	protected void writeJson(String json) throws IOException{
		this.setResponseContentType("application/json;charset=UTF-8");
		this.getWriter().write(json);
	}
	protected void writeHtml(String html) throws IOException{
		this.setResponseContentType("text/html;charset=UTF-8");
		this.getWriter().write(html);
	}
	/**
	 * 将pagebean的数据以json写回
	 * @param excludes 需要被排除的属性
	 * @throws IOException
	 */
	public void WritePageBean2Json(String[]  excludes) throws IOException{
		JsonConfig jsonConfig=new JsonConfig();
		jsonConfig.setExcludes(excludes);
		JSONObject jsonObject = JSONObject.fromObject(pageBean, jsonConfig);
		String json=jsonObject.toString();
		this.writeJson(json);
	}
	@Override
	public T getModel() {
		return model;
	}

	@Override
	public void setApplication(Map<String, Object> application) {
		this.application = application;
	}

	@Override
	public void setSession(Map<String, Object> session) {
		this.session = session;
	}

	@Override
	public void setRequest(Map<String, Object> request) {
		this.request = request;
	}

}
