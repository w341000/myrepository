package bean;

public class QueryInfo {
	
	private int currentpage=1;//用户当前想看的页码,用户第一次访问时默认值为1
	private int pagesize=5; //记住用户想看的页面大小,用户第一次访问时
	
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
