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
	 * ��ȡ���ﳵ
	 * @param request
	 * @return
	 */
	public static BuyCart getBuyCart(HttpServletRequest request){
		return (BuyCart)request.getSession().getAttribute("buyCart");
	}
	/**
	 * ��ȡ��¼�û�
	 */
	public static Buyer getBuyer(HttpServletRequest request){
		return (Buyer) request.getSession().getAttribute("user");
	}
	/**
	 * ɾ�����ﳵ
	 */
	public static void deleteBuyCart(HttpServletRequest request){
		request.getSession().removeAttribute("buyCart");
	}
	
	/**��ͼƬ���ͽ�����֤ 
	 * @param uploadfile ��Ҫ��У����ļ�
	 * @param contentType �ļ�����
	 * @param filename �ļ��� 
	 * @return  �ļ���Ϊ����������ȷ�ŷ���true,���򷵻�false
	 * @throws NoSupportFileException ����֧�ֵ��ļ�����ʱ�׳����쳣
	 */
	public static boolean validateImageType(File uploadfile,String contentType,String filename) throws NoSupportFileException {
		if(uploadfile!=null&&uploadfile.length()>0){
			List<String> list=Arrays.asList("image/jpeg","image/png","image/bmp","image/pjpeg");
			List<String> allowExtension=Arrays.asList("gif","jpg","bmp","png");
			String ext=filename.substring(filename.lastIndexOf(".")+1).toLowerCase();
			if(list.contains(contentType) && allowExtension.contains(ext))
				return true;
			else
				throw new NoSupportFileException("��֧�ֵ�ͼƬ����");
		}
		return false;
	}
	
	/**
	 * ���ļ����ͽ�����֤,�ļ���Ϊ����������ȷ�ŷ���true,���򷵻�false
	 * @throws NoSupportFileException  ����֧�ֵ��ļ�����ʱ�׳����쳣
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
				throw new NoSupportFileException("��֧�ֵ��ļ�����");
		}
		return false;
	}
	
	/**���沢ѹ��ͼƬ�ļ�,ע��,ֻ֧��jpg,gif,pngͼ�θ�ʽ��ѹ��
	 * 
	 * @param imagename ��Ҫ��ѹ����ͼƬ����,�����ڲ���ʹ��uuid������,���Ե��÷���ǰӦ����ǰ����UUID����������
	 * @param origindir ԭʼͼƬ�ļ��ı���·��
	 * @param reducedir ѹ�����ͼƬ�ļ��ı���·��
	 * @param image ��Ҫ���沢ѹ����ͼ���ļ�
	 * @param width ͼ����
	 */
	public static void saveAndReduceImage(String imagename,String origindir,String reducedir,File image,int width){
		FileOutputStream out = null;
        FileInputStream in = null;
        try {
	    		//���ԭʼ�ļ���ʵ�洢λ��
	    		String realoriginpathdir=ServletActionContext.getServletContext().getRealPath(origindir);
	    		//���ѹ���ļ���ʵ�洢·��
	    		String realreducepathdir=ServletActionContext.getServletContext().getRealPath(reducedir);
	    		File dir=new File(realoriginpathdir);
	    		//����ļ���չ��
	    		String ext=imagename.substring(imagename.lastIndexOf('.')+1);
	    		//����ԭʼ�ļ�Ŀ¼�е��ļ���
	    		if(!dir.exists())
	    			dir.mkdirs();
	    		//ԭʼ�ļ�
	    		File originimage=new File(realoriginpathdir, imagename);
	    		//���������
	            out = new FileOutputStream(originimage);
	            // �����ļ��ϴ���,��logofile��ȡ����
	            in = new FileInputStream(image);
	            byte[] buffer = new byte[1024];
	            int len = 0;
	            //�洢��Ӳ����
	            while ((len = in.read(buffer)) > 0) {
	                out.write(buffer, 0, len);
	            }
	            File newdir=new File(realreducepathdir);
	          //����ѹ���ļ�Ŀ¼�е��ļ���
	    		if(!newdir.exists())
	    			newdir.mkdirs();
	    		//ѹ�����ͼ���ļ�
	    		File reduceimage=new File(newdir,imagename);
	    		//����ѹ���ļ�
	    		ImageSizer.resize(originimage, reduceimage, width, ext);
        }catch(Exception e){ 
        	throw new RuntimeException(e);
        }finally {
            close(out, in);
		}
	}
	
	/**
	 * ʹ��UUID�㷨�����ļ���,���Ұ���ǰ���ڽ����µı���·��,
	 * @param filename ������ļ���
	 * @param savedir ������ļ����Ӧ��Ŀ¼,��/image/brand/,ע��·��Ӧ��Ϊ���·��
	 * @param file  ������ļ�
	 * @return String �ļ��������Է�����λ��
	 * @throws FileNotFoundException
	 * @throws IOException
	 */
	public static String saveFile(String filename,String savedir,File file) {
		FileOutputStream out = null;
        FileInputStream in = null;
        try {
	    		SimpleDateFormat simple=new SimpleDateFormat("yyyy/MM/dd/HH");
	    		//���ɴ洢λ��,�����ھʹ���
	    		String pathdir=savedir+simple.format(new Date());
	    		//����ļ���ʵ�洢λ��
	    		String reallogopathdir=ServletActionContext.getServletContext().getRealPath(pathdir);
	    		File dir=new File(reallogopathdir);
	    		//����Ŀ¼�е��ļ���
	    		if(!dir.exists())
	    			dir.mkdirs();
	    		//�����ļ���
	    		String upfilename=UUID.randomUUID().toString()+filename.substring(filename.lastIndexOf('.'));
	    		//���������
	            out = new FileOutputStream(new File(reallogopathdir, upfilename));
	            // �����ļ��ϴ���,��logofile��ȡ����
	            in = new FileInputStream(file);
	            byte[] buffer = new byte[1024];
	            int len = 0;
	            //�洢��Ӳ����
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
	 * ��uuid�ļ������ļ����浽ָ��·����,ע��·��Ӧ��Ϊ���·��
	 * @param filename �����UUID�ļ���
	 * @param savedir ������ļ����Ӧ��Ŀ¼,��/image/brand/
	 * @param file  ������ļ�
	 * @throws FileNotFoundException
	 * @throws IOException
	 */
	public static void saveUUIDFile(String filename,String savedir,File file) {
		FileOutputStream out = null;
        FileInputStream in = null;
        try {
	    		String pathdir=savedir;
	    		//����ļ���ʵ�洢λ��
	    		String reallogopathdir=ServletActionContext.getServletContext().getRealPath(pathdir);
	    		File dir=new File(reallogopathdir);
	    		//����Ŀ¼�е��ļ���
	    		if(!dir.exists())
	    			dir.mkdirs();
	    		//�����ļ���
	    		String upfilename=filename;
	    		//���������
	            out = new FileOutputStream(new File(reallogopathdir, upfilename));
	            // �����ļ��ϴ���,��logofile��ȡ����
	            in = new FileInputStream(file);
	            byte[] buffer = new byte[1024];
	            int len = 0;
	            //�洢��Ӳ����
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
	 * ʹ��uuid�㷨���ļ�������uuid�ļ���
	 * @param filename ��Ҫ�����ɵ��ļ���
	 * @return uuid�ļ���
	 */
	
	public static String generateUUIDFileName(String filename){
		//�����ļ���
		return UUID.randomUUID().toString()+filename.substring(filename.lastIndexOf('.'));
	}
private static void close(FileOutputStream fos, FileInputStream fis) {
    if (fis != null) {
        try {
            fis.close();
        } catch (IOException e) {
            System.out.println("FileInputStream�ر�ʧ��");
            e.printStackTrace();
        }
    }
    if (fos != null) {
        try {
            fos.close();
        } catch (IOException e) {
            System.out.println("FileOutputStream�ر�ʧ��");
            e.printStackTrace();
        }
    }
}





/***
 * ��ȡURI��·��,��·��Ϊhttp://www.babasport.com/action/post.htm?method=add, �õ���ֵΪ"/action/post.htm"
 * @param request
 * @return
 */
public static String getRequestURI(HttpServletRequest request){     
    return request.getRequestURI();
}
/**
 * ��ȡ��������·��(������·�����������)
 * @param request
 * @return
 */
public static String getRequestURIWithParam(HttpServletRequest request){     
    return getRequestURI(request) + (request.getQueryString() == null ? "" : "?"+ request.getQueryString());
}
/**
 * ���cookie
 * @param response
 * @param name cookie������
 * @param value cookie��ֵ
 * @param maxAge cookie��ŵ�ʱ��(����Ϊ��λ,����������,��3*24*60*60; ���ֵΪ0,cookie����������رն����)
 */
public static void addCookie(HttpServletResponse response, String name, String value, int maxAge) {        
    Cookie cookie = new Cookie(name, value);
    cookie.setPath("/");
    if (maxAge>0) cookie.setMaxAge(maxAge);
    response.addCookie(cookie);
}

/**
 * ��ȡcookie��ֵ
 * @param request
 * @param name cookie������
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
 * ����ǰ���հ׺ͺ󵼿հ�ȥ��html����,ע���κ�html����ᱻ�滻Ϊһ���ո�,��javascript�����css��style����ᱻ�滻��1���ո�
 * ��:    &lt;font&gt;name &lt;/font&gt;                   foo  <br> &lt;style&gt; //code  &lt;/style&gt;    <br>&lt;javascript&gt;//code  &lt;/javascript&gt;  <br> 
 * �滻��:name foo 
 * @param inputString
 * @return
 */
public static String HtmltoText(String inputString) {
    String htmlStr = inputString; //��html��ǩ���ַ���
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
        String regEx_script = "<[\\s]*?script[^>]*?>[\\s\\S]*?<[\\s]*?\\/[\\s]*?script[\\s]*?>"; //����script��������ʽ{��<script[^>]*?>[\\s\\S]*?<\\/script> }
        String regEx_style = "<[\\s]*?style[^>]*?>[\\s\\S]*?<[\\s]*?\\/[\\s]*?style[\\s]*?>"; //����style��������ʽ{��<style[^>]*?>[\\s\\S]*?<\\/style> }
        String regEx_html = "<[^>]+>"; //����HTML��ǩ��������ʽ
        String patternStr = "\\s+";
        
        p_script = Pattern.compile(regEx_script,Pattern.CASE_INSENSITIVE);
        m_script = p_script.matcher(htmlStr);
        htmlStr = m_script.replaceAll(" "); //����script��ǩ

        p_style = Pattern.compile(regEx_style,Pattern.CASE_INSENSITIVE);
        m_style = p_style.matcher(htmlStr);
        htmlStr = m_style.replaceAll(" "); //����style��ǩ
     
        p_html = Pattern.compile(regEx_html,Pattern.CASE_INSENSITIVE);
        m_html = p_html.matcher(htmlStr);
        htmlStr = m_html.replaceAll(" "); //����html��ǩ
        
        p_ba = Pattern.compile(patternStr,Pattern.CASE_INSENSITIVE);
        m_ba = p_ba.matcher(htmlStr);
        htmlStr = m_ba.replaceAll(" "); //���˿ո�
     
     textStr = htmlStr.trim();
     
    }catch(Exception e) {
                System.err.println("Html2Text: " + e.getMessage());
    }          
    return textStr;//�����ı��ַ���
 }
	
}
