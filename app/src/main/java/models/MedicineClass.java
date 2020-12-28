package models;


public class MedicineClass {
    private String nameOfMedicine, barcodeNumber, donatedBy, donatedTo,expirationdate;
    private Integer quantity;

    public MedicineClass(){}


    public MedicineClass(String nameOfMedicine, String donatedBy, String donatedTo, Integer quantity, String barcodeNumber,String expirationdate) {
        this.nameOfMedicine = nameOfMedicine;
        this.donatedBy = donatedBy;
        this.donatedTo = donatedTo;
        this.quantity = quantity;
        this.barcodeNumber = barcodeNumber;
        this.expirationdate=expirationdate;
    }

    public String getNameOfMedicine() {
        return nameOfMedicine;
    }

    public void setNameOfMedicine(String nameOfMedicine) {
        this.nameOfMedicine = nameOfMedicine;
    }



    public String getDonatedBy() {
        return donatedBy;
    }

    public void setDonatedBy(String donatedBy) {
        this.donatedBy = donatedBy;
    }

    public String getDonatedTo() {
        return donatedTo;
    }

    public void setDonatedTo(String donatedTo) {
        this.donatedTo = donatedTo;
    }

    public Integer getQuantity() {
        return quantity;
    }

    public void setQuantity(Integer quantity) {
        this.quantity = quantity;
    }

    public String getBarcodeNumber() {
        return barcodeNumber;
    }

    public void setBarcodeNumber(String barcodeNumber) {
        this.barcodeNumber = barcodeNumber;
    }

    public String getExpirationdate() {
        return expirationdate;
    }

    public void setExpirationdate(String expirationdate) {
        this.expirationdate = expirationdate;
    }

}
