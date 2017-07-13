package service.book.impl;

import javax.annotation.PostConstruct;

import org.hibernate.Query;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import dao.impl.BaseDaoImpl;

import bean.book.GeneratedOrderid;
import service.book.GeneratedOrderidService;
@Service("generatedOrderidService") @Transactional
public class GeneratedOrderidServiceImpl extends BaseDaoImpl<GeneratedOrderid> implements GeneratedOrderidService {

	@Override
	public void init(){
		long count=(Long)this.getSession().createQuery("select count(*) from GeneratedOrderid").uniqueResult();
		if(count==0){
			GeneratedOrderid go=new GeneratedOrderid();
			go.setId("order");
			this.save(go);
		}
	}

	@Override
	public int buildOrderid(){
		Query query=this.getSession().createQuery("update GeneratedOrderid set orderid=orderid+1 where id=:id");
		query.setString("id", "order");
		query.executeUpdate();
		//»√”Ôæ‰¡¢øÃ÷¥––
		this.getSession().flush();
		GeneratedOrderid go=this.get("order");
		return go.getOrderid();
	}
}
