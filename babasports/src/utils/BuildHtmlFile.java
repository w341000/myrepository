package utils;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.io.Writer;
import java.util.Properties;

import org.apache.struts2.ServletActionContext;
import org.apache.velocity.Template;
import org.apache.velocity.VelocityContext;
import org.apache.velocity.app.Velocity;
import bean.product.ProductInfo;

public class BuildHtmlFile {
	static {
		
			Properties prop = new Properties();
			prop.put("runtime.log", ServletActionContext.getServletContext().getRealPath("/WEB-INF/log/velocity.log"));
			prop.put("file.resource.loader.path",ServletActionContext.getServletContext().getRealPath("/WEB-INF/vm"));
			prop.put("input.encoding", "UTF-8");
			prop.put("output.encoding", "UTF-8");
			Velocity.init(prop);
		
	}

	/**
	 * ������Ʒhtmlҳ��
	 * @param product
	 * @param saveDir
	 */
	public static void createProductHtml(ProductInfo product, File saveDir) {
		try {
			if (!saveDir.exists())
				saveDir.mkdirs();
			VelocityContext context = new VelocityContext();
			context.put("product", product);
			// ��velocity�����ֵΪ�ջ�ԭ�����,��$��Ӹ�̾����$!{product.note} ,��ֵΪ��ʱ�Ͳ������
			Template template = Velocity
					.getTemplate("product/productview.html");
			BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(
					new FileOutputStream(new File(saveDir, product.getId()
							+ ".shtml")), "UTF-8"));

			template.merge(context, bw);
			bw.close();
		} catch (IOException e) {
			e.printStackTrace();
		}

	}
	
	/**
	 * ����ģ��html��Ϣ
	 * @param templateName ģ���ļ�Ŀ¼(�����velocity��file.resource.loader.pathĿ¼)
	 * @param writer Ҫд�����ݵ���,�˷�������ر���
	 * @param keys ��Ҫ�����浽�����ĵ�key
	 * @param values ��Ҫ�����浽�����ĵ�value
	 */
	public static void createHtml(String templateName,Writer writer,String[] keys,Object[] values){
		VelocityContext context = new VelocityContext();
		if(keys!=null && values!=null && keys.length==values.length){
			for(int i=0;i<keys.length;i++){
				context.put(keys[i], values[i]);
			}
		}
		Template template = Velocity
				.getTemplate(templateName);
		template.merge(context, writer);
	}

}
