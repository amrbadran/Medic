package com.example.database;

public class Orders {
    int ID;
    String Date;
    String Status;
    String PaymentMethod;
    int quantity;
    double cost;
    String MedName;
    String CustomerName;
    String DistrbuterName;

    public Orders(int ID, String date, String status, String paymentMethod, int quantity, double cost, String medName, String customerName, String distrbuterName) {
        this.ID = ID;
        Date = date;
        Status = status;
        PaymentMethod = paymentMethod;
        this.quantity = quantity;
        this.cost = cost;
        MedName = medName;
        CustomerName = customerName;
        DistrbuterName = distrbuterName;
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

    public String getPaymentMethod() {
        return PaymentMethod;
    }

    public int getQuantity() {
        return quantity;
    }

    public double getCost() {
        return cost;
    }

    public String getMedName() {
        return MedName;
    }

    public String getCustomerName() {
        return CustomerName;
    }

    public String getDistrbuterName() {
        return DistrbuterName;
    }
}
