package web.action.uploadfile;

import java.io.File;
import java.util.Arrays;
import java.util.List;

import javax.annotation.Resource;

import org.apache.struts2.ServletActionContext;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;

import com.opensymphony.xwork2.ActionContext;

import exception.NoSupportFileException;

import service.uploadfile.UploadFileService;
import utils.PropertyUtil;
import utils.WebUtil;

import bean.uploadfile.UploadFile;

import web.action.BaseAction;
@Controller("uploadFileManageAction") @Scope("prototype")
public class UploadFileManageAction extends BaseAction<UploadFile> {
	private File uploadfile;
	private String uploadfileContentType;
	private String uploadfileFileName;
	private String[] fileids;
	@Resource
	private UploadFileService uploadFileService;
	public String[] getFileids() {
		return fileids;
	}
	public void setFileids(String[] fileids) {
		this.fileids = fileids;
	}
	public File getUploadfile() {
		return uploadfile;
	}
	public void setUploadfile(File uploadfile) {
		this.uploadfile = uploadfile;
	}
	public String getUploadfileContentType() {
		return uploadfileContentType;
	}
	public void setUploadfileContentType(String uploadfileContentType) {
		this.uploadfileContentType = uploadfileContentType;
	}

	public String getUploadfileFileName() {
		return uploadfileFileName;
	}
	public void setUploadfileFileName(String uploadfileFileName) {
		this.uploadfileFileName = uploadfileFileName;
	}

	/**
	 * 上传界面
	 * @return
	 */
	public String uploadUI(){
		return "uploadUI";
	}
	/**
	 *保存上传文件
	 */
	public String upload() {
		//如果用户没有上传文件
		if(this.uploadfile==null){
			 request.put("message", "请上传文件");
		        request.put("urladdress", PropertyUtil.readUrl("control.uploadfile.list"));
		        return "error";
		}
		if(this.uploadfile.length()>2048*1024){
			request.put("message", "上传文件大于2M,请重新上传");
	        request.put("urladdress", PropertyUtil.readUrl("control.uploadfile.list"));
	        return "error";
		}
		try {//对上传的文件进行校验,文件不为空且类型正确的情况下进行保存
			if(WebUtil.validateFileType(uploadfile, uploadfileContentType, uploadfileFileName)){
				String path=WebUtil.saveFile(this.uploadfileFileName, "/images/upload/", this.uploadfile);
				model.setFilepath(path);
			}//上传的文件类型错误则输出提示
		} catch (NoSupportFileException e) {
			System.out.println(this.uploadfileContentType);
			 request.put("message", "保存失败,上传的文件类型错误");
			 request.put("urladdress", PropertyUtil.readUrl("control.uploadfile.list"));
			 return "error";
		}//类型正确,进行保存操作 
        uploadFileService.save(model);
        request.put("message", "上传文件成功");
        request.put("imagepath", model.getFilepath());
        request.put("urladdress", PropertyUtil.readUrl("control.uploadfile.list"));
        return "uploadfinish";
	}
	/**
	 * 对上传文件进行删除操作
	 * @return
	 */
	public String delete() {
		try{
			if(fileids==null){
				request.put("message", "请选择要删除的文件!");
				request.put("urladdress", PropertyUtil.readUrl("control.uploadfile.list"));
				return "message";
			}
			for(String s:fileids){
				int id=Integer.parseInt(s);
				model=uploadFileService.get(id);
				//获取文件的真实路径
				String path=ServletActionContext.getServletContext().getRealPath(model.getFilepath());
				File file=new File(path);
				if(file.exists()){//对文件和数据库记录进行删除操作
					file.delete();
				}
				uploadFileService.delete(id);
			}
			request.put("message", "删除成功");
		}catch(Exception e){
			e.printStackTrace();
			request.put("message", "删除失败,发生未知异常");
		}
		request.put("urladdress", PropertyUtil.readUrl("control.uploadfile.list"));
		return "message";
	}
}
