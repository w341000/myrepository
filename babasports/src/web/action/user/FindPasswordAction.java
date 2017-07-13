package web.action.user;

import java.io.StringWriter;

import javax.annotation.Resource;

import org.apache.velocity.Template;
import org.apache.velocity.VelocityContext;
import org.apache.velocity.app.Velocity;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;

import service.user.BuyerService;
import utils.BuildHtmlFile;
import utils.EmailSender;
import utils.MD5;
import utils.PropertyUtil;

import bean.user.Buyer;
import web.action.BaseAction;
/**
 * �һ�����
 * @author Administrator
 *
 */
@Controller @Scope("prototype")
public class FindPasswordAction extends BaseAction<Buyer> {
	@Resource
	private BuyerService buyerService;
	private String validateCode;
	
	
	
	public String getValidateCode() {
		return validateCode;
	}
	public void setValidateCode(String validateCode) {
		this.validateCode = validateCode;
	}
	/**
	 * �һ�����֮�����޸�
	 * @return
	 */
	public String changepassword(){
		if(model.getUsername()!=null && !"".equals(model.getUsername().trim())){
			if(buyerService.exist(model.getUsername().trim())){
				Buyer buyer=buyerService.get(model.getUsername());
				String code=MD5.MD5Encode(buyer.getUsername()+buyer.getPassword());
				if(code.equals(validateCode)){//У��ͨ��,��Դ�Ϸ�
					buyer.setPassword(model.getPassword());
					buyerService.updatePassword(buyer.getUsername(), model.getPassword().trim());
					request.put("message", "�����޸ĳɹ�");
					request.put("urladdress", "/user/logon.action");
					return "message";
				}
			}else
				request.put("message", "�û�������");
		}
		return "errorresult";
		
	}
	/**
	 * �һ�����֮��ʾ�����޸�
	 * @return
	 */
	public String update(){
		if(model.getUsername()!=null && !"".equals(model.getUsername().trim())){
			if(buyerService.exist(model.getUsername().trim())){
				Buyer buyer=buyerService.get(model.getUsername());
				String code=MD5.MD5Encode(buyer.getUsername()+buyer.getPassword());
				if(code.equals(validateCode))//У��ͨ��,��Դ�Ϸ�
					return "findpassword3";
			}else
				request.put("message", "�û�������");
		}
		return "errorresult";
	}
	/**
	 * �һ�����֮�����ʼ�
	 * @return
	 */
	public String getpassword(){
		if(model.getUsername()!=null && !"".equals(model.getUsername().trim())){
			if(buyerService.exist(model.getUsername().trim())){
				Buyer buyer=buyerService.get(model.getUsername());
				StringWriter sw=new StringWriter();
				BuildHtmlFile.createHtml("mailContent.html", sw, new String[]{"username","validateCode"}, new String[]{buyer.getUsername(),
						MD5.MD5Encode(buyer.getUsername()+buyer.getPassword())	});
				String content=sw.toString();
				EmailSender.send(buyer.getEmail(), "�Ͱ��˶���  -�һ�����", content, "text/html");
				return "findpassword2";
			}else
				request.put("message", "�û�������");
		}
		else{
			request.put("message", "�������û���");
		}
		return "findpassword";
	}

}
