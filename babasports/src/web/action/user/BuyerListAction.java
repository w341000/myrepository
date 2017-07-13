package web.action.user;

import javax.annotation.Resource;

import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;

import service.user.BuyerService;

import bean.PageBean;
import bean.QueryResult;
import bean.user.Buyer;
import web.action.BaseAction;
@Controller("buyerListAction") @Scope("prototype")
public class BuyerListAction extends BaseAction<PageBean> {

	@Resource
	private BuyerService buyerService;

	private String query;
	private String realname;
	private String email;
	private String username;
	public String getQuery() {
		return query;
	}
	public void setQuery(String query) {
		this.query = query;
	}
	public String getRealname() {
		return realname;
	}
	public void setRealname(String realname) {
		this.realname = realname;
	}
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	/**
	 * ��ʾ�û���ҳ�б�
	 */
	@Override
	public String execute() throws Exception {
		
		String orderby=" order by regTime desc";
		StringBuffer where=new StringBuffer();
		//����Ӳ�ѯҳ�����,��ƴ��hql��where���
		if("true".equals(query)){
			if(email!=null && !"".equals(email.trim()))//�������ѯ
				where.append(" and email like '%"+email+"%'");
			if(username!=null && !"".equals(username.trim()))//���û�����ѯ
				where.append(" and username like '%"+username+"%'");
			if(realname!=null && !"".equals(realname.trim()))//����ʵ������ѯ
				where.append(" and realname like '%"+realname+"%'");
		}
		QueryResult<Buyer> qr=buyerService.findByPage(where.toString(), orderby, model.getCurrentpage(), model.getPagesize(), null, null);
		model.setList(qr.getList());
		model.setTotalrecord(qr.getTotalrecord());
		request.put("pagebean", model);
		return SUCCESS;
	}

	
}
