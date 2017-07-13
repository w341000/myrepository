package bean;

import java.util.List;

public class PageBean {
	private List list;//本页用户数据
	private int totalrecord;//总记录数
	private int pagesize=10;//页面大小
	private int totalpage;//总页数
	private int currentpage=1;//当前页码
	private int previouspage;//上一页
	private int nextpage;//下一页
	private int[] pagebar;//页码条
	
	public List getList() {
		return list;
	}

	public void setList(List list) {
		this.list = list;
	}

	public int getTotalrecord() {
		return totalrecord;
	}

	public void setTotalrecord(int totalrecord) {
		this.totalrecord = totalrecord;
	}

	public int getPagesize() {
		return pagesize;
	}
	/**
	 * 页面大小 默认值为10
	 * @return
	 */
	public void setPagesize(int pagesize) {
		this.pagesize = pagesize;
	}

	public int getTotalpage() {
		//对总页数进行判断
		if(this.totalrecord%this.pagesize==0){
			this.totalpage=this.totalrecord/this.pagesize;
		}else{
			this.totalpage=this.totalrecord/this.pagesize +1;

		}
		
		return totalpage;
	}
	
	
	public int getCurrentpage() {
		return currentpage;
	}
	/**
	 * 当前页码,默认值为1
	 * @param currentpage
	 */
	public void setCurrentpage(int currentpage) {
		this.currentpage = currentpage;
	}

	public int getPreviouspage() {
		
		if(this.currentpage<=1)
			this.previouspage=1;
		else
			this.previouspage=this.currentpage-1;
		
		return previouspage;
	}
	
	
	public int getNextpage() {
		
		if(this.currentpage>=this.totalpage)
			this.nextpage=this.currentpage;
		else
			this.nextpage=this.currentpage+1;
		
		return nextpage;
	}


	public int[] getPagebar() {
		int[] pagebar;
		int startpage;
		int endpage;
		int totalpage=this.getTotalpage();
		//对页面页码进行判断,总页数小于10则不管当前是第几页都只显示1-10页
		if(this.totalpage<=10){
			pagebar=new int[totalpage];
			startpage=1;
			endpage=totalpage;
		}else{
			pagebar=new int[10];
			//总页数大于10则将起始页面-4,结束页面+5
			startpage=this.currentpage-4;
			endpage=this.currentpage+5;
			//如果起始页面小于1则还是固定显示1-10页
			if(startpage<1){
				startpage=1;
				endpage=10;
			}
			//结束页面大于总页数则固定显示最后10页
			if(endpage>totalpage){
				endpage=totalpage;
				startpage=endpage-9;
			}
		}
		int index=0;
		for(int i=startpage;i<=endpage;i++,index++){
			pagebar[index]=i;
		}
		this.pagebar=pagebar;
		return this.pagebar;
	}
	
}
