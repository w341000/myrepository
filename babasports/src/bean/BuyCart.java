package bean;

import java.util.ArrayList;
import java.util.List;

import bean.book.OrderContactInfo;
import bean.book.OrderDeliverInfo;
import bean.book.PaymentWay;

public class BuyCart {
	private List<BuyItem> items=new ArrayList<BuyItem>();
	/**�ջ���������Ϣ**/
	private OrderDeliverInfo deliverInfo;
	/**��������ϵ��Ϣ**/
	private OrderContactInfo contactInfo;
	/**�ջ����붩�����Ƿ���ͬ**/
	private Boolean buyerIsrecipients;
	/**֧����ʽ**/
	private PaymentWay paymentWay;
	/**���ͷ�**/
	private float deliveFee=10f;
	/**����**/
	private String note;
	
	public String getNote() {
		return note;
	}

	public void setNote(String note) {
		this.note = note;
	}

	public float getDeliveFee() {
		return deliveFee;
	}

	public void setDeliveFee(float deliveFee) {
		this.deliveFee = deliveFee;
	}

	public PaymentWay getPaymentWay() {
		return paymentWay;
	}

	public void setPaymentWay(PaymentWay paymentWay) {
		this.paymentWay = paymentWay;
	}

	public Boolean isBuyerIsrecipients() {
		return buyerIsrecipients;
	}

	public void setBuyerIsrecipients(boolean buyerIsrecipients) {
		this.buyerIsrecipients = buyerIsrecipients;
	}

	public OrderDeliverInfo getDeliverInfo() {
		return deliverInfo;
	}

	public void setDeliverInfo(OrderDeliverInfo deliverInfo) {
		this.deliverInfo = deliverInfo;
	}

	public OrderContactInfo getContactInfo() {
		return contactInfo;
	}

	public void setContactInfo(OrderContactInfo contactInfo) {
		this.contactInfo = contactInfo;
	}

	public List<BuyItem> getItems() {
		return items;
	}

	public void setItems(List<BuyItem> items) {
		this.items = items;
	}

	/**
	 * ��ӹ�����
	 * @param item
	 */
	public void addItem(BuyItem item){
		if(!items.contains(item))
			items.add(item);
		else{
			int index=items.indexOf(item);
			BuyItem currentitem=items.get(index);
			currentitem.setAmount(currentitem.getAmount()+item.getAmount());
		}
	}
	
	/**
	 * ��չ�����
	 */
	public void removeAll(){
		items.clear();
	}
	
	/**
	 * ɾ��������
	 * @param item
	 */
	public void removeBuyItem(BuyItem item){
		if(items.contains(item)) items.remove(item);
	}
	/**
	 *���¹�������
	 * @param item
	 */
	public void updateAmount(BuyItem item){
		for(BuyItem bi:items){
			if(bi.equals(item)){
				bi.setAmount(item.getAmount());
				break;
			}
		}
	}
	
	
	/**
	 *�������¹�������
	 * @param item
	 */
	public void updateAmount(BuyItem... items){
		for(BuyItem bi:this.items){
			for(BuyItem item:items){
				if(bi.equals(item)){
					bi.setAmount(item.getAmount());
					break;
				}
			}
		}
	}
	
	/**
	 * ��ȡӦ���ܽ��
	 * @return
	 */
	public Float getTotalSellPrice(){
		float total=0f;
		for(BuyItem bi:this.items){
			total+=bi.getProduct().getSellprice()*bi.getAmount();
		}
		return total;
	}
	
	/**
	 * ��ȡ�г����ܽ��
	 * @return
	 */
	public Float getTotalMarketPrice(){
		float total=0f;
		for(BuyItem bi:this.items){
			total+=bi.getProduct().getMarketprice()*bi.getAmount();
		}
		return total;
	}
	
	
	
	/**
	 * ��ȡ��ʡ���ܽ��
	 * @return
	 */
	public Float getTotalSavedPrice(){
		
		return getTotalMarketPrice()-getTotalSellPrice();
	}
	/**
	 * ���㶩���ܽ��
	 * @return
	 */
	public float getOrderTotalPrice(){
		return getTotalSellPrice()+getDeliveFee();
	}
	
}
