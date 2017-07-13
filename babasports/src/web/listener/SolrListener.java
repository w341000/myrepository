package web.listener;

import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Date;
import java.util.Timer;
import java.util.TimerTask;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;

import utils.PropertyUtil;

public class SolrListener implements ServletContextListener{
	private Timer full;
	private Timer delta;

	public Boolean runHttpGet(String strUrl) throws Exception {
		Boolean flag = false;
			// ����һ��URL����
			URL url = new URL(strUrl);
			// ��һ��HttpURLConnection����
			HttpURLConnection urlConn = (HttpURLConnection) url
					.openConnection();
			// �������ӳ�ʱ��ʱ��
			urlConn.setDoOutput(true);
			// ��ʹ��post�����ʱ�����ò���ʹ�û���
			urlConn.setUseCaches(false);
			// ���ø�����Ϊget����
			urlConn.setRequestMethod("GET");
			urlConn.setInstanceFollowRedirects(true);
			// ��������content-type
			urlConn.setRequestProperty("Content-Type",
					"application/json, text/javascript");
			// ִ�����Ӳ���
			urlConn.connect();
			if (urlConn.getResponseCode() == 200) {
				flag = true;
			}
		return flag;
	}
	
	
	public Timer fullImport() throws Exception{
		String server=PropertyUtil.readSolrConf("server");
		String port=PropertyUtil.readSolrConf("port");
		String webapp=PropertyUtil.readSolrConf("webapp");
		String core=PropertyUtil.readSolrConf("core");
		String reBuildIndexParams=PropertyUtil.readSolrConf("reBuildIndexParams");
		String reBuildIndexInterval=PropertyUtil.readSolrConf("reBuildIndexInterval");
		
		// ���������·��
		final String strUrl = "http://"+server+":"+port+"/"+webapp+"/"+core+reBuildIndexParams;
		
		Timer timer=new Timer();
		//������������300��ִ��һ��ȫ������,Ȼ��properties�ļ��м��ִ��
		Date date=new Date(new Date().getTime()+300000);
		timer.scheduleAtFixedRate(new TimerTask() {
			@Override
			public void run() {
				try {
					if(runHttpGet(strUrl));
					System.out.println("info"+new Date().toLocaleString() +"���µ��벢��������");
				} catch (Exception e) {
					e.printStackTrace();
					throw new RuntimeException(e);
				}
			}
		}, date, Long.parseLong(reBuildIndexInterval.trim())*60*1000);
		return timer;
		
		
	}
	
	
	public Timer deltaImport() throws Exception{
		String server=PropertyUtil.readSolrConf("server");
		String port=PropertyUtil.readSolrConf("port");
		String webapp=PropertyUtil.readSolrConf("webapp");
		String core=PropertyUtil.readSolrConf("core");
		String params=PropertyUtil.readSolrConf("params");
		String interval=PropertyUtil.readSolrConf("interval");
		
		// ���������·��
		final String strUrl = "http://"+server+":"+port+"/"+webapp+"/"+core+params;
		
		Timer timer=new Timer();
		//������������120��ִ��һ��ȫ������,Ȼ��properties�ļ��м��ִ��
		Date date=new Date(new Date().getTime()+120000);
		
		timer.schedule(new TimerTask() {
			@Override
			public void run() {
				try {
					if(runHttpGet(strUrl));
						System.out.println("info:"+new Date().toLocaleString() +"�����������!");
				} catch (Exception e) {
					e.printStackTrace();
					throw new RuntimeException(e);
				}
			}
		},date, Long.parseLong(interval.trim())*60*1000);
		return timer;
	}

	

	@Override
	public void contextDestroyed(ServletContextEvent sce) {
		full.cancel();
		delta.cancel();
		
	}


	@Override
	public void contextInitialized(ServletContextEvent sce) {
		try {
			full=fullImport();
			delta=deltaImport();
		} catch (Exception e) {
			e.printStackTrace();
			throw new RuntimeException(e);
		}
	}
}
