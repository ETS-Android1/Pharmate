package models;

public class MedicineClass {

    private String nameOfMedicine,barcodeNumber,donatedBy,donatedTo;
    private Integer quantity;

    public MedicineClass() {
    }

    public MedicineClass(String nameOfMedicine, String donatedBy, String donatedTo,Integer quantity,String barcodeNumber) {
        this.nameOfMedicine = nameOfMedicine;
        this.donatedBy = donatedBy;
        this.donatedTo = donatedTo;
        this.quantity = quantity;
        this.barcodeNumber = barcodeNumber;
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
}
