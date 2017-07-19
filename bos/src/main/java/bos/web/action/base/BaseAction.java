package bos.web.action.base;

import java.lang.reflect.ParameterizedType;
import java.util.Map;

import org.apache.struts2.interceptor.ApplicationAware;
import org.apache.struts2.interceptor.RequestAware;
import org.apache.struts2.interceptor.SessionAware;

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
	
	/**
	 * 完成初始化工作
	 */
		@SuppressWarnings("unchecked")
		public BaseAction() {
			//获取子类实际泛型类型信息
			ParameterizedType type = (ParameterizedType) this.getClass()
					.getGenericSuperclass();
			Class<T>  clazz = (Class<T>) type.getActualTypeArguments()[0];
			try {
				//通过类型信息构建一个model
				model = (T) clazz.newInstance();
			} catch (Exception e) {
				throw new RuntimeException(e);
			}
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
