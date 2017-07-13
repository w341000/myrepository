package bean.product;

import java.util.HashSet;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;


@Entity //ָ������һ��ʵ��bean
public class ProductType {
	
	/**���id **/
	@Id//ָ������id,���ɷ�ʽΪ�����Զ�ѡ��
	@GeneratedValue(strategy=GenerationType.AUTO)
	private Integer id;
	/**������� **/
	@Column(length=36,nullable=false)
	private String name;
	/**��ע��Ϣ **/
	@Column(length=200)
	private String note;
	/**�Ƿ�ɼ� **/
	@Column(nullable=false)
	private boolean visible=true;
	/**����� **/
	//һ�Զ��ϵ,�������º�ɾ��,mappedByע���ɶ��һ����parent����ά����ϵ
	@OneToMany(cascade={CascadeType.REFRESH,CascadeType.REMOVE},mappedBy="parent")
	private Set<ProductType> children=new HashSet<ProductType>();
	/** ���������**/
	//���һ��ϵ,��������,optionalѡ��ѡ���Ƿ������Ϊ��,Ĭ��Ϊtrue
	@ManyToOne(cascade={CascadeType.REFRESH})
	@JoinColumn(name="parentid")
	private ProductType parent;
	//һ�Զ��ϵ,����ɾ��,�ɶ��һ����type����ά����ϵ
	@OneToMany(mappedBy="type",cascade=CascadeType.REMOVE)
	private Set<ProductInfo> products=new HashSet<ProductInfo>();
	
	public ProductType(){}
	public ProductType(String name, String note) {
		super();
		this.name = name;
		this.note = note;
	}

	public ProductType(Integer typeId, String typeName) {
		this.id=typeId;
		this.name=typeName;
	}
	public Set<ProductType> getChildren() {
		return children;
	}
	
	public void setChildren(Set<ProductType> children) {
		this.children = children;
	}
	
	
	public ProductType getParent() {
		return parent;
	}
	public void setParent(ProductType parent) {
		this.parent = parent;
	}


	
	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	
	public String getNote() {
		return note;
	}

	
	public void setNote(String note) {
		this.note = note;
	}

	
	public boolean isVisible() {
		return visible;
	}

	
	public void setVisible(boolean visible) {
		this.visible = visible;
	}
	
	
	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}



	public Set<ProductInfo> getProducts() {
		return products;
	}

	public void setProducts(Set<ProductInfo> products) {
		this.products = products;
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
		ProductType other = (ProductType) obj;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		return true;
	}

	
}
