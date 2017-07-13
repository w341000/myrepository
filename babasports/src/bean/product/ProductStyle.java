package bean.product;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Transient;

@Entity
public class ProductStyle {
	@Id @GeneratedValue
	private Integer id;
	/**样式名称**/
	@Column(length=30,nullable=false)
	private String name;
	/**图片**/
	@Column(length=80,nullable=false)
	private String imagename;
	/**是否可见**/
	@Column(nullable=false)
	private boolean visible=true;
	/**多对一关系,级联刷新
	 * 不可选,即不能为空
	 */
	@ManyToOne(cascade=CascadeType.REFRESH,optional=false)
	@JoinColumn(name="productid")
	private ProductInfo product;
	
	public ProductStyle() {}
	public ProductStyle(String name, String imagename) {
		super();
		this.name = name;
		this.imagename = imagename;
	}
	public ProductStyle(Integer styleid) {
		this.id=styleid;
	}
	public ProductStyle(Integer styleid, String name, String imagename) {
		this.id=styleid;
		this.name=name;
		this.imagename=imagename;
	}
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getImagename() {
		return imagename;
	}
	public void setImagename(String imagename) {
		this.imagename = imagename;
	}
	public boolean isVisible() {
		return visible;
	}
	public void setVisible(boolean visible) {
		this.visible = visible;
	}
	public ProductInfo getProduct() {
		return product;
	}
	public void setProduct(ProductInfo product) {
		this.product = product;
	}
	@Transient
	public String getImageFullPath(){
		return "/images/product/"+this.getProduct().getType().getId()+"/"+this.getProduct().getId()+"/prototype/"+imagename;
	}
	@Transient //加上此注解不会被hibernate映射到数据库
	public String getImage140FullPath(){
		return "/images/product/"+this.getProduct().getType().getId()+"/"+this.getProduct().getId()+"/140x/"+imagename;
	}
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((id == null) ? 0 : id.hashCode());
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
		ProductStyle other = (ProductStyle) obj;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		return true;
	}
	
	
}
