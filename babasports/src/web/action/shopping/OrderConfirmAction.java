package web.action.shopping;

import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;

import sun.misc.BASE64Encoder;

import web.action.BaseAction;
@Controller @Scope("prototype")
public class OrderConfirmAction extends BaseAction<Object> {

	@Override
	public String execute() throws Exception {
		BASE64Encoder encoder=new BASE64Encoder();
		String url=encoder.encode("/customer/shopping/confirm.action".getBytes());
		request.put("directUrl", url);
		return SUCCESS;
	}

	
}
