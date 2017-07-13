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
 * 找回密码
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
	 * 找回密码之密码修改
	 * @return
	 */
	public String changepassword(){
		if(model.getUsername()!=null && !"".equals(model.getUsername().trim())){
			if(buyerService.exist(model.getUsername().trim())){
				Buyer buyer=buyerService.get(model.getUsername());
				String code=MD5.MD5Encode(buyer.getUsername()+buyer.getPassword());
				if(code.equals(validateCode)){//校验通过,来源合法
					buyer.setPassword(model.getPassword());
					buyerService.updatePassword(buyer.getUsername(), model.getPassword().trim());
					request.put("message", "密码修改成功");
					request.put("urladdress", "/user/logon.action");
					return "message";
				}
			}else
				request.put("message", "用户不存在");
		}
		return "errorresult";
		
	}
	/**
	 * 找回密码之显示密码修改
	 * @return
	 */
	public String update(){
		if(model.getUsername()!=null && !"".equals(model.getUsername().trim())){
			if(buyerService.exist(model.getUsername().trim())){
				Buyer buyer=buyerService.get(model.getUsername());
				String code=MD5.MD5Encode(buyer.getUsername()+buyer.getPassword());
				if(code.equals(validateCode))//校验通过,来源合法
					return "findpassword3";
			}else
				request.put("message", "用户不存在");
		}
		return "errorresult";
	}
	/**
	 * 找回密码之发送邮件
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
				EmailSender.send(buyer.getEmail(), "巴巴运动网  -找回密码", content, "text/html");
				return "findpassword2";
			}else
				request.put("message", "用户不存在");
		}
		else{
			request.put("message", "请输入用户名");
		}
		return "findpassword";
	}

}
