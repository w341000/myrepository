package bos.utils;

import java.util.List;
import org.hibernate.criterion.DetachedCriteria;


public class PageBean {

	private List rows;//当前页展示的数据集合
	private int pageSize;//页面大小
	private int total;//总记录数
	private int currentPage;//当前页码
	private DetachedCriteria detachedCriteria; //离线查询对象,包装了查询条件
	public List getRows() {
		return rows;
	}
	public void setRows(List rows) {
		this.rows = rows;
	}
	public int getPageSize() {
		return pageSize;
	}
	public void setPageSize(int pageSize) {
		this.pageSize = pageSize;
	}
	public int getTotal() {
		return total;
	}
	public void setTotal(int total) {
		this.total = total;
	}
	public int getCurrentPage() {
		return currentPage;
	}
	public void setCurrentPage(int currentPage) {
		this.currentPage = currentPage;
	}
	public DetachedCriteria getDetachedCriteria() {
		return detachedCriteria;
	}
	public void setDetachedCriteria(DetachedCriteria detachedCriteria) {
		this.detachedCriteria = detachedCriteria;
	}
	
	
}
