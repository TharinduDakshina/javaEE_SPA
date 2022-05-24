package dto;

public class CustomerDTO {
    private String cstId;
    private String cstName;
    private String cstAddress;
    private double cstSalary;

    public CustomerDTO(String cstId, String cstName, String cstAddress, double cstSalary) {
        this.setCstId(cstId);
        this.setCstName(cstName);
        this.setCstAddress(cstAddress);
        this.setCstSalary(cstSalary);
    }

    public CustomerDTO() {
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

    @Override
    public String toString() {
        return "CustomerDTO{" +
                "cstId='" + cstId + '\'' +
                ", cstName='" + cstName + '\'' +
                ", cstAddress='" + cstAddress + '\'' +
                ", cstSalary=" + cstSalary +
                '}';
    }
}
