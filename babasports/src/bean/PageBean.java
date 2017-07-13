package bean;

import java.util.List;

public class PageBean {
	private List list;//��ҳ�û�����
	private int totalrecord;//�ܼ�¼��
	private int pagesize=10;//ҳ���С
	private int totalpage;//��ҳ��
	private int currentpage=1;//��ǰҳ��
	private int previouspage;//��һҳ
	private int nextpage;//��һҳ
	private int[] pagebar;//ҳ����
	
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
	 * ҳ���С Ĭ��ֵΪ10
	 * @return
	 */
	public void setPagesize(int pagesize) {
		this.pagesize = pagesize;
	}

	public int getTotalpage() {
		//����ҳ�������ж�
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
	 * ��ǰҳ��,Ĭ��ֵΪ1
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
		//��ҳ��ҳ������ж�,��ҳ��С��10�򲻹ܵ�ǰ�ǵڼ�ҳ��ֻ��ʾ1-10ҳ
		if(this.totalpage<=10){
			pagebar=new int[totalpage];
			startpage=1;
			endpage=totalpage;
		}else{
			pagebar=new int[10];
			//��ҳ������10����ʼҳ��-4,����ҳ��+5
			startpage=this.currentpage-4;
			endpage=this.currentpage+5;
			//�����ʼҳ��С��1���ǹ̶���ʾ1-10ҳ
			if(startpage<1){
				startpage=1;
				endpage=10;
			}
			//����ҳ�������ҳ����̶���ʾ���10ҳ
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
