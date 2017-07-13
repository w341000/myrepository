package bean.product;

public enum Sex {
	NONE{@Override public String getName() {return "男女不限";}
	@Override
	public String getStrName() {
		return "NONE";
	}},
	MAN{@Override public String getName() {return "男";}
	@Override
	public String getStrName() {
		return "MAN";
	}},
	WOMEN{@Override public String getName() {return "女";}
	@Override
	public String getStrName() {
		return "WOMEN";
	}}; 
	/**
	 * 获得名称
	 */
	public abstract String getName();
	public abstract String getStrName();
}