package bean.product;

import java.util.Date;
import java.util.HashSet;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.Lob;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.OrderBy;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.persistence.Transient;

import org.apache.solr.client.solrj.beans.Field;
import org.hibernate.annotations.Cascade;
@Entity
public class ProductInfo {
	private Integer id;
	/** ���� **/
	private String code;
	/** ��Ʒ���� **/
	private String name;
	/** Ʒ�� **/
	private Brand brand;
	/** �ͺ� **/
	private String model;
	/** �׼�(�ɹ������ļ۸�) **/
	private Float baseprice;
	/** �г��� **/
	private Float marketprice;
	/** ���ۼ� **/
	private Float sellprice;
	/** ���� ��λ:�� **/
	private Integer weight;
	/** ��Ʒ��� **/
	private String description;
	/** ����˵�� **/
	private String buyexplain;
	/** �Ƿ�ɼ� **/
	private Boolean visible = true;
	/** ��Ʒ���� **/
	private ProductType type;
	/** �ϼ����� **/
	private Date createdate = new Date();
	/** ����ָ�� **/
	private Integer clickcount = 1;
	/** ������ **/
	private Integer sellcount = 0;
	/** �Ƿ��Ƽ� **/
	private Boolean commend = false;
	/** �Ա�Ҫ�� **/
	private Sex sexrequest = Sex.NONE;
	/** ��Ʒ��ʽ **/
	private Set<ProductStyle> styles = new HashSet<ProductStyle>();

	public ProductInfo(){}
	public ProductInfo(Integer productid) {
		this.id=productid;
	}

	public ProductInfo(Integer productId, String productName,
			String productModel, Float productMarketprice,
			Float productSellprice, Integer productWeight,
			String productDescription, String productBuyexplain) {
		this.id=productId;
		this.name=productName;
		this.model=productModel;
		this.marketprice=productMarketprice;
		this.sellprice=productSellprice;
		this.weight=productWeight;
		this.description=productDescription;
		this.buyexplain=productBuyexplain;
	}
	@Id@GeneratedValue
	public Integer getId() {
		return id;
	}
	
	public void setId(Integer id) {
		this.id = id;
	}
	@Column(length=30)
	public String getCode() {
		return code;
	}
	
	public void setCode(String code) {
		this.code = code;
	}
	@Column(length=50,nullable=false)
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	/**���һ��ϵ,��������
	 * ָ�����ɵ������Ϊbrandid
	 */
	@ManyToOne(cascade=CascadeType.REFRESH)
	@JoinColumn(name="brandid")
	public Brand getBrand() {
		return brand;
	}
	
	public void setBrand(Brand brand) {
		this.brand = brand;
	}
	@Column(length=20)
	public String getModel() {
		return model;
	}
	
	public void setModel(String model) {
		this.model = model;
	}
	@Column(nullable=false)
	public Float getBaseprice() {
		return baseprice;
	}
	
	public void setBaseprice(Float baseprice) {
		this.baseprice = baseprice;
	}
	@Column(nullable=false)
	public Float getMarketprice() {
		return marketprice;
	}
	
	public void setMarketprice(Float marketprice) {
		this.marketprice = marketprice;
	}
	@Column(nullable=false)
	public Float getSellprice() {
		return sellprice;
	}
	
	public void setSellprice(Float sellprice) {
		this.sellprice = sellprice;
	}
	/**
	 * ��Ʒ���� �ѿ�Ϊ��λ
	 */
	public Integer getWeight() {
		return weight;
	}
	
	public void setWeight(Integer weight) {
		this.weight = weight;
	}
	@Lob @Column(nullable=false)
	public String getDescription() {
		return description;
	}
	
	public void setDescription(String description) {
		this.description = description;
	}
	@Column(length=30)
	public String getBuyexplain() {
		return buyexplain;
	}
	
	public void setBuyexplain(String buyexplain) {
		this.buyexplain = buyexplain;
	}
	@Column(nullable=false)
	public Boolean getVisible() {
		return visible;
	}
	
	public void setVisible(Boolean visible) {
		this.visible = visible;
	}
	/**��Ʒ����,���һ��ϵ,��������
	 * ָ�����ɵ������Ϊtypeid
	 */
	@ManyToOne(cascade=CascadeType.REFRESH,optional=false)
	@JoinColumn(name="typeid")
	public ProductType getType() {
		return type;
	}
	
	public void setType(ProductType type) {
		this.type = type;
	}
	
	@Temporal(TemporalType.DATE)
	public Date getCreatedate() {
		return createdate;
	}
	
	public void setCreatedate(Date createdate) {
		this.createdate = createdate;
	}
	@Column(nullable=false)
	public Integer getClickcount() {
		return clickcount;
	}
	
	public void setClickcount(Integer clickcount) {
		this.clickcount = clickcount;
	}
	@Column(nullable=false)
	public Integer getSellcount() {
		return sellcount;
	}
	
	public void setSellcount(Integer sellcount) {
		this.sellcount = sellcount;
	}
	@Column(nullable=false)
	public Boolean getCommend() {
		return commend;
	}
	public void setCommend(Boolean commend) {
		this.commend = commend;
	}
	//ö�����ͳ־û�ע��,����ʱ����ö�ٵ�����������,ORDINAL��ö�ٵ�ֵ������
	@Enumerated(EnumType.STRING) @Column(length=5,nullable=false)
	public Sex getSexrequest() {
		return sexrequest;
	}

	public void setSexrequest(Sex sexrequest) {
		this.sexrequest = sexrequest;
	}
	/**��Ʒ��ʽһ�Զ��ϵ,�ɶ��һ����productά��
	 * ���������ɾ��
	 * **/
	@OneToMany(mappedBy="product")
	@Cascade({org.hibernate.annotations.CascadeType.REMOVE,org.hibernate.annotations.CascadeType.SAVE_UPDATE})
	@OrderBy("visible desc,id asc")
	public Set<ProductStyle> getStyles() {
		return styles;
	}

	public void setStyles(Set<ProductStyle> styles) {
		this.styles = styles;
	}
	/**
	 * ����ʽ��ӵ���Ʒ����ʽ������,�������ʽ������û�е������
	 * ��������ӽ�������ʽ���ò�ƷΪ�˲�Ʒ
	 * @param style ��Ҫ����ӵ���ʽ
	 */
	public void addProductStyle(ProductStyle style){
		if(!this.styles.contains(style)){
			styles.add(style);
			style.setProduct(this);
		}
	}
	/**
	 * ����ʽ������ɾ��ָ����ʽ
	 * @param style ��Ҫ��ɾ������ʽ
	 */
	public void removeProductStyle(ProductStyle style){
		if(this.styles.contains(style)){
			styles.remove(style);
			style.setProduct(null);
		}
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
		ProductInfo other = (ProductInfo) obj;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		return true;
	}

	@Override
	public String toString() {
		return "ProductInfo [id=" + id + ", code=" + code + ", name=" + name
				+ ", brand=" + brand + ", model=" + model + ", baseprice="
				+ baseprice + ", marketprice=" + marketprice + ", sellprice="
				+ sellprice + ", weight=" + weight + ", description="
				+ description + ", buyexplain=" + buyexplain + ", visible="
				+ visible + ", type=" + type + ", createdate=" + createdate
				+ ", clickcount=" + clickcount + ", sellcount=" + sellcount
				+ ", commend=" + commend + ", sexrequest=" + sexrequest
				+ ", styles=" + styles + "]";
	}
	@Transient
	public Float getSavedPrice(){
		return marketprice-sellprice;
	}
	
}
