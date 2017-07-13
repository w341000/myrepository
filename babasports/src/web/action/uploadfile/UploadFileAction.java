package web.action.uploadfile;

import javax.annotation.Resource;

import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;

import service.uploadfile.UploadFileService;

import bean.PageBean;
import bean.QueryResult;
import bean.product.Brand;
import bean.uploadfile.UploadFile;
import web.action.BaseAction;
@Controller("uploadFileAction") @Scope("prototype")
public class UploadFileAction extends BaseAction<PageBean> {
	@Resource
	private UploadFileService uploadFileService;
	@Override
	public String execute() throws Exception {
		String orderBy=" order by id desc";
		QueryResult<UploadFile> qr=uploadFileService.findByPage(model.getCurrentpage(), model.getPagesize(), orderBy);
		model.setList(qr.getList());
		model.setTotalrecord(qr.getTotalrecord());
		request.put("pagebean", model);
		return SUCCESS;
	}
}
