package bean.product;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;
import org.hibernate.annotations.GenericGenerator;

@Entity@Cache(usage=CacheConcurrencyStrategy.READ_WRITE)
public class Brand {
	@Id @Column(length=36) //指定主键id,使用hibernate的uuid方法进行生成
	@GenericGenerator(name="UUID",strategy="uuid")
	@GeneratedValue(generator="UUID")
	private String code;
	@Column(length=40,nullable=false)
	/**品牌名称**/
	private String name;
	@Column(nullable=false)
	/**是否可见**/
	private boolean visible=true;
	@Column(length=80)
	/**logo图片路径 如:/image/2008/12/12/xxx.gif **/
	private String logopath;
	
	public Brand() {}

	public Brand(String name, String logopath) {
		super();
		this.name = name;
		this.logopath = logopath;
	}
	public String getCode() {
		return code;
	}
	public void setCode(String code) {
		this.code = code;
	}
	
	public String getName() {
		return name;
	}
	
	public void setName(String name) {
		this.name = name;
	}
	
	public boolean isVisible() {
		return visible;
	}
	
	public void setVisible(boolean visible) {
		this.visible = visible;
	}
	
	public String getLogopath() {
		return logopath;
	}
	
	public void setLogopath(String logopath) {
		this.logopath = logopath;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((code == null) ? 0 : code.hashCode());
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
		Brand other = (Brand) obj;
		if (code == null) {
			if (other.code != null)
				return false;
		} else if (!code.equals(other.code))
			return false;
		return true;
	}
	
	
}
