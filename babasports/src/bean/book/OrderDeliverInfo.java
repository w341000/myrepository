package bean.book;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.OneToOne;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import bean.user.Gender;

/**
 * ������Ϣ
 * @author Administrator
 *
 */
@Entity@Cache(usage=CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class OrderDeliverInfo {
	@Id @GeneratedValue
	private Integer deliverid;
	/**�ջ�������**/
	@Column(length=8,nullable=false)
	private String recipients;
	/**�Ա�**/
	@Enumerated(EnumType.STRING) @Column(length=5,nullable=false)
	private Gender gender=Gender.MAN;
	/**��ַ**/
	@Column(length=40,nullable=false)
	private String address;
	/**����**/
	@Column(length=40)
	private String email;
	/**�ʱ�**/
	@Column(length=6)
	private String postalcode;
	/**�绰**/
	@Column(length=18)
	private String tel;
	/**�ֻ�**/
	@Column(length=11)
	private String mobile;
	/**�ͻ���ʽ**/
	@Enumerated(EnumType.STRING) @Column(length=23,nullable=false)
	private DeliverWay deliverWay;
	/**ʱ��Ҫ��**/
	@Column(length=30)
	private String requirement;
	/**��������**/
	@OneToOne(mappedBy="orderDeliverInfo",cascade=CascadeType.REFRESH)
	private Order order;
	
	public Order getOrder() {
		return order;
	}
	public void setOrder(Order order) {
		this.order = order;
	}
	public Integer getDeliverid() {
		return deliverid;
	}
	public void setDeliverid(Integer deliverid) {
		this.deliverid = deliverid;
	}
	public String getRequirement() {
		return requirement;
	}
	public void setRequirement(String requirement) {
		this.requirement = requirement;
	}
	public DeliverWay getDeliverWay() {
		return deliverWay;
	}
	public void setDeliverWay(DeliverWay deliverWay) {
		this.deliverWay = deliverWay;
	}
	public String getRecipients() {
		return recipients;
	}
	public void setRecipients(String recipients) {
		this.recipients = recipients;
	}
	public Gender getGender() {
		return gender;
	}
	public void setGender(Gender gender) {
		this.gender = gender;
	}
	public String getAddress() {
		return address;
	}
	public void setAddress(String address) {
		this.address = address;
	}
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	public String getPostalcode() {
		return postalcode;
	}
	public void setPostalcode(String postalcode) {
		this.postalcode = postalcode;
	}
	public String getTel() {
		return tel;
	}
	public void setTel(String tel) {
		this.tel = tel;
	}
	public String getMobile() {
		return mobile;
	}
	public void setMobile(String mobile) {
		this.mobile = mobile;
	}
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result
				+ ((deliverid == null) ? 0 : deliverid.hashCode());
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
		OrderDeliverInfo other = (OrderDeliverInfo) obj;
		if (deliverid == null) {
			if (other.deliverid != null)
				return false;
		} else if (!deliverid.equals(other.deliverid))
			return false;
		return true;
	}
	
	

}
