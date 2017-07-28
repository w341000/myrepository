package bos.shiro;

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

import bos.dao.IUserDao;
import bos.domain.User;
/**
 * 自定义realm
 */
public class BOSRealm extends AuthorizingRealm {
	@Resource
	private IUserDao userDao;

	/**
	 * 认证方法
	 */
	@Override
	protected AuthenticationInfo doGetAuthenticationInfo(
			AuthenticationToken token) throws AuthenticationException {
		System.out.println("认证方法");
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
	@Override
	protected AuthorizationInfo doGetAuthorizationInfo(
			PrincipalCollection principals) {
		SimpleAuthorizationInfo info=new SimpleAuthorizationInfo();
		info.addStringPermission("staff");
		return info;
	}


}
