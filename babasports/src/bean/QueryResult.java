package bean;

import java.util.List;

public class QueryResult<T> {
	
	private List<T> list;//记住用户看的页的数据
	private int totalrecord; //记住总记录数
	
	/**
	 * 获取所有数据
	 * @return
	 */
	public List<T> getList() {
		return list;
	}
	
	public void setList(List<T> list) {
		this.list = list;
	}
	
	/**
	 * 获取总记录数
	 * @return
	 */
	public int getTotalrecord() {
		return totalrecord;
	}
	
	public void setTotalrecord(int totalrecord) {
		this.totalrecord = totalrecord;
	}
		
}
