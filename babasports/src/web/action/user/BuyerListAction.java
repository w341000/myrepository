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
	 * 显示用户分页列表
	 */
	@Override
	public String execute() throws Exception {
		
		String orderby=" order by regTime desc";
		StringBuffer where=new StringBuffer();
		//如果从查询页面而来,则拼接hql的where语句
		if("true".equals(query)){
			if(email!=null && !"".equals(email.trim()))//按邮箱查询
				where.append(" and email like '%"+email+"%'");
			if(username!=null && !"".equals(username.trim()))//按用户名查询
				where.append(" and username like '%"+username+"%'");
			if(realname!=null && !"".equals(realname.trim()))//按真实姓名查询
				where.append(" and realname like '%"+realname+"%'");
		}
		QueryResult<Buyer> qr=buyerService.findByPage(where.toString(), orderby, model.getCurrentpage(), model.getPagesize(), null, null);
		model.setList(qr.getList());
		model.setTotalrecord(qr.getTotalrecord());
		request.put("pagebean", model);
		return SUCCESS;
	}

	
}
