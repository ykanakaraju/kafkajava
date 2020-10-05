package com.tekcrux.kafka;

import java.time.LocalDateTime;

public class Invoice
{
    private int invoiceNo;
    private LocalDateTime date;
    private String customerName;
    private long invoiceAmount;
    private String cityCode;
    private long discount;
    private long total;

    public Invoice(int invoiceNo,String customerName,long invoiceAmount,String cityCode)
    {
        setInvoiceNo(invoiceNo);
        setCustomerName(customerName);
        setInvoiceAmount(invoiceAmount);
        setDate(LocalDateTime.now());
        setCityCode(cityCode);
    }

    public int getInvoiceNo() {
        return invoiceNo;
    }

    public void setInvoiceNo(int invoiceNo) {
        this.invoiceNo = invoiceNo;
    }

    public LocalDateTime getDate() {
        return date;
    }

    public void setDate(LocalDateTime date) {
        this.date = date;
    }

    public String getCustomerName() {
        return customerName;
    }

    public void setCustomerName(String customerName) {
        this.customerName = customerName;
    }

    public long getInvoiceAmount() {
        return invoiceAmount;
    }

    public void setInvoiceAmount(long invoiceAmount) {
        this.invoiceAmount = invoiceAmount;
        this.total = invoiceAmount - (invoiceAmount*discount)/100;
    }

    public long getDiscount() {
        return discount;
    }

    public void setDiscount(long discount) {
        this.discount = discount;
        this.total = invoiceAmount - (invoiceAmount*discount)/100;
    }

    public long getTotal() {
        return total;
    }

    public void setTotal(long total) {
        this.total = total;
    }

    public String getCityCode() {
        return cityCode;
    }

    public void setCityCode(String cityCode) {
        this.cityCode = cityCode;
    }
}
