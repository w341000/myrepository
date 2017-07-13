package utils;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Properties;
import java.util.UUID;
import java.util.regex.Pattern;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts2.ServletActionContext;

import bean.BuyCart;
import bean.user.Buyer;

import exception.NoSupportFileException;

public class WebUtil {
	private static Properties prop=new Properties();
	static{
		try {
			prop.load(WebUtil.class.getClassLoader().getResourceAsStream("utils/allowUploadFileType.properties"));
		} catch (IOException e) {
			e.printStackTrace();
			throw new RuntimeException();
		}
	}
	
	
	/**
	 * 获取购物车
	 * @param request
	 * @return
	 */
	public static BuyCart getBuyCart(HttpServletRequest request){
		return (BuyCart)request.getSession().getAttribute("buyCart");
	}
	/**
	 * 获取登录用户
	 */
	public static Buyer getBuyer(HttpServletRequest request){
		return (Buyer) request.getSession().getAttribute("user");
	}
	/**
	 * 删除购物车
	 */
	public static void deleteBuyCart(HttpServletRequest request){
		request.getSession().removeAttribute("buyCart");
	}
	
	/**对图片类型进行验证 
	 * @param uploadfile 需要被校验的文件
	 * @param contentType 文件类型
	 * @param filename 文件名 
	 * @return  文件不为空且类型正确才返回true,否则返回false
	 * @throws NoSupportFileException 当不支持的文件类型时抛出此异常
	 */
	public static boolean validateImageType(File uploadfile,String contentType,String filename) throws NoSupportFileException {
		if(uploadfile!=null&&uploadfile.length()>0){
			List<String> list=Arrays.asList("image/jpeg","image/png","image/bmp","image/pjpeg");
			List<String> allowExtension=Arrays.asList("gif","jpg","bmp","png");
			String ext=filename.substring(filename.lastIndexOf(".")+1).toLowerCase();
			if(list.contains(contentType) && allowExtension.contains(ext))
				return true;
			else
				throw new NoSupportFileException("不支持的图片类型");
		}
		return false;
	}
	
	/**
	 * 对文件类型进行验证,文件不为空且类型正确才返回true,否则返回false
	 * @throws NoSupportFileException  当不支持的文件类型时抛出此异常
	 */
	public static boolean validateFileType(File uploadfile,String contentType,String filename) throws NoSupportFileException {
		if(uploadfile!=null&&uploadfile.length()>0){
			String ext=filename.substring(filename.lastIndexOf(".")+1).toLowerCase();
			List<String> allowType=new ArrayList<String>();
			for(Object key:prop.keySet()){
				String value=(String) prop.get(key);
				String[] values=value.split(",");
				for(String s:values){
					allowType.add(s.trim());
				}
			}
			if(allowType.contains(contentType) && prop.keySet().contains(ext))
				return true;
			else
				throw new NoSupportFileException("不支持的文件类型");
		}
		return false;
	}
	
	/**保存并压缩图片文件,注意,只支持jpg,gif,png图形格式的压缩
	 * 
	 * @param imagename 需要被压缩的图片名称,方法内不会使用uuid重命名,所以调用方法前应该提前调用UUID方法重命名
	 * @param origindir 原始图片文件的保存路径
	 * @param reducedir 压缩后的图片文件的保存路径
	 * @param image 需要保存并压缩的图形文件
	 * @param width 图像宽度
	 */
	public static void saveAndReduceImage(String imagename,String origindir,String reducedir,File image,int width){
		FileOutputStream out = null;
        FileInputStream in = null;
        try {
	    		//获得原始文件真实存储位置
	    		String realoriginpathdir=ServletActionContext.getServletContext().getRealPath(origindir);
	    		//获得压缩文件真实存储路径
	    		String realreducepathdir=ServletActionContext.getServletContext().getRealPath(reducedir);
	    		File dir=new File(realoriginpathdir);
	    		//获得文件扩展名
	    		String ext=imagename.substring(imagename.lastIndexOf('.')+1);
	    		//生成原始文件目录中的文件夹
	    		if(!dir.exists())
	    			dir.mkdirs();
	    		//原始文件
	    		File originimage=new File(realoriginpathdir, imagename);
	    		//建立输出流
	            out = new FileOutputStream(originimage);
	            // 建立文件上传流,从logofile读取数据
	            in = new FileInputStream(image);
	            byte[] buffer = new byte[1024];
	            int len = 0;
	            //存储到硬盘中
	            while ((len = in.read(buffer)) > 0) {
	                out.write(buffer, 0, len);
	            }
	            File newdir=new File(realreducepathdir);
	          //生成压缩文件目录中的文件夹
	    		if(!newdir.exists())
	    			newdir.mkdirs();
	    		//压缩后的图形文件
	    		File reduceimage=new File(newdir,imagename);
	    		//生成压缩文件
	    		ImageSizer.resize(originimage, reduceimage, width, ext);
        }catch(Exception e){ 
        	throw new RuntimeException(e);
        }finally {
            close(out, in);
		}
	}
	
