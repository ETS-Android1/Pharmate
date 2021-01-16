package models;

public class UserReceivedMedicineClass {
    private String nameOfMedicine, barcodeNumber, donatedBy, lastDonationTo, expirationdate;
    private Integer quantity;

    public UserReceivedMedicineClass() {
    }

    public UserReceivedMedicineClass(String nameOfMedicine, String barcodeNumber, String donatedBy, String lastDonationTo, String expirationdate, Integer quantity) {
        this.nameOfMedicine = nameOfMedicine;
        this.barcodeNumber = barcodeNumber;
        this.donatedBy = donatedBy;
        this.lastDonationTo = lastDonationTo;
        this.expirationdate = expirationdate;
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

    public String getDonatedBy() {
        return donatedBy;
    }

    public void setDonatedBy(String donatedBy) {
        this.donatedBy = donatedBy;
    }

    public String getlastDonationTo() {
        return lastDonationTo;
    }

    public void setlastDonationTo(String lastDonationTo) {
        this.lastDonationTo = lastDonationTo;
    }

    public String getExpirationdate() {
        return expirationdate;
    }

    public void setExpirationdate(String expirationdate) {
        this.expirationdate = expirationdate;
    }

    public Integer getQuantity() {
        return quantity;
    }

    public void setQuantity(Integer quantity) {
        this.quantity = quantity;
    }
}
