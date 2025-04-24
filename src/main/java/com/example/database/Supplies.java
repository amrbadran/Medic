package com.example.database;

public class Supplies {

    int ID;
    String Date;
    String Status;
    double Cost;
    int quantity;
    String MedName;
    String SupplierName;

    public Supplies(int ID, String date, String status, double cost, int quantity, String medName, String supplierName) {
        this.ID = ID;
        Date = date;
        Status = status;
        Cost = cost;
        this.quantity = quantity;
        MedName = medName;
        SupplierName = supplierName;
    }

    public int getID() {
        return ID;
    }

    public String getDate() {
        return Date;
    }

    public String getStatus() {
        return Status;
    }

    public double getCost() {
        return Cost;
    }

    public int getQuantity() {
        return quantity;
    }

    public String getMedName() {
        return MedName;
    }

    public String getSupplierName() {
        return SupplierName;
    }
}
