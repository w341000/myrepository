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
			// 创建一个URL对象
			URL url = new URL(strUrl);
			// 打开一个HttpURLConnection连接
			HttpURLConnection urlConn = (HttpURLConnection) url
					.openConnection();
			// 设置连接超时的时间
			urlConn.setDoOutput(true);
			// 在使用post请求的时候，设置不能使用缓存
			urlConn.setUseCaches(false);
			// 设置该请求为get请求
			urlConn.setRequestMethod("GET");
			urlConn.setInstanceFollowRedirects(true);
			// 配置请求content-type
			urlConn.setRequestProperty("Content-Type",
					"application/json, text/javascript");
			// 执行连接操作
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
		
		// 设置请求的路径
		final String strUrl = "http://"+server+":"+port+"/"+webapp+"/"+core+reBuildIndexParams;
		
		Timer timer=new Timer();
		//服务器启动后300秒执行一次全量索引,然后按properties文件中间隔执行
		Date date=new Date(new Date().getTime()+300000);
		timer.scheduleAtFixedRate(new TimerTask() {
			@Override
			public void run() {
				try {
					if(runHttpGet(strUrl));
					System.out.println("info"+new Date().toLocaleString() +"重新导入并构建索引");
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
		
		// 设置请求的路径
		final String strUrl = "http://"+server+":"+port+"/"+webapp+"/"+core+params;
		
		Timer timer=new Timer();
		//服务器启动后120秒执行一次全量索引,然后按properties文件中间隔执行
		Date date=new Date(new Date().getTime()+120000);
		
		timer.schedule(new TimerTask() {
			@Override
			public void run() {
				try {
					if(runHttpGet(strUrl));
						System.out.println("info:"+new Date().toLocaleString() +"完成增量索引!");
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
