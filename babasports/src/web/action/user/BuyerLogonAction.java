package web.action.user;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts2.ServletActionContext;
import org.springframework.context.annotation.Scope;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.stereotype.Controller;

import service.user.BuyerService;
import sun.misc.BASE64Decoder;

import bean.user.Buyer;
import web.action.BaseAction;

@Controller
@Scope("prototype")
public class BuyerLogonAction extends BaseAction<Buyer> {
	@Resource
	private BuyerService buyerService;
	private String directUrl;

	public String getDirectUrl() {
		return directUrl;
	}

	public void setDirectUrl( String directUrl) {
		this.directUrl = directUrl;
	}

	/**
	 * ��ʾ��¼���沢���û����е�¼����
	 */
	@Override
	public String execute() throws Exception {

		if (model.getUsername() != null
				&& !"".equals(model.getUsername().trim())
				&& model.getPassword() != null
				&& !"".equals(model.getPassword().trim())) {
			if (buyerService.validate(model.getUsername().trim(), model
					.getPassword().trim())) {
				Buyer user = buyerService.get(model.getUsername().trim());
				if (!user.isVisible()) {
					request.put("message", "���˺��Ѿ�������!");
				} else {
					session.put("user", user);// У��ͨ��
					request.put("message", "�û���¼�ɹ�");
					String url = "/";
					if (directUrl != null && !"".equals(directUrl)) {
						BASE64Decoder decoder = new BASE64Decoder();
						url = new String(decoder.decodeBuffer(directUrl));
					}
					request.put("urladdress", url);
				}
				return "message";
			} else {
				request.put("error", "�û������������");
			}
		}
		return "logon";
	}

}
