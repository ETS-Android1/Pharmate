package models;

public class OrganizationMedicineInventory {

    String organizationName, barcodeNumber;
    Integer quantity;

    public OrganizationMedicineInventory() {
    }

    public OrganizationMedicineInventory(String organizationName, String barcodeNumber, Integer quantity) {
        this.organizationName = organizationName;
        this.barcodeNumber = barcodeNumber;
        this.quantity = quantity;
    }

    public String getOrganizationName() {
        return organizationName;
    }

    public void setOrganizationName(String organizationName) {
        this.organizationName = organizationName;
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
