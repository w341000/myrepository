package bean.book;

import java.util.Date;
import java.util.HashSet;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;
import org.hibernate.annotations.Cascade;

import bean.user.Buyer;

/**
 * ����ʵ��
 */
@Entity @Table(name="t_order")
@Cache(usage=CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class Order {
	/* ������ */
	@Id @Column(length=14)
	private String orderid;
	/* �����û� */
	@ManyToOne(cascade=CascadeType.REFRESH,optional=false)
	@JoinColumn(name="username")
	private Buyer buyer;
	/* ��������ʱ�� */
	@Temporal(TemporalType.TIMESTAMP) @Column(nullable=false)
	private Date createDate = new Date();
	/* ����״̬ */
	@Enumerated(EnumType.STRING) @Column(length=16,nullable=false)
	private OrderState state;
	/* ��Ʒ�ܽ�� */
	@Column(nullable=false)
	private Float productTotalPrice = 0f;
	/* ���ͷ� */
	@Column(nullable=false)
	private Float deliverFee = 0f;
	/* �����ܽ�� */
	@Column(nullable=false)
	private Float totalPrice= 0f;
	/* Ӧ����(ʵ����Ҫ֧���ķ���) */
	@Column(nullable=false)
	private Float payablefee = 0f;
	/* �˿͸��� */
	@Column(length=100)
	private String note;
	/* ֧����ʽ */
	@Enumerated(EnumType.STRING)@Column(length=20,nullable=false)
	private PaymentWay paymentWay;
	/* ֧��״̬ */
	@Column(nullable=false)
    private Boolean paymentstate = false;
    /* ����������Ϣ */
	@OneToOne @Cascade({org.hibernate.annotations.CascadeType.SAVE_UPDATE,org.hibernate.annotations.CascadeType.DELETE,org.hibernate.annotations.CascadeType.REMOVE,org.hibernate.annotations.CascadeType.MERGE})
	@JoinColumn(name="deliver_id")
	private OrderDeliverInfo orderDeliverInfo;
	/* ������������ϵ��Ϣ */
	@OneToOne(optional=false) @Cascade({org.hibernate.annotations.CascadeType.SAVE_UPDATE,org.hibernate.annotations.CascadeType.DELETE,org.hibernate.annotations.CascadeType.REMOVE,org.hibernate.annotations.CascadeType.MERGE})
	@JoinColumn(name="contact_id")
	private OrderContactInfo orderContactInfo;
	/* ������ */
	@OneToMany(mappedBy="order")@Cascade({org.hibernate.annotations.CascadeType.SAVE_UPDATE,org.hibernate.annotations.CascadeType.DELETE,org.hibernate.annotations.CascadeType.REMOVE,org.hibernate.annotations.CascadeType.MERGE})
	private Set<OrderItem> items = new HashSet<OrderItem>();
	/* �Զ������м������û�,���ֵΪnull,������δ������,����,���������� */
	@Column(length=20)
	private String lockuser;
	/*�ͷ�����*/
	@OneToMany(mappedBy="order") @Cascade({org.hibernate.annotations.CascadeType.DELETE})
	private Set<Message> msgs=new HashSet<Message>();
	
	public Order(){}
	public Order(String orderid) {
		this.orderid=orderid;
	}
	public Set<Message> getMsgs() {
		return msgs;
	}
	public void setMsgs(Set<Message> msgs) {
		this.msgs = msgs;
	}
	public String getLockuser() {
		return lockuser;
	}
	public void setLockuser(String lockuser) {
		this.lockuser = lockuser;
	}
	public String getOrderid() {
		return orderid;
	}
	public void setOrderid(String orderid) {
		this.orderid = orderid;
	}
	public Buyer getBuyer() {
		return buyer;
	}
	public void setBuyer(Buyer buyer) {
		this.buyer = buyer;
	}
	public Date getCreateDate() {
		return createDate;
	}
	public void setCreateDate(Date createDate) {
		this.createDate = createDate;
	}
	public OrderState getState() {
		return state;
	}
	public void setState(OrderState state) {
		this.state = state;
	}
	/**
	 * �����Ʒ�ϼ��ܼ�
	 * @return
	 */
	public Float getProductTotalPrice() {
		if(items!=null){
			float totalprice = 0;
			for(OrderItem item:items){
				totalprice=totalprice+item.getProductPrice()*item.getAmount();
			}
			return totalprice;
		}
		return productTotalPrice;
	}
	public void setProductTotalPrice(Float productTotalPrice) {
		this.productTotalPrice = productTotalPrice;
	}
	public Float getDeliverFee() {
		return deliverFee;
	}
	public void setDeliverFee(Float deliverFee) {
		this.deliverFee = deliverFee;
	}
	/**
	 * ��ö����ϼ��ܼ�,�������˷�
	 * @return
	 */
	public Float getTotalPrice() {
		return getProductTotalPrice()+getDeliverFee();
	}
	public void setTotalPrice(Float totalPrice) {
		this.totalPrice = totalPrice;
	}
	public Float getPayablefee() {
		return getTotalPrice();
	}
	public void setPayablefee(Float payablefee) {
		this.payablefee = payablefee;
	}
	public String getNote() {
		return note;
	}
	public void setNote(String note) {
		this.note = note;
	}
	public PaymentWay getPaymentWay() {
		return paymentWay;
	}
	public void setPaymentWay(PaymentWay paymentWay) {
		this.paymentWay = paymentWay;
	}
	public Boolean getPaymentstate() {
		return paymentstate;
	}
	public void setPaymentstate(Boolean paymentstate) {
		this.paymentstate = paymentstate;
	}
	public OrderDeliverInfo getOrderDeliverInfo() {
		return orderDeliverInfo;
	}
	public void setOrderDeliverInfo(OrderDeliverInfo orderDeliverInfo) {
		this.orderDeliverInfo = orderDeliverInfo;
	}
	public OrderContactInfo getOrderContactInfo() {
		return orderContactInfo;
	}
	public void setOrderContactInfo(OrderContactInfo orderContactInfo) {
		this.orderContactInfo = orderContactInfo;
	}
	public Set<OrderItem> getItems() {
		return items;
	}
	public void setItems(Set<OrderItem> items) {
		this.items = items;
	}
	/**
	 * ��Ӷ�����
	 * @param item ������
	 */
	public void addOrderItem(OrderItem item){
		this.items.add(item);	
		item.setOrder(this);
	}
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((orderid == null) ? 0 : orderid.hashCode());
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
		Order other = (Order) obj;
		if (orderid == null) {
			if (other.orderid != null)
				return false;
		} else if (!orderid.equals(other.orderid))
			return false;
		return true;
	}
	
	
}
