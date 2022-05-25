package dto;

public class OrderDetailDTO {
    private String oId;
    private String iId;
    private int qty;
    private double price;

    public OrderDetailDTO(String oId,  String iId, int qty, double price) {
        this.setoId(oId);
        this.setiId(iId);
        this.setQty(qty);
        this.setPrice(price);
    }

    public OrderDetailDTO() {
    }


    public String getoId() {
        return oId;
    }

    public void setoId(String oId) {
        this.oId = oId;
    }


    public String getiId() {
        return iId;
    }

    public void setiId(String iId) {
        this.iId = iId;
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
        return "orderDTO{" +
                "oId='" + oId + '\'' +
                ", iId='" + iId + '\'' +
                ", qty=" + qty +
                ", price=" + price +
                '}';
    }
}
