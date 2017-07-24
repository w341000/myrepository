package bos.web.action;

import java.io.IOException;
import java.util.List;

import javax.annotation.Resource;

import org.apache.commons.lang3.StringUtils;
import org.apache.struts2.ServletActionContext;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;

import cn.itcast.crm.domain.Customer;

import bos.crm.CustomerService;
import bos.domain.User;
import bos.service.IUserService;
import bos.utils.MD5Utils;
import bos.web.action.base.BaseAction;
@Controller @Scope("prototype")
public class UserAction extends BaseAction<User> {
	private String checkcode;
	@Resource
	private IUserService userService;
	
	public void setCheckcode(String checkcode) {
		this.checkcode = checkcode;
	}
	/**
	 * 登录操作
	 * @return
	 */
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
	/**
	 * 退出登录
	 * @return
	 */
	public String logout(){
		session.put("loginUser", null);
		return "login";
	}
	/**
	 * 修改密码
	 * @throws IOException
	 */
	public String editPassword() throws IOException{
		User user=(User) session.get("loginUser");
		String password =model.getPassword();
		password=MD5Utils.md5(password);
		String flag="1";
		try {
			userService.editPassword(password,user.getId());
		} catch (Exception e) {
			e.printStackTrace();
			flag="0";
		}
		ServletActionContext.getResponse().setContentType("text/html;charset=UTF-8");
		ServletActionContext.getResponse().getWriter().write(flag);
		return NONE;
	}
}
