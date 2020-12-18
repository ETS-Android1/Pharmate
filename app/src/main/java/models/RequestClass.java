package models;

public class RequestClass {

    private String medicinename, barcode, requestedBy;
    private Integer quantity;


    public RequestClass(String medicinename, String barcode, String requestedBy, Integer quantity) {
        this.medicinename = medicinename;
        this.barcode = barcode;
        this.requestedBy = requestedBy;
        this.quantity = quantity;
    }

    public String getMedicinename() {
        return medicinename;
    }

    public void setMedicinename(String medicinename) {
        this.medicinename = medicinename;
    }

    public String getBarcode() {
        return barcode;
    }

    public void setBarcode(String barcode) {
        this.barcode = barcode;
    }

    public String getRequestedBy() {
        return requestedBy;
    }

    public void setRequestedBy(String requestedBy) {
        this.requestedBy = requestedBy;
    }

    public Integer getQuantity() {
        return quantity;
    }

    public void setQuantity(Integer quantity) {
        this.quantity = quantity;
    }
}
