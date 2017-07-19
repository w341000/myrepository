package bos.web.action;

import javax.annotation.Resource;

import org.apache.commons.lang3.StringUtils;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;

import bos.domain.User;
import bos.service.IUserService;
import bos.web.action.base.BaseAction;
@Controller @Scope("prototype")
public class UserAction extends BaseAction<User> {
	private String checkcode;
	@Resource
	private IUserService userService;
	
	public void setCheckcode(String checkcode) {
		this.checkcode = checkcode;
	}
	public String login(){
		//生成的验证码
		String key=(String) session.get("key");
		//验证用户验证码是否正确
		if(StringUtils.isNotBlank(checkcode) && checkcode.equals(key)){
			//验证码正确
			User user=userService.login(model);
			if(user!=null){
				//登录成功,将user放入session,跳转的系统首页
				session.put("loginUser", user);
				return "home";
			}else{
				//登录失败,设置错误提示信息,跳转到登录页面
				this.addActionError(getText("loginError"));
				return "login";
			}
		}else{
			//验证码错误
			this.addActionError(getText("validateCodeError"));
			return "login";
		}
	}
}
