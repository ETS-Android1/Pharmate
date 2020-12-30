package models;

public class ReceivedMedicines {
    String barcodeNumber;
    Integer quantity;

    public ReceivedMedicines() {
    }

    public ReceivedMedicines(String barcodeNumber, Integer quantity) {
        this.barcodeNumber = barcodeNumber;
        this.quantity = quantity;
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
