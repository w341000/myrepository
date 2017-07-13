package bean.book;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
/**
 * 生成订单编号的实体bean
 */
@Entity
public class GeneratedOrderid {
	@Id @Column(length=5)
	private String id;
	@Column(nullable=false)
	private Integer orderid=0;
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public Integer getOrderid() {
		return orderid;
	}
	public void setOrderid(Integer orderid) {
		this.orderid = orderid;
	}

}
