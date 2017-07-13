package web.action.privilege;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import sun.reflect.annotation.AnnotationType;

/**
 * 权限配置
 */
@Retention(RetentionPolicy.RUNTIME)//可以将此注解的生命周期设定为运行时,
@Target(ElementType.METHOD)//此注解只可以使用在方法上
public @interface Permission {
	/** 模块*/
	String module();
	/** 权限值*/
	String privilege();
}
