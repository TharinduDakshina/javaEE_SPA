package entity;

public class orderDetail {
    private String oId;
    private String cstId;
    private String iId;
    private int qty;
    private double price;

    public orderDetail(String oId, String cstId, String iId, int qty, double price) {
        this.setoId(oId);
        this.setCstId(cstId);
        this.setiId(iId);
        this.setQty(qty);
        this.setPrice(price);
    }

    public orderDetail() {
    }


    public String getoId() {
        return oId;
    }

    public void setoId(String oId) {
        this.oId = oId;
    }

    public String getCstId() {
        return cstId;
    }

    public void setCstId(String cstId) {
        this.cstId = cstId;
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
                ", cstId='" + cstId + '\'' +
                ", iId='" + iId + '\'' +
                ", qty=" + qty +
                ", price=" + price +
                '}';
    }
}
