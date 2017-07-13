package bean.user;

import java.util.Date;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;
import org.hibernate.annotations.Cascade;


/**
 * �û�ʵ��
 */
@Entity@Cache(usage=CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class Buyer {
	/**�û���**/
	@Id @Column(length=20)
	private String username;
	/**����**/
	@Column(length=32,nullable=false)
	private String password;
	/**��������**/
	@Column(length=40,nullable=false)
	private String email;
	/**��ʵ����**/
	@Column(length=10)
	private String realname;
	/**�Ա�**/
	//ö�����ͳ־û�ע��,����ʱ����ö�ٵ�����������,ORDINAL��ö�ٵ�ֵ������
	@Enumerated(EnumType.STRING) @Column(length=5,nullable=false)
	private Gender gender=Gender.MAN;
	/**��ϵ��ʽ**/
	@OneToOne(cascade=CascadeType.ALL) @Cascade({org.hibernate.annotations.CascadeType.ALL})
	@JoinColumn(name="contact_id")
	private ContactInfo contactInfo;
	/**�Ƿ�����**/
	@Column(nullable=false)
	private boolean visible=true;
	/**ע��ʱ��**/
	@Temporal(TemporalType.TIMESTAMP)@Column(nullable=false)
	private Date regTime=new Date();
	public String getUsername() {
		return username;
	}
	public void setUsername(String username) {
		this.username = username;
	}
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	
	public String getRealname() {
		return realname;
	}
	public void setRealname(String realname) {
		this.realname = realname;
	}
	public Gender getGender() {
		return gender;
	}
	public void setGender(Gender gender) {
		this.gender = gender;
	}
	public ContactInfo getContactInfo() {
		return contactInfo;
	}
	public void setContactInfo(ContactInfo contactInfo) {
		this.contactInfo = contactInfo;
	}
	public boolean isVisible() {
		return visible;
	}
	public void setVisible(boolean visible) {
		this.visible = visible;
	}
	public Date getRegTime() {
		return regTime;
	}
	public void setRegTime(Date regTime) {
		this.regTime = regTime;
	}
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result
				+ ((username == null) ? 0 : username.hashCode());
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
		Buyer other = (Buyer) obj;
		if (username == null) {
			if (other.username != null)
				return false;
		} else if (!username.equals(other.username))
			return false;
		return true;
	}


}
