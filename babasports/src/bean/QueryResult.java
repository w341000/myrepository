package bean;

import java.util.List;

public class QueryResult<T> {
	
	private List<T> list;//��ס�û�����ҳ������
	private int totalrecord; //��ס�ܼ�¼��
	
	/**
	 * ��ȡ��������
	 * @return
	 */
	public List<T> getList() {
		return list;
	}
	
	public void setList(List<T> list) {
		this.list = list;
	}
	
	/**
	 * ��ȡ�ܼ�¼��
	 * @return
	 */
	public int getTotalrecord() {
		return totalrecord;
	}
	
	public void setTotalrecord(int totalrecord) {
		this.totalrecord = totalrecord;
	}
		
}
