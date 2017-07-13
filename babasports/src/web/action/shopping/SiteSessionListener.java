package web.action.shopping;

import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpSession;
import javax.servlet.http.HttpSessionEvent;
import javax.servlet.http.HttpSessionListener;

/**
 * 对session进行监听的监听器
 */
public class SiteSessionListener implements HttpSessionListener {
	//以session的id保存session的集合
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
	 * 通过sessionid获取session
	 * @param sessionID
	 */
	public static HttpSession getSession(String sessionID){
		return sessions.get(sessionID);
	}

	/**
	 * 移除对session的引用
	 * @param sessionID
	 */
	public static void removeSession(String sessionID){
		if(sessions.containsKey(sessionID)) sessions.remove(sessionID);
	}
}
