package models;

public class RequestClass {

    private String medicineName, barcode;
    private Integer quantity;

    public RequestClass() {
    }


    public RequestClass(String medicineName, String barcode, Integer quantity) {
        this.medicineName = medicineName;
        this.barcode = barcode;
        this.quantity = quantity;
    }

    public String getMedicineName() {
        return medicineName;
    }

    public void setMedicineName(String medicineName) {
        this.medicineName = medicineName;
    }

    public String getBarcode() {
        return barcode;
    }

    public void setBarcode(String barcode) {
        this.barcode = barcode;
    }

    public Integer getQuantity() {
        return quantity;
    }

    public void setQuantity(Integer quantity) {
        this.quantity = quantity;
    }
}
