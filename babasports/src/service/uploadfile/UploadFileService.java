package service.uploadfile;

import bean.QueryResult;
import bean.product.ProductType;
import bean.uploadfile.UploadFile;
import dao.BaseDao;
public interface UploadFileService extends BaseDao<UploadFile> {

	QueryResult<UploadFile> findByPage(int pageNo, int pageSize, String orderBy);

}
