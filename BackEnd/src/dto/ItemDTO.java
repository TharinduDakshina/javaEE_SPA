package dto;

public class ItemDTO {
    private String itemId;
    private String itemName;
    private int itemQty;
    private double itemPrice;

    public ItemDTO(String itemId, String itemName, int itemQty, double itemPrice) {
        this.setItemId(itemId);
        this.setItemName(itemName);
        this.setItemQty(itemQty);
        this.setItemPrice(itemPrice);
    }

    public ItemDTO() {
    }


    public String getItemId() {
        return itemId;
    }

    public void setItemId(String itemId) {
        this.itemId = itemId;
    }

    public String getItemName() {
        return itemName;
    }

    public void setItemName(String itemName) {
        this.itemName = itemName;
    }

    public int getItemQty() {
        return itemQty;
    }

    public void setItemQty(int itemQty) {
        this.itemQty = itemQty;
    }

    public double getItemPrice() {
        return itemPrice;
    }

    public void setItemPrice(double itemPrice) {
        this.itemPrice = itemPrice;
    }

    @Override
    public String toString() {
        return "ItemDTO{" +
                "itemId='" + itemId + '\'' +
                ", itemName='" + itemName + '\'' +
                ", itemQty=" + itemQty +
                ", itemPrice=" + itemPrice +
                '}';
    }
}
