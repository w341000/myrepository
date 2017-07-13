package web.action.shopping;

import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpSession;
import javax.servlet.http.HttpSessionEvent;
import javax.servlet.http.HttpSessionListener;

/**
 * ��session���м����ļ�����
 */
public class SiteSessionListener implements HttpSessionListener {
	//��session��id����session�ļ���
	private static Map<String,HttpSession> sessions=new HashMap<String, HttpSession>();

	@Override
	public void sessionCreated(HttpSessionEvent se) {
		sessions.put(se.getSession().getId(), se.getSession());
	}

	@Override
	public void sessionDestroyed(HttpSessionEvent se) {
		sessions.remove(se.getSession().getId());

	}
	/**
	 * ͨ��sessionid��ȡsession
	 * @param sessionID
	 */
	public static HttpSession getSession(String sessionID){
		return sessions.get(sessionID);
	}

	/**
	 * �Ƴ���session������
	 * @param sessionID
	 */
	public static void removeSession(String sessionID){
		if(sessions.containsKey(sessionID)) sessions.remove(sessionID);
	}
}
