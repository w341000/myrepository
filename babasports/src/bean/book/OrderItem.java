package bean.book;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;
import org.hibernate.annotations.Cascade;

/**
 * 订单项
 */
@Entity@Cache(usage=CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class OrderItem {
	@Id @GeneratedValue
	private Integer itemid;
	/* 产品名称 */
	@Column(length=50,nullable=false)
	private String productName;
	/* 产品id */
	@Column(nullable=false)
	private Integer productid;
	/* 产品销售价 */
	@Column(nullable=false)
	private Float productPrice = 0f;
	/* 购买数量 */
	@Column(nullable=false)
	private Integer amount = 1;
	/* 产品样式 */
	@Column(length=30,nullable=false)
	private String styleName;	
	/* 产品样式ID */
	@Column(nullable=false)
	private Integer styleid;
	/* 所属订单 */
	@ManyToOne(optional=false) @Cascade({org.hibernate.annotations.CascadeType.SAVE_UPDATE,org.hibernate.annotations.CascadeType.REFRESH,org.hibernate.annotations.CascadeType.MERGE})
	@JoinColumn(name="order_id")
	private Order order;
	
	public OrderItem(){}
	
	public OrderItem(String productName, Integer productid, Float productPrice,
			Integer amount, String styleName, Integer styleid) {
		super();
		this.productName = productName;
		this.productid = productid;
		this.productPrice = productPrice;
		this.amount = amount;
		this.styleName = styleName;
		this.styleid = styleid;
	}
	public Integer getItemid() {
		return itemid;
	}
	public void setItemid(Integer itemid) {
		this.itemid = itemid;
	}
	public String getProductName() {
		return productName;
	}
	public void setProductName(String productName) {
		this.productName = productName;
	}
	public Integer getProductid() {
		return productid;
	}
	public void setProductid(Integer productid) {
		this.productid = productid;
	}
	public Float getProductPrice() {
		return productPrice;
	}
	public void setProductPrice(Float productPrice) {
		this.productPrice = productPrice;
	}
	public Integer getAmount() {
		return amount;
	}
	public void setAmount(Integer amount) {
		this.amount = amount;
	}
	public String getStyleName() {
		return styleName;
	}
	public void setStyleName(String styleName) {
		this.styleName = styleName;
	}
	public Integer getStyleid() {
		return styleid;
	}
	public void setStyleid(Integer styleid) {
		this.styleid = styleid;
	}
	public Order getOrder() {
		return order;
	}
	public void setOrder(Order order) {
		this.order = order;
	}
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((itemid == null) ? super.hashCode() : itemid.hashCode());
		return result;
	}
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		OrderItem other = (OrderItem) obj;
		if (itemid == null) {
			if (other.itemid != null)
				return false;
		} else if (!itemid.equals(other.itemid))
			return false;
		return true;
	}
	

	
}
