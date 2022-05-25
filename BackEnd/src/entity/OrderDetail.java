package entity;

public class OrderDetail {
    private String orderId;
    private String itemCode;
    private int qty;
    private double price;

    public OrderDetail(String orderId,String itemCode, int qty, double price) {
        this.setOrderId(orderId);
        this.setItemCode(itemCode);
        this.setQty(qty);
        this.setPrice(price);
    }

    public OrderDetail() {
    }

    public String getOrderId() {
        return orderId;
    }

    public void setOrderId(String orderId) {
        this.orderId = orderId;
    }


    public String getItemCode() {
        return itemCode;
    }

    public void setItemCode(String itemCode) {
        this.itemCode = itemCode;
    }

    public int getQty() {
        return qty;
    }

    public void setQty(int qty) {
        this.qty = qty;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    @Override
    public String toString() {
        return "OrderDetail{" +
                "orderId='" + orderId + '\'' +
                ", itemCode='" + itemCode + '\'' +
                ", qty=" + qty +
                ", price=" + price +
                '}';
    }
}
