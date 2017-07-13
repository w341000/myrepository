package web.action.shopping;

import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;

import web.action.BaseAction;
@Controller @Scope("prototype")
public class PaymentWayAction extends BaseAction<Object> {

	
	
	@Override
	public String execute() throws Exception {
		
		return SUCCESS;
	}

	
}
