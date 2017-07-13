package service.user.impl;

import java.io.Serializable;

import org.hibernate.Query;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import bean.user.Buyer;
import service.user.BuyerService;
import utils.MD5;
import dao.impl.BaseDaoImpl;

@Service("buyerService") @Transactional
public class BuyerServiceImpl extends BaseDaoImpl<Buyer> implements BuyerService {
	@Override
	public void updatePassword(String username,String password) {
		String md5psw=MD5.MD5Encode(password);
		Query query=this.getSession().createQuery("update Buyer set password=:password where username=:username");
		query.setString("username", username).setString("password", md5psw);
		query.executeUpdate();
	}

	@Override
	public boolean exist(String username) {
		Query query=this.getSession().createQuery("select count(o) from Buyer o where o.username=:username");
		Long count=(Long) query.setString("username", username).uniqueResult();
		return count>0;
	}

	@Override
	public void save(Buyer t) {
		t.setPassword(MD5.MD5Encode(t.getPassword()));
		super.save(t);
	}

	@Override
	public boolean validate(String username, String password) {
		Query query=this.getSession().createQuery("select count(o) from Buyer o where o.username=:username and o.password =:password");
		Long count=(Long) query.setString("username", username).setString("password", MD5.MD5Encode(password)).uniqueResult();
		return count>0;
	}

	@Override
	public void delete(Serializable... ids) {
		setVisible(false,ids);
		
	}
	@Override
	public void enable(Serializable... ids){
		setVisible(true,ids);
	}
	
	private void setVisible(boolean visible,Serializable... ids) {
		if(ids!=null && ids.length>0){
			Query query=getSession().createQuery("update Buyer set visible=:visible where username in(:usernames)");
			query.setBoolean("visible", visible);
			query.setParameterList("usernames", ids);
			query.executeUpdate();
		}
		
	}
	
	

}
