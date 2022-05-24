package dto;

import java.util.Date;

public class OrderDTO {
    private String orderId;
    private Date date;
    private String cstId;
    private String cstName;
    private String cstAddress;
    private double cstSalary;
    private String iId;
    private String iName;
    private int iQty;
    private double iPrice;
    private int orderQty;
    private double total;
    private double subTotal;

    public OrderDTO(String orderId, Date date, String cstId, String cstName, String cstAddress, double cstSalary, String iId, String iName, int iQty, double iPrice, int orderQty, double total, double subTotal) {
        this.setOrderId(orderId);
        this.setDate(date);
        this.setCstId(cstId);
        this.setCstName(cstName);
        this.setCstAddress(cstAddress);
        this.setCstSalary(cstSalary);
        this.setiId(iId);
        this.setiName(iName);
        this.setiQty(iQty);
        this.setiPrice(iPrice);
        this.setOrderQty(orderQty);
        this.setTotal(total);
        this.setSubTotal(subTotal);
    }

    public OrderDTO() {
    }


    public String getOrderId() {
        return orderId;
    }

    public void setOrderId(String orderId) {
        this.orderId = orderId;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public String getCstId() {
        return cstId;
    }

    public void setCstId(String cstId) {
        this.cstId = cstId;
    }

    public String getCstName() {
        return cstName;
    }

    public void setCstName(String cstName) {
        this.cstName = cstName;
    }

    public String getCstAddress() {
        return cstAddress;
    }

    public void setCstAddress(String cstAddress) {
        this.cstAddress = cstAddress;
    }

    public double getCstSalary() {
        return cstSalary;
    }

    public void setCstSalary(double cstSalary) {
        this.cstSalary = cstSalary;
    }

    public String getiId() {
        return iId;
    }

    public void setiId(String iId) {
        this.iId = iId;
    }

    public String getiName() {
        return iName;
    }

    public void setiName(String iName) {
        this.iName = iName;
    }

    public int getiQty() {
        return iQty;
    }

    public void setiQty(int iQty) {
        this.iQty = iQty;
    }

    public double getiPrice() {
        return iPrice;
    }

    public void setiPrice(double iPrice) {
        this.iPrice = iPrice;
    }

    public int getOrderQty() {
        return orderQty;
    }

    public void setOrderQty(int orderQty) {
        this.orderQty = orderQty;
    }

    public double getTotal() {
        return total;
    }

    public void setTotal(double total) {
        this.total = total;
    }

    public double getSubTotal() {
        return subTotal;
    }

    public void setSubTotal(double subTotal) {
        this.subTotal = subTotal;
    }

    @Override
    public String toString() {
        return "orderDTO{" +
                "orderId='" + orderId + '\'' +
                ", date=" + date +
                ", cstId='" + cstId + '\'' +
                ", cstName='" + cstName + '\'' +
                ", cstAddress='" + cstAddress + '\'' +
                ", cstSalary=" + cstSalary +
                ", iId='" + iId + '\'' +
                ", iName='" + iName + '\'' +
                ", iQty=" + iQty +
                ", iPrice=" + iPrice +
                ", orderQty=" + orderQty +
                ", total=" + total +
                ", subTotal=" + subTotal +
                '}';
    }
}
