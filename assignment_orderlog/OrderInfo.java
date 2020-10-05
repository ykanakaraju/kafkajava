package assignment_orderlog;

public class OrderInfo {
	
	private int orderId;
	private String orderDate;
	private double orderAmount;
	private int storeId;
	private int zoneId; 
	  
	public OrderInfo(int orderId, String orderDate, double orderAmount, int storeId, int zoneId) {
		this.orderId = orderId;
		this.orderDate = orderDate;
		this.orderAmount = orderAmount;
		this.storeId = storeId;
		this.zoneId = zoneId;
	}
	
	public int getZoneId() {
        return zoneId;
    }
	
	public String getOrderInfo() {
        return orderId + "," + orderDate + "," + orderAmount + "," + storeId + "," + zoneId;
    }
}
