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
	 * �ϴ�����
	 * @return
	 */
	public String uploadUI(){
		return "uploadUI";
	}
	/**
	 *�����ϴ��ļ�
	 */
	public String upload() {
		//����û�û���ϴ��ļ�
		if(this.uploadfile==null){
			 request.put("message", "���ϴ��ļ�");
		        request.put("urladdress", PropertyUtil.readUrl("control.uploadfile.list"));
		        return "error";
		}
		if(this.uploadfile.length()>2048*1024){
			request.put("message", "�ϴ��ļ�����2M,�������ϴ�");
	        request.put("urladdress", PropertyUtil.readUrl("control.uploadfile.list"));
	        return "error";
		}
		try {//���ϴ����ļ�����У��,�ļ���Ϊ����������ȷ������½��б���
			if(WebUtil.validateFileType(uploadfile, uploadfileContentType, uploadfileFileName)){
				String path=WebUtil.saveFile(this.uploadfileFileName, "/images/upload/", this.uploadfile);
				model.setFilepath(path);
			}//�ϴ����ļ����ʹ����������ʾ
		} catch (NoSupportFileException e) {
			System.out.println(this.uploadfileContentType);
			 request.put("message", "����ʧ��,�ϴ����ļ����ʹ���");
			 request.put("urladdress", PropertyUtil.readUrl("control.uploadfile.list"));
			 return "error";
		}//������ȷ,���б������ 
        uploadFileService.save(model);
        request.put("message", "�ϴ��ļ��ɹ�");
        request.put("imagepath", model.getFilepath());
        request.put("urladdress", PropertyUtil.readUrl("control.uploadfile.list"));
        return "uploadfinish";
	}
	/**
	 * ���ϴ��ļ�����ɾ������
	 * @return
	 */
	public String delete() {
		try{
			if(fileids==null){
				request.put("message", "��ѡ��Ҫɾ�����ļ�!");
				request.put("urladdress", PropertyUtil.readUrl("control.uploadfile.list"));
				return "message";
			}
			for(String s:fileids){
				int id=Integer.parseInt(s);
				model=uploadFileService.get(id);
				//��ȡ�ļ�����ʵ·��
				String path=ServletActionContext.getServletContext().getRealPath(model.getFilepath());
				File file=new File(path);
				if(file.exists()){//���ļ������ݿ��¼����ɾ������
					file.delete();
				}
				uploadFileService.delete(id);
			}
			request.put("message", "ɾ���ɹ�");
		}catch(Exception e){
			e.printStackTrace();
			request.put("message", "ɾ��ʧ��,����δ֪�쳣");
		}
		request.put("urladdress", PropertyUtil.readUrl("control.uploadfile.list"));
		return "message";
	}
}
