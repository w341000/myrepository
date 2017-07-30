package bos.shiro;

import java.util.List;

import javax.annotation.Resource;

import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.AuthenticationInfo;
import org.apache.shiro.authc.AuthenticationToken;
import org.apache.shiro.authc.SimpleAuthenticationInfo;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.authz.SimpleAuthorizationInfo;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.subject.PrincipalCollection;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import bos.dao.IFunctionDao;
import bos.dao.IUserDao;
import bos.domain.Function;
import bos.domain.User;
/**
 * 自定义realm
 */
public class BOSRealm extends AuthorizingRealm {
	@Resource
	private IUserDao userDao;
	@Resource
	private IFunctionDao functionDao;

	/**
	 * 认证方法
	 */
	@Override 
	protected AuthenticationInfo doGetAuthenticationInfo(
			AuthenticationToken token) throws AuthenticationException {
		UsernamePasswordToken uptoken=(UsernamePasswordToken) token;
		String username = uptoken.getUsername();//从令牌获得的用户名
		User user=userDao.findByUsername(username);
		if(user==null){//用户名不存在
			return null;
		}else{
			String password = user.getPassword();//获得数据库的密码
			//创建简单信息认证对象
			SimpleAuthenticationInfo info=new SimpleAuthenticationInfo(user, password, getName());
			return info;//返回给安全管理器,由安全管理器负责比对数据库的密码和页面提交的密码
		}
	}
	
	/**
	 * 授权方法
	 */
	@Override @Transactional
	protected AuthorizationInfo doGetAuthorizationInfo(
			PrincipalCollection principals) {
		SimpleAuthorizationInfo info=new SimpleAuthorizationInfo();
		//根据当前登录用户,查询对应的权限进行授权
		User user=(User) principals.getPrimaryPrincipal();
		List<Function> list=null;
		if("admin".equals(user.getUsername())){
			//当前用户是超级管理员,查询所有权限
			list = functionDao.findAll();
		}else{
			//根据用户id查询权限
			list=functionDao.findListByUserid(user.getId());
		}
		for (Function function : list) {
			info.addStringPermission(function.getCode());
		}
		return info;
	}


}
