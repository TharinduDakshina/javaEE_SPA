package entity;

import java.util.Date;

public class Order {
    private String orderId;
    private String cId;
    private Date orderDate;
    private String time;
    private double cost;

    public Order(String orderId, String cId, Date orderDate, String time, double cost) {
        this.setOrderId(orderId);
        this.setcId(cId);
        this.setOrderDate(orderDate);
        this.setTime(time);
        this.setCost(cost);
    }

    public Order() {
    }


    public String getOrderId() {
        return orderId;
    }

    public void setOrderId(String orderId) {
        this.orderId = orderId;
    }

    public String getcId() {
        return cId;
    }

    public void setcId(String cId) {
        this.cId = cId;
    }

    public Date getOrderDate() {
        return orderDate;
    }

    public void setOrderDate(Date orderDate) {
        this.orderDate = orderDate;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public double getCost() {
        return cost;
    }

    public void setCost(double cost) {
        this.cost = cost;
    }

    @Override
    public String toString() {
        return "Order{" +
                "orderId='" + orderId + '\'' +
                ", cId='" + cId + '\'' +
                ", orderDate=" + orderDate +
                ", time='" + time + '\'' +
                ", cost=" + cost +
                '}';
    }
}