	/**
	 * 使用UUID算法生成文件名,并且按当前日期建立新的保存路径,
	 * @param filename 保存的文件名
	 * @param savedir 保存的文件相对应用目录,如/image/brand/,注意路径应该为相对路径
	 * @param file  保存的文件
	 * @return String 文件保存的相对服务器位置
	 * @throws FileNotFoundException
	 * @throws IOException
	 */
	public static String saveFile(String filename,String savedir,File file) {
		FileOutputStream out = null;
        FileInputStream in = null;
        try {
	    		SimpleDateFormat simple=new SimpleDateFormat("yyyy/MM/dd/HH");
	    		//生成存储位置,不存在就创建
	    		String pathdir=savedir+simple.format(new Date());
	    		//获得文件真实存储位置
	    		String reallogopathdir=ServletActionContext.getServletContext().getRealPath(pathdir);
	    		File dir=new File(reallogopathdir);
	    		//生成目录中的文件夹
	    		if(!dir.exists())
	    			dir.mkdirs();
	    		//生成文件名
	    		String upfilename=UUID.randomUUID().toString()+filename.substring(filename.lastIndexOf('.'));
	    		//建立输出流
	            out = new FileOutputStream(new File(reallogopathdir, upfilename));
	            // 建立文件上传流,从logofile读取数据
	            in = new FileInputStream(file);
	            byte[] buffer = new byte[1024];
	            int len = 0;
	            //存储到硬盘中
	            while ((len = in.read(buffer)) > 0) {
	                out.write(buffer, 0, len);
	            }
	            return pathdir+"/"+upfilename;
        }catch(Exception e){ 
        	throw new RuntimeException(e);
        }finally {
            close(out, in);
		}
	}
	
	/**
	 * 将uuid文件名的文件保存到指定路径中,注意路径应该为相对路径
	 * @param filename 保存的UUID文件名
	 * @param savedir 保存的文件相对应用目录,如/image/brand/
	 * @param file  保存的文件
	 * @throws FileNotFoundException
	 * @throws IOException
	 */
	public static void saveUUIDFile(String filename,String savedir,File file) {
		FileOutputStream out = null;
        FileInputStream in = null;
        try {
	    		String pathdir=savedir;
	    		//获得文件真实存储位置
	    		String reallogopathdir=ServletActionContext.getServletContext().getRealPath(pathdir);
	    		File dir=new File(reallogopathdir);
	    		//生成目录中的文件夹
	    		if(!dir.exists())
	    			dir.mkdirs();
	    		//生成文件名
	    		String upfilename=filename;
	    		//建立输出流
	            out = new FileOutputStream(new File(reallogopathdir, upfilename));
	            // 建立文件上传流,从logofile读取数据
	            in = new FileInputStream(file);
	            byte[] buffer = new byte[1024];
	            int len = 0;
	            //存储到硬盘中
	            while ((len = in.read(buffer)) > 0) {
	                out.write(buffer, 0, len);
	            }
        }catch(Exception e){ 
        	throw new RuntimeException(e);
        }finally {
            close(out, in);
		}
	}
	
	/**
	 * 使用uuid算法将文件名生成uuid文件名
	 * @param filename 需要被生成的文件名
	 * @return uuid文件名
	 */
	
