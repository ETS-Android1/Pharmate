package models;

public class ReceivedMedicines {
    String userID, barcodeNumber;
    Integer quantity;

    public ReceivedMedicines() {
    }

    public ReceivedMedicines(String userID, String barcodeNumber, Integer quantity) {
        this.userID = userID;
        this.barcodeNumber = barcodeNumber;
        this.quantity = quantity;
    }

    public String getUserID() {
        return userID;
    }

    public void setUserID(String userID) {
        this.userID = userID;
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
