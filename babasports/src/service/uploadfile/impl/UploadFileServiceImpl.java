package service.uploadfile.impl;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import bean.QueryResult;
import bean.uploadfile.UploadFile;
import service.uploadfile.UploadFileService;
import dao.impl.BaseDaoImpl;
@Service("uploadFileService") @Transactional
public class UploadFileServiceImpl extends BaseDaoImpl<UploadFile> implements UploadFileService {
	
	@Override
	public QueryResult<UploadFile> findByPage(int pageNo,
			int pageSize,String orderBy) {
		String hql="from UploadFile " +orderBy;
		String totalHql="select count(*) from UploadFile";
		return super.findByPage(hql,totalHql, pageNo, pageSize);
	}
}
