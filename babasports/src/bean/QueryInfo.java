package bean;

public class QueryInfo {
	
	private int currentpage=1;//�û���ǰ�뿴��ҳ��,�û���һ�η���ʱĬ��ֵΪ1
	private int pagesize=5; //��ס�û��뿴��ҳ���С,�û���һ�η���ʱ
	
	public int getCurrentpage() {
		return currentpage;
	}
	
	public void setCurrentpage(int currentpage) {
		this.currentpage = currentpage;
	}

	public int getPagesize() {
		return pagesize;
	}

	public void setPagesize(int pagesize) {
		this.pagesize = pagesize;
	}
	
}
