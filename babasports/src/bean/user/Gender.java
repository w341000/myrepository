package bean.user;

/**
 * �Ա�
 */
public enum Gender {
	MAN {public String getName() {return "��";}},
	WOMEN {public String getName() {return "Ů";}};
	public abstract  String getName();
}
