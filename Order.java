package com.tekcrux.kafka;

public class Order {
	  private String orderId;
	  private String customerId;	  

	  public Order(String orderId, String customerId) {	    
	    this.orderId = orderId;
	    this.customerId = customerId;
	  }

	  public String getCustomerId() {
	    return customerId;
	  }

	  public String getOrderId() {
	    return orderId;
	  }

	  @Override
	  public String toString() {
	    return "Order{" +
	        "customerId='" + customerId + '\'' +
	        ", orderId=" + orderId +
	        '}';
	  }
}
