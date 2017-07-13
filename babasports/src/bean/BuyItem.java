package bean;

import bean.product.ProductInfo;
import bean.product.ProductStyle;

public class BuyItem {
	/**所购买的商品**/	
	private ProductInfo product;
	/**购买的数量**/
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
		//根据产品样式数量是否相等判断购物项是否相等 
		if(product.getStyles().size()!=other.getProduct().getStyles().size())
			return false;
		if(product.getStyles().size()>0){
			ProductStyle style=product.getStyles().iterator().next();
			ProductStyle otherstyle=other.getProduct().getStyles().iterator().next();
			//根据购物的样式判断购物项是否相等
			if(!style.equals(otherstyle))
				return false;
		}
		return true;
	}
	
	

}
