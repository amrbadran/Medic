package com.example.database;

public class Customer {

    int ID;
    String Name;
    long Phone;
    String Email;
    String Address;
    int Discount;

    public Customer(int ID, String name, long phone, String email, String address, int discount) {
        this.ID = ID;
        Name = name;
        Phone = phone;
        Email = email;
        Address = address;
        Discount = discount;
    }

    public int getID() {
        return ID;
    }

    public String getName() {
        return Name;
    }

    public long getPhone() {
        return Phone;
    }

    public String getEmail() {
        return Email;
    }

    public String getAddress() {
        return Address;
    }

    public int getDiscount() {
        return Discount;
    }
}
