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
	 * 构建产品html页面
	 * @param product
	 * @param saveDir
	 */
	public static void createProductHtml(ProductInfo product, File saveDir) {
		try {
			if (!saveDir.exists())
				saveDir.mkdirs();
			VelocityContext context = new VelocityContext();
			context.put("product", product);
			// 在velocity中如果值为空会原样输出,在$后加感叹号如$!{product.note} ,当值为空时就不会输出
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
	 * 生成模板html信息
	 * @param templateName 模板文件目录(相对于velocity的file.resource.loader.path目录)
	 * @param writer 要写入数据的流,此方法不会关闭流
	 * @param keys 需要被保存到上下文的key
	 * @param values 需要被保存到上下文的value
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
