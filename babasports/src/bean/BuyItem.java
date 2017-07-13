package bean;

import bean.product.ProductInfo;
import bean.product.ProductStyle;

public class BuyItem {
	/**���������Ʒ**/	
	private ProductInfo product;
	/**���������**/
	private int amount;
	public BuyItem(){}
	public BuyItem(ProductInfo product, int amount) {
		this.product=product;
		this.amount=amount;
	}
	public BuyItem(ProductInfo product) {
		this.product=product;
	}
	public ProductInfo getProduct() {
		
		return product;
	}
	public void setProduct(ProductInfo product) {
		this.product = product;
	}
	public int getAmount() {
		return amount;
	}
	public void setAmount(int amount) {
		this.amount = amount;
	}
	@Override
	public int hashCode() {
		String buyitemid=product.hashCode()+"-";
		if(product.getStyles().size()>0)
			buyitemid=buyitemid+product.getStyles().iterator().next().getId();
		
		return buyitemid.hashCode();
	}
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		BuyItem other = (BuyItem) obj;
		if (product == null) {
			if (other.product != null)
				return false;
		} else if (!product.equals(other.product))
			return false;
		//���ݲ�Ʒ��ʽ�����Ƿ�����жϹ������Ƿ���� 
		if(product.getStyles().size()!=other.getProduct().getStyles().size())
			return false;
		if(product.getStyles().size()>0){
			ProductStyle style=product.getStyles().iterator().next();
			ProductStyle otherstyle=other.getProduct().getStyles().iterator().next();
			//���ݹ������ʽ�жϹ������Ƿ����
			if(!style.equals(otherstyle))
				return false;
		}
		return true;
	}
	
	

}
