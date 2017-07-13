package bean;

import java.util.ArrayList;
import java.util.List;

import bean.book.OrderContactInfo;
import bean.book.OrderDeliverInfo;
import bean.book.PaymentWay;

public class BuyCart {
	private List<BuyItem> items=new ArrayList<BuyItem>();
	/**收货人配送信息**/
	private OrderDeliverInfo deliverInfo;
	/**订购者联系信息**/
	private OrderContactInfo contactInfo;
	/**收货人与订购者是否相同**/
	private Boolean buyerIsrecipients;
	/**支付方式**/
	private PaymentWay paymentWay;
	/**配送费**/
	private float deliveFee=10f;
	/**附言**/
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
	 * 添加购物项
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
	 * 清空购物项
	 */
	public void removeAll(){
		items.clear();
	}
	
	/**
	 * 删除购物项
	 * @param item
	 */
	public void removeBuyItem(BuyItem item){
		if(items.contains(item)) items.remove(item);
	}
	/**
	 *更新购买数量
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
	 *批量更新购买数量
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
	 * 获取应付总金额
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
	 * 获取市场价总金额
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
	 * 获取节省的总金额
	 * @return
	 */
	public Float getTotalSavedPrice(){
		
		return getTotalMarketPrice()-getTotalSellPrice();
	}
	/**
	 * 计算订单总金额
	 * @return
	 */
	public float getOrderTotalPrice(){
		return getTotalSellPrice()+getDeliveFee();
	}
	
}
