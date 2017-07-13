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
 * ������ϵ��Ϣ
 * @author Administrator
 */
@Entity@Cache(usage=CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class OrderContactInfo {
	@Id @GeneratedValue
	private Integer contactid;
	/**����������**/
	@Column(length=8,nullable=false)
	private String buyerName;
	/**�Ա�**/
	@Enumerated(EnumType.STRING) @Column(length=5,nullable=false)
	private Gender gender=Gender.MAN;
	/**��ַ**/
	@Column(length=40,nullable=false)
	private String address;
	/**�ʱ�**/
	@Column(length=6)
	private String postalcode;
	/**�绰**/
	@Column(length=18)
	private String tel;
	/**�ֻ�**/
	@Column(length=11)
	private String mobile;
	/**����**/
	@Column(length=40)
	private String email;
	/**��������**/
	@OneToOne(mappedBy="orderContactInfo",cascade=CascadeType.REFRESH)
	private Order order;
	
	public Integer getContactid() {
		return contactid;
	}
	public void setContactid(Integer contactid) {
		this.contactid = contactid;
	}
	public Order getOrder() {
		return order;
	}
	public void setOrder(Order order) {
		this.order = order;
	}
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	
	public String getBuyerName() {
		return buyerName;
	}
	public void setBuyerName(String buyerName) {
		this.buyerName = buyerName;
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
	public String getPostalcode() {
		return postalcode;
	}
	public void setPostalcode(String postalcode) {
		this.postalcode = postalcode;
	}
	public String getMobile() {
		return mobile;
	}
	public void setMobile(String mobile) {
		this.mobile = mobile;
	}
	public String getTel() {
		return tel;
	}
	public void setTel(String tel) {
		this.tel = tel;
	}
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result
				+ ((contactid == null) ? 0 : contactid.hashCode());
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
		OrderContactInfo other = (OrderContactInfo) obj;
		if (contactid == null) {
			if (other.contactid != null)
				return false;
		} else if (!contactid.equals(other.contactid))
			return false;
		return true;
	}

	
	
}