	public static String generateUUIDFileName(String filename){
		//生成文件名
		return UUID.randomUUID().toString()+filename.substring(filename.lastIndexOf('.'));
	}
private static void close(FileOutputStream fos, FileInputStream fis) {
    if (fis != null) {
        try {
            fis.close();
        } catch (IOException e) {
            System.out.println("FileInputStream关闭失败");
            e.printStackTrace();
        }
    }
    if (fos != null) {
        try {
            fos.close();
        } catch (IOException e) {
            System.out.println("FileOutputStream关闭失败");
            e.printStackTrace();
        }
    }
}





/***
 * 获取URI的路径,如路径为http://www.babasport.com/action/post.htm?method=add, 得到的值为"/action/post.htm"
 * @param request
 * @return
 */
public static String getRequestURI(HttpServletRequest request){     
    return request.getRequestURI();
}
/**
 * 获取完整请求路径(含内容路径及请求参数)
 * @param request
 * @return
 */
public static String getRequestURIWithParam(HttpServletRequest request){     
    return getRequestURI(request) + (request.getQueryString() == null ? "" : "?"+ request.getQueryString());
}
/**
 * 添加cookie
 * @param response
 * @param name cookie的名称
 * @param value cookie的值
 * @param maxAge cookie存放的时间(以秒为单位,假如存放三天,即3*24*60*60; 如果值为0,cookie将随浏览器关闭而清除)
 */
public static void addCookie(HttpServletResponse response, String name, String value, int maxAge) {        
    Cookie cookie = new Cookie(name, value);
    cookie.setPath("/");
    if (maxAge>0) cookie.setMaxAge(maxAge);
    response.addCookie(cookie);
}

/**
 * 获取cookie的值
 * @param request
 * @param name cookie的名称
 * @return
 */
public static String getCookieByName(HttpServletRequest request, String name) {
	Map<String, Cookie> cookieMap = WebUtil.readCookieMap(request);
    if(cookieMap.containsKey(name)){
        Cookie cookie = (Cookie)cookieMap.get(name);
        return cookie.getValue();
    }else{
        return null;
    }
}

protected static Map<String, Cookie> readCookieMap(HttpServletRequest request) {
    Map<String, Cookie> cookieMap = new HashMap<String, Cookie>();
    Cookie[] cookies = request.getCookies();
    if (null != cookies) {
        for (int i = 0; i < cookies.length; i++) {
            cookieMap.put(cookies[i].getName(), cookies[i]);
        }
    }
    return cookieMap;
}
/**
 * 忽略前导空白和后导空白去除html代码,注意任何html代码会被替换为一个空格,而javascript代码和css的style代码会被替换成1个空格
 * 例:    &lt;font&gt;name &lt;/font&gt;                   foo  <br> &lt;style&gt; //code  &lt;/style&gt;    <br>&lt;javascript&gt;//code  &lt;/javascript&gt;  <br> 
 * 替换后:name foo 
 * @param inputString
 * @return
 */
public static String HtmltoText(String inputString) {
    String htmlStr = inputString; //含html标签的字符串
    String textStr ="";
    java.util.regex.Pattern p_script;
    java.util.regex.Matcher m_script;
    java.util.regex.Pattern p_style;
    java.util.regex.Matcher m_style;
    java.util.regex.Pattern p_html;
    java.util.regex.Matcher m_html;          
    java.util.regex.Pattern p_ba;
    java.util.regex.Matcher m_ba;
    
    try {
        String regEx_script = "<[\\s]*?script[^>]*?>[\\s\\S]*?<[\\s]*?\\/[\\s]*?script[\\s]*?>"; //定义script的正则表达式{或<script[^>]*?>[\\s\\S]*?<\\/script> }
        String regEx_style = "<[\\s]*?style[^>]*?>[\\s\\S]*?<[\\s]*?\\/[\\s]*?style[\\s]*?>"; //定义style的正则表达式{或<style[^>]*?>[\\s\\S]*?<\\/style> }
        String regEx_html = "<[^>]+>"; //定义HTML标签的正则表达式
        String patternStr = "\\s+";
        
        p_script = Pattern.compile(regEx_script,Pattern.CASE_INSENSITIVE);
        m_script = p_script.matcher(htmlStr);
        htmlStr = m_script.replaceAll(" "); //过滤script标签

        p_style = Pattern.compile(regEx_style,Pattern.CASE_INSENSITIVE);
        m_style = p_style.matcher(htmlStr);
        htmlStr = m_style.replaceAll(" "); //过滤style标签
     
        p_html = Pattern.compile(regEx_html,Pattern.CASE_INSENSITIVE);
        m_html = p_html.matcher(htmlStr);
        htmlStr = m_html.replaceAll(" "); //过滤html标签
        
        p_ba = Pattern.compile(patternStr,Pattern.CASE_INSENSITIVE);
        m_ba = p_ba.matcher(htmlStr);
        htmlStr = m_ba.replaceAll(" "); //过滤空格
     
     textStr = htmlStr.trim();
     
    }catch(Exception e) {
                System.err.println("Html2Text: " + e.getMessage());
    }          
    return textStr;//返回文本字符串
 }
	
}
