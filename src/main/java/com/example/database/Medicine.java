package com.example.database;

import javafx.beans.property.SimpleStringProperty;

public class Medicine {

    private int MedicineID;
    private String BrandName;
    private String ScentficName;
    private String Unit;
    private String ExpDate;
    private int FreeQuantity;
    private double TaxPercent;
    private double PriceBeforeTax;
    private double PriceAfterTax;
    private String CategoryName;


    public Medicine(int medicineID, String brandName, String scentficName, String unit, String expDate, int freeQuantity, double taxPercent, double priceBeforeTax, double priceAfterTax, String categoryName) {
        MedicineID = medicineID;
        BrandName = brandName;
        ScentficName = scentficName;
        Unit = unit;
        ExpDate = expDate;
        FreeQuantity = freeQuantity;
        TaxPercent = taxPercent;
        PriceBeforeTax = priceBeforeTax;
        PriceAfterTax = priceAfterTax;
        CategoryName = categoryName;

    }

    public int getMedicineID() {
        return MedicineID;
    }

    public String getBrandName() {
        return BrandName;
    }

    public String getScentficName() {
        return ScentficName;
    }

    public String getUnit() {
        return Unit;
    }

    public String getExpDate() {
        return ExpDate;
    }

    public int getFreeQuantity() {
        return FreeQuantity;
    }

    public double getTaxPercent() {
        return TaxPercent;
    }

    public double getPriceBeforeTax() {
        return PriceBeforeTax;
    }

    public double getPriceAfterTax() {
        return PriceAfterTax;
    }

    public String getCategoryName() {
        return CategoryName;
    }

}
