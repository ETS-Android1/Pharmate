package com.example.pharmate;

public class Medicine {


    private String name;
    private String amount;
    private String barcodeNumber;


    public Medicine(){}


    public Medicine(String name, String amount, String barcodeNumber) {
        this.name = name;
        this.amount = amount;
        this.barcodeNumber = barcodeNumber;
    }

    public String getName() {
        return name;
    }

    public String getAmount() {
        return amount;
    }

    public String getBarcodeNumber() {
        return barcodeNumber;
    }


}
