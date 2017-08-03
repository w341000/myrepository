package bos.web.action;

import java.io.IOException;
import java.util.List;

import javax.annotation.Resource;

import org.activiti.engine.ProcessEngine;
import org.apache.commons.lang3.StringUtils;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.AuthenticationToken;
import org.apache.shiro.authc.IncorrectCredentialsException;
import org.apache.shiro.authc.UnknownAccountException;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.subject.Subject;
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
	private String[] roleIds;
	
	
	public void setRoleIds(String[] roleIds) {
		this.roleIds = roleIds;
	}
	public void setCheckcode(String checkcode) {
		this.checkcode = checkcode;
	}
	/**
	 * 使用shiro提供的方式进行认证登录操作
	 * @return
	 */
	public String login(){
		//生成的验证码
				String key=(String) session.get("key");
				//验证用户验证码是否正确
				if(StringUtils.isNotBlank(checkcode) && checkcode.equals(key)){
					//验证码正确
					//获得当前用户对象
					Subject subject=SecurityUtils.getSubject();//状态为"未认证"
					String password = model.getPassword();
					password = MD5Utils.md5(password);
					AuthenticationToken token=new UsernamePasswordToken(model.getUsername(),password);//构造用户名密码令牌
					try {
						subject.login(token); //登录成功,跳转首页
						//获取认证信息对象中存储的user对象
						User user=(User) subject.getPrincipal();
						//登录成功,将user放入session,跳转的系统首页
						session.put("loginUser", user);
						return "home";
					} catch (UnknownAccountException e) {
						//登录失败,设置错误提示信息,跳转到登录页面
						this.addActionError(getText("usernameNotFound"));
					} catch (IncorrectCredentialsException e) {
						//登录失败,设置错误提示信息,跳转到登录页面
						this.addActionError(getText("loginError"));
					}
					return "login";
				}else{
					//验证码错误
					this.addActionError(getText("validateCodeError"));
					return "login";
				}
	}
	
	@Deprecated
	public String login_back(){
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
		Subject subject=SecurityUtils.getSubject();
		subject.logout();
		ServletActionContext.getRequest().getSession().invalidate();
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
	/**
	 * 用户分页查询
	 * @return
	 * @throws IOException 
	 */
	public String pageQuery() throws IOException{
		userService.pageQuery(pageBean);
		String[]  excludes=new String[]{"pageSize","currentPage","detachedCriteria","noticebills","roles"};
		this.WritePageBean2Json(excludes);
		return NONE;
	}
	/**
	 * 添加用户
	 */
	public String add(){
		userService.save(model,roleIds);
		return "list";
	}
}
