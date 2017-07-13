package web.action.user;

import java.io.IOException;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts2.ServletActionContext;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;

import service.user.BuyerService;
import sun.misc.BASE64Decoder;
import utils.PropertyUtil;

import bean.user.Buyer;
import web.action.BaseAction;
@Controller @Scope("prototype")
public class BuyerRegAction extends BaseAction<Buyer> {
	@Resource
	private BuyerService buyerService;
	private String directUrl;
	public String getDirectUrl() {
		return directUrl;
	}
	public void setDirectUrl(String directUrl) {
		this.directUrl = directUrl;
	}
	/**
	 * ��ʾע�����
	 */
	public String regUI(){
		return "regUI";
	}
	/**
	 * �û�ע��
	 * @throws IOException 
	 */
	public String reg() throws IOException{
		if(buyerService.exist(model.getUsername().trim())){
			//�����û�����
			request.put("error", "���û��Ѿ�����");
			return "regUI";
		}else{
			buyerService.save(model);
			session.put("user", buyerService.get(model.getUsername().trim()));
			request.put("message", "�û�ע��ɹ�");
			String url="/";
			if(directUrl!=null && !"".equals(directUrl)){
				BASE64Decoder decoder=new BASE64Decoder();
				url=new String(decoder.decodeBuffer(directUrl));
			}
			request.put("urladdress", url);
			return "message";
		}
	}
	
	/**���ajax���������,���м���û��Ƿ����
	 * �ж��û��Ƿ����
	 * @throws Exception 
	 */
	public String isUserExist() throws Exception{
        HttpServletResponse respons = ServletActionContext.getResponse();  
        respons.setContentType("text/html;charset=utf-8"); 
		if(buyerService.exist(model.getUsername().trim())){
			respons.getWriter().print("<font color='red'>���û��Ѿ�����</font>");  
		}else{
			respons.getWriter().print("<font color='green'>�û�������ʹ��</font>");  
		}
		return null;
	}
	
	
}
