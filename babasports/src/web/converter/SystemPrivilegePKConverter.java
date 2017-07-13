package web.converter;

import java.lang.reflect.Member;
import java.util.Map;

import bean.privilege.SystemPrivilegePK;

import com.opensymphony.xwork2.conversion.impl.DefaultTypeConverter;

public class SystemPrivilegePKConverter extends DefaultTypeConverter {
	@Override  
    public Object convertValue(Map<String, Object> context, Object value, Class toType) {  
        try {  
            if(toType==SystemPrivilegePK.class){  
                String[] params=(String[])value;
                	String[] arr=params[0].split(",");
                	if(arr.length==2){
                		return new SystemPrivilegePK(arr[0],arr[1]);
                }
            } else if(toType==SystemPrivilegePK[].class){
            	String[] params=(String[])value;
            	SystemPrivilegePK[] privileges=new SystemPrivilegePK[params.length];
            	for(int i=0;i<params.length;i++){
            		String[] arr=params[i].split(",");
            		if(arr!=null && arr.length==2)
            			privileges[i]=new SystemPrivilegePK(arr[0],arr[1]);
            	}
            	return privileges;
            }else if(toType==String.class){  
            	SystemPrivilegePK id=(SystemPrivilegePK)value;
                return id.getModule()+","+id.getPrivilege();  
            }  
        } catch (Exception e) {  
            e.printStackTrace();  
            throw new RuntimeException(e);
        }  
        return null;  
    }

	
}
