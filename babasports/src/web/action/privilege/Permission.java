package web.action.privilege;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import sun.reflect.annotation.AnnotationType;

/**
 * Ȩ������
 */
@Retention(RetentionPolicy.RUNTIME)//���Խ���ע������������趨Ϊ����ʱ,
@Target(ElementType.METHOD)//��ע��ֻ����ʹ���ڷ�����
public @interface Permission {
	/** ģ��*/
	String module();
	/** Ȩ��ֵ*/
	String privilege();
}
