package service.aop;

import java.io.DataOutputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Date;
import java.util.Timer;
import java.util.TimerTask;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.stereotype.Component;

import service.product.BrandService;
import service.product.ProductInfoService;
import service.product.ProductTypeService;
import service.product.impl.ProductTypeServiceImpl;
import utils.PropertyUtil;

import bean.product.ProductStyle;
import bean.product.ProductType;
//
//@Aspect
//@Component
public class SolrInterceptor {

	@SuppressWarnings("unused")
	@Pointcut("execution(* service.product..*.set*(..))  || execution(* service.product..*.save*(..)) || execution(* service.product..*.update*(..))"
			+ " || execution(* dao..*.set*(..))  || execution(* dao..*.save*(..)) || execution(* dao..*.update*(..))")
	private void point() {}

	@Around("point()")
	// 环绕通知
	public Object doBasicProfiling(ProceedingJoinPoint pjp) throws Throwable {
		Object obj=pjp.proceed();
		if(pjp.getTarget() instanceof ProductTypeService){
			deltaImport("type");
		}else if(pjp.getTarget() instanceof ProductStyle){
			deltaImport("style");
		}else if(pjp.getTarget() instanceof ProductInfoService){
			deltaImport("product");
		}else if(pjp.getTarget() instanceof BrandService){
			deltaImport("brand");
		}
		return obj;
	}

	private void deltaImport(String entity) throws Exception {
		String server=PropertyUtil.readSolrConf("server");
		String port=PropertyUtil.readSolrConf("port");
		String webapp=PropertyUtil.readSolrConf("webapp");
		String core=PropertyUtil.readSolrConf("core");
		String params=PropertyUtil.readSolrConf("params");
		// 设置请求的路径
		String strUrl = "http://"+server.trim()+":"+port.trim()+"/"+webapp.trim()+"/"+core.trim()+params.trim()+"&entity="+entity;
		runHttpGet(strUrl);
	}
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
				// 显示
				InputStreamReader isr = new InputStreamReader(
						urlConn.getInputStream(), "utf-8");
				int i;
				char[] buf=new char[1024];
				StringBuffer sb = new StringBuffer();
				while ((i = isr.read(buf,0,buf.length)) != -1) {
					sb = sb.append(buf,0,i);
				}
				System.out.println(sb.toString());
				isr.close();
			}
		return flag;
	}
}
