package models;

public class DonatedMedicines {
    String barcodeNumber, userID;
    Integer quantity;

    public DonatedMedicines() {
    }

    public DonatedMedicines(String barcodeNumber, String userID, Integer quantity) {
        this.barcodeNumber = barcodeNumber;
        this.userID = userID;
        this.quantity = quantity;
    }

    public String getBarcodeNumber() {
        return barcodeNumber;
    }

    public void setBarcodeNumber(String barcodeNumber) {
        this.barcodeNumber = barcodeNumber;
    }

    public String getUserID() {
        return userID;
    }

    public void setUserID(String userID) {
        this.userID = userID;
    }

    public Integer getQuantity() {
        return quantity;
    }

    public void setQuantity(Integer quantity) {
        this.quantity = quantity;
    }
}
