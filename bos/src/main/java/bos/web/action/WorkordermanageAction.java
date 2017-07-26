package bos.web.action;

import java.io.IOException;

import javax.annotation.Resource;

import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;

import bos.domain.Workordermanage;
import bos.service.IWorkordermanageService;
import bos.web.action.base.BaseAction;
@Controller @Scope("prototype")
public class WorkordermanageAction extends BaseAction<Workordermanage> {
	@Resource
	private IWorkordermanageService workordermanageService;
	
	
	public String add() throws IOException{
		workordermanageService.save(model);
		this.writeHtml("1");
		return NONE;
	}

}
