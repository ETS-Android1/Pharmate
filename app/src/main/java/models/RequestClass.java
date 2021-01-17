package models;

public class RequestClass {
    private String nameOfMedicine, barcodeNumber;
    private Integer quantity;

    public RequestClass() {
    }

    public RequestClass(String nameOfMedicine, String barcodeNumber, Integer quantity) {
        this.nameOfMedicine = nameOfMedicine;
        this.barcodeNumber = barcodeNumber;
        this.quantity = quantity;
    }

    public String getNameOfMedicine() {
        return nameOfMedicine;
    }

    public void setNameOfMedicine(String nameOfMedicine) {
        this.nameOfMedicine = nameOfMedicine;
    }

    public String getBarcodeNumber() {
        return barcodeNumber;
    }

    public void setBarcodeNumber(String barcodeNumber) {
        this.barcodeNumber = barcodeNumber;
    }

    public Integer getQuantity() {
        return quantity;
    }

    public void setQuantity(Integer quantity) {
        this.quantity = quantity;
    }
}
