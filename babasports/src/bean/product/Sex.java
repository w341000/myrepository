package bean.product;

public enum Sex {
	NONE{@Override public String getName() {return "��Ů����";}
	@Override
	public String getStrName() {
		return "NONE";
	}},
	MAN{@Override public String getName() {return "��";}
	@Override
	public String getStrName() {
		return "MAN";
	}},
	WOMEN{@Override public String getName() {return "Ů";}
	@Override
	public String getStrName() {
		return "WOMEN";
	}}; 
	/**
	 * �������
	 */
	public abstract String getName();
	public abstract String getStrName();
}